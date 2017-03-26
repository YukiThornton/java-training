package interpret;

import java.time.LocalDateTime;

public class Variable {

    private Object value;
    private Class<?> type;
    private String name;
    private String pathName;
    private Variable[] arrayElements;
    private Variable parentArray;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    
    public Variable(Object value, Class<?> type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
        this.pathName = type.getCanonicalName();
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }
    
    public Variable(Object value, Class<?> type, String name, Variable[] arrayElements) {
        this.value = value;
        this.type = type;
        this.name = name;
        this.pathName = type.getCanonicalName();
        this.arrayElements = arrayElements;
        for (Variable element : arrayElements) {
            element.parentArray = this;
        }
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }
    
    public String toString() {
        return name;
    }

    public boolean isArray() {
        return arrayElements != null;
    }
    
    public boolean isArrayElement() {
        return parentArray != null;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.lastModifiedAt = LocalDateTime.now();
    }

    public Class<?> getType() {
        return type;
    }
    
    public String getSimpleTypeName() {
        return ReflectionTools.getSimpleName(type);
    }

    public String getName() {
        return name;
    }

    public String getPathName() {
        return pathName;
    }
    
    public Variable[] getArrayElements() {
        return arrayElements;
    }

    public Variable getParentArray() {
        return parentArray;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
    
    
}
