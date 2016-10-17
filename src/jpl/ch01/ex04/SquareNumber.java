package jpl.ch01.ex04;

public class SquareNumber {

	public static void main(String[] args) {
		System.out.println("10以下の平方数");
		int num = 1;
		while (num <= 10) {
			int squareNum = num * num;
			System.out.println(num + "x" + num + "=" + squareNum);
			num++;
		}

	}

}
