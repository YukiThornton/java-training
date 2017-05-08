package gui.ex21;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ClockView {
    private static final Dimension MIN_FRAME_SIZE = new Dimension(600, 300);
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(600, 400);
    private Color bgColor = new Color(0,0,0);
    private Color fgColor = new Color(193, 31, 9);

    private boolean isInitialized = false;

    private JFrame frame;

    public void init() {
        if (isInitialized) {
            throw new IllegalStateException("Use this method only once.");
        }
        LocalDateTime time = LocalDateTime.now();
        frame = new JFrame(time.format(DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setPreferredSize(DEFAULT_FRAME_SIZE);
        frame.setMinimumSize(MIN_FRAME_SIZE);
        frame.setBackground(bgColor);
        
        ClockPanel clockPanel = new ClockPanel();
        clockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(clockPanel);
        
        frame.pack();
        isInitialized = true;
    }
    
    public void show() {
        if (!isInitialized) {
            throw new IllegalStateException("Invoke init first.");
        }
        frame.setVisible(true);    
    }
    
    @SuppressWarnings("serial")
    class ClockPanel extends JPanel {
        int count = 0;
    
        public ClockPanel() {
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
            Rectangle panelSize = ClockPanel.this.getBounds();
            LocalDateTime time = LocalDateTime.now();
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(45 * panelSize.width / DEFAULT_FRAME_SIZE.width));
            g2.setColor(fgColor);
            g2.drawOval(panelSize.width / 6, panelSize.height - panelSize.width / 9 * 4
            , panelSize.width / 3 * 2, panelSize.width / 3 * 2);
            Font timeFont = new Font(Font.MONOSPACED, Font.BOLD, 50 * panelSize.width / DEFAULT_FRAME_SIZE.width);
            TextData timeData = new TextData(time.format(DateTimeFormatter.ofPattern("hh:mm")), timeFont, fgColor, g2);
            timeData.locate(panelSize.width / 6 + panelSize.width / 3 - timeData.width() / 2, panelSize.height - timeData.height());
            timeData.draw();
            Font ampmFont = new Font(Font.MONOSPACED, Font.BOLD, 30 * panelSize.width / DEFAULT_FRAME_SIZE.width);
            TextData ampmData = new TextData(time.format(DateTimeFormatter.ofPattern("a")), ampmFont, fgColor, g2);
            ampmData.locate(panelSize.width / 2 - timeData.width() / 2 - ampmData.width(), panelSize.height - timeData.height() - ampmData.height() / 2);
            ampmData.draw();
            Font secondFont = new Font(Font.MONOSPACED, Font.BOLD, 30 * panelSize.width / DEFAULT_FRAME_SIZE.width);
            TextData secondData = new TextData(time.format(DateTimeFormatter.ofPattern("ss")), secondFont, fgColor, g2);
            secondData.locate(panelSize.width / 2 + timeData.width() / 2, panelSize.height - timeData.height() + secondData.height() / 3);
            secondData.draw();
        }
    }
}
