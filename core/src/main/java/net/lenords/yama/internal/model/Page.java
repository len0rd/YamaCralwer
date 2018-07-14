package net.lenords.yama.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.lenords.yama.internal.conf.PageActionTrigger;
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
	private List<List<Action>> actions;

	public Page(String name, String baseURL) {
		this.name = name;
		this.extractionPatterns = new ArrayList<>();
		this.baseRequest = new CrawlerRequest(baseURL);
		// Storing action lists in another list allows this to be easily adjusted in the
		// event someone wants to add additional triggers
		//TODO: way to restrict size of this list to num elements in the PageActionTrigger enum?
		actions = new ArrayList<>(PageActionTrigger.values().length);
		for (List<Action> list : actions) {
			list = new ArrayList<>();
		}
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

	public List<Action> getActions(PageActionTrigger trigger) {
		return actions.get(trigger.getIndex());
	}

	public void addActions(PageActionTrigger trigger, Action... actions) {
		this.actions.get(trigger.getIndex()).addAll(Arrays.asList(actions));
	}

	public void setActions(PageActionTrigger trigger, List<Action> actions) {
		this.actions.set(trigger.getIndex(), actions);
	}

	public List<Action> getBeforeFetchActions() {
		return this.getActions(PageActionTrigger.BeforeFetch);
	}

	public void addBeforeFetchActions(Action... beforeFetchActions) {
		this.addActions(PageActionTrigger.BeforeFetch, beforeFetchActions);
	}

	public void setBeforeFetchActions(List<Action> beforeFetchActions) {
		this.setActions(PageActionTrigger.BeforeFetch, beforeFetchActions);
	}

	public List<Action> getAfterFetchActions() {
		return this.getActions(PageActionTrigger.AfterFetch);
	}

	public void addAfterFetchActions(Action... afterFetchActions) {
		this.addActions(PageActionTrigger.AfterFetch, afterFetchActions);
	}

	public void setAfterFetchActions(List<Action> afterFetchActions) {
		this.setActions(PageActionTrigger.AfterFetch, afterFetchActions);
	}

	public List<Action> getAfterExtractActions() {
		return this.getActions(PageActionTrigger.AfterExtract);
	}

	public void addAfterExtractActions(Action... afterExtractActions) {
		this.addActions(PageActionTrigger.AfterExtract, afterExtractActions);
	}

	public void setAfterExtractActions(List<Action> afterExtractActions) {
		this.setActions(PageActionTrigger.AfterExtract, afterExtractActions);
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
