
package jpl.ch22.ex01;

public class NumberTable {

    public static void main(String[] args) {
        double[] data = {
                1.234567890123456789, 2345678.901234567891, 3.456789012345678912,
                4.567890123456789123, 5.678901234567891234, 6.789012345678912345,
                7.890123456789123456, 890123.4567891234567, 90.12345678912345678};
        showDoubleTable(data, 3);
    }

    public static void showDoubleTable(double[] data, int row) {
        if (row <= 0 || row >= 9) {
            throw new IllegalArgumentException("row should be a number from 1 to 8.");
        }
        
        int counter = 0;
        for (double num : data) {
            if (counter == row) {
                System.out.println();
                counter = 0;
            }
            if (counter == 0) {
                System.out.print("|");
            }
            System.out.printf("%8.3g|", num);
            counter++;
        }
        System.out.println();
    }
}
