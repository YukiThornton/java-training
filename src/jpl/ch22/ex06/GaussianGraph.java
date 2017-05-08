package jpl.ch22.ex06;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GaussianGraph {

    private Random random = new Random();
    private int[] positiveCounter;
    private int[] negativeCounter;
    private double sum = 0.0;
    private List<Double> overflow = new ArrayList<>();
    
    private int amountOfSamples;
    private int digitsAfterDecimal;
    private int maxValue;
    private int precision;

    public static void main(String[] args) {
        GaussianGraph graph = new GaussianGraph(100, 3, 1);
        System.out.println("Average: " + graph.average());
        if (graph.hasOverflowedValues()) {
            System.out.print("Overflowed values: ");
            double[] overflowedValue = graph.overflowedValues();
            for (int i = 0; i < overflowedValue.length; i++) {
                System.out.print(overflowedValue[i] + ", ");
            }
            System.out.println();
        } else {
            System.out.println("Overflowed values: None");
        }
        graph.show();
    }
    
    public GaussianGraph(int amountOfSamples, int maxValue, int digitsAfterDecimal) {
        this.amountOfSamples = amountOfSamples;
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.maxValue = maxValue;
        this.precision = precision();
        create();
    }
    
    public double average() {
        return sum / amountOfSamples;
    }
    
    public boolean hasOverflowedValues() {
        return overflow.size() > 0;
    }
    
    public double[] overflowedValues() {
        Collections.sort(overflow);
        double[] result = new double[overflow.size()];
        for (int i = 0; i < overflow.size(); i++) {
            result[i] = overflow.get(i).doubleValue();
        }
        return result;
    }
    
    public void show() {
        showNegativePartOfGraph();
        showPositivePartOfGraph();
    }
    
    private void create() {
        positiveCounter = new int[precision * maxValue + 1];
        negativeCounter = new int[precision * maxValue + 1];
        fill();
    }
    
    private void fill() {
        for (int i = 0; i < amountOfSamples; i++) {
            double val = next(digitsAfterDecimal);
            sum += val;
            addValueToGraph(val);
        }    
    }
    
    private double next() {
        return random.nextGaussian();
    }
    
    private double next(int digitsAfterDecimal) {
        BigDecimal decimal = new BigDecimal(next());
        return decimal.setScale(digitsAfterDecimal, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    private void addValueToGraph(double val) {
        if (val > maxValue || val < -maxValue) {
            overflow.add(val);
        } else if (val >= 0) {
            positiveCounter[(int)(val * precision)]++;
        } else {
            negativeCounter[(int)(val * -precision)]++;
        }    
    }
    
    private int precision() {
        int precision = 1;
        for (int i = 1; i <= digitsAfterDecimal; i++) {
            precision *= 10;
        } 
        return precision;   
    }
    
    private void showNegativePartOfGraph() {
        for (int i = negativeCounter.length - 1; i > 0; i--) {
            printNumber(((double)i) / -precision);
            if (negativeCounter[i] != 0) {
                printStars(negativeCounter[i]);
            }
            System.out.println();
        }    
    }
    
    private void showPositivePartOfGraph() {
        for (int i = 0; i < positiveCounter.length; i++) {
            printNumber(((double)i) / precision);
            if (positiveCounter[i] != 0) {
                printStars(positiveCounter[i]);
            }
            System.out.println();
        }    
    }
    
    private void printNumber(double number) {
        System.out.printf("%+1." + digitsAfterDecimal + "f ||", number);
    }
    
    private void printStars(int amount) {
        for (int i = 1; i <= amount; i++) {
            System.out.print("*");            
        }    
    }

}
