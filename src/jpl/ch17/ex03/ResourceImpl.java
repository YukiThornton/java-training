package jpl.ch17.ex03;

public class ResourceImpl implements Resource {
    Object key;
    boolean needsRelease = false;
    
    public ResourceImpl(Object key) {
        this.key = key;
        // Do something.
        needsRelease = true;
    }

    @Override
    public void use(Object key, Object... args) {
        if (!key.equals(this.key)) {
            throw new IllegalArgumentException("wrong key");
        }
        // Do something.
    }

    @Override
    public void release() {
        if(needsRelease) {
            needsRelease = false;
            // Do something.
        }
    }

}
