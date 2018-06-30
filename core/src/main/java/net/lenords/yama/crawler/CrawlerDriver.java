package net.lenords.yama.crawler;

import java.net.URL;
import net.lenords.yama.crawler.conf.CrawlerConf;
import net.lenords.yama.internal.model.request.CrawlerRequest;

public interface CrawlerDriver {

  String getCurrentSource();

  String getCurrentURLStr();

  URL getCurrentURL();

  String resolveRelativeToAbsoluteURLStr(String relativeURL);

  String requestAndGet(CrawlerRequest request);

  CrawlerConf getConfig();

  void close();

}
