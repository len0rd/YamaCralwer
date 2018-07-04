package net.lenords.yama.internal.crawler;

import java.net.URL;

import net.lenords.yama.internal.conf.SeleniumDriverConf;
import net.lenords.yama.internal.model.request.CrawlerRequest;

public interface CrawlerDriver {

	String getCurrentSource();

	String getCurrentURLStr();

	URL getCurrentURL();

	String resolveRelativeToAbsoluteURLStr(String relativeURL);

	String requestAndGet(CrawlerRequest request);

	SeleniumDriverConf getConfig();

	void close();

}
