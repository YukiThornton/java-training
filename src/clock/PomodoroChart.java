package clock;

import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PomodoroChart extends PieChart {
    private final Circle innerCircle;
    private boolean initialized;
    private Data remainingTimeData;
    private Data passedTimeData;

    public PomodoroChart(int remainingTime, int passedTime) {
        super();
        this.remainingTimeData = new Data("remaining", remainingTime);
        this.passedTimeData = new Data("passed", passedTime);
        this.setData(FXCollections.observableArrayList(remainingTimeData, passedTimeData));
        innerCircle = new Circle();
        innerCircle.setFill(Color.WHITESMOKE);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(3);
    }

    public void setTimeValues(int remainingTime, int passedTime) {
        remainingTimeData.setPieValue(remainingTime);
        passedTimeData.setPieValue(passedTime);
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);
        if (!initialized) {
            addInnerCircle();
        }
        updateInnerCircleLayout();
    }

    private void addInnerCircle() {
        if (initialized) {
            new IllegalStateException("Something wrong!");
        }
        getChildren().add(innerCircle);
        initialized = true;
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
