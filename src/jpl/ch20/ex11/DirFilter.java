package jpl.ch20.ex11;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirFilter implements FilenameFilter {

    private String acceptableSuffix;
    
    public DirFilter(String suffix) {
        acceptableSuffix = suffix;
    }
    
    public static void main(String[] args) {
        String[] files = find(args[0], args[1]);
        for (String file : files) {
            System.out.println("\t" + file);
        }
    }
    
    public static String[] find(String dirName, String suffix) {
        if (dirName == null || dirName.isEmpty() || suffix == null || suffix.isEmpty()) {
            throw new IllegalArgumentException("Wrong arguments.");
        }
        File dir = new File(dirName);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("dirName is not an existing directory");
        }
        return dir.list(new DirFilter(suffix));    
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(acceptableSuffix);
    }

}
