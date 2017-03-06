package interpret;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterpretTools {

    /*
    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
        */

    public static List<Class<?>> findClassesIn(String packageName) {
        List<Class<?>> list = new ArrayList<>();
        switch (packageName) {
        case "java.awt":
            list.add(Button.class);
            list.add(Color.class);
            list.add(Checkbox.class);
            list.add(Component.class);
            list.add(Container.class);
            return list;

        default:
            list.add(Boolean.class);
            list.add(Byte.class);
            list.add(Character.class);
            list.add(Class.class);
            list.add(Comparable.class);
            list.add(Double.class);
            list.add(Enum.class);
            list.add(String.class);
            return list;
        }
    
        /*
        String scannedPath = packageName.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        Enumeration<URL> resources = null;
        ArrayList<File> directories = new ArrayList<File>();
        try {
            resources = Thread.currentThread().getContextClassLoader().getResources("jpl.ch14.ex07");
            System.out.println(resources);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (resources.hasMoreElements()) {
            System.out.println("in");
            try {
                directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (File file : directories) {
            System.out.println(file);
        }
        /*
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, packageName));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(findClassesIn(file, packageName));
        }
        return classes;
        return null;
        */
    }

/*
    private static List<Class<?>> findClassesIn(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(findClassesIn(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
        */
    
    
    public static Package[] getSortedPackages() {
        Package[] packages = Package.getPackages();
        Arrays.sort(packages, new Comparator<Package>() {
            @Override
            public int compare(Package pack1, Package pack2) {
                return pack1.getName().compareTo(pack2.getName());
            }
        });
        return packages;
    }

    /*
     * Returns fields below. 
     * 1. public, protected, default (package) access, and private fields on the object's class
     * 2. all accessible public member fields
     */
    public static Field[] getAllFields(Class<?> cls) {
        
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
    
         Field[] declaredFileds = cls.getDeclaredFields();
         Field[] fileds = cls.getFields();
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
    public static Method[] getAllMethods(Class<?> cls) {
        
        if (cls == null) {
            throw new NullPointerException("class is null.");
        }
        
        Method[] declaredMethods = cls.getDeclaredMethods();
        Method[] methods = cls.getMethods();
        Set<Method> set = new HashSet<>();
        set.addAll(Arrays.asList(declaredMethods));
        set.addAll(Arrays.asList(methods));
        return set.toArray(new Method[set.size()]);
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
    
}
