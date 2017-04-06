package jpl.ch17.ex02;

import java.lang.ref.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataHandler {
    private WeakReference<File> lastFile;
    private WeakReference<byte[]> lastData;
    
    byte[] readFile(File file) {
        byte[] data;
        
        if (file.equals(lastFile)) {
            data = lastData.get();
            if (data != null) {
                return data;
            }
        }
        
        data = readBytesFromFile(file);
        lastFile = new WeakReference<File>(file);
        lastData = new WeakReference<byte[]>(data);
        return data;
    }
    
    private byte[] readBytesFromFile(File file) {
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            stream.read(bytes);
        } catch (FileNotFoundException e) {
            return new byte[0];
        } catch (IOException e) {
            return new byte[0];
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    return new byte[0];
                }
            }
        }
        return bytes;
    }
}
