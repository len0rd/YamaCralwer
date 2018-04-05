package net.lenords.yama.model.extract;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExtractionResultTest {


  @Test
  void testGetLatest() {
    Map<String, String> m1 = new HashMap<>();
    m1.put("k1", "v1");
    m1.put("k2", "v2");
    m1.put("k3", null);

    Map<String, String> m2 = new HashMap<>();
    m2.put("k1", "V2");
    m2.put("k2", null);
    m2.put("k3", null);

    ExtractionResult result = new ExtractionResult(Arrays.asList(m1, m2));

    Map<String, String> latest = result.getLatest();

    Assertions.assertNotNull(latest);
    assert !latest.isEmpty();

    Assertions.assertEquals("V2", latest.get("k1"));
    Assertions.assertEquals("v2", latest.get("k2"));
    assert !latest.containsKey("k3");
  }

}
