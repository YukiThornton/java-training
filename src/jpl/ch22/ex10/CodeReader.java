package jpl.ch22.ex10;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeReader {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> list = readTokens(new FileReader("test/jpl/ch22/ex10/sample.txt"), true);
        System.out.println(list.size());
        for (String token : list) {
            if (token == null) {
                System.out.println("null");
                
            }
            System.out.println(token + "!");
        }
    }
    
    public static List<String> readTokens(Readable readable, boolean ignoreComment) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(readable);
        List<String> list = new ArrayList<>();
        if (ignoreComment) {
            scanner.useDelimiter("(\\p{javaWhitespace}\\n)|(\\p{javaWhitespace})|(#.*\\p{javaWhitespace}\\n)");        
        }
        while (scanner.hasNext()) {
            list.add(scanner.next());
        }    
        return list;    
    }

}
