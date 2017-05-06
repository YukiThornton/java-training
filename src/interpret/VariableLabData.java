package interpret;

import interpret.LabInput.InputType;

public class VariableLabData extends LabData {
    private Class<?> variableType;
    private LabInput newVariableNameInput;
    private Variable variable;
    private LabInput newValueInput;
    private ACTION action;
    private enum ACTION {CREATE_PRIMITIVE, CREATE_NON_PRIMITIVE, UPDATE_PRIMITIVE, UPDATE_NON_PRIMITIVE};

    public VariableLabData(Class<?> cls, LabInput newValueInput) {
        this.labType = LabType.VARIABLE;
        this.actionVerb = "Create a new variable";
        this.action = cls.isPrimitive() ? ACTION.CREATE_PRIMITIVE : ACTION.CREATE_NON_PRIMITIVE;
        this.variableType = cls;
        this.variable = null;
        this.newVariableNameInput = LabInput.create(InputType.VARIABLE_NAME);
        this.newValueInput = newValueInput;
    }

    public VariableLabData(Variable variable, LabInput newValueInput) {
        this.labType = LabType.VARIABLE;
        this.actionVerb = "Set a new value";
        this.action = variable.getType().isPrimitive() ? ACTION.UPDATE_PRIMITIVE : ACTION.UPDATE_NON_PRIMITIVE;
        this.variableType = variable.getType();
        this.variable = variable;
        this.newVariableNameInput = null;
        this.newValueInput = newValueInput;
    }
    
    public boolean createsNewValue() {
        return action == ACTION.CREATE_PRIMITIVE || action == ACTION.CREATE_NON_PRIMITIVE;
    }
    
    public boolean isPrimitive() {
        return action == ACTION.CREATE_PRIMITIVE || action == ACTION.UPDATE_PRIMITIVE;
    }

    public Class<?> getVariableType() {
        return variableType;
    }

    public String getSimplePrimitiveTypeName() {
        return ReflectionTools.getSimpleName(variableType);
    }
    
    public LabInput getNewVariableNameInput() {
        return newVariableNameInput;
    }

    public Variable getVariable() {
        return variable;
    }

    public LabInput getNewValueInput() {
        return newValueInput;
    }

    @Override
    public boolean validate() {
        if (!newValueInput.validate()) {
            return false;
        }
        if (createsNewValue() && !newVariableNameInput.validate()) {
            return false;
        }
        return true;
    }

    @Override
    public Variable invoke() throws InterpretException {
        switch (action) {
        case CREATE_PRIMITIVE:
            return invokeToCreatePrimitive();
        case UPDATE_PRIMITIVE:
            return invokeToUpdatePrimitive();
        case CREATE_NON_PRIMITIVE:
            return null;
        case UPDATE_NON_PRIMITIVE:
            return null;
        default:
            throw new IllegalStateException("Something wrong happened!");
        }
    }
    
    private Variable invokeToCreatePrimitive() {
        return new Variable(newValueInput.getValidatedInput(), variableType, (String)newVariableNameInput.getValidatedInput());    
    }

    private Variable invokeToUpdatePrimitive() {
        variable.setValue(newValueInput.getValidatedInput());
        variable.updateLastModifiedAt();
        return null;
    }

}
