package jpl.ch03.ex03;

public class X {
	// 初期化に使う変数をstaticな値にすることで、インスタンスの初期化前にこのフィールドの初期化は済んでいる
	protected static final int X_MASK = 0x00ff;
	protected int fullMask;
	
	{
		System.out.println("X initial block:");
		System.out.printf("xMask: %04x%n", X.X_MASK);
		System.out.printf("fullMask: %04x%n%n", fullMask);		
	}
	
	public X() {
		// fullmaskをYのフィールドを使って初期化してみる
		fullMask = Y.Y_MASK;
		System.out.println("At the last line of X constructor:");
		System.out.printf("xMask: %04x%n", X.X_MASK);
		System.out.printf("fullMask: %04x%n%n", fullMask);
	}
	
	public int mask(int orig) {
		return (orig & fullMask);
	}
	
	public static void main (String[] args) {
		X x = new X();
		int orig = 0x1001;
		int result = x.mask(orig);
		System.out.printf("After mask with %04x%n", orig);
		System.out.printf("xMask: %04x%n", X.X_MASK);
		System.out.printf("fullMask: %04x%n", x.fullMask);
		System.out.printf("mask result: %04x%n", result);
	}
}
