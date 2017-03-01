package jpl.ch16.ex03;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClassContents {

    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName(args[0]);
            System.out.println(c);
            printMembers(mergeMembers(c.getFields(), c.getDeclaredFields()));
            printMembers(mergeMembers(c.getConstructors(), c.getDeclaredConstructors()));
            printMembers(mergeMembers(c.getMethods(), c.getDeclaredMethods()));
        } catch (ClassNotFoundException e) {
            System.out.println("unknown class : " + args[0]);
        }
    }
    
    private static void printMembers(Member[] mems) {
        for (Member m : mems) {
            if (m.getDeclaringClass() == Object.class) {
                continue;
            }
            String decl = m.toString();
            System.out.print("  ");
            System.out.println(strip(decl, "java.lang."));
        }
    }
    
    private static Member[] mergeMembers(Member[] memFirst, Member[] memSecond) {
        Set<Member> set = new HashSet<>();
        set.addAll(Arrays.asList(memFirst));
        set.addAll(Arrays.asList(memSecond));
        return set.toArray(new Member[set.size()]);
    }
    
    private static String strip(String source, String target) {
        return source.replaceAll(target, "");
    }

}
