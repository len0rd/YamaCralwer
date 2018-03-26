package net.lenords.yama.model.extract;

import java.util.List;
import java.util.Map;
import net.lenords.yama.model.actions.ActionResult;

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

  public Map<String, String> getFirstResult() {
    return result != null && !result.isEmpty() ? result.get(0) : null;
  }

  public Map<String, String> getLastResult() {
    return result != null && !result.isEmpty() ? result.get(result.size()-1) : null;
  }
}
