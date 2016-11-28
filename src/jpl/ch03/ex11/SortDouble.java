package jpl.ch03.ex11;

/*
 * Answer to 3.11
 * 
 * SortMatricsのフィールドが全てpublicだったため、
 * SortDoubleクラスのサブクラスも自由にそれらのフィールドの読み書きができてしまう。
 * よってSortMatricsクラスをSortDoubleクラスのネストクラスとして記述し、
 * フィールドをprivateに変更し、それらのgetterを作成した
 */
public abstract class SortDouble {
	private double[] values;
	private final SortMetrics curMetrics = new SortMetrics();
	
	public final SortMetrics sort(double[] data) {
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
	
	protected final double probe(int i) {
		curMetrics.probeCnt++;
		return values[i];
	}
	
	protected final int compare(int i, int j) {
		curMetrics.compareCnt++;
		double d1 = values[i];
		double d2 = values[j];
		if (d1 == d2) 
			return 0;
		else
			return (d1 < d2 ? -1 : 1);
	}
	
	protected final void swap(int i, int j) {
		curMetrics.swapCnt++;
		double tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}

	protected abstract void doSort();

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
