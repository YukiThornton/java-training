package jpl.ch24.ex03;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateInterpret {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Arguments required.");
        }
        
        try {
            DateFormat formatterMedium = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
            String input = null;
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    input = args[0];
                    continue;
                }
                input = input + " " + args[i];
            }
            Date date = formatterMedium.parse(input);
            DateFormat formatterShort = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
            DateFormat formatterLong = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
            DateFormat formatterFull = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
            System.out.println(formatterShort.format(date));
            System.out.println(formatterMedium.format(date));
            System.out.println(formatterLong.format(date));
            System.out.println(formatterFull.format(date));
        } catch (ParseException e) {
            System.out.println("Could not parse the text.");
            e.printStackTrace();
        }
    }
    
    

}
