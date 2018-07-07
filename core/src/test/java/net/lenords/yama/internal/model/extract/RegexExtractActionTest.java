package net.lenords.yama.internal.model.extract;

import net.lenords.yama.internal.conf.ExtractActionTrigger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegexExtractActionTest {

	@Test
	void triggerAdditionTest() {
		Assertions.assertEquals(ExtractActionTrigger.values().length, 4);
	}
}
