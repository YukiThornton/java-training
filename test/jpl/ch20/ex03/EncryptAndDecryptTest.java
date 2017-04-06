package jpl.ch20.ex03;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class EncryptAndDecryptTest {

    @Test
    public void testWrite() throws IOException {
        OutputStream out = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            out = new EncryptOutputStream(byteArrayOutputStream);
            byte[] input = {65, 67};
            byte[] inputResult = {80, 82};

            out.write(input);

            byte[] encryptedInput = byteArrayOutputStream.toByteArray();
            assertTrue(encryptedInput.length == 2);
            for (int i = 0; i < encryptedInput.length; i++) {
                assertTrue(encryptedInput[i] == inputResult[i]);
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
        }
    }

    @Test
    public void testRead() throws IOException {
        InputStream in = null;
        try {
            byte[] inputResult = {65, 67};
            byte[] input = {80, 82};
            in = new DecryptInputStream(new ByteArrayInputStream(input));

            int count = 0;
            int b;
            for (int i = 0; (b = in.read()) != -1; i++) {
                assertTrue(b == inputResult[i]);
                count++;
            }
            assertTrue(count == 2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
