package net.lenords.yama.internal.controller;

import net.lenords.yama.internal.YamaConstants;
import net.lenords.yama.internal.crawler.Crawler;
import net.lenords.yama.internal.crawler.CrawlerDriver;
import net.lenords.yama.internal.crawler.SeleniumCrawlerDriver;
import net.lenords.yama.internal.model.Page;
import net.lenords.yama.internal.model.extract.ByPattern;
import net.lenords.yama.internal.model.extract.ExtractionPattern;
import net.lenords.yama.internal.model.extract.ExtractionResult;
import net.lenords.yama.internal.model.extract.RegexPattern;
import net.lenords.yama.internal.model.request.CrawlerRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * And actual executable instance of a {@link Page}. Invoked by the {@link Crawler} and provided
 * with the current context and a CrawlerDriver to use.
 *
 * @author len0rd
 * @since 2018-06-21
 */
public class PageController {

	//core members:
	private Page page; //TODO::repurcussions of parallelizing model?
	private PageCallbackManager pageCallbackManager;

	//helper members
	private CrawlerRequest lastBuiltRequest;
	private boolean redirectDetected;
	private String rawHtml;
	private List<ExtractionResult> extractionResults;

	public PageController(Page page, PageCallbackManager pageCallbackManager) {
		this.page = page;
		this.pageCallbackManager = pageCallbackManager;
	}

	public Map<String, Object> run(Map<String, Object> context, CrawlerDriver cd) {
		fetch(context, cd);
		context.putAll(extract(cd));
		return context;
	}

	private void fetch(Map<String, Object> context, CrawlerDriver cd) {
		replaceURLTokens(context);
		this.rawHtml = cd.requestAndGet(lastBuiltRequest);
	}

	/**
	 * Runs all ExtractActions associated with this page. This is automatically called by the {@link
	 * #run(Map, CrawlerDriver)} method. NO NEED to call manually unless you've manually modified
	 * something about the driver or RawHtml source within the pageController.
	 *
	 * @param cd The current CrawlerDriver, on which this page was just loaded
	 */
	private Map<String, Object> extract(CrawlerDriver cd) {
		//TODO::This sucksssssssssss

		// Run all extractors
		// these need to be run in order
		final Map<String, Object> lastResultsOfAllExtractors = new HashMap<>();

		for (ExtractionPattern pattern : page.getExtractionPatterns()) {
			ExtractionResult result = null;
			if (pattern instanceof RegexPattern) {
				result = ((RegexPattern) pattern).run(rawHtml);
			} else if (pattern instanceof ByPattern) {
				result = ((ByPattern) pattern).run((SeleniumCrawlerDriver) cd);
			}
			
			if (result != null && !result.isEmpty()) {
				lastResultsOfAllExtractors.putAll(result.getLatest());
			}
		}

		return lastResultsOfAllExtractors;
	}


	/**
	 * Find and replace tokens (ie: ~#Variable_Name#~) in the base request given the current crawler context.
	 * For the example token (~#Variable_Name#~), 'Variable_Name' will be found in the current context and its value
	 * will be substituted for ~#Variable_Name#~ in the built url.
	 *
	 * @param context Current crawler context
	 */
	private void replaceURLTokens(Map<String, Object> context) {
		// TODO: do we need deep copy/clone here? possibly for the http param list
		lastBuiltRequest = new CrawlerRequest(page.getBaseRequest());
		if (lastBuiltRequest.hasTokens()) {
			lastBuiltRequest.setBaseUrl(replaceTokensInString(context, lastBuiltRequest.getBaseUrl()));

			if (lastBuiltRequest.hasTokens()) { // if the request still has tokens

				// then we need to replace all tokens in key value pairs:

				lastBuiltRequest
					.getGetParams()
					.forEach(
						param -> {
							param.setKey(replaceTokensInString(context, param.getKey()));
							param.setValue(replaceTokensInString(context, param.getValue()));
						});
			}
		}
	}

	private String replaceTokensInString(Map<String, Object> context, String replaceIn) {
		Matcher tokenMatcher = YamaConstants.TOKEN_PATTERN.matcher(replaceIn);
		while (tokenMatcher.find()) {
			String keyName = tokenMatcher.group(1);
			if (context.containsKey(keyName)) {
				replaceIn = replaceIn.replace("~#" + keyName + "#~", context.get(keyName).toString());
			}
		}
		return replaceIn;
	}

	public interface PageCallbackManager {
		void onBeforePageLoad();

		void onAfterPageCrawled();
	}
}
