package jpl.ch16.ex05;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class ClassContentsTest {

    @Test
    public void test() {
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        String[] args1 = {"jpl.ch16.ex05.Foo"};
        ClassContents.main(args1);
        String[] args2 = {"jpl.ch16.ex05.Bar"};
        ClassContents.main(args2);
        
        assertEquals(out.toString(), 
                "class jpl.ch16.ex05.Foo" + System.lineSeparator() + 
                "  public String jpl.ch16.ex05.Foo.thisIsFiled" + System.lineSeparator() + 
                "    @jpl.ch16.ex05.MemberInfo(value=thisIsField written on Foo)" + System.lineSeparator() + 
                "  public jpl.ch16.ex05.Foo()" + System.lineSeparator() + 
                "    @jpl.ch16.ex05.MemberInfo(value=constructor written on Foo)" + System.lineSeparator() + 
                "  public void jpl.ch16.ex05.Foo.doFoo()" + System.lineSeparator() + 
                "    @jpl.ch16.ex05.MemberInfo(value=doFoo written on Foo)" + System.lineSeparator() + 
                "class jpl.ch16.ex05.Bar" + System.lineSeparator() + 
                "  public String jpl.ch16.ex05.Foo.thisIsFiled" + System.lineSeparator() + 
                "    @jpl.ch16.ex05.MemberInfo(value=thisIsField written on Foo)" + System.lineSeparator() + 
                "  public jpl.ch16.ex05.Bar()" + System.lineSeparator() + 
                "  public void jpl.ch16.ex05.Foo.doFoo()" + System.lineSeparator() + 
                "    @jpl.ch16.ex05.MemberInfo(value=doFoo written on Foo)" + System.lineSeparator()
                );
    }

}
