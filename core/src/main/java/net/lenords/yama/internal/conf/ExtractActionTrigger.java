package net.lenords.yama.internal.conf;

public enum ExtractActionTrigger implements ActionTrigger {
	BeforeExtraction(0),
	AfterEachExtractionMatch(1),
	NoExtractionMatch(2),
	AfterExtraction(3);

	private int index;

	ExtractActionTrigger(final int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}
}
