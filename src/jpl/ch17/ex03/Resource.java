package jpl.ch17.ex03;

public interface Resource {
    void use(Object key, Object... args);
    void release();
}
