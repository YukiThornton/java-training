package jpl.ch16.ex04;

import java.lang.annotation.Annotation;

public class AnnotationDictionary {

    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName(args[0]);
            showAnnotations(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static void showAnnotations(Class<?> cls) {
        Annotation[] annotations = cls.getAnnotations();
        if (annotations.length == 0) {
            System.out.println("No annotation was found.");
        }
        for(Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }

}
