package jpl.ch22.ex08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class DataReader {
    
    public static List<String[]> readCSVTable(Readable source, int numberOfCells) throws IOException {
        if (source == null || numberOfCells < 1) {
            throw new IllegalArgumentException("Source is null or numberOfCells is smaller than one.");
        }
        @SuppressWarnings("resource")
        Scanner in = new Scanner(source);
        List<String[]> vals = new ArrayList<>();
        Pattern pat = Pattern.compile(getCSVregex(numberOfCells), Pattern.MULTILINE);
        while (in.hasNextLine()) {
            String line = in.findInLine(pat);
            if (line != null) {
                String[] cells = new String[numberOfCells];
                MatchResult match = in.match();
                for (int i = 0; i < numberOfCells; i++)
                    cells[i] = match.group(i+1);
                vals.add(cells);
                in.nextLine();
            } else if (in.nextLine().length() == 0) {
                continue;
            } else {
                throw new IOException("input format error");
            }
        }
        
        IOException ex = in.ioException();
        if (ex != null) 
            throw ex;
        return vals;
    }
    
    private static String getCSVregex(int numberOfCells) {
        StringBuilder builder = new StringBuilder("^");
        String cellExp = "([^,]*)";
        for (int i = 1; i <= numberOfCells; i++) {
            builder.append(cellExp);
            if (i != numberOfCells) {
                builder.append(",");
            } else {
                builder.append("$");
            }
        }
        return builder.toString();        
    }

}
