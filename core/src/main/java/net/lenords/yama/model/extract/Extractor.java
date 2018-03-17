package net.lenords.yama.model.extract;

import net.lenords.yama.model.Tuple;
import net.lenords.yama.util.lang.StrUtils;

public class Extractor implements Tuple<String, String>  {

  private String name, regex;
  private ExtractorType type;
  private boolean stripHtml, trim, convertEntities;

  public Extractor(String name, String regex) {
    this.name = name;
    this.regex = regex;
    this.stripHtml = false;
    this.trim = false;
    this.convertEntities = false;
    this.type = ExtractorType.NORMAL;
  }

  public Extractor(String name, CommonExtractors extractor) {
    this(name, extractor.getRegex());
  }

  /**
   * Turns on HTML Striping for the extracted value of this extractor
   * @return  This, the current extractor
   */
  public Extractor stripHtml() {
    this.stripHtml = true;
    return this;
  }

  /**
   * Turns on String trimming for the extracted value of this extractor
   * @return  This, the current extractor
   */
  public Extractor trim() {
    this.trim = false;
    return this;
  }

  /**
   * Turns on HTML Entity conversion for the extracted value of this extractor
   * @return  This, the current extractor
   */
  public Extractor convertEntities() {
    this.convertEntities = true;
    return this;
  }

  public String runTidyers(String extractedValue) {
    extractedValue = stripHtml ? StrUtils.stripHtml(extractedValue) : extractedValue;
    extractedValue = trim ? StrUtils.trimToNull(extractedValue) : extractedValue;
    //TODO:Convert entities

    return extractedValue;
  }

  public void setStripHtml(boolean stripHtml) {
    this.stripHtml = stripHtml;
  }

  public void setTrim(boolean trim) {
    this.trim = trim;
  }

  public void setConvertEntities(boolean convertEntities) {
    this.convertEntities = convertEntities;
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

  @Override
  public String toString() {
    return "Extractor{" +
        "name='" + name + '\'' +
        ", regex='" + regex + '\'' +
        '}';
  }


  /**
   * For internal use by {@link RegexPattern}
   * @param type The type of this particular pattern
   * @return  This, the current extractor
   */
  Extractor setType(ExtractorType type) {
    this.type = type;
    return this;
  }

  /**
   * For internal use by {@link RegexPattern}
   * @return The current type of this extractor
   */
  ExtractorType getType() {
    return type;
  }

  /**
   * For internal {@link RegexPattern} usage
   */
  enum ExtractorType {
    NORMAL,
    NO_GROUP
  }
}
