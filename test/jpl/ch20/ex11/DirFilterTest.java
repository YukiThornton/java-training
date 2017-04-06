package jpl.ch20.ex11;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirFilterTest {

    @Test
    public void testFind() {
        String[] result = DirFilter.find("test/jpl/ch20/ex11", ".java");
        
        assertTrue(result.length == 1);
        assertTrue(result[0].equals("DirFilterTest.java"));
    }

    @Test
    public void testFindNoMatchingFiles() {
        String[] result = DirFilter.find("test/jpl/ch20/ex11", ".jar");
        
        assertTrue(result.length == 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindNullDirName() {
        DirFilter.find(null, ".java");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindEmptyDirName() {
        DirFilter.find("", ".java");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindNullSuffix() {
        DirFilter.find("test/jpl/ch20/ex11", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindEmptySuffix() {
        DirFilter.find("test/jpl/ch20/ex11", "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindDirNotExist() {
        DirFilter.find("test/jpl/ch20/xxxx", ".java");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFindDirIsNotDirectory() {
        DirFilter.find("test/jpl/ch20/ex11/DirFilterTest.java", ".java");
    }

}
