package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.conf.ExtractActionTrigger;
import net.lenords.yama.internal.model.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Execute a Regular Expression against a given context. Generally
 * the context executed against is the raw html returned from a page load
 *
 * @author len0rd
 */
public class RegexExtractAction implements ExtractAction<String> {
	private RegexPattern pattern;
	private ExtractionResult result;
	private List<List<Action>> actions;


	public RegexExtractAction(RegexPattern pattern) {
		this.pattern = pattern;
		this.result = null;

		// Storing action lists in another list allows this to be easily adjusted in the
		// event someone wants to add additional triggers
		//TODO: Make a container object for actions for Page, Extractor, and Crawler?
		actions = new ArrayList<>(ExtractActionTrigger.values().length);
		for (List<Action> list : actions) {
			list = new ArrayList<>();
		}
	}

	@Override
	public void setActionSpecificContext(String actionSpecificContext) {
		
	}

	@Override
	public ExtractionPattern getExtractionPattern() {
		return pattern;
	}

	@Override
	public ExtractionResult getExtractionResult() {
		return result;
	}

	@Override
	public void clearResult() {
		result = null;
	}

	@Override
	public List<Action> getActions(ExtractActionTrigger trigger) {
		return this.actions.get(trigger.getIndex());
	}

	@Override
	public void addActions(ExtractActionTrigger trigger, Action... action) {
		this.actions.get(trigger.getIndex()).addAll(Arrays.asList(action));
	}

	@Override
	public List<Action> getBeforeExtractionActions() {
		return this.getActions(ExtractActionTrigger.BeforeExtraction);
	}

	@Override
	public void addBeforeExtractionActions(Action... actions) {
		this.addActions(ExtractActionTrigger.BeforeExtraction, actions);
	}

	@Override
	public List<Action> getAfterEachExtractionMatchActions() {
		return this.getActions(ExtractActionTrigger.AfterEachExtractionMatch);
	}

	@Override
	public void addAfterEachExtractionMatchActions(Action... actions) {
		this.addActions(ExtractActionTrigger.AfterEachExtractionMatch, actions);
	}

	@Override
	public List<Action> getNoExtractionMatchActions() {
		return this.getActions(ExtractActionTrigger.NoExtractionMatch);
	}

	@Override
	public void addNoExtractionMatchActions(Action... actions) {
		this.addActions(ExtractActionTrigger.NoExtractionMatch, actions);
	}

	@Override
	public List<Action> getAfterExtractionActions() {
		return this.getActions(ExtractActionTrigger.AfterExtraction);
	}

	@Override
	public void addAfterExtractionActions(Action... actions) {
		this.addActions(ExtractActionTrigger.AfterExtraction, actions);
	}

	@Override
	public ExtractionResult run(Map<String, Object> context, String actionSpecificContext) {
		result = pattern.run(actionSpecificContext);
		return result;
	}

	@Override
	public ExtractionResult run(Map<String, Object> context) {
		return null;
	}

	@Override
	public String getName() {
		return pattern.getName();
	}
}
