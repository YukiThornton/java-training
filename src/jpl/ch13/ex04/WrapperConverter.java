package jpl.ch13.ex04;

import java.util.ArrayList;

public class WrapperConverter {
    public static ArrayList<Object> convert(String source) {
        ArrayList<Object> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        String[] sentences = source.split("\n");
        for(String sentence: sentences){
            Object object = makeObject(sentence);
            if(object != null) {
                result.add(object);
            }
        }
        return result;
    }
    
    private static Object makeObject(String from){
        if (from == null) {
            return null;
        }
        String[] words = from.split(" ");
        if (words.length != 2){
            return null;
        }
        switch (words[0]) {
        case "Boolean":
            return new Boolean(words[1]);
        case "Character":
            if (words[1].length() > 1) {
                return null;
            }
            return new Character(words[1].charAt(0));
        case "Byte":
            try {
                return new Byte(words[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        case "Short":
            try {
                return new Short(words[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        case "Integer":
            try {
                return new Integer(words[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        case "Long":
            try {
                return new Long(words[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        case "Float":
            try {
                return new Float(words[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        case "Double":
            try {
                return new Double(words[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        default:
            return null;
        }
    }
    
    public static void main(String[] args){
        String source = "Boolean true\nCharacter 1\n";
        ArrayList<Object> result = convert(source);
        for (Object object : result) {
            System.out.println(object.getClass() + " " + object);
        }
    }
    
}
