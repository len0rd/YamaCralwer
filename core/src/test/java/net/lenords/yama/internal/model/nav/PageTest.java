package net.lenords.yama.internal.model.nav;

import java.util.HashMap;
import java.util.Map;

import net.lenords.yama.internal.conf.SeleniumDriverConf;
import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;
import net.lenords.yama.internal.conf.SeleniumDriverType;
import net.lenords.yama.internal.model.Page;
import net.lenords.yama.internal.model.extract.RegexPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageTest {

  @Test
  void holdsExtractors() {
    Page testPage =
        new Page(
            "PageTest",
            "https://www.zillow.com/jsonp/Hdp.htm?zpid=~#ID#~&fad=false&hc=false&lhdp=true&callback=YUI.Env.JSONP");

    assert testPage.getName().equals("PageTest");
    assert testPage.getExtractionPatterns() != null;
    assert testPage.getExtractionPatterns().isEmpty();

    // create a bunch of regex extractors, and add to the page:
    RegexPattern desc =
        new RegexPattern("Description")
            .add("<div class=\\\"notranslate zsg-content-item\\\">")
            .add("Description_Ad", ".*?")
            .add("<\\/div>");
    RegexPattern sqft =
        new RegexPattern("SqFt").add("\"area\":").add("Square_Feet", "[^'\"]*").add(",");
    RegexPattern bath =
        new RegexPattern("Bath").add("\"baths\":").add("Bathrooms", "[^'\"]*").add(",");
    RegexPattern beds =
        new RegexPattern("Beds").add("\"beds\":").add("Bedrooms", "[^'\"]*").add(",");
    RegexPattern prop =
        new RegexPattern("Prop Type")
            .add("\"PropertyType\", \"")
            .add("Property_Type", "[^'\"]*")
            .add("\"],");
    RegexPattern year =
        new RegexPattern("Year").add(",\"year\":").add("Year_Built", "[\\d,]+").add(",");
    RegexPattern cost =
        new RegexPattern("Price")
            .add("\"typeAndPrice\":\"For Rent: $")
            .add("Price", ".*?")
            .add("/mo\"");
    RegexPattern cp1 =
        new RegexPattern("Phone1")
            .add("Call: ")
            .add("Contact_Phone1", "[^<>]*?")
            .add("<\\/span>\\s*<\\/div>");
    RegexPattern lot =
        new RegexPattern("Lot").add("Lot:").add("Lot_Size", ".*?").add("acres<\\/li>");

    testPage.addExtractionPatterns(desc, sqft, bath, beds, prop, year, cost, cp1, lot);
    assert testPage.getExtractionPatterns() != null;
    assert !testPage.getExtractionPatterns().isEmpty();

    // make sure the get by name function works
    assert testPage.getExtractor("Description") != null;
    assert testPage.getExtractor("Phone1") != null;
    assert testPage.getExtractor("Prop Type") != null;
  }

}
