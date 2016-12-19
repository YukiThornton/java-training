package jpl.ch10.ex05;

public class CharacterDictionary {

	public static void showCharactersBetween(char from, char to) {
		if (!Character.isDefined(from) || !Character.isDefined(to))
			throw new Error("At least one of the parameters is not defined in Unicode.");
		
		int startPoint,
			endPoint;
		
		if (Character.compare(from, to) <= 0) {
			startPoint = from;
			endPoint = to;
		} else {
			startPoint = to;
			endPoint = from;
		}
		while(startPoint <= endPoint) {
			System.out.println(Character.toChars(startPoint)[0]);
			startPoint++;
		}
	}
	
	public static void main(String[] args) {
		showCharactersBetween((char)0x041f, '3');
	}

}
