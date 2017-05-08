package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import interpret.LabInput.InputType;

public class ConstructorLabData extends LabData {
    private Class<?> declaredClass;
    private Variable destObjectVariable;
    private Constructor<?> constructorToInvoke;
    private LabInput newVariableNameInput;
    private LabInput[] paramInputs;

    public ConstructorLabData(Constructor<?> constructor, LabInput[] paramInputs) {
        this.labType = LabType.CONSTRUCTOR;
        this.actionVerb = "Create new variable";
        this.declaredClass = constructor.getDeclaringClass();
        this.destObjectVariable = null;
        this.constructorToInvoke = constructor;
        this.newVariableNameInput = LabInput.create(InputType.VARIABLE_NAME);
        this.paramInputs = paramInputs;
    }

    public ConstructorLabData(Constructor<?> constructor, Variable destObjectVariable, LabInput[] paramInputs) {
        this.labType = LabType.CONSTRUCTOR;
        this.actionVerb = "Set new value";
        this.declaredClass = constructor.getDeclaringClass();
        this.destObjectVariable = destObjectVariable;
        this.constructorToInvoke = constructor;
        this.newVariableNameInput = null;
        this.paramInputs = paramInputs;
    }

    public boolean hasParameters() {
        return constructorToInvoke.getParameterTypes().length != 0;
    }
    
    public boolean updatesExistingVariable() {
        return destObjectVariable != null;
    }
    
    public Variable getDestObjectVariable() {
        return destObjectVariable;
    }

    public Class<?> getDeclaredClass() {
        return declaredClass;
    }
    
    public String getSimpleDeclaredClassName() {
        return ReflectionTools.getSimpleName(declaredClass);
    }

    public Constructor<?> getConstructorToInvoke() {
        return constructorToInvoke;
    }

    public LabInput getNewVariableNameInput() {
        return newVariableNameInput;
    }

    public LabInput[] getParamInputs() {
        return paramInputs;
    }

    @Override
    public boolean validate() {
        boolean result = true;
        if (!updatesExistingVariable() && !newVariableNameInput.validate()){
            result = false;
        }
        for (LabInput input: paramInputs) {
            if (!input.validate()){
                result = false;
            }
        }
        return result;
    }

    @Override
    public Variable invoke() throws InterpretException {
        Object[] params = new Object[paramInputs.length];
        for (int i = 0; i < paramInputs.length; i++){
            params[i] = paramInputs[i].getValidatedInput();
        }
        Object value;
        try {
            value = constructorToInvoke.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new InterpretException("Failed to create instance.", e.getCause());
        }
        if (updatesExistingVariable()) {
            return updateVariable(value);
        } else {
            return createVariable(value);
        }
    }
    
    private Variable createVariable(Object value) {
        return new Variable(value, declaredClass, (String)newVariableNameInput.getValidatedInput());
    }
    
    private Variable updateVariable(Object value) {
        destObjectVariable.setValue(value);
        destObjectVariable.updateLastModifiedAt();
        return null;
    }
    
    
}
