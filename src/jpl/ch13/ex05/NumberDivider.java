package jpl.ch13.ex05;

public class NumberDivider {
    
    public static String addComma(String source) {
        if (!source.matches("[0-9]*")){
            throw new IllegalArgumentException("Use numbers.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for(int i = source.length() - 1; i >= 0; i--){
            if(count >= 3){
                stringBuilder.insert(0, ',');
                count = 0;                
            }
            stringBuilder.insert(0, source.charAt(i));
            count++;
        }
        return stringBuilder.toString();
    }

}
