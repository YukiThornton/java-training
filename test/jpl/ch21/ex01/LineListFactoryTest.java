package jpl.ch21.ex01;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import jpl.ch20.ex04.LineReader;

public class LineListFactoryTest {

    @Test
    public void testSortedLines() {
        String[] strings = {"abc", "abcd", "acb"};
        int[] sortedIndex = {0, 1, 2};
        testGetSortedLineList(strings, sortedIndex);
    }

    @Test
    public void testUnsortedLines1() {
        String[] strings = {"acb", "abc", "abcd"};
        int[] sortedIndex = {1, 2, 0};
        testGetSortedLineList(strings, sortedIndex);
    }

    @Test
    public void testUnsortedLines2() {
        String[] strings = {"xxx", "acb", "xxx", "abc", "acb"};
        int[] sortedIndex = {3, 1, 4, 0, 2};
        testGetSortedLineList(strings, sortedIndex);
    }

    @Test
    public void testOneLine() {
        String[] strings = {"acb"};
        int[] sortedIndex = {0};
        testGetSortedLineList(strings, sortedIndex);
    }

    @Test
    public void testEmptyLine() {
        String[] strings = new String[0];
        int[] sortedIndex = new int[0];
        testGetSortedLineList(strings, sortedIndex);
    }

    private void testGetSortedLineList(String[] strings, int[] sortedIndex) {
        LineReader lineReader = null;
        String seperator = "\n";
        
        List<String> result = null;
        
        try {
            lineReader = new LineReader(new StringReader(String.join(seperator, strings)));
            result = LineListFactory.getSortedLineList(lineReader);
            
            assertTrue(result.size() == strings.length);
            for (int i = 0; i < strings.length; i++) {
                assertTrue(result.get(i).equals(strings[sortedIndex[i]]));                
            }
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
