package net.lenords.yama.model.nav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.lenords.yama.crawler.CrawlerDriver;
import net.lenords.yama.model.actions.extract.ByExtractAction;
import net.lenords.yama.model.actions.extract.RegexExtractAction;
import net.lenords.yama.model.extract.RegexPattern;
import net.lenords.yama.model.request.CrawlerRequest;
import net.lenords.yama.model.actions.extract.ExtractAction;

/**
 * Represents a page structure to crawl
 *
 * @author len0rd
 * @since 2018-03-21
 */
public class Page {
  private String name, currentURL;
  private String rawHtml;
  private boolean redirectDetected;
  //the original request sent to the driver to process.
  private CrawlerRequest lastBuiltRequest;
  private final CrawlerRequest baseRequest;
  private static final Pattern tokenPattern = Pattern.compile("~#(.+?)#~");
  //created after the request is executed by the driver. Having the two requests
  //allows us to compare and see if we were redirected/cookie additions, etc.
  private CrawlerRequest resultingRequest;
  private List<ExtractAction> extractors;

  public Page(String name, String baseURL) {
    this.name = name;
    this.extractors = new ArrayList<>();
    this.baseRequest = new CrawlerRequest(baseURL);
  }


  /**
   *
   * @param cd  The current crawler driver, to load this page on
   * @return The latest match (ie: the one furthest down on the document) from all extractors,
   *         combined into a single map. These are put into the map in order of their addition to
   *         the page, meaning identically named values across different ExtractActions can be
   *         overwritten. To access a specific ExtractActions results, call {@link #getExtractor(String)}
   *         after calling this run function.
   */
  public Map<String, String> run(Map<String, String> context, CrawlerDriver cd) {
    //reset some the stuff that changes on each load
    extractors.parallelStream().forEach(ExtractAction::clearResult);
    this.currentURL = null;

    findReplaceTokens(context);

    this.rawHtml = cd.requestAndGet(lastBuiltRequest);
    final Map<String, String> lastResultsOfAllExtractors = new HashMap<>();
    runAllExtractors(cd);

    extractors.stream().filter(extractAction -> !extractAction.getExtractionResult().isEmpty())
        .forEach(extractAction ->
            lastResultsOfAllExtractors.putAll(extractAction.getExtractionResult().getLatest()));


    //redirectDetected = lastBuiltRequest.equals()
    //reset orig request now that we're done
    return lastResultsOfAllExtractors;
  }

  /**
   * This request defines the request structure of this page. Setup tokens (ie: ~#TOKEN_NAME#~)
   * within the base url and get parameters of this request. At load time they will be replaced by
   * the current context map, with values that have matching key names (case-sensitive)
   * @return  The base request for this page
   */
  public CrawlerRequest getBaseRequest() {
    return baseRequest;
  }

  public CrawlerRequest getBuiltRequest() {
    return lastBuiltRequest;
  }

  /**
   * Runs all ExtractActions associated with this page. This is automatically called by
   * the {@link #run(Map, CrawlerDriver)} method. NO NEED to call manually unless you've manually
   * modified something about the driver or RawHtml source within this page object.
   * @param cd The current CrawlerDriver, on which this page was just loaded
   */
  private void runAllExtractors(CrawlerDriver cd) {
    //Run all extractors
    //these need to be run in order, otherwise this
    //couldve been done in parallell streams
    for (ExtractAction extractor : extractors) {
      if (extractor instanceof RegexExtractAction) {
        ((RegexExtractAction)extractor).run(rawHtml);
      } else if (extractor instanceof ByExtractAction) {
        ((ByExtractAction)extractor).run(cd);
      }
    }
    /*
    extractors.stream().filter(extractAction -> extractAction instanceof RegexExtractAction)
        .forEach(regexAction -> ((RegexExtractAction)regexAction).run(rawHtml));
    extractors.stream().filter(extractAction -> extractAction instanceof ByExtractAction)
        .forEach(byAction -> ((ByExtractAction)byAction).run(cd));*/

  }

  private void findReplaceTokens(Map<String, String> context) {
    //TODO: do we need deep copy/clone here? possibly for the http param list
    lastBuiltRequest = new CrawlerRequest(baseRequest);
    if (lastBuiltRequest.hasTokens()) {
      String baseURL = lastBuiltRequest.getBaseUrl();
      Matcher urlMatcher = tokenPattern.matcher(baseURL);
      while (urlMatcher.find()) {
        String keyname = urlMatcher.group(1);
        if (context.containsKey(keyname)) {
          baseURL = baseURL.replace("~#" + keyname + "#~", context.get(keyname));
        }
      }
      lastBuiltRequest.setBaseUrl(baseURL);
      if (lastBuiltRequest.hasTokens()) {
        //TODO http param token replacement
      }
    }
  }

  public String getRawHtml() {
    return rawHtml;
  }

  public void setRawHtml(String rawHtml) {
    this.rawHtml = rawHtml;
  }

  public Page addExtractorPatterns(RegexPattern... patterns) {
    if (patterns != null) {
      Arrays.stream(patterns).forEach(pattern -> extractors.add(new RegexExtractAction(pattern)));
    }
    return this;
  }

  public Page addExtractActions(ExtractAction... actions) {
    if (actions != null) {
      extractors.addAll(Arrays.asList(actions));
    }
    return this;
  }


  public ExtractAction getExtractor(String extractorName) {
    return extractors.stream()
        .filter(extractionPattern -> extractionPattern.getName().equals(extractorName))
        .findFirst().orElse(null);
  }

  public List<ExtractAction> getExtractors() {
    return extractors;
  }

  public String getName() {
    return name;
  }


}
