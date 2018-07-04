package net.lenords.yama.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.lenords.yama.internal.model.actions.Action;
import net.lenords.yama.internal.model.extract.ExtractionPattern;
import net.lenords.yama.internal.model.request.CrawlerRequest;

/**
 * Represents a page structure to crawl
 *
 * @author len0rd
 * @since 2018-03-21
 */
public class Page {
	private String name;
	//the base, tokenized request
	private final CrawlerRequest baseRequest;
	private List<ExtractionPattern> extractionPatterns;
	private List<Action> beforeFetchActions, afterFetchActions, afterExtractActions;

	public Page(String name, String baseURL) {
		this.name = name;
		this.extractionPatterns = new ArrayList<>();
		this.baseRequest = new CrawlerRequest(baseURL);
		this.beforeFetchActions  = new ArrayList<>();
		this.afterFetchActions   = new ArrayList<>();
		this.afterExtractActions = new ArrayList<>();
	}

	/**
	 * This request defines the request structure of this page. Setup tokens (ie: ~#TOKEN_NAME#~)
	 * within the base url and get parameters of this request. At load time they will be replaced by
	 * the current context map, with values that have matching key names (case-sensitive)
	 *
	 * @return The base request for this page
	 */
	public CrawlerRequest getBaseRequest() {
		return baseRequest;
	}

	public Page addExtractionPatterns(ExtractionPattern... patterns) {
		if (patterns!= null) {
			extractionPatterns.addAll(Arrays.asList(patterns));
		}
		return this;
	}

	public void setExtractionPatterns(List<ExtractionPattern> extractionPatterns) {
		this.extractionPatterns = extractionPatterns;
	}

	public List<Action> getBeforeFetchActions() {
		return beforeFetchActions;
	}

	public void setBeforeFetchActions(List<Action> beforeFetchActions) {
		this.beforeFetchActions = beforeFetchActions;
	}

	public List<Action> getAfterFetchActions() {
		return afterFetchActions;
	}

	public void setAfterFetchActions(List<Action> afterFetchActions) {
		this.afterFetchActions = afterFetchActions;
	}

	public List<Action> getAfterExtractActions() {
		return afterExtractActions;
	}

	public void setAfterExtractActions(List<Action> afterExtractActions) {
		this.afterExtractActions = afterExtractActions;
	}

	public ExtractionPattern getExtractor(String extractorName) {
		return extractionPatterns
			.stream()
			.filter(extractionPattern -> extractionPattern.getName().equals(extractorName))
			.findFirst()
			.orElse(null);
	}

	public List<ExtractionPattern> getExtractionPatterns() {
		return extractionPatterns;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
