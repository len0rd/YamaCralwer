package net.lenords.yama.model.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.lenords.yama.model.extract.Extractor.ExtractorType;
import net.lenords.yama.util.lang.StrUtils;


/**
 *
 * ATTN::At present there is no support for normal sub-groupings. If you want to use groupings, but
 * don't want them captured, you need to manually add the non-capturing group specifier.
 * ie: if you only want to capture the outer group of: "(this(is a) regex)" then you would need
 * to add it to this pattern as: "(this(?:is a) regex)"
 * OTHERWISE, the pattern matcher will likely match values incorrectly
 * @author len0rd
 * @since 2018-03-16
 */
public class RegexPattern {
  private static final String CONST_REG_NAME = "@@CONST_REGEX_APPEND";
  private List<Extractor> extractors;
  private List<String> extractorNamesToRetrieve;
  private String name;
  private String fullRegex;


  /**
   *
   * @param name  Name of the Pattern
   */
  public RegexPattern(String name) {
    this.name = name;
    this.extractors = new ArrayList<>();
    this.fullRegex = "";
  }

  public RegexPattern add(String regexConst) {
    extractors.add(new Extractor(CONST_REG_NAME, regexConst).setType(ExtractorType.NO_GROUP));
    return this;
  }

  public RegexPattern add(Extractor regexVar) {
    extractors.add(regexVar);
    return this;
  }

  public List<Extractor> getExtractors() {
    return extractors;
  }

  public String build() {
    StringBuilder regex = new StringBuilder();
    extractorNamesToRetrieve = new ArrayList<>();

    final int extractorsSize = extractors.size();
    for (int i = 0; i < extractorsSize; i++) {
      Extractor extractor = extractors.get(i);

      if (extractor.getType() == ExtractorType.NO_GROUP) {
        //then we dont need to make a special group for this
        regex.append(extractor.getValue());
      } else {
        //then this extractor has a group we want to match
        String extractorName = "n" + i;
        regex.append("(?<").append(extractorName).append('>').append(extractor.getValue()).append(')');
        extractorNamesToRetrieve.add(extractorName);
      }

    }

    this.fullRegex = regex.toString();
    return fullRegex;
  }

  public Map<String, String> execute(String matchAgainst) {
    Map<String, String>  results = new HashMap<>();

    if (fullRegex != null && !fullRegex.isEmpty()) {
      Pattern pattern = Pattern.compile(fullRegex);
      Matcher matcher = pattern.matcher(matchAgainst);
      if (matcher.find()) {
        for (String name : extractorNamesToRetrieve) {
          String result = matcher.group(name);
          int extractorIndex = Integer.valueOf(name.substring(1, name.length()));
          String keyName = extractors.get(extractorIndex).getKey();

          if (results.containsKey(keyName)) {
            if (!StrUtils.isNullEmpty(result)) {
              results.put(keyName, result);
            }
          } else {
            results.put(keyName, result);
          }

        }
      } else {
        System.out.println("Matcher failed");
      }
    }

    return results;
  }

  public List<String> getExtractorNamesToRetrieve() {
    return extractorNamesToRetrieve;
  }

  public String getGeneratedRegex() {
    return fullRegex;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
