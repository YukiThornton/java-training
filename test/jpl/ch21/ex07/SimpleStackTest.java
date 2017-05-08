package jpl.ch21.ex07;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

public class SimpleStackTest {

    @Test
    public void testBasicUsage1() {
        SimpleStack<String> stack = new SimpleStack<>();
        String e1 = "1";

        assertTrue(stack.empty());
        assertTrue(stack.search(e1) == -1);
        assertTrue(stack.push(e1).equals(e1));
        assertFalse(stack.empty());
        assertTrue(stack.peek().equals(e1));
        assertFalse(stack.empty());
        assertTrue(stack.search(e1) == 1);
        assertTrue(stack.pop().equals(e1));
        assertTrue(stack.empty());
    }

    @Test
    public void testBasicUsage2() {
        SimpleStack<String> stack = new SimpleStack<>();
        String e1 = "1";
        String e2 = "2";
        String e3 = "3";

        assertTrue(stack.empty());
        assertTrue(stack.search(e1) == -1);
        assertTrue(stack.push(e1).equals(e1));
        assertFalse(stack.empty());
        assertTrue(stack.peek().equals(e1));
        assertFalse(stack.empty());
        assertTrue(stack.search(e1) == 1);
        
        assertTrue(stack.search(e2) == -1);
        assertTrue(stack.push(e2).equals(e2));
        assertFalse(stack.empty());
        assertTrue(stack.peek().equals(e2));
        assertFalse(stack.empty());
        assertTrue(stack.search(e2) == 1);
        assertTrue(stack.search(e1) == 2);
        
        assertTrue(stack.pop().equals(e2));
        assertTrue(stack.search(e2) == -1);
        assertTrue(stack.search(e1) == 1);
                
        assertTrue(stack.search(e3) == -1);
        assertTrue(stack.push(e3).equals(e3));
        assertFalse(stack.empty());
        assertTrue(stack.peek().equals(e3));
        assertFalse(stack.empty());
        assertTrue(stack.search(e3) == 1);
        assertTrue(stack.search(e1) == 2);

        assertTrue(stack.pop().equals(e3));
        assertTrue(stack.pop().equals(e1));
        assertTrue(stack.empty());
    }

    @Test
    public void testPushNotAcceptNull() {
        SimpleStack<String> stack = new SimpleStack<>();
        String e1 = null;

        assertTrue(stack.empty());
        assertTrue(stack.push(e1) == e1);
        assertTrue(stack.empty());
    }

    @Test
    public void testSearchReturnsFistOccurence() {
        SimpleStack<String> stack = new SimpleStack<>();
        String e1 = "1";
        String e2 = "1";
        String e3 = "1";

        assertTrue(stack.push(e1).equals(e1));
        assertTrue(stack.push(e2).equals(e2));
        assertTrue(stack.push(e3).equals(e3));
        assertTrue(stack.search(e1) == 1);
        assertTrue(stack.search(e2) == 1);
        assertTrue(stack.search(e3) == 1);
    }

    @Test(expected=EmptyStackException.class)
    public void testPeekEmptyStack() {
        SimpleStack<String> stack = new SimpleStack<>();
        stack.peek();
    }

    @Test(expected=EmptyStackException.class)
    public void testPopEmptyStack() {
        SimpleStack<String> stack = new SimpleStack<>();
        stack.pop();
    }
}
