package jpl.ch22.ex12;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Pattern;

import jpl.ch03.ex07.Attr;
import jpl.ch22.ex04.Attributed;

public class AttributeReader {

    public static Attributed readAttrs(Reader source) throws IOException {
        Attributed attributed = new AttributedImpl();
        Scanner scanner = new Scanner(source);
        scanner.useDelimiter("(\\p{javaWhitespace}\\n)|(\\p{javaWhitespace})|(#.*\\p{javaWhitespace}\\n)");        
        Pattern pat = Pattern.compile("^([^#=]*)=([^=]*)", Pattern.MULTILINE);
        while (scanner.hasNextLine()) {
            String line = scanner.findInLine(pat);
            if (line != null) {
                String[] tokens = line.split("=");
                Attr attr = new Attr(tokens[0], new Double(tokens[1]));
                attributed.add(attr);
                scanner.nextLine();
            } else if (scanner.nextLine().length() == 0) {
                continue;
            } else {
                System.out.print(scanner.nextLine());
                throw new IOException("input format error");
            }
        }
        return attributed;
    }

}
