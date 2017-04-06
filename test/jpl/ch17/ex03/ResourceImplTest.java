package jpl.ch17.ex03;

import org.junit.Test;

public class ResourceImplTest {

    @Test
    public void testConstructorAndUse() {
        Object key = new Object();
        Resource resource = new ResourceImpl(key);
        resource.use(key, new Object());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testUseThrowsIllegalArgumentException() {
        Object key = new Object();
        Resource resource = new ResourceImpl(key);
        resource.use(new Object(), new Object());
    }
    
    @Test
    public void testConstructorAndUseAndRelease() {
        Object key = new Object();
        Resource resource = new ResourceImpl(key);
        resource.use(key, new Object());
        resource.release();
    }
    
    @Test
    public void testConstructorAndRelease() {
        Object key = new Object();
        Resource resource = new ResourceImpl(key);
        resource.release();
    }
    
    @Test
    public void testConstructorAndUseAndReleaseTwice() {
        Object key = new Object();
        Resource resource = new ResourceImpl(key);
        resource.use(key, new Object());
        resource.release();
        resource.release();
    }
    
    

}
