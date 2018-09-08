package net.lenords.yama.internal.model.extract;

import java.util.Map;

/**
 * Execute a Regular Expression against a given context. Generally
 * the context executed against is the raw html returned from a page load
 *
 * @author len0rd
 */
public class RegexExtractAction extends AbstractExtractAction<String> {
	private RegexPattern pattern;
	private ExtractionResult result;


	public RegexExtractAction(RegexPattern pattern) {
		super();
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
