package jpl.ch22.ex02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class WhichChars {
    private HashSet<Character> set = new HashSet<>();
    
    public WhichChars(String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(new Character(str.charAt(i)));
        }
    }
    
    @Override
    public String toString() {
        List<Character> list = new ArrayList<>(set.size());
        list.addAll(set);
        Collections.sort(list);
        StringBuffer stringBuffer = new StringBuffer();
        for (Character ch : list) {
            stringBuffer.append(ch);
        }
        return stringBuffer.toString();
    }
}
