package java8.ch03.ex20;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MyStreamTest {

    @Test
    public void test1() {
        List<String> src = Arrays.asList("1", "2", "3");
        List<Integer> result = MyStream.map(src, s -> Integer.valueOf(s));
        assertTrue(src.size() == result.size());
        for (int i = 0; i < src.size(); i++) {
            assertTrue(Integer.valueOf(src.get(i)).equals(result.get(i)));
        }
    }

    @Test
    public void test2() {
        List<String> src = Arrays.asList();
        List<Integer> result = MyStream.map(src, s -> {
            fail();
            return null;
        });
        assertTrue(src.size() == result.size());
    }

    @Test(expected=NullPointerException.class)
    public void test3() {
        List<String> src = null;
        MyStream.map(src, s -> Integer.valueOf(s));
    }

    @Test(expected=NullPointerException.class)
    public void test4() {
        List<String> src = Arrays.asList("1", "2", "3");
        MyStream.map(src, null);
    }

}
