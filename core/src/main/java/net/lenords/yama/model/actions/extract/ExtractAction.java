package net.lenords.yama.model.actions.extract;

import net.lenords.yama.model.actions.Action;
import net.lenords.yama.model.extract.ExtractionPattern;
import net.lenords.yama.model.extract.ExtractionResult;

public interface ExtractAction<T> extends Action<T, ExtractionResult> {

  ExtractionPattern getExtractionPattern();

  ExtractionResult getExtractionResult();


}
