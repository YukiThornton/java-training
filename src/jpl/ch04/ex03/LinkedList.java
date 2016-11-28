package jpl.ch04.ex03;

/*
 * Answer to 4.3
 * インターフェースではなく、拡張可能な実装クラスであるべき
 * LinkedListの実装ではObjectのフィールドを扱うので、汎用的に使えます。
 * また、LinkedListを拡張して各メソッドをオーバーライドすることで
 * LinkedStringのように、より特化したクラスを実装することができます。
 * この時LinkedListはインターフェースである必要はありません。
 */
public interface LinkedList {
	
	// ++++++ getters and setters ++++++
	Object getObject();
	void setObject(Object object);
	LinkedList getNextItem();
	void setNextItem(LinkedList linkedList);
	
	// ++++++ other methods ++++++	
	String toString();
	int length();
	LinkedList clone();
}
