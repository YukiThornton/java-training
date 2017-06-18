package java8.ch01.ex09;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class Collection2Test {

    @Test
    public void test() {
        String[] words = {"a", "ab", "abc", "abcd", "abcd"};
        int[] expectedIndeces = {3, 4};

        Collection2Impl<String> collection = new Collection2Impl<>();
        for (String word: words) {
            collection.add(word);
        }
        
        List<String> expectedWords = new ArrayList<>();
        collection.forEachIf(
                (String element) -> {
                    expectedWords.add(element);
                },
                (String elem) -> {
                    return elem.length() > 3;
                });
        
        assertTrue(expectedWords.size() == expectedIndeces.length);
        for (int i = 0; i < expectedIndeces.length; i++) {
            assertTrue(expectedWords.get(i).equals(words[expectedIndeces[i]]));
        }
    }
    
    class Collection2Impl<E> extends ArrayList<E> implements Collection2<E> {
    }

}
