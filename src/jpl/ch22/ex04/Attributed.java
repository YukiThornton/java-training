package jpl.ch22.ex04;

import java.util.Iterator;

import jpl.ch03.ex07.Attr;

public interface Attributed {
    void add (Attr newAttr);
    Attr find (String attrName);
    Attr remove (String attrName);
    Iterator<Attr> attrs();
}
