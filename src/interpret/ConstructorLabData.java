package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import interpret.LabInput.InputType;

public class ConstructorLabData extends LabData {
    private Class<?> declaredClass;
    private Constructor<?> constructorToInvoke;
    private LabInput newVariableNameInput;
    private LabInput[] paramInputs;

    public ConstructorLabData(Constructor<?> constructor, LabInput[] paramInputs) {
        this.labType = LabType.CONSTRUCTOR;
        this.declaredClass = constructor.getDeclaringClass();
        this.constructorToInvoke = constructor;
        this.newVariableNameInput = LabInput.create(InputType.VARIABLE_NAME);
        this.paramInputs = paramInputs;
    }

    public boolean hasParameters() {
        return constructorToInvoke.getParameterTypes().length != 0;
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
        if (!newVariableNameInput.validate()){
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
            throw new InterpretException("Failed to create an instance.", e.getCause());
        }
        return new Variable(value, declaredClass, (String)newVariableNameInput.getValidatedInput());
    }
    
    
}
