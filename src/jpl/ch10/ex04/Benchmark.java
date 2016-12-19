package jpl.ch10.ex04;

/*
 * p.205 練習問題10.4
 * 今までの練習問題からforループを使用した問題を数台選んで、whileループで書き直す
 * do-whileでも書き直すことができるか？
 * そのように書き直したりするか？
 * しないとしたら、なぜか？
 * 
 * Answer:
 * 練習問題 3.5 Benchmarkのrepeatメソッドではwhile文で書き直すことができる。
 * 引数countの値によっては、ループを1回もまわすべきではないこともあるので、do-while文では書き直せない。
 */
public abstract class Benchmark {
	abstract void benchmark();
	
	public final long repeat(int count) {
		long start = System.nanoTime();
		/*
		for (int i = 0; i < count; i++) {
			benchmark();
		}
		*/
		
		int num = 0;
		while(num < count) {
			benchmark();
			count++;
		}
		return (System.nanoTime() - start);
	}

}
