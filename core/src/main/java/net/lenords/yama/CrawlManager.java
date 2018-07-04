package net.lenords.yama;

import net.lenords.yama.internal.model.Crawler;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * Yama public API
 *
 * @author len0rd
 * @since 2018-06-29
 */
public interface CrawlManager {

	void importCrawler(String filename);

	void importCrawler(Crawler crawler);

	void runCrawler(String crawlerName);

	void runCrawler(Crawler crawler);

	Properties globalProperties();

	Optional<Crawler> crawler(String crawlerName);

	Set<Crawler> activeCrawlers();

}
