package jpl.ch20.ex09;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileDetails {

    public static void main(String[] args) {
        try {
            print(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void print(String... filePaths) throws IOException {
        if (filePaths == null) {
            throw new IllegalArgumentException("filePaths is null.");
        }
        for (String filePath : filePaths) {
            print(filePath);
        }
        
    }
    
    public static void print(String filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("filePath is null.");
        }
        File file = new File(filePath);
        System.out.println("Given file path: " + filePath);
        System.out.println("File canonical path: " + file.getCanonicalPath());
        System.out.println("File exists: " + file.exists());
        System.out.println("File is a directory: " + file.isDirectory());
        System.out.println("File is a file: " + file.isFile());
        System.out.println("File name: " + file.getName());
        System.out.println("You can read this file: " + file.canRead());
        System.out.println("You can write on this file: " + file.canWrite());
        System.out.println("This file is hidden: " + file.isHidden());
        System.out.println("Last Modified: " + file.lastModified());
        System.out.println("File length: " + file.length());
    }
    
}
