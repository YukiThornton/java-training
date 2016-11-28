package jpl.ch03.ex03;

public class Y extends X {
	
	protected static final int Y_MASK = 0xff00;
	
	{
		System.out.println("Y initial block:");
		System.out.printf("xMask: %04x%n", X.X_MASK);
		System.out.printf("yMask: %04x%n", Y.Y_MASK);
		System.out.printf("fullMask: %04x%n%n", fullMask);		
	}
	
	public Y() {
		System.out.println("At the first line of Y constructor:");
		System.out.printf("xMask: %04x%n", X.X_MASK);
		System.out.printf("yMask: %04x%n", Y.Y_MASK);
		System.out.printf("fullMask: %04x%n%n", fullMask);		
		fullMask |= Y.Y_MASK;
		System.out.println("At the last line of Y constructor:");
		System.out.printf("xMask: %04x%n", X.X_MASK);
		System.out.printf("yMask: %04x%n", Y.Y_MASK);
		System.out.printf("fullMask: %04x%n%n", fullMask);		
	}

	public static void main(String[] args) {
		Y y = new Y();
		int orig = 0x1001;
		int result = y.mask(orig);
		System.out.printf("After mask with %04x%n", orig);
		System.out.printf("xMask: %04x%n", X.X_MASK);
		System.out.printf("yMask: %04x%n", Y.Y_MASK);
		System.out.printf("fullMask: %04x%n", y.fullMask);
		System.out.printf("mask result: %04x%n", result);

	}

}
