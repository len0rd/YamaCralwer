package net.lenords.yama.internal.model.actions.extract;

import net.lenords.yama.internal.crawler.CrawlerDriver;
import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;
import net.lenords.yama.internal.model.extract.ByPattern;
import net.lenords.yama.internal.model.extract.ExtractionPattern;
import net.lenords.yama.internal.model.extract.ExtractionResult;

public class ByExtractAction implements ExtractAction<CrawlerDriver> {
	private ByPattern pattern;
	private ExtractionResult result;


	public ByExtractAction(ByPattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public ExtractionPattern getExtractionPattern() {
		return pattern;
	}

	@Override
	public ExtractionResult getExtractionResult() {
		return result;
	}

	@Override
	public void clearResult() {
		this.result = null;
	}

	@Override
	public ExtractionResult run(CrawlerDriver context) {
		return pattern.buildAndRun((SeleniumCrawlerDriver) context);
	}

	@Override
	public String getName() {
		return pattern.getName();
	}
}
