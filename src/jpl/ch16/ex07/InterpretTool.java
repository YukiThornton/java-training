package jpl.ch16.ex07;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    
    /*
     * Returns methods below. 
     * 1. public, protected, default (package) access, and private methods on the object's class
     * 2. all accessible public member methods
     */
    public static Method[] getAllMethods(Object object) {
        
        if (object == null) {
            throw new NullPointerException("object is null.");
        }
    
         Method[] declaredMethods = object.getClass().getDeclaredMethods();
         Method[] methods = object.getClass().getMethods();
         Set<Method> set = new HashSet<>();
         set.addAll(Arrays.asList(declaredMethods));
         set.addAll(Arrays.asList(methods));
         return set.toArray(new Method[set.size()]);
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
        
        Method[] methods = getAllMethods(object);
        
        if (methods.length == 0) {
            return;
        }
        
        int selectedMethodIndex = 0;
        for (int i = 0; i < methods.length; i++) {
            try {
                methods[i].setAccessible(true);
                System.out.println(methods[i]);
                
                if (methods[i].getDeclaringClass() == cls) {
                    selectedMethodIndex = i;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return;
            }
        }
        
        try {
            methods[selectedMethodIndex].setAccessible(true);
            methods[selectedMethodIndex].invoke(object, Integer.valueOf(1), "arg2");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return;
        }
        
    }
    
}
