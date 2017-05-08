package interpret;

public abstract class LabData {
    enum LabType{CONSTRUCTOR, STATIC_METHOD, NON_STATIC_METHOD, NEW_ARRAY, VARIABLE, STATIC_FIELD, NON_STATIC_FIELD}
    
    protected LabType labType;
    protected String actionVerb;
    
    public LabType getLabType() {
        return labType;
    }
    
    public String getActionVerb() {
        return actionVerb;
    }
    
    public abstract boolean validate();
    public abstract Variable invoke() throws InterpretException;
}
