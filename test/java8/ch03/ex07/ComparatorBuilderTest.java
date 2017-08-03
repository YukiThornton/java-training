package java8.ch03.ex07;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static java8.ch03.ex07.ComparatorBuilder.comparatorGenerator;
public class ComparatorBuilderTest {

    @Test
    public void testComparatorGenrator1() {
        String[] target = {"abcd", "abCe", "acbd", "a cbe", "bacd", "baCe", "bcad", "b cae", "cabd", "caBe", "cbad", "c bae"};
        testComparatorGenrator(target, true, true, true);
    }

    @Test
    public void testComparatorGenrator2() {
        String[] target = {"a cbe","abcd", "abCe", "acbd", "b cae", "bacd", "baCe", "bcad", "c bae", "cabd", "caBe", "cbad"};
        testComparatorGenrator(target, true, true, false);
    }

    @Test
    public void testComparatorGenrator3() {
        String[] target = {"abCe", "abcd", "acbd", "a cbe", "baCe", "bacd", "bcad", "b cae", "caBe", "cabd", "cbad", "c bae"};
        testComparatorGenrator(target, true, false, true);
    }

    @Test
    public void testComparatorGenrator4() {
        String[] target = {"a cbe","abCe", "abcd", "acbd", "b cae", "baCe", "bacd", "bcad", "c bae", "caBe", "cabd", "cbad"};
        testComparatorGenrator(target, true, false, false);
    }

    @Test
    public void testComparatorGenrator5() {
        String[] target = {"c bae", "cbad", "caBe", "cabd", "b cae", "bcad", "bacd", "a cbe", "acbd", "abCe", "abcd"};
        testComparatorGenrator(target, false, true, true);
    }

    @Test
    public void testComparatorGenrator6() {
        String[] target = {"cbad", "caBe", "cabd", "c bae", "bcad", "baCe", "bacd", "b cae", "acbd", "abCe", "abcd", "a cbe"};
        testComparatorGenrator(target, false, true, false);
    }

    @Test
    public void testComparatorGenrator7() {
        String[] target = {"c bae", "cbad", "cabd", "caBe", "b cae", "bcad", "bacd", "baCe", "a cbe", "acbd", "abcd", "abCe"};
        testComparatorGenrator(target, false, false, true);
    }

    @Test
    public void testComparatorGenrator8() {
        String[] target = {"cbad", "cabd", "caBe", "c bae", "bcad", "bacd", "baCe", "b cae", "acbd", "abcd", "abCe","a cbe"};
        testComparatorGenrator(target, false, false, false);
    }

    public void testComparatorGenrator(String[] target, boolean isAscendingOrder, boolean ignoreCase, boolean ignoreWhitespace) {
        String[] shuffledTarget = shuffle(target);
        while(!isShuffled(target, shuffledTarget)) {
            System.out.println("shuffle again");
            shuffledTarget = shuffle(target);
        }

        Arrays.sort(shuffledTarget, comparatorGenerator(isAscendingOrder, ignoreCase, ignoreWhitespace));
        for (int i = 0; i < target.length; i++) {
            assertTrue(target[i].equals(shuffledTarget[i]));
        }
    }

    private String[] shuffle(String[] target) {
        String[] result = new String[target.length];
        System.arraycopy(target, 0, result, 0, target.length);
        Random random = new Random();
        for (int i = result.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String tmp = result[index];
            result[index] = result[i];
            result[i] = tmp;
        }
        return result;
    }

    private boolean isShuffled(String[] target, String[] shuffledTarget) {
        int count = 0;
        for (int i = 0; i < target.length; i++) {
            if (target[i].equals(shuffledTarget[i])) {
                count++;
            }
        }
        return count < target.length * 0.2;
    }

}
