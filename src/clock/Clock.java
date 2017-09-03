package clock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Clock {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private Label clockDateLabel;
    private Label clockTimeLabel;

    public Clock(Font dateFont, Font timeFont, Color color) {
        clockDateLabel = new Label(clockDate());
        clockTimeLabel = new Label(clockTime());
        clockDateLabel.setFont(dateFont);
        clockTimeLabel.setFont(timeFont);
        clockDateLabel.setTextFill(color);
        clockTimeLabel.setTextFill(color);
    }

    public Label getDateNode() {
        return clockDateLabel;
    }

    public Label getTimeNode() {
        return clockTimeLabel;
    }

    public void update() {
        clockDateLabel.setText(clockDate());
        clockTimeLabel.setText(clockTime());
    }

    public void changeTextColor(Color color) {
        clockDateLabel.setTextFill(color);
        clockTimeLabel.setTextFill(color);
    }

    private String clockDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    private String clockTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

}
