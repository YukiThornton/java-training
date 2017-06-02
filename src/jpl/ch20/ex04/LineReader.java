package jpl.ch20.ex04;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class LineReader extends FilterReader {

    public LineReader(Reader reader) {
        super(reader);
    }
    
    private boolean endOfStream = false;
    
    public String readLine() throws IOException {
        if (endOfStream) {
            return null;
        }
        int c  = super.read();
        while(c == '\n' || c == '\r') {
            c = super.read();
        }
        if (c == -1) {
            endOfStream = true;
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        while(c != -1 && c != '\n' && c != '\r') {
            sb.append((char)c);
            c = super.read();
        }
        if(c == -1) {
            endOfStream = true;
        }
        return sb.toString();
    }

}
