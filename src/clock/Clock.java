package clock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

class Clock {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private static final ColorPalette.Key TEXT_COLOR_KEY = ColorPalette.Key.DARK;
    private Label clockDateLabel;
    private Label clockTimeLabel;

    Clock(PomoFont dateFont, PomoFont timeFont, ColorPalette colorGroup) {
        clockDateLabel = new Label(clockDate());
        clockTimeLabel = new Label(clockTime());
        clockDateLabel.setFont(dateFont.get());
        clockTimeLabel.setFont(timeFont.get());
        clockDateLabel.setTextFill(colorGroup.get(TEXT_COLOR_KEY));
        clockTimeLabel.setTextFill(colorGroup.get(TEXT_COLOR_KEY));
    }

    Label getDateNode() {
        return clockDateLabel;
    }

    Label getTimeNode() {
        return clockTimeLabel;
    }

    void update() {
        clockDateLabel.setText(clockDate());
        clockTimeLabel.setText(clockTime());
    }

    void changeTextColor(ColorPalette palette) {
        clockDateLabel.setTextFill(palette.get(TEXT_COLOR_KEY));
        clockTimeLabel.setTextFill(palette.get(TEXT_COLOR_KEY));
    }

    private String clockDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    private String clockTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

}
