package net.lenords.yama.model.data;

import java.util.HashMap;

public class DataNode extends HashMap<String, Object> {

  public String get(String key) {
    return (String) super.get(key);
  }

}

