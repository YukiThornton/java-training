package jpl.ch20.ex10;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;

public class WordCounter {
    
    public static void main(String[] args) {
        String fileName= "src/jpl/ch20/ex10/WordCounter.java";
        Map<String, Integer> result = null;
        try {
            result = countAllWords(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    
    public static Map<String, Integer> countAllWords(String file) throws IOException {
        InputStreamReader in = new InputStreamReader(new FileInputStream(file));
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        Map<String, Integer> map = new HashMap<>();
        
        while(tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
        	String word = tokenizer.sval;
                if (map.containsKey(word)) {
                    map.put(word, map.get(word).intValue() + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }
        
        return map;
    }
}
