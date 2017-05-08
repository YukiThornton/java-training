package jpl.ch22.ex15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if(line.equals("exit")) {
                System.exit(0);
            }
            if (line.length() == 0) {
                continue;
            }
            try {
                System.out.println(calculateLine(line, " "));
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Enter numbers and operators correctly.");
            }
        }
    }
    
    public static double calculateLine(String line, String divider) {
        String[] tokens = line.split(divider);
        Deque<Double> stack = new ArrayDeque<>();
        for (String token : tokens) {
            pushOrCalculateToken(stack, token);
        }
        if (stack.size() == 1) {
            return stack.poll();
        } else {
            throw new IllegalArgumentException("Calculation has not completed yet.");            
        }
    }
    private static void pushOrCalculateToken(Deque<Double> stack, String token) {
        switch (token) {
        case "+":
            stack.push(add(stack));
            break;
        case "-":
            stack.push(subtract(stack));
            break;
        case "*":
            stack.push(multiply(stack));
            break;
        case "/":
            stack.push(divide(stack));
            break;
        case "%":
            stack.push(mod(stack));
            break;
        default:
            try {
                Double val = Double.parseDouble(token);
                stack.push(val);
            } catch (NumberFormatException e) {
                stack.push(invokeMathMethod(stack, token));
            }
            break;
        }
    }
    
    private static double invokeMathMethod(Deque<Double> stack, String token) {
        Method method = getMathMethod(token);
        if (method == null) {
            throw new IllegalArgumentException("Not math method.");            
        }
        double result;
        try {
            Object[] args = pollElements(stack, method.getGenericParameterTypes().length);
            result = (double)method.invoke(null, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to invoke a Math method.");
        }
        return result;
    }
    
    private static Method getMathMethod(String name) {
        Class<Math> mathClass = Math.class;
        Method[] methods = mathClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
        
    }
    
    private static double add(Deque<Double> stack) {
        Double[] vals = pollElements(stack, 2);
        return vals[0] + vals[1];
    }
    
    private static double subtract(Deque<Double> stack) {
        Double[] vals = pollElements(stack, 2);
        return vals[0] - vals[1];
    }
    
    private static double multiply(Deque<Double> stack) {
        Double[] vals = pollElements(stack, 2);
        return vals[0] * vals[1];
    }
    
    private static double divide(Deque<Double> stack) {
        Double[] vals = pollElements(stack, 2);
        return vals[0] / vals[1];
    }
    
    private static double mod(Deque<Double> stack) {
        Double[] vals = pollElements(stack, 2);
        return vals[0] % vals[1];
    }
    
    private static Double[] pollElements(Deque<Double> stack, int amountOfElements) {
        Double[] array = new Double[amountOfElements];
        for (int i = amountOfElements; i >= 1; i--) {
            Double val = stack.poll();
            if (val == null) {
                throw new IllegalArgumentException("Not enough number to calculate.");
            }
            array[i - 1] = val;
        }
        return array;
    }

}
