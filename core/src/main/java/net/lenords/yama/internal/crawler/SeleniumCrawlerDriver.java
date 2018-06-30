package net.lenords.yama.internal.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersion.BrowserVersionBuilder;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import net.lenords.yama.api.lang.StrUtils;
import net.lenords.yama.api.proxy.ProxyProvider;
import net.lenords.yama.internal.crawler.conf.CrawlerConf;
import net.lenords.yama.internal.model.request.CrawlerRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Interfaces with Selenium WebDriver and provides some basic convenience functions
 *
 * @author len0rd
 */
public class SeleniumCrawlerDriver implements CrawlerDriver {
  private WebDriver driver;
  private CrawlerConf config;

  public SeleniumCrawlerDriver(CrawlerConf driverConf, ProxyProvider proxyProvider) {
    this.config = driverConf;
    initDriver(driverConf, proxyProvider);
  }

  public SeleniumCrawlerDriver(CrawlerConf driverConf) {
    this.config = driverConf;
    // String yamaKurora = "山クローラ"; //Yama Kurōra
    initDriver(driverConf, null);
  }

  @Override
  public String requestAndGet(CrawlerRequest request) {
    driver.get(request.buildUrl());
    return driver.getPageSource();
  }

  public void goBack() {
    driver.navigate().back();
  }

  @Override
  public String getCurrentURLStr() {
    return driver.getCurrentUrl();
  }

  @Override
  public URL getCurrentURL() {
    try {
      return new URL(driver.getCurrentUrl());
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String resolveRelativeToAbsoluteURLStr(String relativeURL) {
    return null;
  }

  @Override
  public String getCurrentSource() {
    return driver.getPageSource();
  }

  public void changeProxy(ProxyProvider newProxy) {
    switch (config.getDriverType()) {
      case CHROME:
        close();
        // as far as i know there's no way to change proxy in chrome without restarting the driver
        initDriver(config, newProxy);
        break;
      case HTMLUNIT:
        // with HTMLUnit you can just change the proxy without having to restart the
        // entire driver!
        ((HtmlUnitDriver) this.driver).setProxySettings(newProxy.generateHtmlUnitProxy());
        break;
    }
  }

  public String clickAndGetFirst(By clickBy) {
    try {
      WebElement firstElement = driver.findElement(clickBy);
      return clickAndGet(firstElement);
    } catch (NoSuchElementException nse) {
      return null;
    }
  }

  public WebElement getFirstElement(By getBy) {
    try {
      return driver.findElement(getBy);
    } catch (NoSuchElementException nse) {
      return null;
    }
  }

  public String getInnerHtml(WebElement elementToGetHtmlInnardsOf) {
    if (elementToGetHtmlInnardsOf != null) {
      return elementToGetHtmlInnardsOf.getAttribute("innerHTML");
    }
    return null;
  }

  public String getInnerHtml(By elementsInnerHtmlToGet) {
    return getInnerHtml(getFirstElement(elementsInnerHtmlToGet));
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
      subElement.sendKeys();
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

  public WebDriver getDriver() {
    return driver;
  }

  public void close() {
    if (driver != null) {
      try {
        driver.close();
        if (driver != null) {
          driver.quit();
        }
      } catch (SessionNotCreatedException snce) {
        System.out.println("Tried to close a driver that was already closed");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void initDriver(CrawlerConf config, ProxyProvider proxyProvider) {
    switch (config.getDriverType()) {
      case CHROME: // Properly configure the chrome driver:
        Map<String, Object> additonalPrefs = new HashMap<>();
        ChromeOptions options = new ChromeOptions();

        if (config.inServerMode()) {
          // if running in server mode, its also headless
          options.addArguments("--headless", "--disable-gpu", "--no-sandbox");
          options.addArguments("--window-size=1900,1050");
        } else if (config.isHeadless()) {
          options.addArguments("--headless");
          options.addArguments("--window-size=1900,1050");
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

      case FIREFOX: // Properly  configure the firefox driver:
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

      case HTMLUNIT: // Properly configure HtmlUnit driver:
        BrowserVersion browserVersion = BrowserVersion.getDefault();
        // since htmlunit is already headless, this cuts down on the number of config options
        // this constructor needs to deal with
        if (!StrUtils.isNullEmpty(config.getUserAgent())) {
          BrowserVersion.BrowserVersionBuilder builder =
              new BrowserVersionBuilder(BrowserVersion.BEST_SUPPORTED);
          builder.setUserAgent(config.getUserAgent());
          browserVersion = builder.build();
        }

        if (config.runJs()) {
          this.driver = new HtmlUnitDriver(browserVersion, true);
        } else {
          this.driver = new HtmlUnitDriver(browserVersion);
        }

        if (proxyProvider != null) {
          ((HtmlUnitDriver) this.driver).setProxySettings(proxyProvider.generateHtmlUnitProxy());
        }
        break;
    }

    // if you dont want a load timeout, set the prop to < 0
    if (config.getPageLoadTimeout() > 0) {
      driver.manage().timeouts().pageLoadTimeout(config.getPageLoadTimeout(), TimeUnit.SECONDS);
    }
  }
}
