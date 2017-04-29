package gui.ex21;

public class ClockController {
    private ClockView view;
    
    public ClockController() {
        view = new ClockView();
        view.init();
    }
    
    public void start() {
        if (view == null) {
            throw new IllegalStateException("view is null.");
        }
        view.show();
    
    }

}
