package jpl.ch10.ex04;

public class ObjectBenchmark extends Benchmark {

	void benchmark() {
		new Object();
	}

	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		long time = new ObjectBenchmark().repeat(count);
		System.out.println(count + " objects in " + time + " nanoseconds");
	}

}
