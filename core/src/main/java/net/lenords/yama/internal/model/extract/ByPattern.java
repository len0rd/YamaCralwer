package net.lenords.yama.internal.model.extract;

import java.util.List;
import java.util.Map;
import net.lenords.yama.crawler.SeleniumCrawlerDriver;

/**
 * Represents a By pattern. By's are used within Selenium to select specific HTML tags based on
 * a variety of properties.
 *
 * @authpr len0rd
 * @since 2018-03-21
 */
public class ByPattern implements ExtractionPattern<SeleniumCrawlerDriver> {

  @Override
  public SeleniumCrawlerDriver build() {
    return null;
  }

  @Override
  public ExtractionResult buildAndExecute(SeleniumCrawlerDriver matchAgainst) {
    return null;
  }

  @Override
  public ExtractionResult execute(SeleniumCrawlerDriver matchAgainst) {
    return null;
  }

  @Override
  public List<String> getExtractorNamesToRetrieve() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }
}
