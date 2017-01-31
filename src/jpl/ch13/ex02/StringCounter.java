package jpl.ch13.ex02;

public class StringCounter {

    static public int countString(String source, String target) {
        int count = 0;
        int index = source.indexOf(target.charAt(0));
        while(index >= 0 && (index + target.length()) <= source.length()) {
            String sub = source.substring(index, index + target.length());
            if (sub.equals(target)) {
                count++;
            }
            index = source.indexOf(target.charAt(0), index + 1);
        }
        if (count == 0) {
            return -1;
        } else {
            return count;
        }
        
    }
}
