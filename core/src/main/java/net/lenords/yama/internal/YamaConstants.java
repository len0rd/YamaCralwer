package net.lenords.yama.internal;


import java.util.regex.Pattern;

/**
 * Static objects commonly used across the code base
 *
 * @author len0rd
 * @since 2018-06-29
 */
public class YamaConstants {

	// compiled pattern of what a token in a RegexExtractor, or url looks like
	public static final Pattern TOKEN_PATTERN = Pattern.compile("~#(.+?)#~");

	// this map key stays in the current context for the duration of a crawl. It stores
	// a reference to the current CrawlerController, similar to Screen-scraper's "session" variable
	public static final String CURRENT_CRAWLER_CONTEXT_KEY = "crawler";

	// this map key goes into the current context and stores a reference to the current
	// PageController for that context. This is akin to Screen-scraper's "scrapeableFile" variable
	// that is accessible from scripts.
	public static final String CURRENT_PAGE_CONTEXT_KEY = "page";


}
