package jpl.ch22.ex14;

import java.util.StringTokenizer;

public class DataReader {
    public static double sumDouble(String input) {
        if (input == null) {
            throw new NullPointerException("input is null.");
        }
        StringTokenizer tokenizer = new StringTokenizer(input, " ");
        double sum = 0;
        while (tokenizer.hasMoreTokens()) {
            try {
                double val = Double.parseDouble(tokenizer.nextToken());
                sum += val;
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return sum;
    }
}
