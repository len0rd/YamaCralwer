package net.lenords.yama.model;

public class CrawlerRequest {

  private String url;
  //private List<> cookies
  public CrawlerRequest(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
