package jpl.ch10.ex05;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class CharacterDictionaryTest {

	@Test
	public void testShowCharacterBetweenParameters() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("a" + System.lineSeparator());
		stringBuilder.append("b" + System.lineSeparator());
		stringBuilder.append("c" + System.lineSeparator());
		
		CharacterDictionary.showCharactersBetween('a', 'c');
		
		assertThat(out.toString(), is(stringBuilder.toString()));
	}

	@Test
	public void testShowCharacterBetweenReversedParameters() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("c" + System.lineSeparator());
		stringBuilder.append("d" + System.lineSeparator());
		stringBuilder.append("e" + System.lineSeparator());
		
		CharacterDictionary.showCharactersBetween('c', 'e');
		
		assertThat(out.toString(), is(stringBuilder.toString()));
	}

	@Test
	public void testShowCharacterShowsOnlyParameters() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("f" + System.lineSeparator());
		stringBuilder.append("g" + System.lineSeparator());
		
		CharacterDictionary.showCharactersBetween('f', 'g');
		
		assertThat(out.toString(), is(stringBuilder.toString()));
	}

	@Test
	public void testShowCharacterWithSameParameters() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("h" + System.lineSeparator());
		
		CharacterDictionary.showCharactersBetween('h', 'h');
		
		assertThat(out.toString(), is(stringBuilder.toString()));
	}

	@Test(expected=Error.class)
	public void testShowCharacterWithFirstParameterInvalid() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		CharacterDictionary.showCharactersBetween((char)0xffff, 'i');
	}

	@Test(expected=Error.class)
	public void testShowCharacterWithSecondParameterInvalid() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		CharacterDictionary.showCharactersBetween('j', (char)0xffff);
	}

	@Test(expected=Error.class)
	public void testShowCharacterWithBothParametersInvalid() {
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		CharacterDictionary.showCharactersBetween((char)0xffff, (char)0xffff);
	}

}
