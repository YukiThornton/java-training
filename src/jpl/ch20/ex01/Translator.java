package jpl.ch20.ex01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Translator {

    public static void main(String[] args) {
        InputStream in = System.in;
        OutputStream out = System.out;
        try {
            translateByte(in, out, (byte)'l', (byte)'L');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void translateByte(InputStream in, OutputStream out, byte from, byte to) throws IOException {
        if (in == null || out == null) {
            throw new IllegalArgumentException("Null stream.");
        }
        int b;
        while((b = in.read()) != -1) {
            // bに負の値があった場合に動かなくなってしまうことを防ぐためにbyteにキャストする
            out.write((byte)b == from ? to : b);
        }
    }

}
