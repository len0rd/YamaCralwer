package net.lenords.yama.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersion.BrowserVersionBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import net.lenords.yama.crawler.conf.CrawlerConf;
import net.lenords.yama.model.CrawlerRequest;
import net.lenords.yama.proxy.ProxyProvider;
import net.lenords.yama.util.lang.StrUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CrawlerDriver {
  private WebDriver driver;
  private CrawlerConf config;

  public CrawlerDriver(CrawlerConf driverConf, ProxyProvider proxyProvider) {
    this.config = driverConf;
    initDriver(driverConf, proxyProvider);
  }

  public CrawlerDriver(CrawlerConf driverConf) {
    this.config = driverConf;
    String yamaKurora = "山クローラ"; //Yama Kurōra
    initDriver(driverConf, null);
  }

  public String requestAndGet(CrawlerRequest request) {
    driver.get(request.getUrl());
    return driver.getPageSource();
  }

  public void goBack() {
    driver.navigate().back();
  }

  public String getCurrentSource() {
    return driver.getPageSource();
  }

  public void changeProxy(ProxyProvider newProxy) {
    switch (config.getDriverType()) {
      case CHROME:
        close();
        initDriver(config, newProxy);
        break;
    }
  }

  public String clickAndGetFirst(By clickBy) {
    try  {
      WebElement firstElement = driver.findElement(clickBy);
      return clickAndGet(firstElement);
    } catch (NoSuchElementException nse) {
      return null;
    }
  }

  public WebElement getFirstElement(By getBy) {
    try  {
      return driver.findElement(getBy);
    } catch (NoSuchElementException nse) {
      return null;
    }
  }

  public String getInnerHtml(By elementsInnerHtmlToGet) {
    WebElement el = getFirstElement(elementsInnerHtmlToGet);
    if (el != null) {
      return el.getAttribute("innerHTML");
    }
    return null;
  }

  public String clickAndGet(WebElement clickElement) {
    clickElement.click();
    return driver.getPageSource();
  }

  public String getElementContents(By getBy) {
    WebElement gottenElement = getFirstElement(getBy);
    return gottenElement != null ? gottenElement.getText() : null;
  }

  public String getElementContents(By subExtractor, WebElement mainElement) {
    try {
      WebElement subElement = mainElement.findElement(subExtractor);
      return subElement.getText();
    } catch (NoSuchElementException nse) {
      return null;
    }
  }

  public List<WebElement> getAllElementsOf(By getBy) {
    return driver.findElements(getBy);
  }

  public List<WebElement> getAllElementsOf(By getBy, WebElement elementToSelectFrom) {
    return elementToSelectFrom.findElements(getBy);
  }

  public String getDriverInfo() {
    return driver.toString();
  }

  public CrawlerConf getConfig() {
    return config;
  }

  public void close() {
    if (driver != null) {
      driver.quit();
    }
  }

  private void initDriver(CrawlerConf config, ProxyProvider proxyProvider) {
    switch (config.getDriverType()) {
      case CHROME: //Properly configure the chrome driver:
        Map<String, Object> additonalPrefs = new HashMap<>();
        ChromeOptions options = new ChromeOptions();

        if (config.inServerMode()) {
          //if running in server mode, its also headless
          options.addArguments("--headless", "--disable-gpu", "--no-sandbox");
        } else if (config.isHeadless()) {
          options.addArguments("--headless");
        }

        if (proxyProvider != null) {
          options.setProxy(proxyProvider.generateChromeProxy());
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

      case FIREFOX: //Properly  configure the firefox driver:
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

      case HTMLUNIT: //Properly configure HtmlUnit driver:
        BrowserVersion browserVersion = BrowserVersion.getDefault();
        //since htmlunit is already headless, this cuts down on the number of config options
        //this constructor needs to deal with
        if (!StrUtils.isNullEmpty(config.getUserAgent())) {
          BrowserVersion.BrowserVersionBuilder builder = new BrowserVersionBuilder(BrowserVersion.BEST_SUPPORTED);
          builder.setUserAgent(config.getUserAgent());
          browserVersion = builder.build();
        }

        if (config.runJs()) {
          this.driver = new HtmlUnitDriver(browserVersion, true);
        } else {
          this.driver = new HtmlUnitDriver(browserVersion);
        }
        break;
    }

    //if you dont want a load timeout, set the prop to < 0
    if (config.getPageLoadTimeout() > 0) {
      driver.manage().timeouts().pageLoadTimeout(config.getPageLoadTimeout(), TimeUnit.SECONDS);
    }

  }


}
