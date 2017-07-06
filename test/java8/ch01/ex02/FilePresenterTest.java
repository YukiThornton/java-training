package java8.ch01.ex02;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class FilePresenterTest {

    @Test
    public void testSubDirectories1() {
        File[] dirs = FilePresenter.subDirectories("test/java8/ch01/ex02/dir1");
        assertTrue(dirs.length == 2);
        Set<String> dirNames = new HashSet<>();
        for (File dir: dirs) {
            dirNames.add(dir.getName());
        }
        assertTrue(dirNames.contains("dir1-1"));
        assertTrue(dirNames.contains("dir1-2"));
    }

    @Test
    public void testSubDirectories2() {
        File[] dirs = FilePresenter.subDirectories("test/java8/ch01/ex02/dir2");
        assertTrue(dirs.length == 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectories3() {
        FilePresenter.subDirectories(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectories4() {
        FilePresenter.subDirectories("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectories5() {
        FilePresenter.subDirectories("fakedir");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectories6() {
        FilePresenter.subDirectories("test/java8/ch01/ex02/dir1/file1-3");
    }

    @Test
    public void testSubDirectoriesMethodRef1() {
        File[] dirs = FilePresenter.subDirectoriesMethodRef("test/java8/ch01/ex02/dir1");
        assertTrue(dirs.length == 2);
        Set<String> dirNames = new HashSet<>();
        for (File dir: dirs) {
            dirNames.add(dir.getName());
        }
        assertTrue(dirNames.contains("dir1-1"));
        assertTrue(dirNames.contains("dir1-2"));
    }

    @Test
    public void testSubDirectoriesMethodRef2() {
        File[] dirs = FilePresenter.subDirectoriesMethodRef("test/java8/ch01/ex02/dir2");
        assertTrue(dirs.length == 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectoriesMethodRef3() {
        FilePresenter.subDirectoriesMethodRef(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectoriesMethodRef4() {
        FilePresenter.subDirectoriesMethodRef("");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectoriesMethodRef5() {
        FilePresenter.subDirectoriesMethodRef("fakedir");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubDirectoriesMethodRef6() {
        FilePresenter.subDirectoriesMethodRef("test/java8/ch01/ex02/dir1/file1-3");
    }

}
