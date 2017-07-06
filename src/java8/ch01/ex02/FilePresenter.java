package java8.ch01.ex02;

import java.io.File;

public class FilePresenter {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Specify true/false and a root path name.");
        }
        boolean useMethodReference = Boolean.parseBoolean(args[0]);
        File[] subDirectories = null;
        if (useMethodReference) {
            subDirectories = subDirectories(args[1]);
        } else {
            subDirectories = subDirectoriesMethodRef(args[1]);
        }
        for (File file: subDirectories) {
            System.out.println(file.getName());
        }
    }

    public static File[] subDirectories(String rootPath) {
        File root = createFile(rootPath);
        if (!isValidDirectory(root)) {
            throw new IllegalArgumentException("rootPath does not exist or is not a directory.");
        }
        return root.listFiles((File pathName, String name) -> new File(pathName + "/" + name).isDirectory());
    }

    public static File[] subDirectoriesMethodRef(String rootPath) {
        File root = createFile(rootPath);
        if (!isValidDirectory(root)) {
            throw new IllegalArgumentException("rootPath does not exist or is not a directory.");
        }
        return root.listFiles(File::isDirectory);
    }

    private static File createFile(String rootPath) {
        if (rootPath == null || rootPath.isEmpty()) {
            throw new IllegalArgumentException("rootPath is null or empty.");
        }
        return new File(rootPath);
    }

    private static boolean isValidDirectory(File file) {
        return file.exists() && file.isDirectory();
    }
}
