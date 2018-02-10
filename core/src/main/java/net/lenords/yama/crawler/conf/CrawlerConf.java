package net.lenords.yama.crawler.conf;

public class CrawlerConf {
  private SeleniumDriverType driver;
  private boolean headless, loadImg, runJs, inServerMode;
  private String customArgs, defaultUserAgent;


  public CrawlerConf(SeleniumDriverType type, boolean runJs, boolean loadImg) {
    this.driver = type;
    this.runJs = runJs;
    this.loadImg = loadImg;

    //set defaults
    this.headless = true;
    this.inServerMode = true;
    this.customArgs = null;
    this.defaultUserAgent = null;
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

  public String getDefaultUserAgent() {
    return defaultUserAgent;
  }

  public void setDefaultUserAgent(String defaultUserAgent) {
    this.defaultUserAgent = defaultUserAgent;
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
        ", defaultUserAgent='" + defaultUserAgent + '\'' +
        '}';
  }
}
