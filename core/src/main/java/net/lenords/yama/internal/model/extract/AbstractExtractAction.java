package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.conf.ExtractActionTrigger;
import net.lenords.yama.internal.model.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Basic definition for an action to extract data. There are two
 * main ways to extract data in Yama: Regex and By.
 *
 * <p>
 * <b>Regex:</b> ({@link RegexExtractAction}) Execute a Regular Expression against a given
 *    context. Generally the context executed against is the raw html returned from a page load
 * <p>
 * <b>By:</b> ({@link ByExtractAction}) Uses Selenium's By interface to enable powerful
 *    extraction options. These include extraction by id, css selector, class name, and xpath.
 *
 * @param <T> Context to run against.
 *
 * @author len0rd
 */
public abstract class AbstractExtractAction<T> implements Action<ExtractionResult> {
	private List<List<Action>> actions;

	public AbstractExtractAction() {
		// Storing action lists in another list allows this to be easily adjusted in the
		// event someone wants to add additional triggers
		//TODO: Make a container object for actions for Page, Extractor, and Crawler?
		actions = new ArrayList<>(ExtractActionTrigger.values().length);
		for (List<Action> list : actions) {
			list = new ArrayList<>();
			actions.add(list);
		}
	}

	public abstract void setActionSpecificContext(T actionSpecificContext);

	public abstract ExtractionPattern getExtractionPattern();

	public abstract ExtractionResult getExtractionResult();

	public abstract ExtractionResult run(Map<String, Object> context, T actionSpecificContext);

	public abstract void clearResult();

	public List<Action> getActions(ExtractActionTrigger trigger) {
		return this.actions.get(trigger.getIndex());
	}

	public void addActions(ExtractActionTrigger trigger, Action... action) {
		this.actions.get(trigger.getIndex()).addAll(Arrays.asList(action));
	}

	public List<Action> getBeforeExtractionActions() {
		return this.getActions(ExtractActionTrigger.BeforeExtraction);
	}

	public void addBeforeExtractionActions(Action... actions) {
		this.addActions(ExtractActionTrigger.BeforeExtraction, actions);
	}

	public List<Action> getAfterEachExtractionMatchActions() {
		return this.getActions(ExtractActionTrigger.AfterEachExtractionMatch);
	}

	public void addAfterEachExtractionMatchActions(Action... actions) {
		this.addActions(ExtractActionTrigger.AfterEachExtractionMatch, actions);
	}

	public List<Action> getNoExtractionMatchActions() {
		return this.getActions(ExtractActionTrigger.NoExtractionMatch);
	}

	public void addNoExtractionMatchActions(Action... actions) {
		this.addActions(ExtractActionTrigger.NoExtractionMatch, actions);
	}

	public List<Action> getAfterExtractionActions() {
		return this.getActions(ExtractActionTrigger.AfterExtraction);
	}

	public void addAfterExtractionActions(Action... actions) {
		this.addActions(ExtractActionTrigger.AfterExtraction, actions);
	}

}
