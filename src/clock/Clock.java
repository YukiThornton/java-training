package clock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Clock {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private Label clockDateLabel;
    private Label clockTimeLabel;

    public Clock() {
        clockDateLabel = new Label(clockDate());
        clockTimeLabel = new Label(clockTime());
        clockDateLabel.setFont(new Font(50));
        clockTimeLabel.setFont(new Font(50));
    }

    public Node getDateNode() {
        return clockDateLabel;
    }

    public Node getTimeNode() {
        return clockTimeLabel;
    }

    public void update() {
        clockDateLabel.setText(clockDate());
        clockTimeLabel.setText(clockTime());
    }

    private String clockDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    private String clockTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

}
