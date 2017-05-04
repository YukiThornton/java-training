package jpl.ch22.ex11;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    
    public static List<String[]> readCSVTable(Reader source, int numberOfCells) throws IOException {
        if (source == null || numberOfCells < 1) {
            throw new IllegalArgumentException("Source is null or numberOfCells is smaller than one.");
        }
        List<String[]> list = new ArrayList<>();
        StreamTokenizer tokenizer = new StreamTokenizer(source);
        tokenizer.eolIsSignificant(true);
        int arrayIndex = 0;
        String[] array = new String[numberOfCells];
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                array[arrayIndex++] = tokenizer.sval;
            } else if (tokenizer.ttype == ',') {
                if (arrayIndex == numberOfCells) {
                    throw new IOException("input format error");
                }
            } else if (tokenizer.ttype == StreamTokenizer.TT_EOL) {
                if (arrayIndex == 0) {
                    continue;
                }
                if (arrayIndex != numberOfCells) {
                    throw new IOException("input format error");
                }
                list.add(array);
                array = new String[numberOfCells];
                arrayIndex = 0;
            }
        }
        return list;
    }
}
