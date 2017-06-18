package java8.ch01.ex07;

public class RunnableTools {

    public static void main(String[] args) {
        new Thread(andThen(
                () -> {
                    System.out.print("Hello, ");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                () -> { System.out.print("world."); }
        )).start();
    }


    public static Runnable andThen(Runnable runner1, Runnable runner2) {
        return () -> {
            runner1.run();
            runner2.run();
        };
    }
}
