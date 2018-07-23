package net.lenords.yama.internal.controller;

import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

import net.lenords.yama.internal.conf.SeleniumDriverConf;
import net.lenords.yama.internal.conf.SeleniumDriverType;
import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;
import net.lenords.yama.internal.model.Page;
import net.lenords.yama.internal.model.extract.RegexPattern;

public class PageControllerTest {

    @Test
    void runPage() {

        SeleniumDriverConf conf =
        new SeleniumDriverConf(
            SeleniumDriverType.CHROME,
            false,
            false,
            false,
            false,
            null,
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36",
            -1);
        SeleniumCrawlerDriver cd = new SeleniumCrawlerDriver(conf);

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

        //testPage.addExtractionPatterns(desc, sqft, bath, beds, prop, year, cost, cp1, lot);

        Map<String, Object> testContext = new HashMap<>();
        testContext.put("ID", "2090417095");
        // Map<String, Object> result = testPage.run(testContext, cd);
        // assert result != null;
        // assert !result.isEmpty();

        cd.close();
    }

     //TODO: FIXME
  @Test
  void replaceVarInBaseURL() {
    Page testPage =
        new Page(
            "PageTest",
            "https://www.zillow.com/jsonp/Hdp.htm?zpid=~#ID#~&fad=false&hc=false&lhdp=true&callback=YUI.Env.JSONP");

    Map<String, Object> testContext = new HashMap<>();
    testContext.put("ID", "2090417095");

    // testPage.findReplaceTokens(testContext);

    // Assertions.assertEquals(
    //     "https://www.zillow.com/jsonp/Hdp.htm?zpid=2090417095&fad=false&hc=false&lhdp=true&callback=YUI.Env.JSONP",
    //     testPage.getBuiltRequest().getBaseUrl(),
    //     "Failed to replace variable in page's base url");
    // Assertions.assertEquals(
    //     "https://www.zillow.com/jsonp/Hdp.htm?zpid=~#ID#~&fad=false&hc=false&lhdp=true&callback=YUI.Env.JSONP",
    //     testPage.getBaseRequest().getBaseUrl(),
    //     "Base request was modified when it shouldn't have been");
  }
}