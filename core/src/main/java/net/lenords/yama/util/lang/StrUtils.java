package net.lenords.yama.util.lang;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.lenords.yama.model.extract.CommonRegex;
import org.apache.commons.text.StringEscapeUtils;

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

  public static boolean isNullEmpty(Object o) {
    return o == null || (o instanceof String && ((String) o).trim().isEmpty());
  }

  public static String pretty(String toPretty, List<String> regexRemoveFromStr) {
    toPretty = trimToNull(toPretty);
    if (!isNullEmpty(toPretty) && regexRemoveFromStr != null ) {
      for (String remove : regexRemoveFromStr) {
        toPretty = toPretty.replaceAll(remove, "");
      }
    }
    return toPretty;
  }

  /**
   * Test if a string contains a match to the given Regex. Optional case-sensitivity and Regex
   * capture group.
   *
   * @param s String to be tested
   * @param Regex String of regular expression to be applied to 's'
   * @param caseSensitive Whether the pattern should be case-sensitive(true) or not
   * @param retGroup Integer of the match group from the regular expression to return (default is
   * 0)
   * @return String of the match, if any, null otherwise
   */
  public static String regexGet(String s, String Regex, boolean caseSensitive, int retGroup) {
    String ret = null;
    if (s != null && !s.isEmpty()) {
      Pattern p;
      if (caseSensitive) {
        p = Pattern.compile(Regex);
      } else {
        p = Pattern.compile(Regex, Pattern.CASE_INSENSITIVE);
      }
      Matcher m = p.matcher(s);
      if (m.find()) {
        try {
          ret = m.group(retGroup);
        } catch (Exception e) {
          ret = m.group();
        }
      }
    }
    return ret;
  }

  public static String regexGet(String s, String Regex, boolean caseSensitive) {
    return regexGet(s, Regex, caseSensitive, 0);
  }

  public static String stripHtml(String s) {
    if (s != null) {
      s = s.replaceAll(CommonRegex.HTML_TAG.getRegex(), "");
      s = StringEscapeUtils.unescapeHtml4(s);
    }
    return s;
  }


}
