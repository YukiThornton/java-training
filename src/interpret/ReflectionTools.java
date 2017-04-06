package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReflectionTools {

    public static Field[] getStaticFields(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        Set<Field> set = getAllFields(cls);
        for (Iterator<Field> i = set.iterator(); i.hasNext();) {
            Field field = i.next();
            if (!Modifier.isStatic(field.getModifiers())) {
                i.remove();
            }
        }
        return getSortedFieldArray(set);
    }
    public static Field[] getFields(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        Set<Field> set = getAllFields(cls);
        return getSortedFieldArray(set);
    }
    private static Field[] getSortedFieldArray(Set<Field> set) {
        Field[] array = set.toArray(new Field[set.size()]);
        Arrays.sort(array, new Comparator<Field>() {

            @Override
            public int compare(Field o1, Field o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return array;
    }
    public static Method[] getStaticMethods(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        Set<Method> set = getAllMethods(cls);
        for (Iterator<Method> i = set.iterator(); i.hasNext();) {
            Method method = i.next();
            if (!Modifier.isStatic(method.getModifiers())) {
                i.remove();
            }
        }
        return getSortedMethodArray(set);
    }
    public static Method[] getMethods(Class<?> cls) {
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        Set<Method> set = getAllMethods(cls);
        return getSortedMethodArray(set);
    }
    private static Method[] getSortedMethodArray(Set<Method> set) {
        Method[] array = set.toArray(new Method[set.size()]);
        Arrays.sort(array, new Comparator<Method>() {

            @Override
            public int compare(Method o1, Method o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return array;
    }
    /*
     * Returns constructors below. 
     * 1. public, protected, default (package) access, and private constructors on the object's class
     */
    public static Constructor<?>[] getDeclaredConstructors(Class<?> cls) {
        
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        
        return cls.getDeclaredConstructors();
    }
    
    /*
     * Returns an Object of the specified field value.
     */
    public static Object getFieldValue(Field field, Object instance) throws IllegalArgumentException, IllegalAccessException {
        
        if (field == null) {
            throw new NullPointerException("class is null.");
        }
        if (!Modifier.isStatic(field.getModifiers()) && instance == null) {
            return null;
        }
        field.setAccessible(true);
        return field.get(instance);
    }
    
    public static String stringifyModifiers(int modifiers) {
        StringBuffer stringBuffer = new StringBuffer();
        if (Modifier.isPublic(modifiers)) {
            stringBuffer.append("public ");
        } else if (Modifier.isProtected(modifiers)) {
            stringBuffer.append("protected ");
        } else if (Modifier.isPrivate(modifiers)) {
            stringBuffer.append("private ");
        }
        if (Modifier.isAbstract(modifiers)) {
            stringBuffer.append("abstract ");
        }
        if (Modifier.isStatic(modifiers)) {
            stringBuffer.append("static ");
        }
        if (Modifier.isFinal(modifiers)) {
            stringBuffer.append("final ");        
        }
        if (Modifier.isSynchronized(modifiers)) {
            stringBuffer.append("synchronized ");
        }
        if (Modifier.isNative(modifiers)) {
            stringBuffer.append("native ");
        }
        if (Modifier.isStrict(modifiers)) {        
            stringBuffer.append("strictfp ");
        }
        if (Modifier.isTransient(modifiers)) {
            stringBuffer.append("transient ");
        }
        if (Modifier.isVolatile(modifiers)) {
            stringBuffer.append("volatile ");
        }

        return stringBuffer.toString();
    }
    public static String stringifyParams(Type[] params) {
        if (params.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        int count = 1;
        for (Type param : params) {
            stringBuffer.append(getSimpleName(param));
            stringBuffer.append(" ");
            stringBuffer.append("arg" + count++);
            stringBuffer.append(", ");
        }
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        return stringBuffer.toString();
    }

    public static String getSimpleName(Type type) {
        if (type instanceof Class<?>) {
            Class<?> cls = (Class<?>)type;
            return getSimpleName(cls);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            return getSimpleName(parameterizedType);
        } else if(type instanceof TypeVariable<?>) {
            TypeVariable<?> typeVariable = (TypeVariable<?>)type;
            return getSimpleName(typeVariable);
        } else if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType)type;
            return getSimpleName(genericArrayType);
        } else if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType)type;
            getSimpleName(wildcardType);
        } else {
            throw new RuntimeException("Cannot retrieve type of " + type);
        }
        return null;
    }
    
    public static String getSimpleName(Class<?> cls) {
        return cls.getSimpleName();
        
    }
    
    public static String getSimpleName(ParameterizedType parameterizedType) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getSimpleName(parameterizedType.getRawType()));
        stringBuffer.append("<");
        for (Type actualType : parameterizedType.getActualTypeArguments()) {
            stringBuffer.append(getSimpleName(actualType));
            stringBuffer.append(", ");
        }
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        stringBuffer.append(">");
        return stringBuffer.toString();
    }
    
    public static String getSimpleName(TypeVariable<?> typeVariable) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(typeVariable.getName());
        stringBuffer.append(stringifyBounds(typeVariable.getBounds(), true));
        return stringBuffer.toString();
    }
    
    public static String getSimpleName(GenericArrayType genericArrayType) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getSimpleName(genericArrayType.getGenericComponentType()));
        stringBuffer.append("[]");
        return stringBuffer.toString();
    }
    
    public static String getSimpleName(WildcardType wildcardType) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");
        stringBuffer.append(stringifyBounds(wildcardType.getUpperBounds(), true));
        stringBuffer.append(stringifyBounds(wildcardType.getLowerBounds(), false));
        return stringBuffer.toString();        
    }
    public static String stringifyBounds(Type[] bounds, boolean isUpperBounds) {
        if ((isUpperBounds && bounds.length == 1 && bounds[0] == Object.class) || (!isUpperBounds && bounds.length == 0)) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String expression = isUpperBounds ? "extends" : "super";
        stringBuffer.append(" ");
        stringBuffer.append(expression);
        stringBuffer.append(" ");
        stringBuffer.append(stringifyTypesDividedByComma(bounds));
        return stringBuffer.toString();
    }
    private static String stringifyTypesDividedByComma(Type[] types) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Type type : types) {
            stringBuffer.append(getSimpleName(type));
            stringBuffer.append(", ");
        }
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        return stringBuffer.toString();
    }
    
    
    
    /*
     * Returns fields below. 
     * 1. public, protected, default (package) access, and private fields on the object's class
     * 2. all accessible public member fields
     */
    private static Set<Field> getAllFields(Class<?> cls) {
        
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        
        Field[] declaredFileds = cls.getDeclaredFields();
        Field[] fileds = cls.getFields();
        Set<Field> set = new HashSet<>();
        set.addAll(Arrays.asList(declaredFileds));
        set.addAll(Arrays.asList(fileds));
        return set;
    }
    /*
     * Returns methods below. 
     * 1. public, protected, default (package) access, and private methods on the object's class
     * 2. all accessible public member methods
     */
    private static Set<Method> getAllMethods(Class<?> cls) {
        
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        
        Method[] declaredMethods = cls.getDeclaredMethods();
        Method[] methods = cls.getMethods();
        Set<Method> set = new HashSet<>();
        set.addAll(Arrays.asList(declaredMethods));
        set.addAll(Arrays.asList(methods));
        return set;
    }
    
}
