package interpret;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class Interpret {

    private static final Dimension MIN_FRAME_SIZE = new Dimension(500, 300);
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(1000, 800);
    private static JTree classTree;
    private static JPanel memberPane;
    private static JPanel leftPane;
    private static JTable fieldTable;
    private static JTable methodTable;

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
        leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.add(setExplorerPane());
        setMemberPane(Object.class, new Object());
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
     
    private static JPanel setExplorerPane() {
        setClassTree();
        JScrollPane classScrollPane = ComponentFactory.createJScrollPane(classTree);
        JPanel instanceScrollPane = makeTextPanel("Instance tree comes here");
        return ComponentFactory.createClassExplorerPane(classScrollPane, instanceScrollPane);
    }

    /**
     * Set a new member panel of the specified class.
     */
    private static void setMemberPane(Class<?> cls, Object instance) {
        if (memberPane != null) {
            leftPane.remove(memberPane);
        }

        // All icons are drawn in rgb(4, 33, 81).
        try {
            setFieldTable(cls, instance);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setMethodTable(cls);
        memberPane = ComponentFactory.createMemberPane(makeTextPanel("general"), makeTextPanel("nested"), ComponentFactory.createJScrollPane(fieldTable), makeTextPanel("constructor"), ComponentFactory.createJScrollPane(methodTable));
        leftPane.add(memberPane);
        leftPane.validate();
        leftPane.repaint();
    }
    
    /**
     * Set a new class tree of all the classes,
     * and add function on it.
     */
    private static void setClassTree() {
        classTree = ComponentFactory.createPackageTree();
        classTree.addTreeSelectionListener(new TreeSelectionListener() {
            
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)classTree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                if (!node.isLeaf()) {
                    return;
                }
                setMemberPane((Class<?>)node.getUserObject(), null);
            }
        });
    }

    /**
     * Set a new field table with specified class,
     * and add function on it.
     */
    private static void setFieldTable(Class<?> cls, Object instance) throws IllegalArgumentException, IllegalAccessException {
        fieldTable = ComponentFactory.createFieldTableScrollPane(cls, instance);
        fieldTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(e);
            }
        });
    }
     
    /**
     * Set a new method table with specified class,
     * and add function on it.
     */
    private static void setMethodTable(Class<?> cls) {
        methodTable = ComponentFactory.createMethodTable(cls);
        methodTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(e);
            }
        });
    }
}
