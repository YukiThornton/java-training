package jpl.ch20.ex03;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EncryptOutputStream extends FilterOutputStream {

    private static final byte MASK = 0x11;

    public EncryptOutputStream(OutputStream out) {
        super(out);
    }
    
    public void write(int i) throws IOException {
        super.write(i ^ MASK);
    }

}
