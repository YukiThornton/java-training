package jpl.ch09.ex03;

/*
 * p.185 練習問題 9.3:
 * 練習問題 7.3の解答をより明瞭 or より簡潔に書く
 */
public class PascalsTriangle {

	public static void main(String[] args) {
		try {
			int[][] triangle = PascalsTriangle.create(12);
			PascalsTriangle.show(triangle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int[][] create(int rowNum) throws Exception {
		if (rowNum <= 0)
			throw new Exception("Impossible!");
		int[][] triangle = new int[rowNum][];
		for(int i = 0; i < rowNum; i++) {
			triangle[i] = new int[i + 1];
		}
		return fillUp(triangle);
	}
	
	private static int[][] fillUp(int[][] triangle) throws Exception {
		if (!isValidTriangle(triangle))
			throw new Exception("The triangle is null or empty!");
		triangle[0][0] = 1;
		for(int j = 1; j < triangle.length; j++) {
			for(int i = 0; i < triangle[j].length; i++) {
				int left = (i == 0 ? 0: triangle[j-1][i-1]);
				int right = (i == (triangle[j].length - 1) ? 0: triangle[j-1][i]);
				triangle[j][i] = left + right;
			}
		}
		return triangle;
	}
	
	private static boolean isValidTriangle(int[][] triangle) {
		if (triangle == null || triangle.length == 0) 
			return false;
		for (int[] row: triangle) {
			if (row == null || row.length == 0) 
				return false;
		}
		return true;
	}
	
	public static void show(int[][] triangle) throws Exception {
		if (!isValidTriangle(triangle))
			throw new Exception("The triangle is null or empty!");

		String[] textRows = new String[triangle.length];
		for(int j = 0; j < triangle.length; j++) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < triangle[j].length; i++) {
				sb.append("-" + triangle[j][i]);
			}
			sb.append("-");
			textRows[j] = sb.toString();
		}
		
		int maxWidth = textRows[textRows.length - 1].length();
		
		for(String text: textRows) {
			int gap = (maxWidth- text.length()) / 2;
			for (int i = 0; i < gap; i++) {
				System.out.print(" ");
			}
			System.out.println(text);
		}
	}
 
}
