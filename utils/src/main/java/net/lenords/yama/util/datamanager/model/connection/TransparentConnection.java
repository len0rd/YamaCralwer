package net.lenords.yama.util.datamanager.model.connection;

import java.sql.Connection;
import java.sql.SQLException;
import net.lenords.yama.util.datamanager.model.conf.ConnectionConfig;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Holds the Apache DatabaseConnectionPool (dbcp) for the given connection. Also holds an easily
 * accessible {@link ConnectionConfig} object which holds the basic details of the current connection.
 * <p>
 *   Originally a simple {@link Connection} was used, however this proved to be an issue when the
 *   Connection was required for extended periods (ie: days). The dbcp solves this issue by closing
 *   and reopening connections when necessary.
 *
 * @author tyler miller
 * @since 2017-12-08
 */
public interface TransparentConnection {

  Connection getConn() throws SQLException;

  void close() throws SQLException;

  BasicDataSource getDataSource();

  ConnectionConfig getConnectionConf();
}
