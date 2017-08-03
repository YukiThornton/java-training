package java8.ch03.ex16;

import static org.junit.Assert.*;

import org.junit.Assume;
import org.junit.Test;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.istack.internal.logging.Logger;

public class AsyncFunctionsTest {

    @Test
    public void test1() {
        AsyncFunctions.doInOrderAsync(() -> {
            return "Hello";
        }, (result, err) -> {
            if (err != null) {
                fail();
            } else {
                assertTrue(result.equals("Hello"));
            }
        });
    }

    @Test
    public void test2() {
        AsyncFunctions.doInOrderAsync(() -> {
            throw new RuntimeException();
        }, (result, err) -> {
            if (err != null) {
                assertTrue(true);
            } else {
                fail();
            }
        });
    }

    @Test
    public void useCase1() {
        AsyncFunctions.doInOrderAsync(() -> {
            String input = "User Input";
            if (validate(input)) {
                return input;
            } else {
                throw new RuntimeException("Wrong input");
            }
        }, (result, err) -> {
            if (err != null) {
                System.out.println("ERROR!!");
            } else {
                System.out.println("Input: " + result);
                // Do something.
            }
        });
    }
    
    private boolean validate(String input) {
        return input.length() > 1;
    }

}
