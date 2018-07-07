package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.conf.ExtractActionTrigger;
import net.lenords.yama.internal.model.Action;

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
	private List<Action>[] actions;


	public RegexExtractAction(RegexPattern pattern) {
		this.pattern = pattern;
		this.result = null;
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
	public void addAction(ExtractActionTrigger trigger, Action action) {

	}

	@Override
	public void addBeforeExtractionAction(Action action) {

	}

	@Override
	public void addAfterEachExtractionMatchAction(Action action) {

	}

	@Override
	public void addNoExtractionMatchAction(Action action) {

	}

	@Override
	public void addAfterExtractionAction(Action action) {

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
