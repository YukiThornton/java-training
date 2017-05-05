package interpret;

import java.lang.reflect.Array;

import interpret.LabInput.InputType;

public class ArrayLabData extends LabData {
    private Class<?> arrayType;
    private LabInput newVariableNameInput;
    private LabInput arrayLengthInput;
    
    public ArrayLabData(Class<?> arrayType) {
        this.labType = LabType.NEW_ARRAY;
        this.actionVerb = "create a new array.";
        this.arrayType = arrayType;
        this.newVariableNameInput = LabInput.create(InputType.VARIABLE_NAME);
        this.arrayLengthInput = LabInput.create(InputType.INDEX);
    }

    public Class<?> getArrayType() {
        return arrayType;
    }
    
    public LabInput getNewVariableNameInput() {
        return newVariableNameInput;
    }

    public LabInput getArrayLengthInput() {
        return arrayLengthInput;
    }

    public String getSimpleArrayTypeName() {
        return ReflectionTools.getSimpleName(arrayType);
    }
    
    @Override
    public boolean validate() {
        return newVariableNameInput.validate() && arrayLengthInput.validate();
    }

    @Override
    public Variable invoke() throws InterpretException {
        int arrayLength = (int)arrayLengthInput.getValidatedInput();
        Object array = Array.newInstance(arrayType, arrayLength);
        return new Variable(array, array.getClass(), (String)newVariableNameInput.getValidatedInput());
    }

}
