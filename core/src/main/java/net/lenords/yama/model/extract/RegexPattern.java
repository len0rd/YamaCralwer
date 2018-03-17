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
 *  Represents a regular expression pattern, with constant regular expressions as well
 *  as portions of the expression you want extracted into variables.
 *  <p>
 *    Variables you want from the pattern are returned upon a call to the {@link #execute(String)}
 *    method.
 *  <p>
 *    To build a regex to run, make properly ordered calls to the {@link #add(String)}
 *    and {@link #add(Extractor)} methods.
 *  <p>
 *    Example: Build a RegexPattern to match the following:
 *  <br>
 *    "&lt;h1&gt;~@fooBar@~&lt;/h1&gt;"
 *  <br>
 *    Where '~@fooBar@~' is the variable we want to extract and it has a regex like this: "[^&lt;&gt;]*".
 *
 *    In order to properly build a RegexPattern, you need to sequentially call the 'add' methods like
 *    so:
 *  <br>
 *    pattern.add("&lt;h1&gt;").add(new Extractor("fooBar", "[^&lt;&gt;]*")).add("&lt;/h1&gt;")
 *  <br>
 *    Once you've added all the pieces of your extractor in order, call {@link #build()} and then
 *    {@link #execute(String)} to get a map with fooBar and it's extracted value.
 *
 *
 * @author len0rd
 * @since 2018-03-16
 */
public class RegexPattern {
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


  /**
   * Add constant regex to the full pattern.
   *
   * @param regexConst  The regular expression to add.
   * @return            This, the current RegexPattern
   */
  public RegexPattern add(String regexConst) {
    extractors.add(new Extractor(null, regexConst).setType(ExtractorType.NO_GROUP));
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
            //if our results already contains a key of the same name,
            //only overwrite the value if this new value is not null/empty
            if (!StrUtils.isNullEmpty(result)) {
              results.put(keyName, result);
            }
          } else {
            results.put(keyName, result);
          }

        }
      }
    }

    return results;
  }

  public Map<String, String> buildAndExecute(String matchAgainst) {
    build();
    return buildAndExecute(matchAgainst);
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
