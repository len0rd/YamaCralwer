package net.lenords.yama.crawler;

import net.lenords.yama.internal.model.nav.PageInstance;

public class CrawlerInstance implements PageInstance.PageCallbackManager {

  private Crawler crawler;

  public CrawlerInstance(Crawler crawler) {
    this.crawler = crawler;
  }

  @Override
  public void onBeforePageLoad() {}

  @Override
  public void onAfterPageCrawled() {}
}
