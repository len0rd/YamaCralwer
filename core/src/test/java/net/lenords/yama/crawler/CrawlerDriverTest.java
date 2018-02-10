package net.lenords.yama.crawler;

import net.lenords.yama.crawler.conf.CrawlerConf;
import net.lenords.yama.crawler.conf.SeleniumDriverType;
import net.lenords.yama.model.CrawlerRequest;
import org.junit.jupiter.api.Assertions;

public class CrawlerDriverTest {

  private void assertSuccessfulInstantiation(SeleniumDriverType type, CrawlerDriver cd1) {
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
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.HTMLUNIT, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiation_Chrome() {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.CHROME, false, false);
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.CHROME, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiation_Firefox() {
    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.FIREFOX, false, false);
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.FIREFOX, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiationDebugMode_Chrome() {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.CHROME, false, true,
        true, false, null, null, 30);
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.CHROME, cd1);
    cd1.close();
  }

  @org.junit.jupiter.api.Test
  void basicInstantiationDebugMode_Firefox() {
    System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
    CrawlerConf conf = new CrawlerConf(SeleniumDriverType.FIREFOX, false, true,
        true, false, null, null, 30);
    CrawlerDriver cd1 = new CrawlerDriver(conf);
    assertSuccessfulInstantiation(SeleniumDriverType.FIREFOX, cd1);
    cd1.close();
  }



}
