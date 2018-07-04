package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.crawler.CrawlerDriver;
import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;

/**
 * Uses Selenium's By interface to enable powerful extraction
 * options. These include extraction by id, css selector, class
 * name, and xpath.
 *
 * @author len0rd
 */
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
