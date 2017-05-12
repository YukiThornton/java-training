package jpl.ch20.ex02;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Translator extends FilterInputStream{

    private static final byte TO_POSITIVE_INT = (byte)0xFF;

    private byte from;
    private byte to;

    protected Translator(InputStream in, byte from, byte to) {
        super(in);
        this.from = from;
        this.to = to;
    }

    public static void main(String[] args) {
        InputStream in = System.in;
        OutputStream out = System.out;
        FilterInputStream filterInput = null;
        try {
            filterInput = new Translator(in, (byte)'l', (byte)'L');
            int b;
            while((b = filterInput.read()) != -1) {
                out.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (filterInput != null) {
                try {
                    filterInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public int read() throws IOException {
        int b = super.read();
        if (b == -1) {
            return -1;
        } else if ((byte)b == from){
            return to & TO_POSITIVE_INT;
        } else {
            return b & TO_POSITIVE_INT;
        }
    }

}
