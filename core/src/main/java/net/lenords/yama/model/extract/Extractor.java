package net.lenords.yama.model.extract;

import net.lenords.yama.model.Tuple;

public class Extractor implements Tuple<String, String>  {

  private String name, regex;
  private ExtractorType type;

  public Extractor(String name, String regex) {
    this.name = name;
    this.regex = regex;
    this.type = ExtractorType.NORMAL;
  }

  @Override
  public String getKey() {
    return name;
  }

  @Override
  public void setKey(String key) {
    this.name = key;
  }

  @Override
  public String getValue() {
    return regex;
  }

  @Override
  public void setValue(String value) {
    this.regex = value;
  }

  @Override
  public boolean hasValue() {
    return regex != null && !regex.isEmpty();
  }

  public Extractor setType(ExtractorType type) {
    this.type = type;
    return this;
  }

  public ExtractorType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Extractor{" +
        "name='" + name + '\'' +
        ", regex='" + regex + '\'' +
        '}';
  }

  public enum ExtractorType {
    NORMAL,
    NO_GROUP
  }
}
