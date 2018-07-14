package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.conf.ExtractActionTrigger;
import net.lenords.yama.internal.crawler.CrawlerDriver;
import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;
import net.lenords.yama.internal.model.Action;

import java.util.Map;

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
	public void addAction(ExtractActionTrigger trigger, Action action) {

	}

	@Override
	public void addBeforeExtractionAction(Action action) {

	}

	@Override
	public void addAfterEachExtractionMatchAction(Action action) {

	}

	@Override
	public void addNoExtractionMatchAction(Action action) {

	}

	@Override
	public void addAfterExtractionAction(Action action) {

	}

	@Override
	public ExtractionResult run(Map<String, Object> context) {
		return pattern.buildAndRun((SeleniumCrawlerDriver) context);
	}

	@Override
	public ExtractionResult run(Map<String, Object> context, CrawlerDriver crawlerDriver) {
		return null;
	}

	@Override
	public String getName() {
		return pattern.getName();
	}
}
