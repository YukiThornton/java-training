package gui.ex14;

import java.awt.Dimension;
import java.util.prefs.Preferences;

public class ClockMain implements Runnable {

    private static ClockFrame frame;

    public final static Dimension DEFAULT_FRAME_SIZE = new Dimension(400, 600);
    public final static Dimension MINIMUN_FRAME_SIZE = new Dimension(275, 160);
    private final static int PAINT_INTERVAL = 100;
    private final static String APP_TITLE = "Clock";
    
    public static void main(String[] args) {
        Thread thread = new Thread(new ClockMain());
        ClockPreference clockPreference = new ClockPreference();
        
        frame = new ClockFrame(APP_TITLE, clockPreference);
        
        frame.setVisible(true);
        thread.start();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(PAINT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.repaint();
        }
    }

}
