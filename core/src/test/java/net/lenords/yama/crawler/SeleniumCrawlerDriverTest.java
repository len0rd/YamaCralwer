package net.lenords.yama.crawler;

import net.lenords.yama.crawler.conf.CrawlerConf;
import net.lenords.yama.crawler.conf.SeleniumDriverType;
import org.junit.jupiter.api.Assertions;

public class SeleniumCrawlerDriverTest {

  private void assertSuccessfulInstantiation(SeleniumDriverType type, SeleniumCrawlerDriver cd1) {
    String info = cd1.getDriverInfo().toLowerCase();
    if (type == SeleniumDriverType.CHROME) {
      assert info.contains("chromedriver");
    } else if (type == SeleniumDriverType.FIREFOX) {
      assert info.contains("firefoxdriver");
    } else if (type == SeleniumDriverType.HTMLUNIT) {
      assert info.contains("htmlunitdriver");
    } else {
      Assertions.fail("Driver type unknown. Unable to check for successful instantiation");
    }

  }

  @org.junit.jupiter.api.Test
  void basicInstantiation_HtmlUnit() {

    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.HTMLUNIT, false, false);
    SeleniumCrawlerDriver cd1 = new SeleniumCrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.HTMLUNIT, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiation_Chrome() {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.CHROME, false, false);
    SeleniumCrawlerDriver cd1 = new SeleniumCrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.CHROME, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiation_Firefox() {
    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.FIREFOX, false, false);
    SeleniumCrawlerDriver cd1 = new SeleniumCrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.FIREFOX, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiationDebugMode_Chrome() {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.CHROME, false, true,
        true, false, null, null, 30);
    SeleniumCrawlerDriver cd1 = new SeleniumCrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.CHROME, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiationDebugMode_Firefox() {
    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.FIREFOX, false, true,
        true, false, null, null, 30);
    SeleniumCrawlerDriver cd1 = new SeleniumCrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.FIREFOX, cd1);
    cd1.close();
  }



}
