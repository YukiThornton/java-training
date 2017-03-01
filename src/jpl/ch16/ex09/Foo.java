package jpl.ch16.ex09;
import java.util.List;

import jpl.ch16.ex05.*;

@ClassInfo(
    created = "Today",
    createdBy = "Me",
    lastModified = "Today",
    lastModifiedBy = "Me",
    revision = 1
)
public class Foo {
    
    @MemberInfo("thisIsField written on Foo")
    public String thisIsFiled;
    
    @MemberInfo("constructor written on Foo")
    public Foo() {
        super();
    }
    
    @MemberInfo("doFoo written on Foo")
    public void doFoo(List<String> list) {
        
    }
}
