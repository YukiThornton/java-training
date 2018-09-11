package clock;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

class IconButton implements ControlButton {

    private static enum ColorPolicy {
        STATIC,
        DYNAMIC;
    }

    static enum Type {
        DELETE_TIMER("\uf00d", AppFont.ICON_30, ColorPolicy.STATIC, ColorPalette.GRAY);

        private String iconIdentifier;
        private AppFont font;
        private ColorPolicy colorPolicy;
        private ColorPalette palette;

        private Type(String iconIdentifier, AppFont font, ColorPolicy colorPolicy, ColorPalette palette) {
            this.iconIdentifier = iconIdentifier;
            this.font = font;
            this.colorPolicy = colorPolicy;
            this.palette = palette;
        }
    }

    private static enum SingletonType {
        SINGLE_DELETE_MODE_SWITCH("\uf1f8", AppFont.ICON_40, ColorPolicy.STATIC, ColorPalette.GRAY),
        SINGLE_REPORT("\uf15c", AppFont.ICON_40, ColorPolicy.STATIC, ColorPalette.GRAY),
        SINGLE_ADD_WORK_TIMER("\uf0fe", AppFont.ICON_50, ColorPolicy.STATIC, TimerType.WORK_DEFAULT.colorPalette()),
        SINGLE_ADD_BREAK_TIMER("\uf0fe", AppFont.ICON_50, ColorPolicy.STATIC, TimerType.BREAK_DEFAULT.colorPalette()),
        SINGLE_START("\uf04b", AppFont.ICON_50, ColorPolicy.DYNAMIC, InitialValues.COLOR_PALETTE),
        SINGLE_PAUSE("\uf04c", AppFont.ICON_50, ColorPolicy.DYNAMIC, InitialValues.COLOR_PALETTE),
        SINGLE_SKIP("\uf051", AppFont.ICON_50, ColorPolicy.DYNAMIC, InitialValues.COLOR_PALETTE),
        SINGLE_STOP("\uf04d", AppFont.ICON_50, ColorPolicy.DYNAMIC, InitialValues.COLOR_PALETTE);

        private String iconIdentifier;
        private AppFont font;
        private ColorPolicy colorPolicy;
        private ColorPalette palette;

        private SingletonType(String iconIdentifier, AppFont font, ColorPolicy colorPolicy, ColorPalette palette) {
            this.iconIdentifier = iconIdentifier;
            this.font = font;
            this.colorPolicy = colorPolicy;
            this.palette = palette;
        }
    }

    static IconButton DELETE_MODE_SWITCH = create(SingletonType.SINGLE_DELETE_MODE_SWITCH);
    static IconButton REPORT = create(SingletonType.SINGLE_REPORT);
    static IconButton ADD_WORK_TIMER = create(SingletonType.SINGLE_ADD_WORK_TIMER);
    static IconButton ADD_BREAK_TIMER = create(SingletonType.SINGLE_ADD_BREAK_TIMER);
    static IconButton START = create(SingletonType.SINGLE_START);
    static IconButton PAUSE = create(SingletonType.SINGLE_PAUSE);
    static IconButton SKIP = create(SingletonType.SINGLE_SKIP);
    static IconButton STOP = create(SingletonType.SINGLE_STOP);

    private Label label;
    private ColorPolicy colorPolicy;

    static IconButton create(Type type) {
        return new IconButton(type.iconIdentifier, type.font, type.colorPolicy, type.palette);
    }

    private static IconButton create(SingletonType type) {
        return new IconButton(type.iconIdentifier, type.font, type.colorPolicy, type.palette);
    }

    private IconButton(String iconIdentifier, AppFont font, ColorPolicy colorPolicy, ColorPalette palette) {
        label = new Label(iconIdentifier);
        label.setFont(font.get());
        this.colorPolicy = colorPolicy;
        setColorPalette(label, palette);
    }

    @Override
    public Control get() {
        return label;
    }

    @Override
    public void setOnMouseClicked(EventHandler<MouseEvent> handler) {
        label.setOnMouseClicked(handler);
    }

    @Override
    public void hide() {
        label.setVisible(false);
    }

    @Override
    public void show() {
        label.setVisible(true);
    }

    @Override
    public boolean canChangeColorPalette() {
        return this.colorPolicy == ColorPolicy.DYNAMIC;
    }

    @Override
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
