package jpl.ch20.ex08;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class EntryTable {

    private static final String TABLE_SPLIT = ",";

    public static void main(String[] args) {
        try {
            File source = new File("src/jpl/ch20/ex08/sample.txt");
            File table = new File("src/jpl/ch20/ex08/table.txt");
            
            long[] pointers = getEntryPointers(source, "%%");
            createTableFile(table, pointers);
            long[] tPointers = getEntryPointers(table);
            int index = getRandomIndex(tPointers.length);
            String[] entry = getEntry(source, index, tPointers);
            System.out.println("Entry#" + (index + 1));
            for (String line : entry) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static long[] getEntryPointers(File file, String entryPrefix) throws IOException {
        RandomAccessFile raf = null;
        List<Long> list = new ArrayList<>();
        try {
            raf = new RandomAccessFile(file, "r");
            
            long currentPointer = raf.getFilePointer();
            String currentLine = raf.readLine();
            while (currentLine != null) {
                if (currentLine.startsWith(entryPrefix)) {
                    list.add(currentPointer);
                }
                currentPointer = raf.getFilePointer();
                currentLine = raf.readLine();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            close(raf);
        }
        return toPrimitiveArray(list);
    }
    
    public static void createTableFile(File file, long[] data) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            raf.seek(0);
            raf.writeBytes("0" + TABLE_SPLIT + data[0]);
            for (int i = 1; i < data.length; i++) {
                raf.writeBytes("\n" + i + TABLE_SPLIT + data[i]);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            close(raf);
        }
    }
    public static long[] getEntryPointers(File table) throws IOException {
        RandomAccessFile raf = null;
        List<Long> list = new ArrayList<>();
        try {
            raf = new RandomAccessFile(table, "r");
            long currentPointer = raf.getFilePointer();
            String currentLine = raf.readLine();
            while (currentLine != null) {
                list.add(Long.valueOf(currentLine.split(TABLE_SPLIT)[1]));
                currentPointer = raf.getFilePointer();
                currentLine = raf.readLine();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            close(raf);
        }
        return toPrimitiveArray(list);
    }
    
    public static String[] getEntry(File source, int pointerIndex, long[] pointerList) throws IOException {
        RandomAccessFile raf = null;
        List<String> list = new ArrayList<>();
        try {
            raf = new RandomAccessFile(source, "r");
            raf.seek(pointerList[pointerIndex]);
            boolean isLastIndex = pointerIndex + 1 == pointerList.length;
            String line = raf.readLine();
            while (true) {
                if (isLastIndex && line == null) {
                    break;
                }
                list.add(line);
                if (!isLastIndex) {
                    if(raf.getFilePointer() >= pointerList[pointerIndex + 1]) {
                        break;
                    }
                }
                line = raf.readLine();      
            }
        } catch (IOException e) {
            throw e;
        } finally {
            close(raf);
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static long[] toPrimitiveArray(List<Long> list) {
        long[] result = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
    
    private static void close(RandomAccessFile raf) {
        try {
            if (raf != null) {
                raf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int getRandomIndex(int max) {
        return (int)(Math.floor(Math.random() * max));
    }

}
