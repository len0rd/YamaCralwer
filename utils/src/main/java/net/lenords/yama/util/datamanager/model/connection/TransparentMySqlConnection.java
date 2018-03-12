package net.lenords.yama.util.datamanager.model.connection;

import java.sql.Connection;
import java.sql.SQLException;
import net.lenords.yama.util.datamanager.model.conf.ConnectionConfig;
import net.lenords.yama.util.datamanager.model.conf.SqlConnectionConfig;
import org.apache.commons.dbcp2.BasicDataSource;

public class TransparentMySqlConnection implements TransparentConnection {
  private BasicDataSource dataSource;
  private ConnectionConfig connConf;

  protected TransparentMySqlConnection(){
    this.dataSource = null;
  }

  public TransparentMySqlConnection(SqlConnectionConfig mysqlConn) {
    this();
    this.connConf = mysqlConn;
    createConnection(mysqlConn);
  }

  protected void createConnection(SqlConnectionConfig mysqlConn) {

    if (dataSource != null) {
      try {
        this.close();
      } catch (SQLException ignored) {}
    }

    this.dataSource = new BasicDataSource();
    this.dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    this.dataSource.setUrl("jdbc:mysql://" + mysqlConn.getFullHost());
    this.dataSource.setUsername(mysqlConn.getUser());
    this.dataSource.setPassword(mysqlConn.getPass());

    //datasource config:
    this.dataSource.setMaxOpenPreparedStatements(200);
    this.dataSource.setMaxIdle(20);
    this.dataSource.setMinIdle(2);
    this.dataSource.setMaxTotal(100);
    this.dataSource.setRemoveAbandonedTimeout(60);
    this.dataSource.setRemoveAbandonedOnBorrow(true);
    this.dataSource.setRemoveAbandonedOnMaintenance(true);
  }

  public Connection getConn() throws SQLException {
    return dataSource.getConnection();
  }

  public void close() throws SQLException {
    if (dataSource != null && !dataSource.isClosed()) {
      dataSource.close();
    }
  }

  public BasicDataSource getDataSource() {
    return dataSource;
  }

  public ConnectionConfig getConnectionConf() {
    return connConf;
  }
}
