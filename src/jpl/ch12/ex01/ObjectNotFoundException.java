package jpl.ch12.ex01;

@SuppressWarnings("serial")
public class ObjectNotFoundException extends Exception {
    private Object object;
    
    public ObjectNotFoundException(Object object) {
        super("Could not find " + object.toString());
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
