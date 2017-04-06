package jpl.ch20.ex06;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Calculator {
    private static char[] operators = {'+', '-', '='};
    private static final String END_COMMAND = "end";
    
    private Reader reader;
    private String[] names;
    private double[] values;

    public static void main(String[] args) {
        Reader reader = new InputStreamReader(System.in);
        String[] names = {"apple", "banana", "orange"};
        Calculator calculator = new Calculator(reader, names);
        double[] values = null;
        try {
            values = calculator.readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i] + " = " + values[i]);
        }
    }
    
    public Calculator(Reader reader, String[] names) {
        this.reader = reader;
        this.names = names;
        this.values = new double[names.length]; 
    }
    
    public double[] readInput() throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        
        int currentNameIndex = -1;
        int currentOperatorIndex = -1;
        
        while(tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
                if (tokenizer.sval.equals(END_COMMAND)) {
                    return values;
                }
                int wordTokenIndex;
                if ((wordTokenIndex = getWordIndex(tokenizer)) >= 0) {
                    currentNameIndex = wordTokenIndex;
                    currentOperatorIndex = -1;
                } else {
                    currentNameIndex = -1;
                    currentOperatorIndex = -1;
                }
            } else if (isOperator(tokenizer.ttype)) {
                int operatorIndex;
                if (currentNameIndex >= 0 && (operatorIndex = getOperatorIndex(tokenizer)) >= 0) {
                    currentOperatorIndex = operatorIndex;
                } else {
                    currentNameIndex = -1;
                    currentOperatorIndex = -1;                    
                }
            } else if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                if (currentOperatorIndex >= 0) {
                    values[currentNameIndex] = doMath(tokenizer.nval, currentOperatorIndex, currentNameIndex);
                }
                currentNameIndex = -1;
                currentOperatorIndex = -1;                    
            }

        }
        
        return values;
     }
     
    private int getWordIndex(StreamTokenizer tokenizer) {
        for (int i = 0; i < names.length; i++) {
            if (tokenizer.sval.equals(names[i])) {
                return i;
            }
        }
        return -1;
    }
    
    private int getOperatorIndex(StreamTokenizer tokenizer) {
        for (int i = 0; i < operators.length; i++) {
            if (tokenizer.ttype == operators[i]) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isOperator(int ttype) {
        for (char operator : operators) {
            if (operator == ttype) {
                return true;
            }
        }
        return false;
    }
    
    private double doMath(double val, int operatorIndex, int valueIndex) {
        switch (operatorIndex) {
        case 0:
            return values[valueIndex] + val;

        case 1:
            return values[valueIndex] - val;

        case 2:
            return val;

        default:
            throw new IllegalStateException("Something wrong!");
        }
    }

}
