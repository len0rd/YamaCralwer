package net.lenords.yama.util.datamanager.model.conf;

public class SshConnectionConfig extends ConnectionConfig {
  private String privateKey;

  public SshConnectionConfig(String host, String user, String pass, int port, boolean enabled,
      String privateKey) {
    super(host, user, pass, port, enabled);
    this.privateKey = privateKey;
  }

  public SshConnectionConfig(String host, String user, String pass, int port, boolean enabled) {
    super(host, user, pass, port, enabled);
  }

  public boolean hasPrivateKey() {
    return privateKey != null && !privateKey.isEmpty();
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  @Override
  public String toString() {
    return "SshConnectionConfig{" +
        "host='" + getHost() + '\'' +
        ", user='" + getUser() + '\'' +
        ", pass=" + getPass() + '\'' +
        "privateKey='" + privateKey + '\'' +
        "}";
  }
}
