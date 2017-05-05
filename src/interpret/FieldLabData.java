package interpret;

import java.lang.reflect.Field;

public class FieldLabData extends LabData {
    private Class<?> declaredClass;
    private Variable variable;
    private Field fieldToInvoke;
    private LabInput newFieldValueInput;
    
    public FieldLabData(Field fieldToInvoke, LabInput newFieldValueInput) {
        this.labType = LabType.STATIC_FIELD;
        this.declaredClass = fieldToInvoke.getDeclaringClass();
        this.variable = null;
        this.fieldToInvoke = fieldToInvoke;
        this.newFieldValueInput = newFieldValueInput;
    }

    public FieldLabData(Variable variable, Field fieldToInvoke, LabInput newFieldValueInput) {
        this.labType = LabType.NON_STATIC_FIELD;
        this.declaredClass = variable.getType();
        this.variable = variable;
        this.fieldToInvoke = fieldToInvoke;
        this.newFieldValueInput = newFieldValueInput;
    }

    public Class<?> getDeclaredClass() {
        return declaredClass;
    }

    public Class<?> getFieldType() {
        return fieldToInvoke.getType();
    }

    public String getSimpleDeclaredClassName() {
        return ReflectionTools.getSimpleName(declaredClass);
    }
    
    public String getModifiers() {
        return ReflectionTools.stringifyModifiers(fieldToInvoke.getModifiers());
    }

    public Variable getVariable() {
        return variable;
    }

    public Field getFieldToInvoke() {
        return fieldToInvoke;
    }
    
    public Object getCurrentFieldValue() {
        Object value = null;
        try {
            value = ReflectionTools.getFieldValue(fieldToInvoke, labType == LabType.STATIC_FIELD ? null : variable.getValue());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            value = "Failed to obtain the current value.";
        }
        return value;
    }

    public LabInput getNewFieldValueInput() {
        return newFieldValueInput;
    }

    public String getFieldName() {
        return fieldToInvoke.getName();
    }
    
    @Override
    public boolean validate() {
        return newFieldValueInput.validate();
    }

    @Override
    public Variable invoke() throws InterpretException {
        try {
            fieldToInvoke.setAccessible(true);
            fieldToInvoke.set(labType == LabType.STATIC_FIELD ? null : variable.getValue(), newFieldValueInput.getValidatedInput());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new InterpretException("Failed to change the value of " + getFieldName() + ".", e);
        }
        return null;
    }

}
