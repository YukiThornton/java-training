package jpl.ch07.ex02;

/*
 * p.148 練習問題 7.2
 * 数値である各基本データ型のフィールドを宣言
 * 異なるリテラル形式を使用して値を代入してみる
 */
public class NumericField {
	
	public static void main(String[] args) {
		char charLiteral = 'a';
		byte byteLiteral = 101;
		short shortLiteral = 102;
		int intLiteral = 103;
		long longLiteral = 104L;
		float floatLiteral = 105.F;
		double doubleLiteral = 106.D;

		System.out.println("[START] fields");
		System.out.println("char field: " + charLiteral);
		System.out.println("byte field: " + byteLiteral);
		System.out.println("short field: " + shortLiteral);
		System.out.println("int field: " + intLiteral);
		System.out.println("long field: " + longLiteral);
		System.out.println("float field: " + floatLiteral);
		System.out.println("double field: " + doubleLiteral);
		System.out.println("[FINISH] fields");
		System.out.println("");
		
		// charに代入
		System.out.println("[START] toChar");
		toChar(charLiteral, byteLiteral, shortLiteral, intLiteral, longLiteral, floatLiteral, doubleLiteral);
		System.out.println("[FINISH] toChar");
		System.out.println("");
		
		// byteに代入
		System.out.println("[START] toByte: byteMAX=" + Byte.MAX_VALUE + ", byteMIN=" + Byte.MIN_VALUE);
		toByte(charLiteral, byteLiteral, shortLiteral, intLiteral, longLiteral, floatLiteral, doubleLiteral);
		System.out.println("[MAX Challenge]");
		String byteMax = "228";
		toByte((char)(Byte.MAX_VALUE + 1), (byte)0, Short.parseShort(byteMax), Integer.parseInt(byteMax), Long.parseLong(byteMax), Float.parseFloat(byteMax), Double.parseDouble(byteMax));
		System.out.println("[FINISH] toByte");
		System.out.println("");

		// shortに代入
		System.out.println("[START] toShort: shortMAX=" + Short.MAX_VALUE + ", shortMIN=" + Short.MIN_VALUE);
		toShort(charLiteral, byteLiteral, shortLiteral, intLiteral, longLiteral, floatLiteral, doubleLiteral);
		System.out.println("[MAX Challenge]");
		String shortMax = "42768";
		toShort((char)(Short.MAX_VALUE + 1), (byte)0, (short)0, Integer.parseInt(shortMax), Long.parseLong(shortMax), Float.parseFloat(shortMax), Double.parseDouble(shortMax));
		System.out.println("[FINISH] toShort");
		System.out.println("");

		// intに代入
		System.out.println("[START] toInt: intMAX=" + Integer.MAX_VALUE + ", intMIN=" + Integer.MIN_VALUE);
		toInt(charLiteral, byteLiteral, shortLiteral, intLiteral, longLiteral, floatLiteral, doubleLiteral);
		System.out.println("[MAX Challenge]");
		String intMax = "3147483648";
		toInt((char)(Integer.MAX_VALUE + 1), (byte)0, (short)0, (int)0, Long.parseLong(intMax), Float.parseFloat(intMax), Double.parseDouble(intMax));
		System.out.println("[FINISH] toInt");
		System.out.println("");

		// longに代入
		System.out.println("[START] toLong: longMAX=" + Long.MAX_VALUE + ", longMIN=" + Long.MIN_VALUE);
		toLong(charLiteral, byteLiteral, shortLiteral, intLiteral, longLiteral, floatLiteral, doubleLiteral);
		System.out.println("[MAX Challenge]");
		String longMax = "10223372036854775808";
		toLong((char)(Long.MAX_VALUE + 1), (byte)0, (short)0, (int)0, (long)0, Float.parseFloat(longMax), Double.parseDouble(longMax));
		System.out.println("[FINISH] toLong");
		System.out.println("");

		// floatに代入
		System.out.println("[START] toFloat: floatMAX=" + Float.MAX_VALUE + ", floatMIN=" + Float.MIN_VALUE);
		toFloat(charLiteral, byteLiteral, shortLiteral, intLiteral, longLiteral, floatLiteral, doubleLiteral);
		System.out.println("[MAX Challenge]");
		String floatMax = "3.5028235E38F";
		toFloat((char)(Long.MAX_VALUE + 1), (byte)0, (short)0, (int)0, (long)0, (float)0, Double.parseDouble(floatMax));
		System.out.println("[FINISH] toFloat");
		System.out.println("");

		// doubleに代入
		System.out.println("[START] toDoulbe: doubleMAX=" + Double.MAX_VALUE + ", doubleMIN=" + Double.MIN_VALUE);
		toDouble(charLiteral, byteLiteral, shortLiteral, intLiteral, longLiteral, floatLiteral, doubleLiteral);
		System.out.println("[FINISH] toDouble");
		System.out.println("");
	}
	
