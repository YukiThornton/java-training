package jpl.ch04.ex02;

public class SortInteger extends SortHarness {

	protected void doSort() {
		for (int i = 0; i < getDataLength(); i++) {
			for (int j = i + 1; j < getDataLength(); j++) {
				if (compare(i, j) > 0) {
					swap(i, j);
				}
			}
		}
	}

	public int doComapre(Object o1, Object o2) {
		int int1 = ((Integer)o1).intValue();
		int int2 = ((Integer)o2).intValue();
		
		if (int1 == int2) 
			return 0;
		else
			return (int1 < int2 ? -1 : 1);
	}

}
