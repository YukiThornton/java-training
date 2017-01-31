package jpl.ch13.ex06;

public class NumberDivider {
    
    public static String addComma(String source, char divider, int gap) {
        if (!source.matches("[0-9]*")){
            throw new IllegalArgumentException("Use numbers.");
        }
        if (gap <= 0){
            throw new IllegalArgumentException("Set a number bigger than 0 for gap.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for(int i = source.length() - 1; i >= 0; i--){
            if(count >= gap){
                stringBuilder.insert(0, divider);
                count = 0;                
            }
            stringBuilder.insert(0, source.charAt(i));
            count++;
        }
        return stringBuilder.toString();
    }

}
