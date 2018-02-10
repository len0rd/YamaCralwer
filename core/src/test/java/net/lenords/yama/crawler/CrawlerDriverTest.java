package net.lenords.yama.crawler;

import net.lenords.yama.crawler.conf.CrawlerConf;
import net.lenords.yama.crawler.conf.SeleniumDriverType;

public class CrawlerDriverTest {

  @org.junit.jupiter.api.Test
  void basicInstantiation_HtmlUnit() {

    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.HTMLUNIT, false, false);
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    String type = cd1.getDriverInfo();
    assert type.toLowerCase().contains("htmlunitdriver");

  }

  @org.junit.jupiter.api.Test
  void basicInstantiation_Chrome() {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.CHROME, false, false);
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    String type = cd1.getDriverInfo();
    assert type.toLowerCase().contains("chromedriver");
  }

  @org.junit.jupiter.api.Test
  void basicInstantiation_Firefox() {
    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.FIREFOX, false, false);
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    String type = cd1.getDriverInfo();
    assert type.toLowerCase().contains("firefoxdriver");
  }

}
