package net.lenords.yama.util.datamanager.model.conf;

public class SqlConnectionConfig extends ConnectionConfig {
  private String database;
  private Boolean useSSL;

  public SqlConnectionConfig(String host, String user, String pass, int port, boolean enabled) {
    super(host, user, pass, port, enabled);
    this.useSSL = null;
  }

  public SqlConnectionConfig(String host, String user, String pass, int port, boolean enabled,
      String database, Boolean useSSL) {
    super(host, user, pass, port, enabled);
    this.database = database;
    this.useSSL = useSSL;
  }

  public SqlConnectionConfig(String host, String user, String pass, int port, boolean enabled,
      Boolean useSSL) {
    super(host, user, pass, port, enabled);
    this.useSSL = useSSL;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public Boolean getUseSSL() {
    return useSSL;
  }

  public void setUseSSL(Boolean useSSL) {
    this.useSSL = useSSL;
  }

  public boolean hasDatabase() {
    return database != null && !database.isEmpty();
  }

  public String getFullHost() {
    String fullName = super.getHost();
    if (super.getPort() != -1) {
      fullName += ":" + getPort();
    }

    if (hasDatabase()) {
      fullName += "/" + database;
    }

    if (useSSL != null) {
      fullName += "?useSSL=" + String.valueOf(useSSL);
    }

    return fullName;
  }

  @Override
  public String toString() {
    return "MySqlConnectionConfig{" +
        "host='" + getHost() + '\'' +
        ", user='" + getUser() + '\'' +
        ", pass='" + getPass() + '\'' +
        ", port=" + getPort() +
        ", database='" + database + '\'' +
        ", useSSL=" + useSSL +
        '}';
  }
}