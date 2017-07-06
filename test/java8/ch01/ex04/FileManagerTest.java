package java8.ch01.ex04;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FileManagerTest {

    @Test
    public void testSort1() {
        String[] pathNames = {
                "test/java8/ch01/ex04/dir1",
                "test/java8/ch01/ex04/dir1/dir1",
                "test/java8/ch01/ex04/dir1/dir2",
                "test/java8/ch01/ex04/dir1/dir3",
                "test/java8/ch01/ex04/dir1/abc1",
                "test/java8/ch01/ex04/dir1/abc2",
                "test/java8/ch01/ex04/dir1/abc3",
                "test/java8/ch01/ex04/dir1/dir1/abc1",
                "test/java8/ch01/ex04/dir1/dir1/abc2",
                "test/java8/ch01/ex04/dir1/dir1/abc3",
                "test/java8/ch01/ex04/dir1/dir2/abc1",
                "test/java8/ch01/ex04/dir1/dir2/abc2",
                "test/java8/ch01/ex04/dir1/dir2/abc3",
                "test/java8/ch01/ex04/dir1/dir3/abc1",
                "test/java8/ch01/ex04/dir1/dir3/abc2",
                "test/java8/ch01/ex04/dir1/dir3/abc3"
        };
        File[] files = new File[pathNames.length];
        for (int i = 0; i < pathNames.length; i++) {
            files[i] = new File(pathNames[i]);
        }
        List<File> fileList = Arrays.asList(files);
        Collections.shuffle(fileList);
        files = fileList.toArray(files);

        FileManager.sort(files);
        
        for (int i = 0; i < files.length; i++) {
            assertTrue(new File(pathNames[i]).equals(files[i]));
        }
    }

    @Test
    public void testSort2() {
        File[] files = new File[0];
        FileManager.sort(files);
        assertTrue(files.length == 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSort3() {
        FileManager.sort(null);
    }
}
