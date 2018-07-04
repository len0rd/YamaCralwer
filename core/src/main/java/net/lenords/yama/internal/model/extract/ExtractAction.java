package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.model.Action;

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
public interface ExtractAction<T> extends Action<T, ExtractionResult> {

	ExtractionPattern getExtractionPattern();

	ExtractionResult getExtractionResult();

	void clearResult();


}
