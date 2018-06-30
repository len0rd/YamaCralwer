package net.lenords.yama.internal.crawler;

import net.lenords.yama.internal.controller.PageController;

public class CrawlerInstance implements PageController.PageCallbackManager {

	private Crawler crawler;

	public CrawlerInstance(Crawler crawler) {
		this.crawler = crawler;
	}

	@Override
	public void onBeforePageLoad() {
	}

	@Override
	public void onAfterPageCrawled() {
	}
}
