package jpl.ch10.ex04;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class ImprovedFibonacciTest {

	@Test
	public void test() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("1:  1" + System.lineSeparator());
		stringBuilder.append("2:  1" + System.lineSeparator());
		stringBuilder.append("3:  2 *" + System.lineSeparator());
		stringBuilder.append("4:  3" + System.lineSeparator());
		stringBuilder.append("5:  5" + System.lineSeparator());
		stringBuilder.append("6:  8 *" + System.lineSeparator());
		stringBuilder.append("7: 13" + System.lineSeparator());
		stringBuilder.append("8: 21" + System.lineSeparator());
		stringBuilder.append("9: 34 *" + System.lineSeparator());
		
		ImprovedFibonacci.main(new String[0]);
		
		assertThat(out.toString(), is(stringBuilder.toString()));
	}

}
