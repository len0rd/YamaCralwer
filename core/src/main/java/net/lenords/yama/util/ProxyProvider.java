package net.lenords.yama.util;

import java.util.Objects;

import org.openqa.selenium.Proxy;

public class ProxyProvider {
  private static final String DEFAULT_SERVICE_NAME = "No Service Name";
  private static final int DEFAULT_ID = -1;

  private String service_name, host, username, password;
  private int id, port;

  public ProxyProvider(
      String service_name, String host, String username, String password, int id, int port) {
    this.service_name = service_name;
    this.host = host;
    this.username = username;
    this.password = password;
    this.id = id;
    this.port = port;
  }

  public ProxyProvider(String host, String username, String password, int port) {
    this(DEFAULT_SERVICE_NAME, host, username, password, DEFAULT_ID, port);
  }

  public String getService_name() {
    return service_name;
  }

  public void setService_name(String service_name) {
    this.service_name = service_name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getHostName() {
    return host + ":" + port;
  }

  public Proxy generateChromeProxy() {
    Proxy proxy = new Proxy();
    proxy.setSocksProxy(getHostName());
    proxy.setHttpProxy(getHostName());
    proxy.setSslProxy(getHostName());
    if (!StrUtils.isNullEmpty(getUsername())) {
      proxy.setSocksUsername(getUsername());

      // we should only have a password if we have a username
      if (!StrUtils.isNullEmpty(getPassword())) {
        proxy.setSocksPassword(getPassword());
      }
    }
    return proxy;
  }

  public Proxy generateHtmlUnitProxy() {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy(getHostName());
    if (!StrUtils.isNullEmpty(getUsername())) {
      proxy.setSocksProxy(getHostName());
      proxy.setSocksUsername(getUsername());

      // we should only have a password if we have a username
      if (!StrUtils.isNullEmpty(getPassword())) {
        proxy.setSocksPassword(getPassword());
      }
    }
    return proxy;
  }

  @Override
  public String toString() {
    return "ProxyProvider{"
        + "service_name='"
        + service_name
        + '\''
        + ", host='"
        + host
        + '\''
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + ", id="
        + id
        + ", port="
        + port
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProxyProvider that = (ProxyProvider) o;
    return port == that.port
        && Objects.equals(host, that.host)
        && Objects.equals(username, that.username)
        && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, username, password, port);
  }
}
