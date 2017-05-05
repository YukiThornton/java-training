package interpret;

public class Info {
    enum InfoType {
        CLASS("Class", false, true, true, true), 
        PRIMITIVE_TYPE("Primitive Type", false, false, false, false),
        OBJECT_VARIABLE("Variable", true, true, false, true),
        PRIMITIVE_VARIABLE("Primitive Variable", true, false, false, false);
        
        private String value;
        private boolean canHaveVariable;
        private boolean hasFields;
        private boolean hasConstructors;
        private boolean hasMethods;
        
        private InfoType(String value, boolean canHaveVariable, boolean hasFields, boolean hasConstructors, boolean hasMethods){
            this.value = value;
            this.canHaveVariable = canHaveVariable;
            this.hasFields = hasFields;
            this.hasConstructors = hasConstructors;
            this.hasMethods = hasMethods;
        }
    }
    
    private InfoType infoType;
    private String infoTitle;
    private Class<?> type;
    private Variable variable;
    private boolean variableCreatable;
    private boolean arrayCreatable;
    private boolean modifieble;
    
    private Info() {
    }
    
    public static Info create(InfoType infoType, Class<?> type, Variable variable) {
        if (!(infoType.canHaveVariable ^ variable == null)) {
            throw new IllegalArgumentException();
        }
        Info info = new Info();
        info.infoType = infoType;
        info.type = type;
        info.variable = variable;
        switch (infoType) {
        case CLASS:
        case PRIMITIVE_TYPE:
            info.variableCreatable = true;
            info.arrayCreatable = true;
            info.modifieble = false;
            info.infoTitle = infoType.value + " " + info.getTypeName();
            break;
        case OBJECT_VARIABLE:
        case PRIMITIVE_VARIABLE:
            info.variableCreatable = false;
            info.arrayCreatable = false;
            info.modifieble = true;
            info.infoTitle = infoType.value + " " + info.getSimpleTypeName() + " " + info.variable.toString();
            break;
        default:
            info.variableCreatable = false;
            info.arrayCreatable = false;
            info.modifieble = true;
            break;
        }
        return info;
    }
    
    public InfoType getInfoType() {
        return infoType;
    }
    public String getInfoTitle() {
        return infoTitle;
    }
    public Class<?> getType() {
        return type;
    }
    public Class<?> getSuperCls() {
        return type.getSuperclass();
    }
    public Class<?>[] getInterfaces() {
        return type.getInterfaces();
    }
    public Class<?> getEnclosingCls() {
        return type.getEnclosingClass();
    }
    public String getTypeName() {
        return type.getCanonicalName();
    }
    public String getSimpleTypeName() {
        return ReflectionTools.getSimpleName(type);
    }
    public boolean isVariable() {
        return variable != null;
    }
    public Variable getVariable() {
        return variable;
    }

    public boolean canCreateVariable() {
        return variableCreatable;
    }

    public boolean canCreateArray() {
        return arrayCreatable;
    }
    public boolean hasFields(){
        return infoType.hasFields;
    }
    public boolean hasConstructors() {
        return infoType.hasConstructors;
    }
    public boolean hasMethods() {
        return infoType.hasMethods;
    }

    public boolean canModify() {
        return modifieble;
    }
    
}
