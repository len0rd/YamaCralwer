package net.lenords.yama.internal.model.actions.extract;

import net.lenords.yama.internal.model.actions.Action;
import net.lenords.yama.internal.model.extract.ExtractionPattern;
import net.lenords.yama.internal.model.extract.ExtractionResult;

public interface ExtractAction<T> extends Action<T, ExtractionResult> {

	ExtractionPattern getExtractionPattern();

	ExtractionResult getExtractionResult();

	void clearResult();


}
