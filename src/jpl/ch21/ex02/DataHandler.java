package jpl.ch21.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.WeakHashMap;

public class DataHandler {
    private WeakHashMap<File, byte[]> history = new WeakHashMap<>();
    
    byte[] readFile(File file) throws IOException {
        byte[] data;
        
        if (history.containsKey(file)) {
            data = history.get(file);
            if (data != null) {
                return data;
            }
        }
        
        data = readBytesFromFile(file);
        history.put(file, data);
        return data;
    }
    
    private byte[] readBytesFromFile(File file) throws IOException {
        FileInputStream in = null;
        byte[] result = null;
        try {
            in = new FileInputStream(file);
            result = new byte[(int)file.length()];
            in.read(result);
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return result;
    }

}
