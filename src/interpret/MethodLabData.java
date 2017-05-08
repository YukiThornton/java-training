package interpret;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import interpret.LabInput.InputType;

public class MethodLabData extends LabData {
    private Class<?> declaredClass;
    private Variable variable;
    private Method methodToInvoke;
    private LabInput newVariableNameInput;
    private LabInput[] paramInputs;

    public MethodLabData(Class<?> declaredClass, Method method, LabInput[] paramInputs) {
        this.labType = LabType.STATIC_METHOD;
        this.actionVerb = "Invoke";
        this.declaredClass = declaredClass;
        this.methodToInvoke = method;
        if (method.getReturnType() != void.class) {
            this.newVariableNameInput = LabInput.create(InputType.VARIABLE_NAME);
        }
        this.paramInputs = paramInputs;
    }

    public MethodLabData(Variable variable, Method method, LabInput[] paramInputs) {
        this.labType = LabType.NON_STATIC_METHOD;
        this.actionVerb = "Invoke";
        this.variable = variable;
        this.declaredClass = variable.getType();
        this.methodToInvoke = method;
        if (method.getReturnType() != void.class) {
            this.newVariableNameInput = LabInput.create(InputType.VARIABLE_NAME);
        }
        this.paramInputs = paramInputs;
    }

    public boolean hasReturnType() {
        return methodToInvoke.getReturnType() != void.class;
    }
    
    public boolean hasParameters() {
        return methodToInvoke.getParameterTypes().length != 0;
    }
    
    public Class<?> getDeclaredClass() {
        return declaredClass;
    }
    
    public String getSimpleDeclaredClassName() {
        return ReflectionTools.getSimpleName(declaredClass);
    }
    
    public Variable getVariable() {
        return variable;
    }

    public Method getMethodToInvoke() {
        return methodToInvoke;
    }
    
    public Class<?> getReturnType() {
        return methodToInvoke.getReturnType();
    }
    
    public String getReturnTypeSimpleName() {
        return ReflectionTools.getSimpleName(methodToInvoke.getReturnType());
    }
    
    public String getMethodName() {
        return methodToInvoke.getName();
    }
    
    public String getModifiers() {
        return ReflectionTools.stringifyModifiers(methodToInvoke.getModifiers());
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
        if (hasReturnType()) {
            if (!newVariableNameInput.validate()){
                result = false;
            }
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
            value = methodToInvoke.invoke(variable == null ? null : variable.getValue(), params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new InterpretException("Failed to invoke " + getMethodName() + ".", e.getCause());
        }
        if (hasReturnType()) {
            return new Variable(value, getReturnType(), (String)newVariableNameInput.getValidatedInput());
        } else {
            return null;
        }
    }

}
