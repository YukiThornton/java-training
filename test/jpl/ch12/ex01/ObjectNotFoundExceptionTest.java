package jpl.ch12.ex01;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectNotFoundExceptionTest {

    @Test
    public void testSetAndGetObject() {
        String object1 = "object1";
        String object2 = "object2";
        
        ObjectNotFoundException exception = new ObjectNotFoundException(object1);

        assertEquals(object1, (String)exception.getObject());

        exception.setObject(object2);

        assertEquals(object2, (String)exception.getObject());

    }


}
