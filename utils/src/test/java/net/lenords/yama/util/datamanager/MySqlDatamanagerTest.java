package net.lenords.yama.util.datamanager;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import net.lenords.yama.util.datamanager.model.conf.SqlConnectionConfig;
import org.junit.jupiter.api.Test;

public class MySqlDatamanagerTest {

  @Test
  void derp() throws Exception {
    SqlConnectionConfig conf = new SqlConnectionConfig("localhost", "tyler",
        "dz77GAi7980", 3306, true, "re_stat", false);

    MySqlDatamanager dm = new MySqlDatamanager(conf);

    DatabaseMetaData dbmd = dm.getConnection().getMetaData();

    //ResultSet tablesRs = dbmd.getTables(null, null, "export_total", new String[]{"TABLE"});
    //ResultSet tablesRs = dbmd.getPrimaryKeys("re_fsbo_front", null, "re_fsbo");
    ResultSet tablesRs = dbmd.getIndexInfo(null, null, "export_total", true, false);
    //ResultSet tablesRs = dbmd.getExportedKeys(null, null, "export_total");

    if (tablesRs.first()) {
      final int columnCount = tablesRs.getMetaData().getColumnCount();
      for (int i = 1; i <= columnCount; i++) {
        System.out.print(tablesRs.getMetaData().getColumnName(i) + ", ");
      }
      System.out.println("\n");
      do {
        for (int i = 1; i <= columnCount; i++) {
          System.out.print(tablesRs.getObject(i) + ", ");
        }
        System.out.println("\n");
      } while (tablesRs.next());
    }

  }

}
