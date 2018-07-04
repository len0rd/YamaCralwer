package net.lenords.yama.internal.model;

import net.lenords.yama.internal.conf.SeleniumDriverConf;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Top level model object of a crawler. Contains associated pages, and
 * advanced crawler settings.
 *
 *
 * @author len0rd
 * @since 2018-06-21
 */
public class Crawler {

	private String name;
	private Set<Page> pages;
	private SeleniumDriverConf advancedConfig;
	private List<Action> beforeCrawlStarts, afterCrawlEnds;

	public Crawler(String name) {
		this.name = name;
		this.pages = new HashSet<>();
	}

	public Crawler addPage(Page pageToAdd) {
		if (pages.stream().noneMatch(page -> page.getName().equals(pageToAdd.getName()))) {
			pages.add(pageToAdd);
		} else {
			System.out.println(
				"Could not add page: "
					+ pageToAdd.getName()
					+ " since the crawler already has a page by that name");
		}
		return this;
	}

	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	public List<Action> getBeforeCrawlStarts() {
		return beforeCrawlStarts;
	}

	public void setBeforeCrawlStarts(List<Action> beforeCrawlStarts) {
		this.beforeCrawlStarts = beforeCrawlStarts;
	}

	public List<Action> getAfterCrawlEnds() {
		return afterCrawlEnds;
	}

	public void setAfterCrawlEnds(List<Action> afterCrawlEnds) {
		this.afterCrawlEnds = afterCrawlEnds;
	}

	public String getName() {
		return name;
	}

	public SeleniumDriverConf getAdvancedConfig() {
		return advancedConfig;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAdvancedConfig(SeleniumDriverConf advancedConfig) {
		this.advancedConfig = advancedConfig;
	}
}
