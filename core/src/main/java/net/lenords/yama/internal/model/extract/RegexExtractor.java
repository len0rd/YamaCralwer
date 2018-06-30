package net.lenords.yama.internal.model.extract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lenords.yama.api.lang.StrUtils;
import net.lenords.yama.internal.model.Page;
import net.lenords.yama.internal.model.Tuple;
import org.apache.commons.text.StringEscapeUtils;

public class RegexExtractor implements Tuple<String, String> {

  private String name, regex;
  private ExtractorType type;
  private boolean stripHtml, trim, convertEntities, tidyBeforeSubExtraction;
  private List<RegexPattern> subExtractors;

  public RegexExtractor(String name, String regex) {
    this.name = name;
    this.regex = regex;
    this.stripHtml = false;
    this.trim = false;
    this.convertEntities = false;
    this.tidyBeforeSubExtraction = false;
    this.type = ExtractorType.NORMAL;
    this.subExtractors = new ArrayList<>();
  }

  public RegexExtractor(String name, CommonRegex extractor) {
    this(name, extractor.getRegex());
  }

  /**
   * Turns on HTML Striping for the extracted value of this extractor
   *
   * @return This, the current extractor
   */
  public RegexExtractor stripHtml() {
    this.stripHtml = true;
    return this;
  }

  /**
   * Turns on String trimming for the extracted value of this extractor
   *
   * @return This, the current extractor
   */
  public RegexExtractor trim() {
    this.trim = true;
    return this;
  }

  /**
   * Turns on HTML Entity conversion for the extracted value of this extractor
   *
   * @return This, the current extractor
   */
  public RegexExtractor convertEntities() {
    this.convertEntities = true;
    return this;
  }

  /**
   * Add a sub-extraction pattern to this extractor.
   *
   * <p>Sub-extractor patterns are RegexPatterns that are applied to the extracted value of this
   * extractor. They can be applied before or after tidying the value. For each sub-extractor, it's
   * latest, non-null match will be returned by the {@link #runTidiersAndSubextractors(String)}
   * method. These are passed up to the top-level Pattern, which will return an {@link
   * ExtractionResult}. This result will contain each time the base extracted value (this) was
   * successfully extracted, with the associated, matched sub-extractors.
   *
   * <p>Functionally, sub-extractors are identical to Screen-scrapers sub-extractor patterns. The
   * main difference is Yama's sub-extractors have infinite depth (ie: you can have a sub-extractor
   * of a sub-extractor of a sub-extractor, etc), while Screen-scraper only allows a depth of 1,
   * further depth has to be explicitly scripted.
   *
   * <p>Identical to screen-scraper sub-extractors (as well as {@link
   * Page} level extractors), order matters. If there are multiple
   * sub-extractors that extract for the same key, the value returned will be the latest match.
   *
   * @see #tidyBeforeSubExtraction()
   * @param pattern The pattern to add as a sub-extractor to this extraction token
   * @return This, the current extractor
   */
  public RegexExtractor addSubExtractor(RegexPattern pattern) {
    subExtractors.add(pattern);
    return this;
  }

  public RegexExtractor addSubExtractors(RegexPattern... patterns) {
    subExtractors.addAll(Arrays.asList(patterns));
    return this;
  }

  /**
   * Runs tidiers before sub-extractors are applied to extraction result Default is false,
   * indicating that tidying of the value is run after applying sub-extractors
   *
   * @return This, the current extractor
   */
  public RegexExtractor tidyBeforeSubExtraction() {
    this.tidyBeforeSubExtraction = true;
    return this;
  }

  public String runTidiers(String extractedValue) {
    extractedValue =
        convertEntities ? StringEscapeUtils.unescapeHtml4(extractedValue) : extractedValue;
    extractedValue = stripHtml ? StrUtils.stripHtml(extractedValue) : extractedValue;
    extractedValue = trim ? StrUtils.trimToNull(extractedValue) : extractedValue;

    return extractedValue;
  }

  public Map<String, String> runTidiersAndSubextractors(String extractedValue) {
    Map<String, String> subExtracted = new HashMap<>();
    extractedValue = tidyBeforeSubExtraction ? runTidiers(extractedValue) : extractedValue;

    if (!StrUtils.isNullEmpty(extractedValue)) {
      // Sub-Extractors which extract the same key will overwrite in pattern order.
      for (RegexPattern subExtractor : subExtractors) {
        subExtracted.putAll(subExtractor.buildAndExecute(extractedValue).getLatest());
      }

      extractedValue = tidyBeforeSubExtraction ? extractedValue : runTidiers(extractedValue);
      if (!StrUtils.isNullEmpty(extractedValue)) {
        subExtracted.put(name, extractedValue);
      }
    }

    return subExtracted;
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

  public List<RegexPattern> getSubExtractors() {
    return subExtractors;
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
    return "Extractor{" + "name='" + name + '\'' + ", regex='" + regex + '\'' + '}';
  }

  /**
   * For internal use by {@link RegexPattern}
   *
   * @param type The type of this particular pattern
   * @return This, the current extractor
   */
  RegexExtractor setType(ExtractorType type) {
    this.type = type;
    return this;
  }

  /**
   * For internal use by {@link RegexPattern}
   *
   * @return The current type of this extractor
   */
  ExtractorType getType() {
    return type;
  }

  /** For internal {@link RegexPattern} usage */
  enum ExtractorType {
    NORMAL,
    NO_GROUP
  }
}
