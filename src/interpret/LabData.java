package interpret;

public abstract class LabData {
    enum LabType{CONSTRUCTOR, STATIC_METHOD, NON_STATIC_METHOD, NEW_ARRAY, ARRAY_ELEMENT, MODIFY_VARIABLE}
    
    protected LabType labType;
    
    public LabType getLabType() {
        return labType;
    }
    
    public abstract boolean validate();
    public abstract Variable invoke() throws InterpretException;

    /*
    private Class<?> declaredClass;
    private Variable declaredVariable;
    private Field fieldToModify;
    private Constructor<?> constructorToInvoke;
    private Method methodToInvoke;
    private int indexOfArray;
    private LabInput newVariableNameInput;
    private LabInput[] paramInputs;
    
    protected LabData() {
    }
    
    public static LabData create(LabType labType, Class<?> declaredClass, Constructor<?> constructor) {
        if (labType != LabType.CONSTRUCTOR) {
            throw new IllegalArgumentException("Use this method only for LabType.Constructor.");
        }
        LabData labData = new LabData();
        labData.labType = labType;
        labData.declaredClass = declaredClass;
        labData.constructorToInvoke = constructor;
        // labInputs
        labData.newVariableNameInput = new LabInput(InputType.VARIABLE_NAME);
        
        return labData;
    }
    
    public static LabData create(LabType labType, Class<?> declaredClass, Method method) {
        if (labType != LabType.STATIC_METHOD) {
            throw new IllegalArgumentException("Use this method only for LabType.STATIC_METHOD.");
        }
        LabData labData = new LabData();
        labData.labType = labType;
        labData.declaredClass = declaredClass;
        labData.methodToInvoke = method;
        // labInputs
        labData.newVariableNameInput = new LabInput(InputType.VARIABLE_NAME);
        
        return labData;
    }
    
    public static LabData create(LabType labType, Variable declaredVariable, Method method) {
        if (labType != LabType.NON_STATIC_METHOD) {
            throw new IllegalArgumentException("Use this method only for LabType.NON_STATIC_METHOD.");
        }
        LabData labData = new LabData();
        labData.labType = labType;
        labData.declaredVariable = declaredVariable;
        labData.methodToInvoke = method;
        // labInputs
        return labData;
    }
    
    public static LabData create(LabType labType, Variable declaredVariable, Field field) {
        if (labType != LabType.MODIFY_VARIABLE) {
            throw new IllegalArgumentException("Use this method only for LabType.MODIFY_VARIABLE.");
        }
        LabData labData = new LabData();
        labData.labType = labType;
        labData.declaredVariable = declaredVariable;
        labData.fieldToModify = field;
        // labInputs
        return labData;
    }
    
    public static LabData create(LabType labType, Class<?> declaredClass) {
        if (labType != LabType.NEW_ARRAY) {
            throw new IllegalArgumentException("Use this method only for LabType.NEW_ARRAY.");
        }
        LabData labData = new LabData();
        labData.labType = labType;
        labData.declaredClass = declaredClass;
        // labInputs
        return labData;
    }
    
    public static LabData create(LabType labType, Class<?> declaredClass, int indexOfArray) {
        if (labType != LabType.ARRAY_ELEMENT) {
            throw new IllegalArgumentException("Use this method only for LabType.ARRAY_ELEMENT.");
        }
        LabData labData = new LabData();
        labData.labType = labType;
        labData.declaredClass = declaredClass;
        labData.indexOfArray = indexOfArray;
        // labInputs
        return labData;
    }
    
    */
}
