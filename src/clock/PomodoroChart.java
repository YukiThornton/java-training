package clock;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PomodoroChart extends PieChart {
    private Data remainingTimeData;
    private Data passedTimeData;

    public PomodoroChart(int remainingTime, int passedTime) {
        super();
        this.remainingTimeData = new Data("remaining", remainingTime);
        this.passedTimeData = new Data("passed", passedTime);
        this.setData(FXCollections.observableArrayList(passedTimeData, remainingTimeData));
        remainingTimeData.getNode().setStyle("-fx-pie-color: #80bfff");
        passedTimeData.getNode().setStyle("-fx-pie-color: #1745cf;");
        this.setLegendVisible(false);
        this.setLabelsVisible(false);
        this.setStartAngle(90);
    }

    public void setTimeValues(int remainingTime, int passedTime) {
        remainingTimeData.setPieValue(remainingTime);
        passedTimeData.setPieValue(passedTime);
    }

    public void dimColor() {
        remainingTimeData.getNode().setStyle("-fx-pie-color: #9fbfdf;");
        passedTimeData.getNode().setStyle("-fx-pie-color: #506295;");
    }

    public void brighterColor() {
        remainingTimeData.getNode().setStyle("-fx-pie-color: #80bfff;");
        passedTimeData.getNode().setStyle("-fx-pie-color: #1745cf;");
    }
}
