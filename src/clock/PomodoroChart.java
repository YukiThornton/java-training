package clock;

import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PomodoroChart extends PieChart {
    private Circle innerCircle;
    private Data remainingTimeData;
    private Data passedTimeData;

    public PomodoroChart(int remainingTime, int passedTime) {
        super();
        this.remainingTimeData = new Data("remaining", remainingTime);
        this.passedTimeData = new Data("passed", passedTime);
        this.setData(FXCollections.observableArrayList(passedTimeData, remainingTimeData));
        remainingTimeData.getNode().setStyle("-fx-pie-color: white");
        passedTimeData.getNode().setStyle("-fx-pie-color: black;");
        innerCircle = new Circle();
        innerCircle.setFill(Color.WHITESMOKE);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(3);
        this.setLegendVisible(false);
        this.setLabelsVisible(false);
        this.setStartAngle(90);
        
        addInnerCircle();
    }

    public void setTimeValues(int remainingTime, int passedTime) {
        remainingTimeData.setPieValue(remainingTime);
        passedTimeData.setPieValue(passedTime);
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);
        updateInnerCircleLayout();
    }

    private void addInnerCircle() {
        getChildren().add(innerCircle);
    }
    private void updateInnerCircleLayout() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (Data data: getData()) {
            Node node = data.getNode();

            Bounds bounds = node.getBoundsInParent();
            if (bounds.getMinX() < minX) {
                minX = bounds.getMinX();
            }
            if (bounds.getMinY() < minY) {
                minY = bounds.getMinY();
            }
            if (bounds.getMaxX() > maxX) {
                maxX = bounds.getMaxX();
            }
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }

        innerCircle.setCenterX(minX + (maxX - minX) / 2);
        innerCircle.setCenterY(minY + (maxY - minY) / 2);

        innerCircle.setRadius((maxX - minX) / 4);
    }}
