package jpl.ch09.ex04;

/*
 * p.192 練習問題 9.4
 * コードを書かずにどの式が正しくないか判断する
 * 正しい式であれば、その型と値を考える
 */
public class Answer {

	public static void main(String[] args) {
		/*
		 *  3 << 2L - 1
		 *  
		 *  (予想)
		 *  答え: int型の6
		 *  理由:
		 *  ① longの2 - intの1 = longの1
		 *  ② intの3 << longの1= intの6
		 *  
		 *  (実際)
		 *  答え: int型の6
		 */
		int result1 = 3 << 2L - 1;
		System.out.println("3 << 2L - 1");
		System.out.println("答え: int型の" + result1);

		/*
		 *  (3 << 2L) - 1
		 *  
		 *  (予想)
		 *  答え: int型の11
		 *  理由:
		 *  ① intの3 << 2L = intの12
		 *  ② intの12 - intの1 = 11
		 *  
		 *  (予想)
		 *  答え: int型の11
		 */
		int result2 = (3 << 2L) - 1;
		System.out.println("(3 << 2L) - 1");
		System.out.println("答え: int型の" + result2);

		/*
		 *  10 < 12 == 6 > 17
		 *  
		 *  (予想)
		 *  答え: boolean型のfalse
		 *  理由:
		 *  ① 10 < 12 => true
		 *  ② 6 > 17 => false
		 *  ③ true == false => false
		 *  
		 *  (実際)
		 *  答え: boolean型のfalse
		 */
		boolean result3 = 10 < 12 == 6 > 17;
		System.out.println("10 < 12 == 6 > 17");
		System.out.println("答え: boolean型の" + result3);
		
		/*
		 *  10 << 12 == 6 >> 17
		 *  
		 *  (予想)
		 *  答え: boolean型のfalse
		 *  理由:
		 *  ① 10 << 12 => 0xa000
		 *  ② 6 >> 17 => 0
		 *  ③ 0xa000 == 0 => false
		 *  
		 *  (実際)
		 *  答え: boolean型のfalse
		 */
		boolean result4 = 10 << 12 == 6 >> 17;
		System.out.println("10 << 12 == 6 >> 17");
		System.out.println("答え: boolean型の" + result4);
		
		/*
		 *  13.5e-1 % Float.POSITIVE_INFINITY
		 *  
		 *  (予想)
		 *  答え: double型の13.5e-1
		 *  理由:
		 *  ① doubleの13.5e-1 % Double.POSITIVE_INFINITY => doubleの13.5e-1
		 *  
		 *  (実際)
		 *  答え: double型の13.5e-1
		 */
		double result5 = 13.5e-1 % Float.POSITIVE_INFINITY;
		System.out.println("13.5e-1 % Float.POSITIVE_INFINITY");
		System.out.println("答え: double型の" + result5);
		
		/*
		 *  Float.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY
		 *  
		 *  (予想)
		 *  答え: double型のNaN
		 *  理由:
		 *  ① Double.POSITIVE_INFINITY + Double.NEGATIVE.INFINITY => doubleのNaN
		 *  
		 *  (実際)
		 *  答え: double型のNaN
		 */
		double result6 = Float.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY;
		System.out.println("Float.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY");
		System.out.println("答え: double型の" + result6);
		
		/*
		 *  Double.POSITIVE_INFINITY - Float.NEGATIVE_INFINITY
		 *  
		 *  (予想)
		 *  答え: double型のDouble.POSITIVE_INFINITY
		 *  理由:
		 *  ① Double.POSITIVE_INFINITY - Double.NEGATIVE.INFINITY => Double.POSITIVE_INFINITY
		 *  
		 *  (実際)
		 *  答え: double型のNaN
		 */
		double result7 = Double.POSITIVE_INFINITY - Float.NEGATIVE_INFINITY;
		System.out.println("Double.POSITIVE_INFINITY - Float.NEGATIVE_INFINITY");
		if (result7 > 0)
			System.out.println("答え: double型のPositive " + result7);
		
		/*
		 *  0.0 / -0.0 == -0.0 / 0.0
		 *  
		 *  (予想)
		 *  答え: boolean型のfalse
		 *  理由:
		 *  ① doubleの0.0 / doubleの-0.0 => NaN
		 *  ② doubleの-0.0 / doubleの0.0 => NaN
		 *  ③ NaN == NaN => false (p.166)
		 *  
		 *  (実際)
		 *  答え: boolean型のfalse
		 */
		boolean result8 = 0.0 / -0.0 == -0.0 / 0.0;
		System.out.println("0.0 / -0.0 == -0.0 / 0.0");
		System.out.println("答え: boolean型の" + result8);
		
		/*
		 *  Integer.MAX_VALUE - Integer.MIN_VALUE
		 *  
		 *  (予想)
		 *  答え: int型の0
		 *  理由:
		 *  ① Max値のあとはMin値まで戻る
		 *  
		 *  (実際)
		 *  答え: int型の-1
		 */
		int result9 = Integer.MAX_VALUE - Integer.MIN_VALUE;
		System.out.println("Integer.MAX_VALUE - Integer.MIN_VALUE");
		System.out.println("答え: int型の" + result9);
		
		/*
		 *  Long.MAX_VALUE + 5
		 *  
		 *  (予想)
		 *  答え: long型の-9223372036854775804(Long.MIN_VALUE + 4)
		 *  理由:
		 *  ① Max値のあとはMin値まで戻る
		 *  
		 *  (実際)
		 *  答え: long型の-9223372036854775804(Long.MIN_VALUE + 4)
		 */
		long result10 = Long.MAX_VALUE + 5;
		System.out.println("Long.MAX_VALUE + 5");
		System.out.println("答え: long型の" + result10);
		
		/*
		 *  (short)5 * (byte)10
		 *  
		 *  (予想)
		 *  答え: int型の50
		 *  理由:
		 *  ① 整数値の算術計算式はintになる（p.187）
		 *  
		 *  (実際)
		 *  答え: int, short, byte型の50 (暗黙で変換が行われてしまうため、式の型はわからなかった)
		 */
		byte result11 = (short)5 * (byte)10;
		System.out.println("(short)5 * (byte)10");
		System.out.println("答え: int, short, byte型の" + result11);
		
		/*
		 *  (i < 15 ? 1.72e3f: 0)
		 *  
		 *  (予想)
		 *  答え: iが15以下であればfloat型の1.72e3f, iが15より大きければfloat型の0
		 *  理由:
		 *  ① 第２式と第３式の値がともに数値の基本データ型であり、intはfloatに格上げされる（p.183）
		 *  
		 *  (実際)
		 *  答え: iが15以下であればfloat型の1.72e3f, iが15より大きければfloat型の0
		 */
		int i = 3;
		float result12= (i < 15 ? 1.72e3f: 0);
		System.out.println("(i < 15 ? 1.72e3f: 0)");
		System.out.println("答え: float型の" + result12);
		
		/*
		 *  i++ + i++ + --i // iの初期値は3
		 *  
		 *  (予想)
		 *  答え: int型の11
		 *  理由:
		 *  ① 後置演算子が行われる。（p.191）
		 *  	i++ => 3 (i=>4), 
		 *  	i++ => 4 (i=>5)
		 *  ② 単項演算子が行われる。
		 *  	--i => 4 (i=>4)
		 *  ③ 加算が行われる
		 *  	3 + 4 + 4 => 11
		 *  
		 *  (実際)
		 *  答え: int型の11
		 */
		int result13 = (i++ + i++ + --i);
		System.out.println("i++ + i++ + --i");
		System.out.println("答え: int型の" + result13);
		
	}

}
