package net.lenords.yama.util.lang;

public class StrUtils {

  public static String trimToNull(String s) {
    if (s != null) {
      s = s.trim();
      s = s.isEmpty() ? null : s;
    }
    return s;
  }

  public static boolean isNullEmpty(String s) {
    return s == null ||
           s.trim().isEmpty();
  }

}
