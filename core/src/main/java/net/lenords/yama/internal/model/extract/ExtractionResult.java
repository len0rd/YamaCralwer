package net.lenords.yama.internal.model.extract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lenords.yama.internal.model.ActionResult;

public class ExtractionResult implements ActionResult<List<Map<String, String>>> {
	private List<Map<String, String>> result;

	public ExtractionResult(List<Map<String, String>> result) {
		this.result = result;
	}

	@Override
	public void setResult(List<Map<String, String>> result) {
		this.result = result;
	}

	@Override
	public List<Map<String, String>> getResult() {
		return result;
	}

	@Override
	public boolean isEmpty() {
		return result == null || result.isEmpty();
	}

	public Map<String, String> getFirst() {
		return !isEmpty() ? result.get(0) : null;
	}

	public Map<String, String> getLast() {
		return !isEmpty() ? result.get(result.size() - 1) : null;
	}

	public Map<String, String> getLatest() {
		if (!isEmpty()) {
			//stream needs to be serial (go through list in order of extraction)
			return result.stream()
				.reduce(new HashMap<>(), (accumulated, nextMap) -> {
					nextMap.entrySet().stream().filter(entry -> entry.getValue() != null)
						.forEach(entry ->
							accumulated.put(entry.getKey(), entry.getValue()));
					return accumulated;
				});
		}
		return null;
	}

}
