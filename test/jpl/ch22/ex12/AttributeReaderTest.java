package jpl.ch22.ex12;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import jpl.ch03.ex07.Attr;
import jpl.ch22.ex04.Attributed;

public class AttributeReaderTest {

    @Test
    public void testReadAttrs() throws IOException {
        Reader reader = new FileReader("test/jpl/ch22/ex12/goodSample1.txt");
        Attributed attributed = AttributeReader.readAttrs(reader);

        Iterator<Attr> attrs = attributed.attrs();
        int index = 0;
        while (attrs.hasNext()) {
            index++;
            attrs.next();
        }
        
        List<Attr> expectedList = getExpectedValues();
        assertTrue(index == expectedList.size());
        for (Attr expected : expectedList) {
            Attr actual = attributed.find(expected.getName());
            assertTrue(actual != null);
            assertTrue(actual.getValue().equals(expected.getValue()));
        }
        
    }
    
    private List<Attr> getExpectedValues() {
        List<Attr> list = new ArrayList<>(3);
        list.add(new Attr("apple", new Double(0)));
        list.add(new Attr("banana", new Double(2)));
        list.add(new Attr("orange", new Double(90)));
        return list;
    }

}
