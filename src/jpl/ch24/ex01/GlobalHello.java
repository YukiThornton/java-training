package jpl.ch24.ex01;

import java.util.Locale;
import java.util.ResourceBundle;

public class GlobalHello {

    public static void main(String[] args) {
        ResourceBundle res = AnotherResourceBundle.getBundle("jpl.ch24.ex01.AnotherResourceBundle", new Locale("en", "AU"));
        //ResourceBundle res = ResourceBundle.getBundle("jpl.ch24.ex01.GlobalRes", new Locale("eo", "KI"));
        String msg;
        if (args.length > 0) {
            msg = res.getString(GlobalRes.GOODBYE);
        } else {
            msg = res.getString(GlobalRes.HELLO);
        }
        System.out.println(msg);
    }

}
