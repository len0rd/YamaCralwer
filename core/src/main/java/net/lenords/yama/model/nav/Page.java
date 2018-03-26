package net.lenords.yama.model.nav;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.lenords.yama.crawler.CrawlerDriver;
import net.lenords.yama.model.extract.ExtractionPattern;
import net.lenords.yama.model.extract.ExtractionResult;
import net.lenords.yama.model.request.CrawlerRequest;
import net.lenords.yama.model.actions.extract.ExtractAction;

/**
 * Represents a page structure to crawl
 *
 * @author len0rd
 * @since 2018-03-21
 */
public class Page {
  private String name;
  private final String baseURL;
  private String rawHtml;
  //the original request sent to the driver to process.
  private CrawlerRequest originalRequest;
  //created after the request is executed by the driver. Having the two requests
  //allows us to compare and see if we were redirected/cookie additions, etc.
  private CrawlerRequest resultingRequest;
  private List<ExtractionPattern> extractors;
  private List<ExtractionResult> results;

  public Page(String name, String baseURL) {
    this.name = name;
    this.baseURL = baseURL;
  }

  /**
   * A map of all extraction results. For each ExtractionPattern
   * @param cd
   * @return
   */
  public Map<String, String> run(CrawlerDriver cd) {
    this.results.clear();
    this.rawHtml = cd.requestAndGet(originalRequest);
    //extractors.forEach();
  }

  public void replaceInBaseURL(String variable, String value) {

  }

  public Page addExtractorPatterns(ExtractionPattern... patterns) {
    if (patterns != null) {
      this.extractors.addAll(Arrays.asList(patterns));
    }
    return this;
  }

  public Page addExtractorPattern(ExtractionPattern extract) {
    extractors.add(extract);
    return this;
  }

  public ExtractionPattern getExtractor(String extractorName) {
    Optional<ExtractionPattern> result = extractors.stream().filter(extractionPattern -> extractionPattern.getName().equals(extractorName)).findFirst();
    if (result.isPresent()) {
      return result.get();
    }
    return null;
  }



}
