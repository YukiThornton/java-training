package jpl.ch07.ex01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;


public class HelloWorldTest {

	@Test
	public void testMain() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		HelloWorld.main(new String[0]);
		
		assertThat(out.toString(), is("Hello, World." + System.lineSeparator()));
	}

}
