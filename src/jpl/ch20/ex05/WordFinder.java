package jpl.ch20.ex05;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class WordFinder {

    public static void main(String[] args) {
        try {
            find("src/jpl/ch20/ex05/sample.txt", args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void find(String filePath, String target) throws IOException{
        FileReader fileReader;
        LineNumberReader lineNumberReader = null;
        try {
            fileReader = new FileReader(filePath);
            lineNumberReader = new LineNumberReader(fileReader);
            
            String line;
            while((line = lineNumberReader.readLine()) != null) {
                if (line.indexOf(target) != -1) {
                    System.out.println(lineNumberReader.getLineNumber() + ": " + line);
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (lineNumberReader != null) {
                    lineNumberReader.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }    
    }

}
