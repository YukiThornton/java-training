package interpret;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import interpret.TabbedPane.Tab;

public class Interpret {

    private static final Dimension MIN_FRAME_SIZE = new Dimension(500, 300);
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(1000, 800);

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the frame.
        JFrame frame = new JFrame("Interpret");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
        frame.setPreferredSize(DEFAULT_FRAME_SIZE);
        frame.setMinimumSize(MIN_FRAME_SIZE);
         
        //Add contents to the frame.
        JPanel leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.add(createExplorerPane());
        leftPane.add(makeTextPanel("Details"));
        leftPane.setMaximumSize(new Dimension(300, 1000));
        frame.add(leftPane);
        
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.add(makeTextPanel("Lab"));
        rightPane.add(makeTextPanel("Console"));
        frame.add(rightPane);
         
        //Display the frame.
        frame.pack();
        frame.setVisible(true);
    }
    
    
    private static JPanel makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        panel.setPreferredSize(new Dimension(410, 50));
        return panel;
    }
     
    private static TabbedPane createExplorerPane() {
        // All icons are drawn in rgb(4, 33, 81).
        ImageIcon documentIcon = createImageIcon("images/icon_document.png");
        ImageIcon cubeIcon = createImageIcon("images/icon_cube.png");
        Tab classTab = new Tab(makeTextPanel("Class tree comes here"), documentIcon, "Class Explorer", "Classes available here.");
        Tab instanceTab = new Tab(makeTextPanel("Instance tree comes here"), cubeIcon, "Instance Explorer", "Instances available here.");
        Tab[] tabs = {classTab, instanceTab};
        return new TabbedPane(tabs);
    }
     
    /** Returns an ImageIcon, or null if the path was invalid. */
    private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPane.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
     

}
