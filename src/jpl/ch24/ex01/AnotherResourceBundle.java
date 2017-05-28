package jpl.ch24.ex01;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AnotherResourceBundle extends ResourceBundle {

    public static final String HELLO = "hello";
    public static final String GOODBYE = "goodbye";
    
    protected AnotherResourceBundle() {
        super();
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    @Override
    protected Object handleGetObject(String key) {
        if (key.equals(HELLO)) {
            return "Hello";
        }
        if (key.equals(GOODBYE)) {
            return "Goodbye";
        }
        return null;
    }
    
    @Override
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList(HELLO, GOODBYE));
    }

}
