package net.lenords.yama.internal.model.actions.extract;

import net.lenords.yama.internal.model.extract.ExtractionPattern;
import net.lenords.yama.internal.model.extract.ExtractionResult;
import net.lenords.yama.internal.model.extract.RegexPattern;

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
