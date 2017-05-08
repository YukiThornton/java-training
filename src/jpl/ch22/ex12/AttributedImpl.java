package jpl.ch22.ex12;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import jpl.ch03.ex07.Attr;
import jpl.ch22.ex04.Attributed;

public class AttributedImpl extends Observable implements Attributed {

    private Map<String, Attr> attrTable = new HashMap<String, Attr>();
    
    @Override
    public void add(Attr newAttr) {
        attrTable.put(newAttr.getName(), newAttr);
    }

    @Override
    public Attr find(String attrName) {
        return attrTable.get(attrName);
    }

    @Override
    public Attr remove(String attrName) {
        return attrTable.remove(attrName);
    }

    @Override
    public Iterator<Attr> attrs() {
        return attrTable.values().iterator();
    }
    
}
