package java8.ch03.ex24;

import static org.junit.Assert.*;

import org.junit.Test;

public class PairTest {

    @Test
    public void testFlatMap1() {
        Pair<String> originalPair = new Pair<String>("1", "2");
        Pair<Integer> integerPair = originalPair.flatMap((a, b) -> new Pair<Integer>(Integer.valueOf(a), Integer.valueOf(b)));
        assertTrue(integerPair.first().intValue() == 1);
        assertTrue(integerPair.second().intValue() == 2);
    }

    @Test(expected=NullPointerException.class)
    public void testFlatMap2() {
        Pair<String> originalPair = new Pair<String>("1", "2");
        originalPair.flatMap(null);
    }

    @Test
    public void testMap1() {
        Pair<String> originalPair = new Pair<String>("1", "2");
        Pair<Integer> integerPair = originalPair.map(s -> Integer.valueOf(s));
        assertTrue(integerPair.first().intValue() == 1);
        assertTrue(integerPair.second().intValue() == 2);
    }

    @Test(expected=NullPointerException.class)
    public void testMap2() {
        Pair<String> originalPair = new Pair<String>("1", "2");
        originalPair.map(null);
    }

    @Test(expected=NumberFormatException.class)
    public void testMap3() {
        Pair<String> originalPair = new Pair<String>(null, null);
        originalPair.map(s -> Integer.valueOf(s));
    }

}
