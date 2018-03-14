package net.lenords.yama.util.datamanager.model;

public class ForeignKey {
  private String databaseName, tableName;
  private String fkColumn, debugName;

  public ForeignKey(String schemaName, String tableName, String fkColumn, String debugName) {
    this.databaseName = schemaName;
    this.tableName = tableName;
    this.fkColumn = fkColumn;
    this.debugName = debugName;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public String getTableName() {
    return tableName;
  }

  public String getFkColumn() {
    return fkColumn;
  }

  public String getDebugName() {
    return debugName;
  }

  @Override
  public String toString() {
    return "ForeignKey{" +
        "databaseName='" + databaseName + '\'' +
        ", tableName='" + tableName + '\'' +
        ", fkColumn='" + fkColumn + '\'' +
        ", debugName='" + debugName + '\'' +
        '}';
  }
}
