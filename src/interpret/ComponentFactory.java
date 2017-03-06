package interpret;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class ComponentFactory {
    
    /**
     * Return a tree filled with packages.
     */
    public static JTree createPackageTree() {
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode();
        createPackageNodes(top);
 
        //Create a tree that allows one selection at a time.
        JTree tree = new JTree(top);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
        //Listen for when the selection changes.
        tree.putClientProperty("JTree.lineStyle", "Horizontal");
 
        //Create the scroll pane and add the tree to it. 
        return tree;
    }
    
    /**
     * Add nodes to on top node of the package tree.
     */
    private static void createPackageNodes(DefaultMutableTreeNode top) {
        for (Package pack : InterpretTools.getSortedPackages()) {
            DefaultMutableTreeNode packNode = new DefaultMutableTreeNode(pack.getName());
            top.add(packNode);
     
            for (Class<?> cls : InterpretTools.findClassesIn(pack.getName())) {
                packNode.add(new DefaultMutableTreeNode(cls));
            }
        }
    }

    /**
     * Return a table filled with fields of specified class.
     */
    public static JTable createFieldTableScrollPane(Class<?> cls, Object instance) throws IllegalArgumentException, IllegalAccessException {
        String[] columnNames = {
                "Modifier",
                "Type",
                "Field",
                "Value"
        };
        
        Field[] fields = InterpretTools.getAllFields(cls);
        Object[][] fieldData = new Object[fields.length][columnNames.length];
        for (int i = 0; i < fields.length; i++) {
            fieldData[i][0] = InterpretTools.stringifyModifiers(fields[i].getModifiers());
            fieldData[i][1] = InterpretTools.getSimpleName(fields[i].getGenericType());
            fieldData[i][2] = fields[i].getName();
            fieldData[i][3] = InterpretTools.getFieldValue(fields[i], instance);
        }
 
        final JTable table = new JTable(fieldData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        return table;
    }

    /**
     * Return a table filled with methods of specified class.
     */
    public static JTable createMethodTable(Class<?> cls) {
        String[] columnNames = {
                "Modifier",
                "Type",
                "Method",
                "Parameter"
        };
        
        Method[] methods = InterpretTools.getAllMethods(cls);
        Object[][] methodData = new Object[methods.length][columnNames.length];
        for (int i = 0; i < methods.length; i++) {
            methodData[i][0] = InterpretTools.stringifyModifiers(methods[i].getModifiers());
            methodData[i][1] = InterpretTools.getSimpleName(methods[i].getGenericReturnType());
            methodData[i][2] = methods[i].getName();
            methodData[i][3] = InterpretTools.stringifyParams(methods[i].getGenericParameterTypes());
        }
 
        final JTable table = new JTable(methodData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        return table;
    }
    
    /**
     * Return a class explorer pane filled with all the classes.
     */
    public static JPanel createClassExplorerPane(Component cls, Component instance) {
        // All icons are drawn in rgb(4, 33, 81).
        ImageIcon documentIcon = createImageIcon("images/icon_document.png");
        ImageIcon cubeIcon = createImageIcon("images/icon_cube.png");
        Tab classTab = new Tab(cls, documentIcon, "Class Explorer", "Classes available here.");
        Tab instanceTab = new Tab(instance, cubeIcon, "Instance Explorer", "Instances available here.");
        Tab[] tabs = {classTab, instanceTab};
        return createPaneWithTabbedPanes(tabs, false);
    }
    
    /**
     * Return a member pane filled with tabbed pane of specified class.
     */
    public static JPanel createMemberPane(Component general, Component nested, Component field, Component constructor, Component method) {
        ImageIcon documentIcon = createImageIcon("images/icon_document.png");
        Tab generalTab = new Tab(general, documentIcon, "General", "General information");
        Tab nestedTab = new Tab(nested, documentIcon, "Nested", "Nested class/interface");
        Tab fieldTab = new Tab(field, documentIcon, "Field", "Field summary");
        Tab constTab = new Tab(constructor, documentIcon, "Constructor", "Constructor summary");
        Tab methodTab = new Tab(method, documentIcon, "Method", "Method summary");
        Tab[] tabs = {generalTab, nestedTab, fieldTab, constTab, methodTab};
        return createPaneWithTabbedPanes(tabs, true);
    }
    
    /**
     * Return a JScrollPane filled with the specified view.
     */
    public static JScrollPane createJScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setPreferredSize(new Dimension(410, 50));
        return scrollPane;        
    }
    
    /** 
     * Returns an ImageIcon, or null if the path was invalid. 
     */
    private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ComponentFactory.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    /**
     * Return a JPane filled with a JtabbedPane.
     */
    private static JPanel createPaneWithTabbedPanes(Tab[] tabs, boolean wrapped) {
        JPanel panel = new JPanel(new GridLayout(1, 1));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        for (Tab tab : tabs) {
            tabbedPane.addTab(tab.getTitle(), tab.getIcon(), tab.getPanel(), tab.getTip());
        }
         
        //Add the tabbed pane to this panel.
        panel.add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        if (wrapped) {
            tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        } else {
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        }
        return panel;
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
