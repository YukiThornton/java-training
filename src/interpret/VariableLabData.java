package interpret;

import interpret.LabInput.InputType;

public class VariableLabData extends LabData {
    private Class<?> variableType;
    private LabInput newVariableNameInput;
    private Variable variable;
    private LabInput newValueInput;

    public VariableLabData(Class<?> cls, LabInput newValueInput) {
        this.labType = LabType.VARIABLE;
        this.actionVerb = "Create new variable";
        this.variableType = cls;
        this.variable = null;
        this.newVariableNameInput = LabInput.create(InputType.VARIABLE_NAME);
        this.newValueInput = newValueInput;
    }

    public VariableLabData(Variable variable, LabInput newValueInput) {
        this.labType = LabType.VARIABLE;
        this.actionVerb = "Set new value";
        this.variableType = variable.getType();
        this.variable = variable;
        this.newVariableNameInput = null;
        this.newValueInput = newValueInput;
    }
    
    public boolean createsNewValue() {
        return variable == null;
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
        if (createsNewValue()) {
            return invokeToCreatePrimitive();
        } else {
            return invokeToUpdatePrimitive();
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
