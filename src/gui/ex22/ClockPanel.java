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

import gui.ex22.ClockValues.DecorativeFrame;
import gui.ex22.ClockValues.FontSizeRatioOptions;

@SuppressWarnings("serial")
public class ClockPanel extends JPanel {

    private JFrame parent;
    private ClockValues values;
    private boolean shouldResizeWindowSize = false;
    private boolean currentWindowSizeDeterminesFontSize = ClockValues.DEFAULT_WINDOW_SIZE_DETEMINES_FONT_SIZE;
    private int currentFontSize;
    private DecorativeFrame currentDecoration;
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
        Rectangle rectangle = parent.getContentPane().getBounds();
        Point centerOfPanel = getCenterPoint(rectangle);
        int ovalDiameter = (int) (values.fontSize(rectangle.width) * 8);
        if (values.decoration() != DecorativeFrame.NONE) {
            drawOval(g2, values.clockOvalWidth(rectangle.width), ovalDiameter, centerOfPanel);
        }
        drawTime(g2, rectangle.width, centerOfPanel);
    }

    private Point getCenterPoint(Rectangle rectangle) {
        return new Point(rectangle.x + rectangle.width / 2, rectangle.height / 2);
    }

    private void paintComponentBasedOnFontSize(Graphics2D g2) {
        int fontSize = values.fontSize();
        if (shouldResizeWindowSize || currentFontSize != fontSize || currentDecoration != values.decoration()) {
            calcAndChangeSize((double)fontSize / ClockValues.FONT_SIZE_OPTIONS[ClockValues.DEFAULT_FONT_SIZE_INDEX]);
            shouldResizeWindowSize = false;
            currentFontSize = fontSize;
            currentDecoration = values.decoration();
        }
        Rectangle rectangle = parent.getContentPane().getBounds();
        Point centerOfPanel = getCenterPoint(rectangle);
        int ovalDiameter = (int) (fontSize * 8);
        if (values.decoration() != DecorativeFrame.NONE) {
            drawOval(g2, values.clockOvalWidth(rectangle.width), ovalDiameter, centerOfPanel);
        }
        drawTime(g2, rectangle.width, centerOfPanel);
    }

    public void setValues(ClockValues newValues) {
        values = newValues;
        if (currentWindowSizeDeterminesFontSize != values.windowSizeDeterminesFontSize()) {
            currentWindowSizeDeterminesFontSize = values.windowSizeDeterminesFontSize();
            if (!currentWindowSizeDeterminesFontSize) {
                shouldResizeWindowSize = true;
                currentFontSize = ClockValues.FONT_SIZE_OPTIONS[ClockValues.DEFAULT_FONT_SIZE_INDEX];
            }
        }
        repaint();
    }

    private void calcAndChangeSize(double ratio) {
        Dimension baseSize = ClockValues.DEFAULT_FRAME_SIZE;
        if (values.decoration() == DecorativeFrame.NONE) {
            ratio /= 2;
            changeSize((int)(baseSize.getWidth() * ratio), (int)(baseSize.getHeight() * ratio));
        } else {
            changeSize((int)(baseSize.getWidth() * ratio), (int)(baseSize.getHeight() * ratio));
        }
    }

    private void changeSize(int width, int height) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                parent.getContentPane().setPreferredSize(new Dimension(width, height));
                dimension = new Dimension(width, height);
                parent.pack();
            }
        });
    }
    private void drawOval(Graphics2D g2, int ovalThickness, int ovalDiameter, Point centerOfOval) {
        g2.setStroke(new BasicStroke(ovalThickness));
        int halfOval = ovalDiameter / 2;
        g2.setColor(values.fgColor());
        g2.drawOval(centerOfOval.x - halfOval, centerOfOval.y - halfOval, ovalDiameter, ovalDiameter);
    }

    private void drawTime(Graphics2D g2, int paneWidth, Point center) {
        Font standardFont = values.font(paneWidth);
        Font smallerFont = values.font(FontSizeRatioOptions.SMALLER, paneWidth);
        
        LocalDateTime time = LocalDateTime.now();

        TextData timeData = new TextData(time, "hh:mm", standardFont, values.fgColor(), g2);
        timeData.locate(center.x - timeData.width() / 2, center.y + timeData.height() / 6);
        timeData.draw();
        
        TextData ampmData = new TextData(time, "a", smallerFont, values.fgColor(), g2);
        ampmData.locate(timeData.getIntX() - ampmData.width(), timeData.getIntY() - ampmData.height() / 2);
        ampmData.draw();

        TextData secondData = new TextData(time, "ss", smallerFont, values.fgColor(), g2);
        secondData.locate(timeData.getIntX() + timeData.width(), timeData.getIntY() + secondData.height() / 3);
        secondData.draw();
    }
}
