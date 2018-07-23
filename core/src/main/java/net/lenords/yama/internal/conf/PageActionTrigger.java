package net.lenords.yama.internal.conf;

public enum PageActionTrigger implements ActionTrigger{
	BeforeFetch(0),
	AfterFetch(1),
	AfterExtract(2);

	private int index;

	PageActionTrigger(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
