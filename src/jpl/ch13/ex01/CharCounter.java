package jpl.ch13.ex01;

public class CharCounter {

    static public int countChar(String str, char ch) {
        int count = 0;
        int index = str.indexOf(ch);
        while(index >= 0) {
            count++;
            index = str.indexOf(ch, index + 1);
        }
        if (count == 0) {
            return -1;
        } else {
            return count;
        }
        
    }
}
