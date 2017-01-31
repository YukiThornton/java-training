package jpl.ch11.ex03;

import jpl.ch11.ex02.Attr;


/*
 * Answer to ch11.ex03
 * Attributedインターフェースでは、interface宣言やAttrクラスの引数や戻り値に型引数がつくようになる。
 * Attributedインターフェースを実装したAttributedImplでは、implements宣言にいれる型引数が、
 * 実装内で使われるAttrクラスの型引数になる。
 */
public interface Attributed<V> {

    void add(Attr<V> newAttr);
    void find(String attrName);
    Attr<V> remove(String attrName);
    java.util.Iterator<Attr<V>> attrs();
    
}