	private static void toChar(char charLiteral, byte byteLiteral, short shortLiteral,
			int intLiteral, long longLiteral, float floatLiteral, double doubleLiteral) {
		char charToChar = charLiteral;
		System.out.println("char(" + charLiteral + ") -> char: " + charToChar + " // cast不要");
		char byteToChar = (char) byteLiteral;
		System.out.println("byte(" + byteLiteral + ") -> char: " + byteToChar + " // cast必要");
		char shortToChar = (char) shortLiteral;
		System.out.println("short(" + shortLiteral + ") -> char: " + shortToChar + " // cast必要");			
		char intToChar = (char) intLiteral;
		System.out.println("int(" + intLiteral + ") -> char: " + intToChar + " // cast必要");			
		char longToChar = (char) longLiteral;
		System.out.println("long(" + longLiteral + ") -> char: " + longToChar + " // cast必要");
		char floatToChar = (char) floatLiteral;
		System.out.println("float(" + floatLiteral + ") -> char: " + floatToChar + " // cast必要");
		char doubleToChar = (char) doubleLiteral;
		System.out.println("double(" + doubleLiteral + ") -> char: " + doubleToChar + " // cast必要");
	}
		
	private static void toByte(char charLiteral, byte byteLiteral, short shortLiteral,
			int intLiteral, long longLiteral, float floatLiteral, double doubleLiteral) {
		byte charToByte = (byte) charLiteral;
		System.out.println("char(" + charLiteral + ") -> byte: " + charToByte + " // cast必要");
		if (byteLiteral != (byte)0) {
			byte byteToByte = byteLiteral;
			System.out.println("byte(" + byteLiteral + ") -> byte: " + byteToByte + " // cast不要");			
		}
		byte shortToByte = (byte) shortLiteral;
		System.out.println("short(" + shortLiteral + ") -> byte: " + shortToByte + " // cast必要");
		byte intToByte = (byte) intLiteral;
		System.out.println("int(" + intLiteral + ") -> byte: " + intToByte + " // cast必要");
		byte longToByte = (byte) longLiteral;
		System.out.println("long(" + longLiteral + ") -> byte: " + longToByte + " // cast必要");
		byte floatToByte = (byte) floatLiteral;
		System.out.println("float(" + floatLiteral + ") -> byte: " + floatToByte + " // cast必要");
		byte doubleToByte = (byte) doubleLiteral;
		System.out.println("double(" + doubleLiteral + ") -> byte: " + doubleToByte + " // cast必要");
	}

	private static void toShort(char charLiteral, byte byteLiteral, short shortLiteral,
			int intLiteral, long longLiteral, float floatLiteral, double doubleLiteral) {
		short charToshort = (short) charLiteral;
		System.out.println("char(" + charLiteral + ") -> short: " + charToshort + " // cast必要");
		if (byteLiteral != (byte)0) {
			short byteToShort = byteLiteral;
			System.out.println("byte(" + byteLiteral + ") -> short: " + byteToShort + " // cast不要");
		}
		if (shortLiteral != (short)0) {
			short shortToShort = shortLiteral;
			System.out.println("short(" + shortLiteral + ") -> short: " + shortToShort + " // cast不要");			
		}
		short intToShort = (short) intLiteral;
		System.out.println("int(" + intLiteral + ") -> short: " + intToShort + " // cast必要");
		short longToShort = (short)longLiteral;
		System.out.println("long(" + longLiteral + ") -> short: " + longToShort + " // cast必要");
		short floatToShort = (short) floatLiteral;
		System.out.println("float(" + floatLiteral + ") -> short: " + floatToShort + " // cast必要");
		short doubleToShort = (short) doubleLiteral;
		System.out.println("double(" + doubleLiteral + ") -> short: " + doubleToShort + " // cast必要");
	}

	private static void toInt(char charLiteral, byte byteLiteral, short shortLiteral,
			int intLiteral, long longLiteral, float floatLiteral, double doubleLiteral) {
		int charToInt = charLiteral;
		System.out.println("char(" + charLiteral + ") -> int: " + charToInt + " // cast不要");
		if (byteLiteral != (byte)0) {
			int byteToInt = byteLiteral;
			System.out.println("byte(" + byteLiteral + ") -> int: " + byteToInt + " // cast不要");
		}
		if (shortLiteral != (short)0) {
			int shortToInt = shortLiteral;
			System.out.println("short(" + shortLiteral + ") -> int: " + shortToInt + " // cast不要");			
		}
		if (intLiteral != (int)0) {
			int intToInt = intLiteral;
			System.out.println("int(" + intLiteral + ") -> int: " + intToInt + " // cast不要");			
		}
		int longToInt = (int) longLiteral;
		System.out.println("long(" + longLiteral + ") -> int: " + longToInt + " // cast必要");
		int floatToInt = (int) floatLiteral;
		System.out.println("float(" + floatLiteral + ") -> int: " + floatToInt + " // cast必要");
		int doubleToInt = (int) doubleLiteral;
		System.out.println("double(" + doubleLiteral + ") -> int: " + doubleToInt + " // cast必要");
	}
	
