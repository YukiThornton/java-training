package jpl.ch10.ex03;

import jpl.ch06.ex01.Day;

/*
 * p.204 練習問題 10.3
 * 週の曜日を表すenumを受け取って、その日が働く日であればtrue, そうでなければfalseを返すメソッドを作成する
 * ネスとしたif-else文 -> switch文を使用して書く
 */
public class DaySorter {

	public static void main(String[] args) {
		System.out.println("true for workday, false for weekend");
		System.out.println("Sunday: " + isWorkday(Day.SUNDAY));
		System.out.println("Monday: " + isWorkday(Day.MONDAY));
		System.out.println("Tuesday: " + isWorkday(Day.TUESDAY));
		System.out.println("Wednesday: " + isWorkday(Day.WEDNESDAY));
		System.out.println("Thursday: " + isWorkday(Day.THURSDAY));
		System.out.println("Friday: " + isWorkday(Day.FRIDAY));
		System.out.println("Saturday: " + isWorkday(Day.SATURDAY));
	}
	
	public static boolean isWorkday(Day day) {
		/*
		// if-else文で書く場合（if-else文をネスとさせる方法は考えつかなかった）
		if (day == Day.SATURDAY || day == Day.SUNDAY) {
			return false;
		} else if (day == Day.MONDAY || day == Day.TUESDAY || 
				day == Day.WEDNESDAY || day == Day.THURSDAY || day == Day.FRIDAY){
			return true;			
		} else {
			throw new Error("Something wrong happened!");
		}
		*/
		// switch文で書く場合
		switch (day) {
		case SATURDAY: case SUNDAY:
			return false;
		case MONDAY: case TUESDAY: case WEDNESDAY: case THURSDAY: case FRIDAY:
			return true;
		default:
			throw new Error("Something wrong happened!");
		}
	}

}
