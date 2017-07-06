package java8.ch02.ex01;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class BookReaderTest {

    @Test
    public void testCountWordsLongerThanTwelve1() throws IOException {
        BookReader reader = new BookReader();
        assertTrue(8 == reader.countWordsLongerThanTwelve(createWordList("test/java8/ch02/ex01/test1.txt")));
    }

    @Test
    public void testCountWordsLongerThanTwelve2() throws IOException {
        BookReader reader = new BookReader();
        assertTrue(0 == reader.countWordsLongerThanTwelve(createWordList("test/java8/ch02/ex01/test2.txt")));
    }

    @Test
    public void testCountWordsLongerThanTwelveParallel1() throws IOException, InterruptedException {
        BookReader reader = new BookReader();
        assertTrue(8 == reader.countWordsLongerThanTwelveParallel(createWordList("test/java8/ch02/ex01/test1.txt")));
    }

    @Test
    public void testCountWordsLongerThanTwelveParallel2() throws IOException, InterruptedException {
        BookReader reader = new BookReader();
        assertTrue(0 == reader.countWordsLongerThanTwelveParallel(createWordList("test/java8/ch02/ex01/test2.txt")));
    }

    private List<String> createWordList(String pathname) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get(pathname)), StandardCharsets.UTF_8);
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }
}
