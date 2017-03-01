package jpl.ch16.ex06;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InterpretTool {

    /*
     * Returns new instance of the specified class.
     * This method uses the class's constructor which has no arguments.
     */
    public static Object newInstance(Class<?> cls) throws InstantiationException {
    
        if (cls == null) {
            throw new NullPointerException("cls is null.");
        }
    
        Constructor<?> constructor;
        try {
            constructor = cls.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            InstantiationException exception = new InstantiationException("Could not find matching constructors.");
            exception.initCause(e);
            throw exception;
        }
        
        constructor.setAccessible(true);
        Object result = null;
        try {
            result = constructor.newInstance();
        } catch (InstantiationException e) {
            InstantiationException exception = new InstantiationException("Could not find matching constructors.");
            exception.initCause(e);
            throw exception;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            InstantiationException exception = new InstantiationException("Proper arguments were not set.");
            exception.initCause(e);
            throw exception;
        } catch (InvocationTargetException e) {
            InstantiationException exception = new InstantiationException("The constructors threw an exception.");
            exception.initCause(e);
            throw exception;
        }
        return result;
    }
    
    /*
     * Returns fields below. 
     * 1. public, protected, default (package) access, and private fields on the object's class
     * 2. all accessible public member fields
     */
    public static Field[] getAllFields(Object object) {
        
        if (object == null) {
            throw new NullPointerException("object is null.");
        }
    
         Field[] declaredFileds = object.getClass().getDeclaredFields();
         Field[] fileds = object.getClass().getFields();
         Set<Field> set = new HashSet<>();
         set.addAll(Arrays.asList(declaredFileds));
         set.addAll(Arrays.asList(fileds));
         return set.toArray(new Field[set.size()]);
    }
    
    public static void main(String[] args) {
        Class<?> cls = null;
        try {
            cls = Class.forName("jpl.ch16.ex06.SampleChild");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Object object = null;

        try {
            object = newInstance(cls);
        } catch (InstantiationException e) {
            e.printStackTrace();
            return;
        }
        
        Field[] fields = getAllFields(object);
        
        if (fields.length == 0) {
            return;
        }
        
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                System.out.println(field + " = " + field.get(object));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        
        try {
            fields[0].set(object, Integer.valueOf(10));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                System.out.println(field + " = " + field.get(object));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        
    }
    
}
