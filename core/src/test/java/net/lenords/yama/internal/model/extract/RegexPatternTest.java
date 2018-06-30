package net.lenords.yama.internal.model.extract;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegexPatternTest {

  @Test
  void basicPattern() {
    String matchAgainst = "<h2><a href=\"https://arstechnica.com/gadgets/2018/03/galaxy-s9-review-faster-specs-better-biometrics-in-a-familiar-package/\">Galaxy S9+ review—Faster specs, better biometrics in a familiar package</a></h2>";

    RegexPattern pattern = new RegexPattern("basicPattern");

    pattern.add(".*href=\"").add(new RegexExtractor("link", "[^\"']*"))
        .add("\">").add(new RegexExtractor("name", "[^<>]*"));
    pattern.build();


    ExtractionResult results = pattern.execute(matchAgainst);

    System.out.println(pattern.getGeneratedRegex());

    assert results != null;
    assert !results.getResult().isEmpty();
    Assertions.assertEquals("https://arstechnica.com/gadgets/2018/03/galaxy-s9-review-faster-specs-better-biometrics-in-a-familiar-package/", results.getFirst().get("link"));
    Assertions.assertEquals("Galaxy S9+ review—Faster specs, better biometrics in a familiar package", results.getFirst().get("name"));

  }

  @Test
  void usingCommonExtractors() {
    String matchAgainstSuccess = "Hello There! General Kenobi! You are a bold one! Here's my "
        + "email: grievous.general@bionic-man.net";

    RegexPattern emailMatcher = new RegexPattern("emailMatcher");
    emailMatcher.add(".*?").add(new RegexExtractor("name", "(K|k)enobi")).add(".*?")
        .add(new RegexExtractor("email", CommonRegex.EMAIL_ADDRESS));

    String regex = emailMatcher.build();

    System.out.println(regex);

    ExtractionResult results = emailMatcher.execute(matchAgainstSuccess);

    assert results != null;
    assert !results.getResult().isEmpty();
    Assertions.assertEquals("Kenobi", results.getFirst().get("name"));
    Assertions.assertEquals("grievous.general@bionic-man.net", results.getFirst().get("email"));

  }

  @Test
  void runTidiers() {
    String matchAgainst = "<h2><a href=\"https://arstechnica.com/gadgets/2018/03/galaxy-s9-review-faster-specs-better-biometrics-in-a-familiar-package/\">  Galaxy S9+ review—Faster specs, better biometrics in a familiar package  </a></h2>";

    RegexPattern tidyTest = new RegexPattern("tidyTest");
    tidyTest.add("<h2>").add((new RegexExtractor("articleTitle", ".*?")).stripHtml().trim())
        .add("</h2>");

    String regex = tidyTest.build();
    System.out.println(regex);

    ExtractionResult results = tidyTest.execute(matchAgainst);

    assert results != null;
    assert !results.getResult().isEmpty();
    Assertions.assertEquals("Galaxy S9+ review—Faster specs, better biometrics in a familiar package", results.getFirst().get("articleTitle"));

  }

  @Test
  void weirdRegex() {
    String matchAgainst = "><div id=\\\"home-description-fav-note\\\" class=\\\"hide zsg-content-component zsg-annotation-area prop-mod prop-notes\\\"><h4>My Notes<\\/h4><span id=\\\"fav-note-text\\\" class=\\\"prop-mod-bd\\\" data-zpid=\\\"2106124789\\\"><\\/span><span class=\\\"prop-notes-edit edit-note\\\"><a id=\\\"edit-note-btn\\\" class=\\\"show-lightbox\\\" href=\\\"#add-note-code\\\"> Edit<\\/a><\\/span><\\/div><\\/div><div class=\\\"zsg-lg-2-3 zsg-md-1-1 hdp-header-description\\\"><div class=\\\"zsg-content-component\\\"><div class=\\\"notranslate zsg-content-item\\\">JUST RENOVATED 3 bedroom in Golden Hill! Features an open floor plan, Air Conditioning and heat, spacious living room, hardwood floors throughout, renovated kitchen with granite counter tops, stainless steel appliances, large bedrooms and washer and dryer IN UNIT!! This is one home you do not want to miss! <br\\/>Close to Balboa Park, South Park and Downtown! Complex is gated with friendly neighbors and lots of street parking. Walk to Golden Hill farmers market every Saturday! Great restaurants and shops nearby, convenient freeway access.<br\\/>Rent is $2,500 per month with a security deposit of $2,500. Water &amp; Trash is included. SDG&amp;E is tenants responsibility. Cats allowed with a $500 deposit. No dogs allowed.<\\/div><\\/div><\\/div><div id=\\\"fios-scroll-tracker\\\"><\\/div><div class=\\\"hdp-facts zsg-content-component z-moreless\\\"><div class=\\\"hdp-facts-expandable-container clear\\\"><p class=\\\"hdp-fact-category-title\\\">Facts and Features<\\/p><div class=\\\"zsg-g zsg-g_gutterless\\\"><div class=\\\"zsg-lg-1-3 zsg-md-1-2\\\"><div class=\\\"hdp-fact-ataglance-container zsg-media\\\"><div class=\\\"zsg-media-img\\\"><span class=\\\"hdp-fact-ataglance-icon zsg-icon-buildings\\\"><\\/span><\\/div><div class=\\\"zsg-media-bd\\\"><p class=\\\"hdp-fact-ataglance-heading\\\">Type<\\/p><div class=\\\"hdp-fact-ataglance-value\\\">Apartment<\\/div><\\/div><";

    RegexPattern desc = new RegexPattern("Description").add("<div class=\\\\\\\"notranslate zsg-content-item\\\\\\\">").add("Description_Ad", ".*?").add("<\\\\\\/div>");

    ExtractionResult result = desc.buildAndExecute(matchAgainst);
    assert !result.isEmpty();
    System.out.println(result.getFirst().get("Description_Ad"));
  }

}
