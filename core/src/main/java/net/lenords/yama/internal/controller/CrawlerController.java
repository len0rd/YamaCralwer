package net.lenords.yama.internal.controller;

import net.lenords.yama.internal.Constants;
import net.lenords.yama.internal.model.Crawler;
import net.lenords.yama.internal.model.Action;

import java.util.HashMap;
import java.util.Map;

public class CrawlerController implements PageController.PageCallbackManager {

	private Crawler crawler;

	public CrawlerController(Crawler crawler) {
		this.crawler = crawler;
	}

	/**
	 * The main method to run the Crawler
	 */
	public void run() {
		// Create the base context with a reference to this controller:
		Map<String, Object> baseContext = new HashMap<>();
		 baseContext.put(Constants.CURRENT_CRAWLER_CONTEXT_KEY, this);

	}

	// Callback manager methods:

	@Override
	public Map<String, Object> onBeforeFetch(Map<String, Object> context, Action action) {
		return null;
	}

	@Override
	public Map<String, Object> onAfterFetch(Map<String, Object> context, Action action) {
		return null;
	}

	@Override
	public Map<String, Object> onAfterExtract(Map<String, Object> context, Action action) {
		return null;
	}
}
