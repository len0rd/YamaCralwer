package net.lenords.yama.util.datamanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author len0rd
 * @since 2018-02-24
 */
public class Schema {
  private String tableName;
  private List<String> columns;

  public Schema(String tableName) {
    this.tableName = tableName;
    this.columns = new ArrayList<>();
  }

  public Schema(String tableName, List<String> columns) {
    this.tableName = tableName;
    this.columns = columns;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public List<String> getColumns() {
    return columns;
  }

  public void setColumns(List<String> columns) {
    this.columns = columns;
  }

  public void addColumn(String column) {
    this.columns.add(column);
  }

  public boolean hasColumn(String columnName) {
    return columnName.contains(columnName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tableName);
  }
}
