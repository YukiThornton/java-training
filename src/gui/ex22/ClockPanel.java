package gui.ex22;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Point;

import gui.ex22.ClockValues.FontSizeRatioOptions;

@SuppressWarnings("serial")
public class ClockPanel extends JPanel {

    private JFrame parent;
    private ClockValues values;
    private int currentFontSize;
    private Dimension dimension;

    public ClockPanel(JFrame parent, ClockValues values) {
        super();
        if (parent == null || values == null) {
            throw new IllegalArgumentException("Values are null");
        }
        this.parent = parent;
        this.values = values;
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        setBackground(values.bgColor());
        
        if (values.windowSizeDeterminesFontSize()) {
            paintComponentBasedOnWindowSize(g2);
        } else {
            paintComponentBasedOnFontSize(g2);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return dimension;
    }

    private void paintComponentBasedOnWindowSize(Graphics2D g2) {
        Rectangle rectangle = this.getBounds();
        Point centerOfPanel = new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
        int ovalDiameter = (int) (values.fontSize(rectangle.width) * 8);
        Point centerOfOval = new Point(centerOfPanel.x, (int)(rectangle.y + rectangle.height - ovalDiameter / 9 * 2));
        drawOval(g2, values.clockOvalWidth(rectangle.width), ovalDiameter, centerOfOval);
        drawTime(g2, rectangle.width, centerOfOval);
    }

    private void paintComponentBasedOnFontSize(Graphics2D g2) {
        int fontSize = values.fontSize();
        if (values.fontSizeIndex() == ClockValues.DEFAULT_FONT_SIZE_INDEX) {
            changeSize(ClockValues.DEFAULT_FRAME_SIZE.width, ClockValues.DEFAULT_FRAME_SIZE.height);
            currentFontSize = fontSize;
        } else if (currentFontSize != fontSize) {
            calcAndChangeSize((double)fontSize / ClockValues.FONT_SIZE_OPTIONS[ClockValues.DEFAULT_FONT_SIZE_INDEX]);
            currentFontSize = fontSize;
        }
        Rectangle rectangle = this.getBounds();
        Point centerOfPanel = new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
        int ovalDiameter = (int) (fontSize * 8);
        Point centerOfOval = new Point(centerOfPanel.x, (int)(rectangle.y + rectangle.height - ovalDiameter / 9 * 2));
        drawOval(g2, values.clockOvalWidth(rectangle.width), ovalDiameter, centerOfOval);
        drawTime(g2, rectangle.width, centerOfOval);
    }

    public void setValues(ClockValues newValues) {
        values = newValues;
        repaint();
    }

    private void calcAndChangeSize(double ratio) {
        Dimension baseSize = ClockValues.DEFAULT_FRAME_SIZE;
        changeSize((int)(baseSize.getWidth() * ratio), (int)(baseSize.getHeight() * ratio));
    }

    private void changeSize(int width, int height) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                parent.setPreferredSize(new Dimension(width, height));
                dimension = new Dimension(width, height);
                parent.pack();
            }
        });
    }
    private void drawOval(Graphics2D g2, int ovalThickness, int ovalDiameter, Point centerOfOval) {
        g2.setStroke(new BasicStroke(ovalThickness));
        g2.setColor(values.fgColor());
        g2.drawOval(centerOfOval.x - ovalDiameter / 2, centerOfOval.y - ovalDiameter / 2, ovalDiameter, ovalDiameter);
    }

    private void drawTime(Graphics2D g2, int paneWidth, Point center) {
        Font standardFont = values.font(paneWidth);
        Font smallerFont = values.font(FontSizeRatioOptions.SMALLER, paneWidth);
        
        LocalDateTime time = LocalDateTime.now();

        TextData timeData = new TextData(time, "hh:mm", standardFont, values.fgColor(), g2);
        timeData.locate(center.x - timeData.width() / 2, center.y - timeData.height() / 2);
        timeData.draw();
        
        TextData ampmData = new TextData(time, "a", smallerFont, values.fgColor(), g2);
        ampmData.locate(timeData.getIntX() - ampmData.width(), timeData.getIntY() - ampmData.height() / 2);
        ampmData.draw();

        TextData secondData = new TextData(time, "ss", smallerFont, values.fgColor(), g2);
        secondData.locate(timeData.getIntX() + timeData.width(), timeData.getIntY() + secondData.height() / 3);
        secondData.draw();
    }
}
