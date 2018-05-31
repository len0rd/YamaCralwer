package net.lenords.yama.model.extract;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegexExtractorTest {

  @Test
  void runTidies() {
    String runAgainst =
        " &lt;a href=///profile//user3818206/// class=/profile-name-link/&gt;  JScott Jones &lt;//a&gt; ";

    // Extractor with all tidiers
    RegexExtractor re = new RegexExtractor("asdf", "asdf").trim().convertEntities().stripHtml();

    String tidyResult = re.runTidiers(runAgainst);
    Assertions.assertEquals("JScott Jones", tidyResult);
  }
}
