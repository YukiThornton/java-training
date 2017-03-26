package interpret;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class ComponentTools {

    /**
     * Return a tree with specified root node.
     */
    public static JTree initializeTree(DefaultMutableTreeNode root) {
        if (root == null) {
            throw new IllegalArgumentException("root is null.");
        }
        JTree tree = new JTree(root);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
        tree.putClientProperty("JTree.lineStyle", "Horizontal");
 
        return tree;
    }    
    /**
     * Return a JScrollPane filled with the specified view.
     */
    public static JScrollPane createJScrollPane(Component view, Dimension dimension) {
        JScrollPane scrollPane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(dimension);
        return scrollPane;        
    }
    
    public static JPanel createTextPanel(String text, Dimension dimension) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        panel.setPreferredSize(dimension);
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    public static JPanel createTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    /**
     * Return a JPane filled with a JtabbedPane.
     */
    public static JTabbedPane createTabbedPane(Tab[] tabs, boolean wrapped) {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        for (Tab tab : tabs) {
            tabbedPane.addTab(tab.getTitle(), tab.getIcon(), tab.getPanel(), tab.getTip());
        }
         
        //The following line enables to use scrolling tabs.
        if (wrapped) {
            tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        } else {
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        }
        return tabbedPane;
    }
    /** 
     * Returns an ImageIcon, or null if the path was invalid. 
     */
    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ComponentTools.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    public static JPanel createDoubleColumnTitledPane(String title, String[] strLabels, Component[] components, Color bgColor) {
        if (strLabels.length != components.length) {
            throw new IllegalArgumentException("labels and components should be same size.");
        }
        JPanel panel = new JPanel(new GridBagLayout());
        TitledBorder border = BorderFactory.createTitledBorder(title);
        panel.setBorder(border);
        panel.setBackground(bgColor);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < strLabels.length; i++) {
            addLabelOnLeft(panel, constraints, strLabels[i], i);
            addComponentsOnRight(panel, constraints, components[i], i);
        }
        return panel;
    }
    public static JPanel createTitledPane(String title, String message, Color bgColor) {
        JPanel panel = createTextPanel(message);
        TitledBorder border = BorderFactory.createTitledBorder(title);
        panel.setBorder(border);
        panel.setBackground(bgColor);
        return panel;
    }
    private static void addLabelOnLeft(JPanel to, GridBagConstraints constraints, String strLabel, int gridy){
        Label label = new Label(strLabel);
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.EAST;
        to.add(label, constraints);
    }
    private static void addComponentsOnRight(JPanel to, GridBagConstraints constraints, Component component, int gridy){
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.WEST;
        to.add(component, constraints);
    }
    
    public static class Tab {
        private Component panel;
        private ImageIcon icon;
        private String title;
        private String tip;
        
        public Tab(Component panel, ImageIcon icon, String title, String tip) {
            this.panel = panel;
            this.icon = icon;
            this.title = title;
            this.tip = tip;
        }
        
        public Component getPanel() {
            return panel;
        }

        public ImageIcon getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }
        
        public String getTip() {
            return tip;
        }
    }
     
}
