package java8.ch01.ex04;

import java.io.File;
import java.util.Arrays;

public class FileManager {
    
    public static void sort(File[] files) {
        if (files == null) {
            throw new IllegalArgumentException("files is null.");
        }
        Arrays.sort(files, (file1, file2) -> {
            File fileA = (File)file1;
            File fileB = (File)file2;
            if (fileA.isFile() && !fileB.isFile()) {
                return 1;
            }
            if (fileB.isFile() && !fileA.isFile()) {
                return -1;
            }
            return fileA.getAbsolutePath().compareTo(fileB.getAbsolutePath());
        });
    }
}
