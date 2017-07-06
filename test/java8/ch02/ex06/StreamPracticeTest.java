package java8.ch02.ex06;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.Test;

public class StreamPracticeTest {

    @Test
    public void testCharacterStream1() {
        char[] charArray = {'a', 'b', 'c'};
        testCharacterStream(charArray);
    }

    @Test
    public void testCharacterStream2() {
        char[] charArray = {};
        testCharacterStream(charArray);
    }

    @Test(expected=NullPointerException.class)
    public void testCharacterStream3() {
        StreamPractice.characterStream(null);
    }

    private void testCharacterStream(char[] charArray) {
        Stream<Character> stream = StreamPractice.characterStream(new String(charArray));
        Character[] result = stream.toArray(Character[]::new);
        assertTrue(charArray.length == result.length);
        for (int i = 0; i < result.length; i++) {
            assertTrue(charArray[i] == result[i].charValue());
        }
    }

}
