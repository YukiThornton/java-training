package java8.ch01.ex06;

public interface RunnableEx {

/*
 * Callable<Void>が使用できない理由はわからなかった。
 */

    void run() throws Exception;

    public static Runnable uncheck(RunnableEx runner) {
        return () -> {
            try {
                runner.run();
            } catch (Exception e) {
            }
        };
        
    }
}
