package jpl.ch01.ex15;

public interface ObjectManager extends Lookup {
	void add(String name, Object item);
	void remove(String name);
}