	private static void toLong(char charLiteral, byte byteLiteral, short shortLiteral,
			int intLiteral, long longLiteral, float floatLiteral, double doubleLiteral) {
		long charToLong = charLiteral;
		System.out.println("char(" + charLiteral + ") -> long: " + charToLong + " // cast不要");
		if (byteLiteral != (byte)0) {
			long byteToLong = byteLiteral;
			System.out.println("byte(" + byteLiteral + ") -> long: " + byteToLong + " // cast不要");
		}
		if (shortLiteral != (short)0) {
			long shortToLong = shortLiteral;
			System.out.println("short(" + shortLiteral + ") -> long: " + shortToLong + " // cast不要");			
		}
		if (intLiteral != (int)0) {
			long intToLong = intLiteral;
			System.out.println("int(" + intLiteral + ") -> long: " + intToLong + " // cast不要");			
		}
		if (longLiteral != (long)0) {
			long longToLong = longLiteral;
			System.out.println("long(" + longLiteral + ") -> long: " + longToLong + " // cast不要");			
		}
		long floatToLong = (long) floatLiteral;
		System.out.println("float(" + floatLiteral + ") -> long: " + floatToLong + " // cast必要");
		long doubleToLong = (long) doubleLiteral;
		System.out.println("double(" + doubleLiteral + ") -> long: " + doubleToLong + " // cast必要");
	}
	
	private static void toFloat(char charLiteral, byte byteLiteral, short shortLiteral,
			int intLiteral, long longLiteral, float floatLiteral, double doubleLiteral) {
		float charToFloat = charLiteral;
		System.out.println("char(" + charLiteral + ") -> float: " + charToFloat + " // cast不要");
		if (byteLiteral != (byte)0) {
			float byteToFloat = byteLiteral;
			System.out.println("byte(" + byteLiteral + ") -> float: " + byteToFloat + " // cast不要");
		}
		if (shortLiteral != (short)0) {
			float shortToFloat = shortLiteral;
			System.out.println("short(" + shortLiteral + ") -> float: " + shortToFloat + " // cast不要");			
		}
		if (intLiteral != (int)0) {
			float intToFloat = intLiteral;
			System.out.println("int(" + intLiteral + ") -> float: " + intToFloat + " // cast不要");			
		}
		if (longLiteral != (long)0) {
			float longToFloat = longLiteral;
			System.out.println("long(" + longLiteral + ") -> float: " + longToFloat + " // cast不要");			
		}
		if (floatLiteral != (float)0) {
			float floatToFloat = floatLiteral;
			System.out.println("float(" + floatLiteral + ") -> float: " + floatToFloat + " // cast不要");			
		}
		float doubleToFloat= (float) doubleLiteral;
		System.out.println("double(" + doubleLiteral + ") -> float: " + doubleToFloat + " // cast必要");
	}

	private static void toDouble(char charLiteral, byte byteLiteral, short shortLiteral,
			int intLiteral, long longLiteral, float floatLiteral, double doubleLiteral) {
		double charToDouble = charLiteral;
		System.out.println("char(" + charLiteral + ") -> double: " + charToDouble + " // cast不要");
		double byteToDouble = byteLiteral;
		System.out.println("byte(" + byteLiteral + ") -> double: " + byteToDouble + " // cast不要");
		double shortToDouble = shortLiteral;
		System.out.println("short(" + shortLiteral + ") -> double: " + shortToDouble + " // cast不要");			
		double intToDouble = intLiteral;
		System.out.println("int(" + intLiteral + ") -> double: " + intToDouble + " // cast不要");			
		double longToDouble = longLiteral;
		System.out.println("long(" + longLiteral + ") -> double: " + longToDouble + " // cast不要");			
		double floatToDouble = floatLiteral;
		System.out.println("float(" + floatLiteral + ") -> double: " + floatToDouble + " // cast不要");			
		double doubleToDouble = doubleLiteral;
		System.out.println("double(" + doubleLiteral + ") -> double: " + doubleToDouble + " // cast不要");
	}
}
