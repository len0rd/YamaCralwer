package net.lenords.yama.internal.model.extract;

/**
 * Execute a Regular Expression against a given context. Generally
 * the context executed against is the raw html returned from a page load
 *
 * @author len0rd
 */
public class RegexExtractAction implements ExtractAction<String> {
	private RegexPattern pattern;
	private ExtractionResult result;


	public RegexExtractAction(RegexPattern pattern) {
		this.pattern = pattern;
		this.result = null;
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
	public ExtractionResult run(String context) {
		result = pattern.run(context);
		return result;
	}

	@Override
	public String getName() {
		return pattern.getName();
	}
}
