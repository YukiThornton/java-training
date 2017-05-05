package interpret;

import java.lang.reflect.Array;
import java.time.LocalDateTime;

public class Variable {

    private Object value;
    private Class<?> type;
    private String name;
    private String pathName;
    private Variable[] arrayElements;
    private Variable parentArray;
    private int indexInParentArray;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    
    public Variable(Object value, Class<?> type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
        this.pathName = type.getCanonicalName();
        if (type.isArray()) {
            this.arrayElements = new Variable[Array.getLength(value)];
            for (int i = 0; i < Array.getLength(value); i++) {
                arrayElements[i] = new Variable(this, i, Array.get(value, i), type.getComponentType());
            }
        }
        this.indexInParentArray = -1;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }
    
    private Variable(Variable parent, int indexInParentArray, Object value, Class<?> type) {
        this.value = value;
        this.type = type;
        this.name = null;
        this.pathName = type.getCanonicalName();
        this.parentArray = parent;
        this.indexInParentArray = indexInParentArray;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    
    }
    
    public String toString() {
        if (isArrayElement()) {
            return parentArray.getName() + "[" + indexInParentArray + "]";
        } else {
            return name;
        }
    }

    public boolean isArray() {
        return arrayElements != null;
    }
    
    public boolean isArrayElement() {
        return parentArray != null;
    }
    
    public boolean isNull() {
        return value == null;
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

    public int getIndexInParentArray() {
        return indexInParentArray;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
    
    
}
