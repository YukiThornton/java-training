package jpl.ch24.ex01;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AnotherResourceBundle_en_AU extends AnotherResourceBundle {
    @Override
    public Object handleGetObject(String key) {
        if (key.equals(AnotherResourceBundle.HELLO)) return "G'Day";
        return null;
    }

    @Override
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList(AnotherResourceBundle.HELLO));
    }
}
