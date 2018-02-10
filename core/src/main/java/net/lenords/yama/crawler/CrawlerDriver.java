package net.lenords.yama.crawler;

import java.util.HashMap;
import java.util.Map;
import net.lenords.yama.crawler.conf.CrawlerConf;
import net.lenords.yama.crawler.conf.SeleniumDriverType;
import net.lenords.yama.util.lang.StrUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CrawlerDriver {
  private WebDriver driver;
  private SeleniumDriverType type;

  public CrawlerDriver(CrawlerConf driverConf) {

    String yama = "山";
    initDriver(driverConf);
  }

  public String requestAndGet(String request) {
    return null;
  }


  public String clickAndGet(By clickBy) {
    return null;
  }

  public String getDriverInfo() {
    return driver.toString();
  }

  private void initDriver(CrawlerConf config) {
    switch (config.getDriverType()) {
      case CHROME:
        Map<String, Object> addtionalPrefs = new HashMap<>();
        ChromeOptions options = new ChromeOptions();

        if (config.isHeadless() || config.inServerMode()) {
          options.addArguments("--headless");
        }
        if (!config.loadImgs()) {
          addtionalPrefs.put("profile.managed_default_content_settings.images", 2);
        }
        if (!StrUtils.isNullEmpty(config.getDefaultUserAgent())) {
          options.addArguments("--user-agent=" + config.getDefaultUserAgent());
        }
        if (config.inServerMode()) {
          options.addArguments("--disable-gpu", "--no-sandbox");
        }

        if (!addtionalPrefs.isEmpty()) {
          options.setExperimentalOption("prefs", addtionalPrefs);
        }
        this.driver = new ChromeDriver(options);
        break;
      case FIREFOX:

        //"webdriver.gecko.driver"

        this.driver = new FirefoxDriver();
        break;
      case HTMLUNIT:


        if (config.runJs()) {
          this.driver = new HtmlUnitDriver(true);
        } else {
          this.driver = new HtmlUnitDriver();
        }

        break;
    }
  }


}
