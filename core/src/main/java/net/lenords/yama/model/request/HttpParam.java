package net.lenords.yama.model.request;

import java.util.Objects;
import net.lenords.yama.model.Tuple;

public class HttpParam implements Tuple<String, String>{
  private String key, value;


  public HttpParam(String key, String value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean hasValue() {
    return value != null && !value.isEmpty();
  }

  @Override
  public String toString() {
    return key + "=" + value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HttpParam httpParam = (HttpParam) o;
    return Objects.equals(key, httpParam.key) &&
        Objects.equals(value, httpParam.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }
}
