package jpl.ch21.ex06;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;

public class Concat {
    public static void main (String[] args) throws IOException {
        InputStream in;
        if (args.length == 0) {
            in = System.in;
        } else {
            Enumeration<InputStream> files = new InputStreamEnumeration(args);
            in = new SequenceInputStream(files);
        }
        int ch;
        while ((ch = in.read()) != -1) {
            System.out.write(ch);
        }
    }
    
    private static class InputStreamEnumeration implements Enumeration<InputStream> {
        
        private String[] array;
        private int nextIndex = 0;

        public InputStreamEnumeration(String[] array) {
            this.array = array;
        }

        @Override
        public boolean hasMoreElements() {
            return array.length > nextIndex;
        }

        @Override
        public InputStream nextElement() {
            if (!hasMoreElements()) {
                return null;
            }
            InputStream fileIn = null;
            InputStream bufIn = null;

            try {
                fileIn = new FileInputStream(array[nextIndex]);
                bufIn = new BufferedInputStream(fileIn);
                nextIndex++;
            } catch (IOException e) {
                return null;
            } 
            
            return bufIn;
        }
    
    }
}
