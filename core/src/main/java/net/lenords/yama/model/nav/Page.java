package net.lenords.yama.model.nav;

import java.util.List;
import net.lenords.yama.model.request.CrawlerRequest;
import net.lenords.yama.model.actions.extract.ExtractAction;

public class Page {
  private String rawHtml;
  //the original request sent to the driver to process.
  private CrawlerRequest originalRequest;
  //created after the request is executed by the driver. Having the two requests
  //allows us to compare and see if we were redirected/cookie additions, etc.
  private CrawlerRequest resultingRequest;

  List<ExtractAction> extractActions;


}
