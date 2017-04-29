package gui.ex21;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        start();
    }
    
    public static void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClockController controller = new ClockController();
                controller.start();
            }
        });
    }

}
