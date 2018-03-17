package net.lenords.yama.model.extract;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegexPatterTest {

  @Test
  void  basicPattern() throws Exception {
    String matchAgainst = "<h2><a href=\"https://arstechnica.com/gadgets/2018/03/galaxy-s9-review-faster-specs-better-biometrics-in-a-familiar-package/\">Galaxy S9+ review—Faster specs, better biometrics in a familiar package</a></h2>";

    RegexPattern pattern = new RegexPattern("basicPattern");

    pattern.add(".*href=\"").add(new Extractor("link", "[^\"']*"))
        .add("\">").add(new Extractor("name", "[^<>]*"));
    pattern.build();


    Map<String, String> results = pattern.execute(matchAgainst);

    System.out.println(pattern.getGeneratedRegex());

    assert results != null;
    assert !results.isEmpty();
    Assertions.assertEquals("https://arstechnica.com/gadgets/2018/03/galaxy-s9-review-faster-specs-better-biometrics-in-a-familiar-package/", results.get("link"));
    Assertions.assertEquals("Galaxy S9+ review—Faster specs, better biometrics in a familiar package", results.get("name"));

  }

}
