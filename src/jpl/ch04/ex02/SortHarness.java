package jpl.ch04.ex02;

/*
 * ComparableObjectをimplementする以外にもComparable<?> valueを宣言することも考えたが、
 * Comparableを使った場合、String、Integerなどの既存クラスをComparableをimplementするように再定義する必要がある。
 * 比較したいオブジェクトのクラス定義の外に比較方法を記述できるComparableObjectをimplementする方法を選択した。
 */
public abstract class SortHarness implements ComparableObject{
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
	
	public abstract int doComapre(Object o1, Object o2);

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
