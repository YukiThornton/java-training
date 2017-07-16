package gui.ex24;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockController {
    private ClockView view;
    private ClockValues values;
    private MenuPreferenceActionListener menuPreferenceActionListener;
    
    public ClockController(ClockView view, ClockValues values) {
        if (view == null) {
            throw new IllegalStateException("view is null.");
        }
        this.view = view;
        this.values = values;
        this.menuPreferenceActionListener = new MenuPreferenceActionListener();
        view.init(values, this);
    }
    
    public void start() {
        view.show();
    }
    
    public ActionListener getActionListnerForMenuPreference() {
        return menuPreferenceActionListener;
    }
    
    public void onValuesChanged(ClockValues newValues) {
        values = newValues;
    }

    public void onWindowClosing() {
        values.saveFrameBounds(view.getFrameBounds());
        values.saveViewPref();
        System.exit(0);
    }

    private void showPreferenceDialog() {
        view.showPreferenceDialog(values);
    }

    class MenuPreferenceActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            showPreferenceDialog();
        }
    
    }

}
