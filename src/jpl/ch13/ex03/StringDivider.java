package jpl.ch13.ex03;

import java.util.ArrayList;
import java.util.List;

public class StringDivider {
    public static String[] delimitedString(String from, char start, char end) {
        int startIndex = from.indexOf(start);
        if (startIndex < 0) {
            return null;
        }
        
        List<String> result = new ArrayList<>();
        while(startIndex >= 0){
            if (startIndex == from.length() - 1) {
                result.add(from.substring(startIndex));
                break;
            }
            int endIndex = from.indexOf(end, startIndex + 1);
            if (endIndex < 0) {
                result.add(from.substring(startIndex));
                break;
            } else {
                result.add(from.substring(startIndex, endIndex + 1));
                startIndex = from.indexOf(start, endIndex);
                continue;
            }
        }
        return result.toArray(new String[result.size()]);
    }
    
    
}
