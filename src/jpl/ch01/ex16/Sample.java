package jpl.ch01.ex16;

public class Sample {

	public static void main(String[] args) {
		MyUtilities util = new MyUtilities();
		try {
			double[] result = util.getDataSet("abc");
		} catch (BadDataSetException e) {
			System.out.println(e.getDataSetName());
			System.out.println(e.getIoException().getMessage());
			e.printStackTrace();
		}

	}

}
