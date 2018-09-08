package net.lenords.yama.internal.model.extract;

import java.util.List;

import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;
import org.openqa.selenium.By;

/**
 * Represents a By pattern. By's are used within Selenium to select specific HTML tags based on
 * a variety of properties.
 *
 * @authpr len0rd
 * @since 2018-03-21
 */
public class ByPattern implements ExtractionPattern<SeleniumCrawlerDriver> {
	private String name;
	private By by;

	public ByPattern(String name) {
		this.name = name;
	}

	public ByPattern() {
		this("By Extractor Pattern");
	}


	@Override
	public SeleniumCrawlerDriver build() {
		return null;
	}

	@Override
	public ExtractionResult buildAndRun(SeleniumCrawlerDriver matchAgainst) {
		return null;
	}

	@Override
	public ExtractionResult run(SeleniumCrawlerDriver matchAgainst) {
		return null;
	}

	@Override
	public List<String> getExtractorNamesToRetrieve() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}
}
