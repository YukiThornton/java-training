package interpret;

import javax.swing.SwingUtilities;

public class Main {

    private static ClassCollector classCollector;
    
    static{
        classCollector = new ClassCollector();
    }

    public static void main(String[] args) {
        start();
    }
    
    public static void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterpretController controller = new InterpretController(classCollector);
                controller.start();
            }
        });
    }

}
