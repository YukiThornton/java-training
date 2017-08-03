package java8.ch03.ex07;

import java.util.Comparator;

public class ComparatorBuilder {
    static Comparator<String> comparatorGenerator(boolean isAscendingOrder, boolean ignoreCase, boolean ignoreWhitespace) {
        return (a, b) -> {
            if (ignoreWhitespace) {
                a = a.replaceAll("\\s", "");
                b = b.replaceAll("\\s", "");
            }
            int result = 0;
            if (ignoreCase) {
                result = a.compareToIgnoreCase(b);
            } else {
                result = a.compareTo(b);
            }
            if (isAscendingOrder) {
                return result;
            } else {
                return -1 * result;
            }
        };
    }
}
