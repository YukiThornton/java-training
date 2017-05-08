package jpl.ch22.ex04;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import jpl.ch03.ex07.Attr;

public class AttrCounterTest {

    @Test
    public void testAddAndTotal1() {
        AttributedImpl attributedImpl = new AttributedImpl();
        AttrCounter attrCounter = new AttrCounter(attributedImpl);
        for (int i = 0; i < 20; i++) {
            attributedImpl.add(new Attr("name" + i, "value" + i));
        }
        assertTrue(attrCounter.total() == 20);

    }

    @Test
    public void testAddAndTotal2() {
        AttributedImpl attributedImpl = new AttributedImpl();
        AttrCounter attrCounter = new AttrCounter(attributedImpl);
        Attr attr = new Attr("name", "value");
        for (int i = 0; i < 20; i++) {
            attributedImpl.add(attr);
        }
        Iterator<Attr> iterator = attributedImpl.attrs();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertTrue(attrCounter.total() == 20);
        assertTrue(count == 1);
    }

    @Test
    public void testRemoveAndTotal1() {
        AttributedImpl attributedImpl = new AttributedImpl();
        AttrCounter attrCounter = new AttrCounter(attributedImpl);
        for (int i = 0; i < 20; i++) {
            attributedImpl.add(new Attr("name" + i, "value" + i));
        }
        for (int i = 0; i < 5; i++) {
            attributedImpl.remove("name" + i);
        }
        assertTrue(attrCounter.total() == 15);

    }

    @Test
    public void testRemoveAndTotal2() {
        AttributedImpl attributedImpl = new AttributedImpl();
        AttrCounter attrCounter = new AttrCounter(attributedImpl);
        assertTrue(null == attributedImpl.remove("name"));
    }

    @Test
    public void testFindAndTotal1() {
        AttributedImpl attributedImpl = new AttributedImpl();
        AttrCounter attrCounter = new AttrCounter(attributedImpl);
        for (int i = 0; i < 20; i++) {
            attributedImpl.add(new Attr("name" + i, "value" + i));
        }
        for (int i = 0; i < 5; i++) {
            String result = (String)attributedImpl.find("name" + i).getValue();
            assertTrue(result.equals("value" + i));
        }
        assertTrue(attrCounter.total() == 20);

    }

    @Test
    public void testFindAndTotal2() {
        AttributedImpl attributedImpl = new AttributedImpl();
        AttrCounter attrCounter = new AttrCounter(attributedImpl);
        assertTrue(attributedImpl.find("name") == null);

    }

    @Test
    public void testAttrsAndTotal() {
        AttributedImpl attributedImpl = new AttributedImpl();
        AttrCounter attrCounter = new AttrCounter(attributedImpl);
        for (int i = 0; i < 20; i++) {
            attributedImpl.add(new Attr("name" + i, "value" + i));
        }
        Iterator<Attr> iterator = attributedImpl.attrs();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertTrue(attrCounter.total() == 20);
        assertTrue(count == 20);
    }

}
