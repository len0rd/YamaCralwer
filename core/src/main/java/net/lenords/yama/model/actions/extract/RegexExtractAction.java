package net.lenords.yama.model.actions.extract;

import net.lenords.yama.model.extract.ExtractionPattern;
import net.lenords.yama.model.extract.ExtractionResult;
import net.lenords.yama.model.extract.RegexPattern;

public class RegexExtractAction implements ExtractAction<String> {
  private RegexPattern pattern;


  @Override
  public ExtractionPattern getExtractionPattern() {
    return null;
  }

  @Override
  public ExtractionResult getExtractionResult() {
    return null;
  }

  @Override
  public ExtractionResult run(String context) {
    return null;
  }
}
