package java8.ch01.ex03;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class FilePresenterTest {

    @Test
    public void testFiles1() {
        File[] files = FilePresenter.files("test/java8/ch01/ex03/dir1", ".txt");
        assertTrue(files.length == 2);
        Set<String> dirNames = new HashSet<>();
        for (File dir: files) {
            dirNames.add(dir.getName());
        }
        assertTrue(dirNames.contains("file1.txt"));
        assertTrue(dirNames.contains("file3.txt"));
    }

    @Test
    public void testFiles2() {
        File[] files = FilePresenter.files("test/java8/ch01/ex03/dir1/dir2", ".txt");
        assertTrue(files.length == 0);
    }

    @Test
    public void testFiles3() {
        FilePresenter.files(null, ".txt");
    }

    @Test
    public void testFiles4() {
        FilePresenter.files("", ".txt");
    }

    @Test
    public void testFiles5() {
        FilePresenter.files("fakedir", ".txt");
    }

    @Test
    public void testFiles6() {
        FilePresenter.files("test/java8/ch01/ex03/dir1/file1.txt", ".txt");
    }

    @Test
    public void testFiles7() {
        FilePresenter.files("test/java8/ch01/ex03/dir1/dir2", null);
    }

    @Test
    public void testFiles8() {
        FilePresenter.files("test/java8/ch01/ex03/dir1/dir2", "");
    }

    @Test
    public void testFiles9() {
        FilePresenter.files("test/java8/ch01/ex03/dir1/dir2", "txt");
    }

}
