package jpl.ch03.ex12;

public abstract class SortHarness {
	private Object[] values;
	private final SortMetrics curMetrics = new SortMetrics();
	
	public final SortMetrics sort(Object[] data) {
		values = data;
		curMetrics.init();
		doSort();
		return getMetrics();
	}
	
	public final SortMetrics getMetrics() {
		return curMetrics.clone();
	}
	
	protected final int getDataLength() {
		return values.length;
	}
	
	protected final Object probe(int i) {
		curMetrics.probeCnt++;
		return values[i];
	}
	
	protected final int compare(int i, int j) {
		curMetrics.compareCnt++;
		Object o1 = values[i];
		Object o2 = values[j];
		return doComapre(o1, o2);
	}
	
	protected final void swap(int i, int j) {
		curMetrics.swapCnt++;
		Object tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}

	protected abstract void doSort();
	
	/**
	 * 第１引数と第２引数を比較して、その結果を整数値で返す.
	 * 第１引数が第２引数より大きい時は正の数
	 * 第１引数と第２引数が等しい時は0.
	 * 第１引数が第２引数より小さい時は負の数を返す。
	 * @param o1
	 * @param o2
	 * @return 比較結果の整数値
	 */
	protected abstract int doComapre(Object o1, Object o2);

	final class SortMetrics implements Cloneable {
		private long probeCnt,
					compareCnt,
					swapCnt;
	
		public void init() {
			probeCnt = swapCnt = compareCnt = 0;
		}
	
		public long getProbeCnt() {
			return probeCnt;
		}
		
		public long getCompareCnt() {
			return compareCnt;
		}
		
		public long getSwapCnt() {
			return swapCnt;
		}
		
		public String toString() {
			return probeCnt + " probes " + 
					compareCnt + " compares " + 
					swapCnt + " swaps ";
		}
	
		public SortMetrics clone() {
			try {
				return (SortMetrics) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new InternalError(e.toString());
			}
		}

	}
}
