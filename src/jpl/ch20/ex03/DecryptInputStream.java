package jpl.ch20.ex03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DecryptInputStream extends FilterInputStream {

    private static final byte MASK = 0x11;
    
    public static void main(String[] args) throws IOException {
        OutputStream out = null;
        InputStream in = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            out = new EncryptOutputStream(byteArrayOutputStream);
            
            byte[] input = {62,67,63,91};
            
            System.out.print("Before encryption: ");
            for (byte b: input) {
                System.out.print((char)b);
            }
            System.out.println();
            
            out.write(input);
            byte[] encryptedInput = byteArrayOutputStream.toByteArray();
            System.out.print("After encryption: ");
            for (byte b: encryptedInput) {
                System.out.print((char)b);
            }
            System.out.println();

            in = new DecryptInputStream(new ByteArrayInputStream(encryptedInput));

            System.out.print("After decryption: ");
            int b;
            while((b = in.read()) != -1) {
                System.out.print((char)b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected DecryptInputStream(InputStream in) {
        super(in);
    }
    
    public int read() throws IOException {
        int b = super.read();
        return (b == -1 ? b : b ^ MASK);
    }

}
