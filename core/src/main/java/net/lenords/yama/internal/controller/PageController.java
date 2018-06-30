package net.lenords.yama.internal.controller;

import net.lenords.yama.internal.crawler.Crawler;
import net.lenords.yama.internal.model.Page;

/**
 * And actual executable instance of a {@link Page}. Invoked by the {@link Crawler} and provided
 * with the current context and a CrawlerDriver to use.
 *
 * @author len0rd
 * @since 2018-06-21
 */
public class PageController {

  private Page page;
  private PageCallbackManager pageCallbackManager;

  public PageController(Page page, PageCallbackManager pageCallbackManager) {
    this.page = page;
    this.pageCallbackManager = pageCallbackManager;
  }

  public interface PageCallbackManager {
    void onBeforePageLoad();

    void onAfterPageCrawled();
  }
}
