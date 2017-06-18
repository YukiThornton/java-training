package java8.ch01.ex03;

import java.io.File;
import java.io.FilenameFilter;

public class FilePresenter {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Specify a root path name and an extension.");
        }
        File[] files = files(args[0], args[1]);
        for (File file: files) {
            System.out.println(file.getName());
        }
    }

    public static File[] files(String parentPath, String extension) {
        if (isValidExtensionName(extension)) {
            throw new IllegalArgumentException("extension is not valid.");
        }
        File parent = createFile(parentPath);
        if (!isValidDirectory(parent)) {
            throw new IllegalArgumentException("rootPath does not exist or is not a directory.");
        }
        String[] fileNames = parent.list((File pathName, String fileName) -> {
            File file = new File(fileName);
            return file.isFile() && fileName.endsWith(extension);
        });
        return createFiles(fileNames);
    }

    private static File createFile(String rootPath) {
        if (rootPath == null || rootPath.isEmpty()) {
            throw new IllegalArgumentException("rootPath is null or empty.");
        }
        return new File(rootPath);
    }

    private static File[] createFiles(String[] fileNames) {
        File[] results = new File[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            if (fileNames[i] == null || fileNames[i].isEmpty()) {
                throw new IllegalArgumentException("rootPath is null or empty.");
            }
            results[i] = new File(fileNames[i]);
        }
        return results;
    }

    private static boolean isValidDirectory(File file) {
        return file.exists() && file.isDirectory();
    }

    private static boolean isValidExtensionName(String extensionName) {
        return extensionName != null && !extensionName.isEmpty() && extensionName.startsWith(".");
    }
}
