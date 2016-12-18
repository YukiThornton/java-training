package jpl.ch09.ex02;

/*
 * p.183 練習問題 9.2
 * ビット操作演算子だけを使用し、渡されたintで1となっているビット数を調べるメソッドを作成する
 * 公開されているアルゴリズムと比較する
 */
public class BitCounter {

	public static void main(String[] args) {
		System.out.println(BitCounter.count(0xe));
	}

	public static int count(int target) {
		int count = 0;
		int mask = 0xf;
		while (target != 0) {
			count += countFourBits(mask & target);
			target = target >>> 4;		
		}
		return count;
	}
	
	private static int countFourBits(int target) {
		int count = 0;
		int[] squareNums = {8, 4, 2, 1};
		for (int squareNum : squareNums) {
			if (target / squareNum >= 1)
				count++;			
			target = target % squareNum;
		}
		return count;
	}
}
