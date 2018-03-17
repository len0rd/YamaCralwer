package net.lenords.yama.crawler;

import net.lenords.yama.crawler.conf.CrawlerConf;

public interface CrawlerDriver {

  String getCurrentSource();

  CrawlerConf getConfig();

  void close();

}
