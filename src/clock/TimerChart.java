package clock;

import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

public class TimerChart extends PieChart {
    private Data remainingTimeData;
    private Data passedTimeData;
    private ColorSet colorSet;

    public TimerChart(int remainingTime, int passedTime, ColorSet colorSet) {
        super();
        this.remainingTimeData = new Data("remaining", remainingTime);
        this.passedTimeData = new Data("passed", passedTime);
        this.setData(FXCollections.observableArrayList(passedTimeData, remainingTimeData));
        this.colorSet = colorSet;
        dimColor();
        this.setLegendVisible(false);
        this.setLabelsVisible(false);
        this.setStartAngle(90);
    }

    public void setTimeValues(int remainingTime, int passedTime) {
        remainingTimeData.setPieValue(remainingTime);
        passedTimeData.setPieValue(passedTime);
    }

    public void dimColor() {
        remainingTimeData.getNode().setStyle("-fx-pie-color: " + colorSet.light() + ";-fx-border-color: derive(-fx-pie-color, -10%);");
        passedTimeData.getNode().setStyle("-fx-pie-color: " + colorSet.dark() + ";-fx-border-color: derive(-fx-pie-color, -10%);");
    }

    public void brighterColor() {
        remainingTimeData.getNode().setStyle("-fx-pie-color: " + colorSet.saturatedLight() + ";-fx-border-color: derive(-fx-pie-color, -10%);");
        passedTimeData.getNode().setStyle("-fx-pie-color: " + colorSet.saturatedDark() + ";-fx-border-color: derive(-fx-pie-color, -10%);");
    }

}
