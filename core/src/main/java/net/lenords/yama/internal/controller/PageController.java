package net.lenords.yama.internal.controller;

import net.lenords.yama.internal.YamaConstants;
import net.lenords.yama.internal.crawler.Crawler;
import net.lenords.yama.internal.crawler.CrawlerDriver;
import net.lenords.yama.internal.model.Page;
import net.lenords.yama.internal.model.request.CrawlerRequest;

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
	private Page page;
	private PageCallbackManager pageCallbackManager;

	//helper members
	private CrawlerRequest lastBuiltRequest;
	private boolean redirectDetected;
	private String rawHtml;

	public PageController(Page page, PageCallbackManager pageCallbackManager) {
		this.page = page;
		this.pageCallbackManager = pageCallbackManager;
	}


	private void fetch(Map<String, Object> context, CrawlerDriver cd) {
		replaceURLTokens(context);
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
