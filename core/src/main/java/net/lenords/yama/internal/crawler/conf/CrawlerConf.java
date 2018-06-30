package net.lenords.yama.internal.crawler.conf;

public class CrawlerConf {
  private static final boolean DEFAULT_HEADLESS = true, DEFAULT_IN_SERVER = true,
          DEFAULT_LOAD_IMG = false, DEFAULT_RUN_JS = true;
  private static final String DEFAULT_CUSTOM_ARGS = null, DEFAULT_UA = null;
  private static final int DEFAULT_PL_TIMEOUT = 30;

  private SeleniumDriverType driver;
  private boolean headless, loadImg, runJs, inServerMode;
  private String customArgs, userAgent;
  private int pageLoadTimeout;

  public CrawlerConf(SeleniumDriverType type) {
    this(type, DEFAULT_LOAD_IMG, DEFAULT_RUN_JS);
  }

  public CrawlerConf(SeleniumDriverType type, boolean loadImg, boolean runJs) {
    this(type, DEFAULT_HEADLESS, loadImg, runJs, DEFAULT_IN_SERVER, DEFAULT_CUSTOM_ARGS,
        DEFAULT_UA, DEFAULT_PL_TIMEOUT);
  }

  public CrawlerConf(SeleniumDriverType driver, boolean headless, boolean loadImg, boolean runJs,
      boolean inServerMode, String customArgs, String defaultUserAgent, int pageLoadTimeout) {
    this.driver = driver;
    this.headless = headless;
    this.loadImg = loadImg;
    this.runJs = runJs;
    this.inServerMode = inServerMode;
    this.customArgs = customArgs;
    this.userAgent = defaultUserAgent;
    this.pageLoadTimeout = pageLoadTimeout;
  }

  public SeleniumDriverType getDriverType() {
    return driver;
  }

  public void setDriverType(SeleniumDriverType driver) {
    this.driver = driver;
  }

  public boolean isHeadless() {
    return headless;
  }

  public void setHeadless(boolean headless) {
    this.headless = headless;
  }

  public boolean loadImgs() {
    return loadImg;
  }

  public void setLoadImg(boolean load_img) {
    this.loadImg = load_img;
  }

  public boolean runJs() {
    return runJs;
  }

  public boolean inServerMode() {
    return inServerMode;
  }

  public void setInServerMode(boolean serverMode) {
    this.inServerMode = serverMode;
  }

  public void setRunJs(boolean runJs) {
    this.runJs = runJs;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String defaultUserAgent) {
    this.userAgent = defaultUserAgent;
  }

  public String getCustomArgs() {
    return customArgs;
  }

  public void setCustomArgs(String customArgs) {
    this.customArgs = customArgs;
  }

  @Override
  public String toString() {
    return "CrawlerConf{" +
        "driver=" + driver +
        ", headless=" + headless +
        ", loadImg=" + loadImg +
        ", runJs=" + runJs +
        ", inServerMode=" + inServerMode +
        ", customArgs='" + customArgs + '\'' +
        ", defaultUserAgent='" + userAgent + '\'' +
        '}';
  }

  public int getPageLoadTimeout() {
    return pageLoadTimeout;
  }

  public void setPageLoadTimeout(int pageLoadTimeout) {
    this.pageLoadTimeout = pageLoadTimeout;
  }
}
