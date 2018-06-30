package net.lenords.yama.internal.model.nav;

import net.lenords.yama.crawler.Crawler;

/**
 * And actual executable instance of a {@link Page}. Invoked by the {@link Crawler} and provided
 * with the current context and a CrawlerDriver to use.
 *
 * @author len0rd
 * @since 2018-06-21
 */
public class PageInstance {

  private Page page;
  private PageCallbackManager pageCallbackManager;

  public PageInstance(Page page, PageCallbackManager pageCallbackManager) {
    this.page = page;
    this.pageCallbackManager = pageCallbackManager;
  }

  public interface PageCallbackManager {
    void onBeforePageLoad();

    void onAfterPageCrawled();
  }
}
