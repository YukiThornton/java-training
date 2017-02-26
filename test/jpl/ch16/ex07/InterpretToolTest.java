package jpl.ch16.ex07;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import jpl.ch16.ex06.Sample;
import jpl.ch16.ex06.SampleChild;

public class InterpretToolTest {

    @Test
    public void testNewInstance() throws InstantiationException {
    
        Class<?> objectCls = null;
        Class<?> stringCls = null;
        Class<?> arrayListCls = null;
        Class<?> innerCls = null;
        
        try {
            objectCls = Class.forName("java.lang.Object");
            stringCls = Class.forName("java.lang.String");
            arrayListCls = Class.forName("java.util.ArrayList");
            innerCls = Class.forName("jpl.ch16.ex06.Sample$PublicInner");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(InterpretTool.newInstance(objectCls) instanceof Object);
        assertTrue(InterpretTool.newInstance(stringCls) instanceof String);
        assertTrue(InterpretTool.newInstance(arrayListCls) instanceof ArrayList);
        InterpretTool.newInstance(innerCls);
    }

    @Test(expected = InstantiationException.class)
    public void testNewInstanceWithEnum() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> cls = null;
        
        try {
            cls = Class.forName("java.time.DayOfWeek");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        
        InterpretTool.newInstance(cls);
    }

    @Test(expected = InstantiationException.class)
    public void testNewInstanceWithAnnotation() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> cls = null;
        
        try {
            cls = Class.forName("java.lang.Deprecated");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        
        InterpretTool.newInstance(cls);
    }

    @Test(expected = InstantiationException.class)
    public void testNewInstanceWithInterface() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> cls = null;
        
        try {
            cls = Class.forName("java.util.List");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        
        InterpretTool.newInstance(cls);
    }

    @Test(expected = NullPointerException.class)
    public void testNewInstanceWithNullArgument() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> cls = null;
                
        InterpretTool.newInstance(cls);
    }

    @Test
    public void testGetAllFields() {
    
        Sample sample = new Sample();
        SampleChild sampleChild = new SampleChild();
        
        assertTrue(InterpretTool.getAllFields(sample).length == 2);
        assertTrue(InterpretTool.getAllFields(sampleChild).length == 3);
    }
    
    @Test(expected = NullPointerException.class)
    public void testGetAllFieldsWithNullArgument() {
        InterpretTool.getAllFields(null);
    }
    
    @Test
    public void testGetAllMethods() {
    
        Sample sample = new Sample();
        SampleChild sampleChild = new SampleChild();
        
        assertTrue(InterpretTool.getAllMethods(sample).length == 11);
        assertTrue(InterpretTool.getAllMethods(sampleChild).length == 12);
    }
    
    @Test(expected = NullPointerException.class)
    public void testGetAllMethodsWithNullArgument() {
        InterpretTool.getAllMethods(null);
    }
    
}
