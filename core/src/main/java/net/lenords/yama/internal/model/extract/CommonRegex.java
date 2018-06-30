package net.lenords.yama.internal.model.extract;


/**
 * Contains a set of frequently used regular expressions, for data extraction.
 *
 * @author len0rd
 */
public enum CommonRegex {
  NON_HTML_TAG {
    @Override
    public String getRegex() {
      return "[^<>]*";
    }
  },
  NON_QUOTES {
    @Override
    public String getRegex() {
      return "[^\"']*";
    }
  },
  /**
   * Matches a North-American phone number, as per the North-American-Numbering Plan
   * https://en.wikipedia.org/wiki/North_American_Numbering_Plan
   */
  NA_PHONE_NUM {
    @Override
    public String getRegex() {
      return "[(]?\\s*[2-9]\\d{2}[^A-z0-9]*?[2-9]\\d{2}[^A-z0-9]*?\\d{4}";
    }
  },
  /**
   * Matches everything within an HTML tag, including the l/r angle brackets.
   * eg: %lt;a href-"some-url.com"&gt; will all be captured
   */
  HTML_TAG {
    @Override
    public String getRegex() {
      return "<[^<>]*>";
    }
  },
  /**
   * This regex is RFC 5322 compliant, and was sourced from:
   * www.emailregex.com
   */
  EMAIL_ADDRESS {
    @Override
    public String getRegex() {
      return "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    }
  },
  LAZY_ALL_NEWLINE {
    @Override
    public String getRegex() {
      return "(?s).*?";
    }
  },
  CANADA_ZIP {
    @Override
    public String getRegex() {
      return "[A-z]\\d[A-z][\\s-]?\\d[A-z]\\d";
    }
  };


  public abstract String getRegex();
}
