package jpl.ch16.ex09;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.HashSet;

public class ClassContents {

    public static void main(String[] args) {
        Class<?> cls = HashMap.class;
        System.out.println(cls);
        printClassDeclaration(cls, 0);

    }
    
    private static final String OMITTING_PACKAGE_NAME = "java.lang.";
    
    public static void printClassDeclaration(Class<?> cls, int indent) {
        printModifiers(cls, indent);
        System.out.print("class ");
        System.out.print(getSimpleName(cls) + " ");
        if (cls.getTypeParameters().length != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<");
            for (Type typeParameter : cls.getTypeParameters()) {
                stringBuffer.append(getSimpleName(typeParameter));
                stringBuffer.append(", ");
            }
            stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
            stringBuffer.append("> ");
            System.out.print(stringBuffer.toString());
        }
        System.out.print(stringifySuperClass(cls));
        System.out.print(stringifyInterfaces(cls));
        
        System.out.println("{");
        
        printFields(cls.getDeclaredFields(), indent + 1);
        printConstructors(cls.getDeclaredConstructors(), indent + 1);
        printMethods(cls.getDeclaredMethods(), indent + 1);
        printIndent(indent);
        System.out.println("}");
    }
    
    public static void printModifiers(AnnotatedElement element, int indent) {
        printAnnotations(element, indent);
        printIndent(indent);
        if (element instanceof Class<?>) {
            Class<?> cls = (Class<?>)element;
            System.out.print(stringifyModifiers(cls.getModifiers()));        
        } else if (element instanceof Field) {
            Field field = (Field)element;
            System.out.print(stringifyModifiers(field.getModifiers()));        
        } else if (element instanceof Constructor) {
            Constructor<?> constructor = (Constructor<?>)element;
            System.out.print(stringifyModifiers(constructor.getModifiers()));        
        } else if (element instanceof Method) {
            Method method = (Method)element;
            System.out.print(stringifyModifiers(method.getModifiers()));        
        } else {
            throw new RuntimeException("Cannot retrieve modifiers of " + element);
        }
    }
    
    public static void printAnnotations(AnnotatedElement element, int indent) {
        for (Annotation annotation : element.getAnnotations()) {
            printIndent(indent);
            System.out.println(strip(annotation.toString(), OMITTING_PACKAGE_NAME));
        }
    }
    
    public static void printFields(Field[] fields, int indent) {
        for (Field field : fields) {
            printField(field, indent);
        }
    }
    
    public static void printField(Field field, int indent) {
        printModifiers(field, indent);
        System.out.print(getSimpleName(field.getGenericType()) + " ");
        System.out.println(field.getName() + ";");
    }
    
    public static void printConstructors(Constructor<?>[] constructors, int indent) {
        for (Constructor<?> constructor : constructors) {
            printConstructor(constructor, indent);
        }
    }
    
    public static void printConstructor(Constructor<?> constructor, int indent) {
        printModifiers(constructor, indent);
        System.out.print(getSimpleName(constructor.getDeclaringClass()) + " (");
        System.out.print(stringifyParams(constructor.getGenericParameterTypes()));
        System.out.print(") ");
        System.out.println(stringifyExceptions(constructor.getGenericExceptionTypes()) + "{}");
    }
    
    public static void printMethods(Method[] methods, int indent) {
        for (Method method: methods) {
            printMethod(method, indent);
        }
    }
    
    public static void printMethod(Method method, int indent) {
        printModifiers(method, indent);
        System.out.print(getSimpleName(method.getGenericReturnType()) + " ");
        System.out.print(method.getName() + " (");
        System.out.print(stringifyParams(method.getGenericParameterTypes()));
        System.out.print(") ");
        System.out.println(stringifyExceptions(method.getGenericExceptionTypes()) + "{}");        
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
    
    public static String stringifySuperClass(Class<?> cls) {
        StringBuffer stringBuffer = new StringBuffer();
        Type superClass = cls.getGenericSuperclass();
        if (superClass == null || superClass == Object.class) {
            return "";
        }
        stringBuffer.append("extends ");
        stringBuffer.append(getSimpleName(superClass));
        stringBuffer.append(" ");
        return stringBuffer.toString();
    }
    
    public static String stringifyInterfaces(Class<?> cls) {
        StringBuffer stringBuffer = new StringBuffer();
        Type[] interfaces = cls.getGenericInterfaces();
        if (interfaces.length == 0) {
            return "";
        }
        stringBuffer.append("implements ");
        stringBuffer.append(stringifyTypesDividedByComma(interfaces));
        stringBuffer.append(" ");
        return stringBuffer.toString();
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
    
    public static String stringifyExceptions(Type[] exceptions) {
        if (exceptions.length <= 0) {
            return "";
        }
        return "throws " + stringifyTypesDividedByComma(exceptions) + " ";
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
    
    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("    ");
        }    
    }
    
    private static String strip(String source, String target) {
        return source.replaceAll(target, "");
    }


}
