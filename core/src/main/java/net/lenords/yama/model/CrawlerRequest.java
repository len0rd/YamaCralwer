package net.lenords.yama.model;

public class CrawlerRequest {

  private String url;
  private int requestTimeout;
  //private List<> cookies
  public CrawlerRequest(String url) {
    this.url = url;
    this.requestTimeout = -1;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getRequestTimeout() {
    return requestTimeout;
  }

  public void setRequestTimeout(int requestTimeout) {
    this.requestTimeout = requestTimeout;
  }
}
