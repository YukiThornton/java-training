package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class UserVariable {
    private Object object;
    private Member createdWith;
    private Class<?> type;
    private String name;
    
    public UserVariable(Object object, Member createdWith, String name) {
        this.object = object;
        this.createdWith = createdWith;
        if (createdWith instanceof Method) {
            this.type = ((Method)createdWith).getReturnType();
        } else if (createdWith instanceof Constructor<?>) {
            this.type = ((Constructor<?>)createdWith).getDeclaringClass();
        } else {
            System.out.println("Something wrong happened in UserVariable constructor");
        }
        this.name = name;
    }
    
    public String getNonSimpleTypeName() {
        return type.getCanonicalName();
    }
    
    public String getSimpleTypeName() {
        return InterpretTools.getSimpleName(type);
    }
    
    public Object getObject() {
        return object;
    }
    public Member getCreatedWith() {
        return createdWith;
    }
    public Class<?> getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    
    public String toString() {
        return getName();
    }

}
