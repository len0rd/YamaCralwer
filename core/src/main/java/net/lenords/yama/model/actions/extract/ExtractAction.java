package net.lenords.yama.model.actions.extract;

import net.lenords.yama.crawler.CrawlerDriver;
import net.lenords.yama.model.actions.Action;

public interface ExtractAction extends Action<CrawlerDriver> {

  String getExtractorAsString();

  String getActionResultAsString();


}
