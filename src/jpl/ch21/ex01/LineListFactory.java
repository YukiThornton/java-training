package jpl.ch21.ex01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jpl.ch20.ex04.LineReader;

public class LineListFactory {
    public static List<String> getSortedLineList(LineReader reader) throws IOException {
        List<String> list = new ArrayList<>();
        String str;
        while((str = reader.readLine()) != null) {
            insertLine(list, str);
        }
        return list;
    }
    
    private static void insertLine(List<String> list, String newLine) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).compareTo(newLine) > 0) {
                list.add(i, newLine);
                return;
            }
        }
        list.add(newLine);
    }
}
