package java8.ch03.ex01;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

    private Logger logger;
    
    public MyLogger(Logger logger) {
        this.logger = logger;
    }

    public void logif(Level level, BooleanSupplier condition, Supplier<String> message) {
        if (logger.isLoggable(level) && condition.getAsBoolean()) {
            logger.log(level, message.get());
        }
    }

}
