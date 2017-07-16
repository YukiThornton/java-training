package gui.ex24;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        start();
    }
    
    public static void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClockView view = new ClockView();
                ClockValues values = new ClockValues();
                ClockController controller = new ClockController(view, values);
                controller.start();
            }
        });
    }

}
