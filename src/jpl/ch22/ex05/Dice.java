package jpl.ch22.ex05;

import java.util.Arrays;
import java.util.Random;

public class Dice {

    private static final int MIN_NUM_OF_DIE = 1;
    private static final int MAX_NUM_OF_DIE = 6;
    
    public static void main(String[] args) {
        showDistribution(3, 500);
    }
    
    public static void showDistribution(int numberOfDice, int numberOfTimes) {
        if (numberOfDice <= 0 || numberOfTimes <= 0) {
            throw new IllegalArgumentException("The number of dice and the number of times should be more than 0.");
        }
        
        int[] actual = getActualDistribution(numberOfDice, numberOfTimes);
        int[] expected = getExpectedDistribution(numberOfDice);
        double[] actualPercentage = convertIntoPercentage(actual);
        double[] expectedPercentage = convertIntoPercentage(expected);
        for (int i = 0; i < expected.length; i++) {
            System.out.printf("value%2d: expected %.2f%%, actual %2d times(%.2f%%)\n", i, expectedPercentage[i], actual[i], actualPercentage[i]);
        }    
    }

    public static int roll() {
        Random random = new Random();
        return random.nextInt(MAX_NUM_OF_DIE) + 1;
    }

    public static int roll(int numberOfDice) {
        if (numberOfDice <= 0) {
            throw new IllegalArgumentException("The number of dice should be more than 0.");
        }
        int sum = 0;
        for (int i = 0; i < numberOfDice; i++) {
            sum += roll();
        }
        return sum;
    }
    
    public static int[] getActualDistribution(int numberOfDice, int numberOfTimes) {
        if (numberOfDice <= 0 || numberOfTimes <= 0) {
            throw new IllegalArgumentException("The number of dice and the number of times should be more than 0.");
        }
        
        int[] result = getNewDistributionArray(numberOfDice, 0);
        for (int i = 1; i <= numberOfTimes; i++) {
            result[roll(numberOfDice)]++;
        }
        return result;
    }
    
    public static int[] getExpectedDistribution(int numberOfDice) {
        if (numberOfDice <= 0) {
            throw new IllegalArgumentException("The number of dice should be more than 0.");
        }
        
        int[] expectedDistributionForOneDie = getExpectedDistributionForOneDie();
        if (numberOfDice == 1) {
            return expectedDistributionForOneDie;
        }
        
        return getExpectedDistributionForMoreThanOneDie(expectedDistributionForOneDie, numberOfDice);    
    }
    
    private static int[] getNewDistributionArray(int numberOfDice, int initialValue) {
        int[] result = new int[numberOfDice * MAX_NUM_OF_DIE + 1];
        Arrays.fill(result, initialValue);
        return result;
    }
    
    private static int[] getExpectedDistributionForOneDie() {
        int[] result = getNewDistributionArray(1, 1);
        result[0] = 0;
        return result;    
    }
    
    private static int[] getExpectedDistributionForMoreThanOneDie(int[] expectedDistributionForOneDie, int numberOfDice) {
        int[] smallerResult = expectedDistributionForOneDie;
        int[] biggerResult = null;
        for (int i = 2; i <= numberOfDice; i++) {
            biggerResult = getNewDistributionArray(i, 0);
            for (int j = MIN_NUM_OF_DIE; j <= MAX_NUM_OF_DIE; j++) {
                for (int k = 0; k < smallerResult.length; k++) {
                    biggerResult[k + j] += smallerResult[k];
                }
            }
            smallerResult = biggerResult;
        }
        return biggerResult;
    }
    
    private static double[] convertIntoPercentage(int[] target) {
        int sum = 0;
        for (int val : target) {
            sum += val;
        }
        double[] result = new double[target.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = ((double)target[i] / (double)sum) * 100;
        }
        return result;
    }
}
