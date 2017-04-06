package jpl.ch20.ex04;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class LineReader extends FilterReader {

    protected LineReader(Reader reader) {
        super(reader);
    }
    
    private boolean endOfStream = false;
    
    public String readLine() throws IOException {
        if (endOfStream) {
            return null;
        }
        int c  = super.read();
        while(c == 10 || c == 13) {
            c = super.read();
        }
        if (c == -1) {
            endOfStream = true;
            return null;
        }
        
        StringBuffer stringBuffer = new StringBuffer();
        while(c != -1 && c != 10 && c != 13) {
            stringBuffer.append((char)c);
            c = super.read();
        }
        if(c == -1) {
            endOfStream = true;
        }
        return stringBuffer.toString();
    }

}
