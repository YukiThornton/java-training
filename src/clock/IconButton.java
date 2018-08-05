package clock;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

enum IconButton implements ControlButton {
    DELETE("\uf1f8", AppFont.ICON_40, ColorPolicy.STATIC, ColorPalette.GRAY),
    REPORT("\uf15c", AppFont.ICON_40, ColorPolicy.STATIC, ColorPalette.GRAY),
    ADD_WORK_TIMER("\uf0fe", AppFont.ICON_50, ColorPolicy.STATIC, TimerType.WORK_DEFAULT.colorPalette()),
    ADD_BREAK_TIMER("\uf0fe", AppFont.ICON_50, ColorPolicy.STATIC, TimerType.BREAK_DEFAULT.colorPalette()),
    START("\uf04b", AppFont.ICON_50, ColorPolicy.DYNAMIC, PomodoroController.INITIAL_TIMER_TYPE.colorPalette()),
    PAUSE("\uf04c", AppFont.ICON_50, ColorPolicy.DYNAMIC, PomodoroController.INITIAL_TIMER_TYPE.colorPalette()),
    SKIP("\uf051", AppFont.ICON_50, ColorPolicy.DYNAMIC, PomodoroController.INITIAL_TIMER_TYPE.colorPalette()),
    STOP("\uf04d", AppFont.ICON_50, ColorPolicy.DYNAMIC, PomodoroController.INITIAL_TIMER_TYPE.colorPalette());

    private Label label;
    private ColorPolicy colorPolicy;

    private static enum ColorPolicy {
        STATIC,
        DYNAMIC;
    }

    private IconButton(String iconIdentifier, AppFont font, ColorPolicy colorPolicy, ColorPalette palette) {
        label = new Label(iconIdentifier);
        label.setFont(font.get());
        this.colorPolicy = colorPolicy;
        setColorPalette(label, palette);
    }

    public Control get() {
        return label;
    }

    public void setOnMouseClicked(EventHandler<MouseEvent> handler) {
        label.setOnMouseClicked(handler);
    }

    public boolean canChangeColorPalette(){
        return this.colorPolicy == ColorPolicy.DYNAMIC;
    }

    public void changeColorPalette(ColorPalette to) {
        if (!canChangeColorPalette()) {
            throw new IllegalStateException("Not allowed to change color palette.");
        }
        setColorPalette(this.label, to);
    }

    private static void setColorPalette(Label label, ColorPalette palette) {
        Color base = palette.get(palette == ColorPalette.GRAY ? ColorPalette.Key.SATURATED_DARK: ColorPalette.Key.LIGHT);
        Color hover = palette.get(ColorPalette.Key.DARK);
        label.setTextFill(base);
        label.setOnMouseEntered(event -> label.setTextFill(hover));
        label.setOnMouseExited(event -> label.setTextFill(base));
    }
}
