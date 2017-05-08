package jpl.ch22.ex04;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import jpl.ch03.ex07.Attr;

public class AttributedImpl extends Observable implements Attributed {

    private Map<String, Attr> attrTable = new HashMap<String, Attr>();
    
    public enum AttributedOperation{
        ADD, REMOVE, FIND, ATTRS
    };
    
    @Override
    public void add(Attr newAttr) {
        attrTable.put(newAttr.getName(), newAttr);
        setChanged();
        notifyObservers(AttributedOperation.ADD);
    }

    @Override
    public Attr find(String attrName) {
        Attr result = attrTable.get(attrName);
        setChanged();
        notifyObservers(AttributedOperation.FIND);
        return result;
    }

    @Override
    public Attr remove(String attrName) {
        Attr result = attrTable.remove(attrName);
        setChanged();
        notifyObservers(AttributedOperation.REMOVE);
        return result;
    }

    @Override
    public Iterator<Attr> attrs() {
        Iterator<Attr> result = attrTable.values().iterator();
        setChanged();
        notifyObservers(AttributedOperation.ATTRS);
        return result;
    }
    
}
