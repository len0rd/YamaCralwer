package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.crawler.CrawlerDriver;
import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;

import java.util.Map;

/**
 * Uses Selenium's By interface to enable powerful extraction
 * options. These include extraction by id, css selector, class
 * name, and xpath.
 *
 * @author len0rd
 */
public class ByExtractAction extends AbstractExtractAction<CrawlerDriver> {
	private ByPattern pattern;
	private ExtractionResult result;


	public ByExtractAction(ByPattern pattern) {
		super();
		this.pattern = pattern;
		this.result = null;
	}

	@Override
	public void setActionSpecificContext(CrawlerDriver actionSpecificContext) {

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
	public ExtractionResult run(Map<String, Object> context) {
		return null;
	}

	@Override
	public ExtractionResult run(Map<String, Object> context, CrawlerDriver crawlerDriver) {
		result = pattern.run((SeleniumCrawlerDriver) crawlerDriver);
		return result;
	}

	@Override
	public String getName() {
		return pattern.getName();
	}
}
