package jpl.ch07.ex03;

/*
 * p.155 練習問題 7.3:
 * 深さが12のパスカルの三角形を計算するプログラムの作成
 * 三角形の各行を適切な長さの配列とし、各行の配列を長さ12のintの配列に格納する
 * 定数12ではなく、各配列の長さを使用して配列の配列を表示するメソッドにより、結果を表示するプログラムにする
 * 表示メソッドを変えることなく、12を他の定数に変更してみる
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
				int left;
				if (i == 0)
					left = 0;
				else
					left = triangle[j-1][i-1];
				int right;
				if (i == (triangle[j].length - 1))
					right = 0;
				else
					right = triangle[j-1][i];
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
