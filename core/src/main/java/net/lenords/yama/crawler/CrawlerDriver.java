package net.lenords.yama.crawler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.lenords.yama.crawler.conf.CrawlerConf;
import net.lenords.yama.crawler.conf.SeleniumDriverType;
import net.lenords.yama.model.CrawlerRequest;
import net.lenords.yama.util.lang.StrUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CrawlerDriver {
  private WebDriver driver;
  private SeleniumDriverType type;

  public CrawlerDriver(CrawlerConf driverConf) {

    String yama = "山";
    initDriver(driverConf);
  }

  public String requestAndGet(CrawlerRequest request) {
    driver.get(request.getUrl());
    return driver.getPageSource();
  }


  public String clickAndGet(By clickBy) {
    return null;
  }

  public String getDriverInfo() {
    return driver.toString();
  }

  public void close() {
    if (driver != null) {
      driver.quit();
    }
  }

  private void initDriver(CrawlerConf config) {
    switch (config.getDriverType()) {
      case CHROME:
        Map<String, Object> additonalPrefs = new HashMap<>();
        ChromeOptions options = new ChromeOptions();

        if (config.inServerMode()) {
          //if running in server mode, its also headless
          options.addArguments("--headless", "--disable-gpu", "--no-sandbox");
        } else if (config.isHeadless()) {
          options.addArguments("--headless");
        }

        if (!config.loadImgs()) {
          additonalPrefs.put("profile.managed_default_content_settings.images", 2);
        }
        if (!StrUtils.isNullEmpty(config.getUserAgent())) {
          options.addArguments("--user-agent=" + config.getUserAgent());
        }
        if (!config.runJs()) {
          additonalPrefs.put("profile.managed_default_content_settings.javascript", 2);
        }

        if (!additonalPrefs.isEmpty()) {
          options.setExperimentalOption("prefs", additonalPrefs);
        }
        this.driver = new ChromeDriver(options);
        break;

      case FIREFOX:
        FirefoxOptions ffOptions = new FirefoxOptions();

        if (config.isHeadless() || config.inServerMode()) {
          ffOptions.setHeadless(true);
        }

        if (!StrUtils.isNullEmpty(config.getUserAgent())) {
          ffOptions.addPreference("general.useragent.override", config.getUserAgent());
        }
        if (!config.loadImgs()) {
          ffOptions.addPreference("permissions.default.image", 2);
        }
        if (!config.runJs()) {
          ffOptions.addPreference("javascript.enabled", false);
        }

        this.driver = new FirefoxDriver(ffOptions);
        break;

      case HTMLUNIT:
        DesiredCapabilities caps = DesiredCapabilities.htmlUnit();


        if (config.runJs()) {
          this.driver = new HtmlUnitDriver(true);
        } else {
          this.driver = new HtmlUnitDriver();
        }

        break;
    }

    //this means if you dont want a load timeout, set the prop to < 0
    if (config.getPageLoadTimeout() > 0) {
      driver.manage().timeouts().pageLoadTimeout(config.getPageLoadTimeout(), TimeUnit.SECONDS);
    }

  }


}
