package jpl.ch22.ex11;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DataReaderTest {

    @Test
    public void testReadCSVTable1() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/goodSample1.csv");
        List<String[]> actual = DataReader.readCSVTable(source, 4);
        List<String[]> expected = getTest1ResultWith4Cells();
        
        assertTrue(actual.size() == expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertTrue(actual.get(i).length == expected.get(i).length);
            for (int j = 0; j < actual.get(i).length; j++) {
                assertTrue(actual.get(i)[j].equals(expected.get(i)[j]));
            }
        }
        
        source.close();
    }
    
    @Test
    public void testReadCSVTable2() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/goodSample2.csv");
        List<String[]> actual = DataReader.readCSVTable(source, 1);
        List<String[]> expected = getTest1ResultWith1Cell();
        
        assertTrue(actual.size() == expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertTrue(actual.get(i).length == expected.get(i).length);
            for (int j = 0; j < actual.get(i).length; j++) {
                assertTrue(actual.get(i)[j].equals(expected.get(i)[j]));
            }
        }
        
        source.close();
    }
        
    @Test
    public void testReadCSVTable3() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/goodSampleWithEmptyLines.csv");
        List<String[]> actual = DataReader.readCSVTable(source, 4);
        List<String[]> expected = getTest1ResultWith4Cells();
        
        assertTrue(actual.size() == expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertTrue(actual.get(i).length == expected.get(i).length);
            for (int j = 0; j < actual.get(i).length; j++) {
                assertTrue(actual.get(i)[j].equals(expected.get(i)[j]));
            }
        }
        
        source.close();


    }
    
    @Test
    public void testReadCSVTable4() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/goodSampleEndsWithEmptyLines.csv");
        List<String[]> actual = DataReader.readCSVTable(source, 4);
        List<String[]> expected = getTest1ResultWith4Cells();
        
        assertTrue(actual.size() == expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertTrue(actual.get(i).length == expected.get(i).length);
            for (int j = 0; j < actual.get(i).length; j++) {
                assertTrue(actual.get(i)[j].equals(expected.get(i)[j]));
            }
        }
        
        source.close();
    }
    
    @Test
    public void testReadCSVTable5() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/badSampleEmptyFile.csv");
        List<String[]> actual = DataReader.readCSVTable(source, 4);
        List<String[]> expected = new ArrayList<>();
        
        assertTrue(actual.size() == expected.size());
        
        source.close();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testReadCSVTableThrowsIllegalArgumentException1() throws IOException {
        DataReader.readCSVTable(null, 4);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testReadCSVTableThrowsIllegalArgumentException2() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/goodSample1.csv");
        DataReader.readCSVTable(source, 0);
    }
    
    @Test(expected=IOException.class)
    public void testReadCSVTableThrowsIOException1() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/badSampleLessCells.csv");
        DataReader.readCSVTable(source, 4);
        
        source.close();
    }
    
    @Test(expected=IOException.class)
    public void testReadCSVTableThrowsIOException2() throws IOException {
        Reader source = new FileReader("test/jpl/ch22/ex08/badSampleMoreCells.csv");
        DataReader.readCSVTable(source, 4);
        
        source.close();
    }
    
    private List<String[]> getTest1ResultWith4Cells() {
        String[] line1 = {"aaa", "bbb", "ccc", "ddd"};
        String[] line2 = {"aa", "bb", "cc", "dd"};
        String[] line3 = {"a", "b", "c", "d"};
        List<String[]> list = new ArrayList<>(3);
        list.add(line1);
        list.add(line2);
        list.add(line3);
        return list;
    }

    private List<String[]> getTest1ResultWith1Cell() {
        String[] line1 = {"aaa"};
        String[] line2 = {"aa"};
        String[] line3 = {"a"};
        List<String[]> list = new ArrayList<>(3);
        list.add(line1);
        list.add(line2);
        list.add(line3);
        return list;
    }

}
