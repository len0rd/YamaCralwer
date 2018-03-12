package net.lenords.yama.util.datamanager;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lenords.yama.util.datamanager.model.Schema;
import net.lenords.yama.util.datamanager.model.conf.SqlConnectionConfig;
import net.lenords.yama.util.datamanager.model.connection.TransparentConnection;
import net.lenords.yama.util.datamanager.model.connection.TransparentMySqlConnection;

public class MySqlDatamanager {
  private static final String DEFAULT_MYSQL_CONF_LOCATION = "conf/mysql_connection.json";
  private TransparentConnection conn;
  private Map<String, Schema> schemas;

  public MySqlDatamanager(SqlConnectionConfig config) {
    this.conn = new TransparentMySqlConnection(config);
    buildSchemas();
  }

  public MySqlDatamanager(String host, String user, String pass, String database, int port) {
    this(new SqlConnectionConfig(host, user, pass, port, true, database, false));
  }

  public MySqlDatamanager(String jsonConfigLocation) {
    Gson gson = new Gson();
    System.out.println("Getting configuration");

    SqlConnectionConfig mysqlConn = null;
    try  {
      mysqlConn = gson.fromJson(new FileReader(getAbsolutePath() + jsonConfigLocation), SqlConnectionConfig.class);

    } catch (FileNotFoundException e) {
      System.out.println("ERR:: Failed to find mysql config. Any query attempt will fail");
      e.printStackTrace();
      return;
    } catch (URISyntaxException e) {
      System.out.println("ERR:: Failed to load mysql conf, something wrong with pathfinding.");
      e.printStackTrace();
      return;
    }
    System.out.println("Connecting");
    this.conn = new TransparentMySqlConnection(mysqlConn);
    System.out.println("Building Schemas");
    buildSchemas();
    System.out.println("Ready");
  }

  public MySqlDatamanager() {
    this(DEFAULT_MYSQL_CONF_LOCATION);
  }

  public Connection getConnection() {
    try {
      return conn.getConn();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void insert(String table, Map<String, Object> data) {
    //check for valid table
    table = table.toLowerCase();
    if (!schemas.containsKey(table)) {
      System.out.println("Trying to insert into a table that doesn't seem to exist in this connection!");
      return;
    }

    //build a query that can be used in a PreparedStatement
    Schema schemaToInsertOn = schemas.get(table);
    StringBuilder insertQuery = new StringBuilder("INSERT INTO ").append(table).append("(");
    List<String> columnWriteOrder = new ArrayList<>();
    for (String key : data.keySet()) {
      if (schemaToInsertOn.hasColumn(key.toLowerCase())) {
        columnWriteOrder.add(key);
        insertQuery.append(key).append(", ");
      }
    }
    //remove the last comma
    if (insertQuery.charAt(insertQuery.length()-2) == ',') {
      insertQuery.delete(insertQuery.length()-2, insertQuery.length());
    }
    insertQuery.append(") VALUES(");
    for (int i = 0; i < columnWriteOrder.size() - 1; i++) {
      insertQuery.append("?, ");
    }
    insertQuery.append("?)");

    //Now add the values to the query with a PreparedStatement
    try (PreparedStatement insert = conn.getConn().prepareStatement(insertQuery.toString())) {
      for (int i = 0; i < columnWriteOrder.size(); i++) {
        //setObject will figure out the proper type for stuff.
        insert.setObject(i+1, data.get(columnWriteOrder.get(i)));
      }
      insert.execute();
    } catch (SQLException e) {
      System.out.println(insertQuery.toString() + "\n\n");
      e.printStackTrace();
    }

  }

  private void buildSchemas() {
    schemas = new HashMap<>();
    SqlConnectionConfig conf = (SqlConnectionConfig) conn.getConnectionConf();
    String getTableNames;
    if (conf.hasDatabase()) {
      getTableNames = "SELECT table_name FROM information_schema.tables WHERE table_schema=?;";
    } else { //avoid trying to build all the info on defaul mysql tables
      getTableNames = "SELECT table_name FROM information_schema.tables "
          + "WHERE table_schema != 'sys' AND table_schema != 'performance_schema' "
          + "AND table_schema != 'information_schema' AND table_schema != 'mysql'";
    }


    //Initialize all the Schema objects by getting the table names for the connected database
    //if no database was specifiecd, then we'll get all the table names in the connection
    try (PreparedStatement getNames = conn.getConn().prepareStatement(getTableNames)) {
      if (conf.hasDatabase()) {
        getNames.setString(1, conf.getDatabase());
      }

      try (ResultSet rs = getNames.executeQuery()) {
        while (rs.next()) {
          schemas.put(rs.getString(1), new Schema(rs.getString(1)));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    //now get information on the columns for all the tables we're building schemas for
    if (!schemas.isEmpty()) {
      for (String name : schemas.keySet()) {
        Schema currentSchema = schemas.get(name);
        String query = "SELECT * FROM `" + name + "` LIMIT 1";
        try (PreparedStatement tableStruct = conn.getConn().prepareStatement(query)) {
          try (ResultSet rs = tableStruct.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            final int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
              currentSchema.addColumn(rsmd.getColumnName(i).toLowerCase());
            }
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

  }

  private String getAbsolutePath() throws URISyntaxException {
    String path = getAbsoluteJarFilePath();
    path = path.substring(0, path.lastIndexOf(File.separator) + 1);
    return path;
  }

  private String getAbsoluteJarFilePath() throws URISyntaxException {
    return MySqlDatamanager.class.getProtectionDomain().getCodeSource().getLocation()
        .toURI()
        .getPath();
  }

}
