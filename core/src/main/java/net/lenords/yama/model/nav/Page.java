package net.lenords.yama.model.nav;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  private final String baseURL;
  private String rawHtml;
  //the original request sent to the driver to process.
  private CrawlerRequest originalRequest;
  //created after the request is executed by the driver. Having the two requests
  //allows us to compare and see if we were redirected/cookie additions, etc.
  private CrawlerRequest resultingRequest;
  private List<ExtractAction> extractors;

  public Page(String name, String baseURL) {
    this.name = name;
    this.baseURL = baseURL;
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
  public Map<String, String> run(CrawlerDriver cd) {
    //reset some the stuff that changes on each load
    extractors.forEach(ExtractAction::clearResult);
    this.currentURL = null;
    this.rawHtml = cd.requestAndGet(originalRequest);

    runAllExtractors(cd);
    Map<String, String> lastResultsOfAllExtractors = new HashMap<>();
    extractors.forEach(extractAction -> lastResultsOfAllExtractors.putAll(extractAction.getExtractionResult().getLastResult()));

    //reset orig request now that we're done
    this.originalRequest = new CrawlerRequest(baseURL);
    return lastResultsOfAllExtractors;
  }

  public void replaceInBaseURL(String variable, String value) {
    String currentUrl = originalRequest.getBaseUrl();
    currentUrl = currentURL.replace("~#" + variable + "#~", value);
    originalRequest.setBaseUrl(currentUrl);
  }

  /**
   * Runs all ExtractActions associated with this page. This is automatically called by
   * the {@link #run(CrawlerDriver)} method. NO NEED to call manually unless you've manually
   * modified something about the driver or RawHtml source within this page object.
   * @param cd The current CrawlerDriver, on which this page was just loaded
   */
  public void runAllExtractors(CrawlerDriver cd) {
    //Run all extractors
    extractors.stream().filter(extractAction -> extractAction instanceof RegexExtractAction)
        .forEach(regexAction -> ((RegexExtractAction)regexAction).run(rawHtml));
    extractors.stream().filter(extractAction -> extractAction instanceof ByExtractAction)
        .forEach(byAction -> ((ByExtractAction)byAction).run(cd));

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
    Optional<ExtractAction> result = extractors.stream().filter(extractionPattern -> extractionPattern.getName().equals(extractorName)).findFirst();
    return result.orElse(null);
  }

  String getName() {
    return name;
  }



}
