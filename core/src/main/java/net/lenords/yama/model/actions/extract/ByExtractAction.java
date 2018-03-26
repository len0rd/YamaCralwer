package net.lenords.yama.model.actions.extract;

import net.lenords.yama.crawler.CrawlerDriver;
import net.lenords.yama.model.actions.Action;
import net.lenords.yama.model.actions.ActionResult;
import net.lenords.yama.model.extract.ByPattern;
import net.lenords.yama.model.extract.ExtractionPattern;
import net.lenords.yama.model.extract.ExtractionResult;

public class ByExtractAction implements ExtractAction<CrawlerDriver> {
  private ByPattern pattern;


  @Override
  public ExtractionPattern getExtractionPattern() {
    return pattern;
  }

  @Override
  public ExtractionResult getExtractionResult() {
    return null;
  }

  @Override
  public ExtractionResult run(CrawlerDriver context) {
    return null;
  }
}
