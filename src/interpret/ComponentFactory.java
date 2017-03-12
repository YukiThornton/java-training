package interpret;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class ComponentFactory {

    private static final int DEFAULT_TEXT_FIELD_COLUMN = 10;
    private static final Color COMMENT_COLOR = new Color(26,  114, 11);
    private static final String REGEX_VALID_VARIABLE_NAME = "[a-zA-Z_\\p{Sc}]+[a-zA-Z0-9_\\p{Sc}]*";
    //private static final String REGEX_SINGLE_QUOTED_UNICODE = "\'\\([0-9a-fA-F]{4}?)\'";
    private static final String REGEX_SINGLE_QUOTED = "\'([^\']*)\'";
    private static final String REGEX_DOUBLE_QUOTED = "\"([^\"]*)\"";
    private static final String JAVA_KEYWORDS[] = { "abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while" };
    private static JTextField returnVariableName;
    private static Component[] labParamComponents;
    private static String[] labParamTypeNames;
    private static List<Object> labParamInputs;
    
    public static Object[] getLabParamInputArray() {
        return labParamInputs.toArray();
    }
    public static String getReturnVariableName() {
        return returnVariableName.getText();
    }
    
    /**
     * Return a tree filled with packages.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static JTree createPackageTree() throws ClassNotFoundException, IOException {
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
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    private static void createPackageNodes(DefaultMutableTreeNode top) throws ClassNotFoundException, IOException {
        for (Package pack : InterpretTools.getSortedPackages()) {
            DefaultMutableTreeNode packNode = new DefaultMutableTreeNode(pack.getName());
            top.add(packNode);
     
            for (Class<?> cls : InterpretTools.findClassesIn(pack)) {
                packNode.add(new DefaultMutableTreeNode(cls));
            }
        }
    }

    /**
     * Return a tree with only one node.
     */
    public static JTree createTreeWithSingleNode() {
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode();
 
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
     * Return a tree with specified root node.
     */
    public static JTree createTreeWithSingleNode(DefaultMutableTreeNode root) {
        //Create a tree that allows one selection at a time.
        JTree tree = new JTree(root);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
        //Listen for when the selection changes.
        tree.putClientProperty("JTree.lineStyle", "Horizontal");
 
        //Create the scroll pane and add the tree to it. 
        return tree;
    }
    
    /**
     * Return a tree with only one node.
     */
    public static JTree loadTreeWithMap(JTree to, Map<String, List<UserVariable>> map) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)to.getModel().getRoot();
        root.removeAllChildren();
        for (String key : map.keySet()) {
            DefaultMutableTreeNode keyNode = new DefaultMutableTreeNode(key);
            root.add(keyNode);
     
            for (UserVariable variable : map.get(key)) {
                keyNode.add(new DefaultMutableTreeNode(variable));
            }
        }
        return createTreeWithSingleNode(root);
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
 
        final JTable table = new JTable(new InterpretTableModel(fieldData, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        return table;
    }

    /**
     * Return a table filled with methods of specified class.
     */
    public static JTable createMethodTable(Method[] methods) {
        String[] columnNames = {
                "Modifier",
                "Type",
                "Method",
                "Parameter"
        };
        
        Object[][] methodData = new Object[methods.length][columnNames.length];
        for (int i = 0; i < methods.length; i++) {
            methodData[i][0] = InterpretTools.stringifyModifiers(methods[i].getModifiers());
            methodData[i][1] = InterpretTools.getSimpleName(methods[i].getGenericReturnType());
            methodData[i][2] = methods[i].getName();
            methodData[i][3] = InterpretTools.stringifyParams(methods[i].getGenericParameterTypes());
        }
 
        final JTable table = new JTable(new InterpretTableModel(methodData, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        return table;
    }
    
    /**
     * Return a table filled with constructors of specified class.
     */
    public static JTable createConstructorTable(Constructor<?>[] constructors) {
        String[] columnNames = {
                "Modifier",
                "Method",
                "Parameter"
        };
        
        Object[][] constructorData = new Object[constructors.length][columnNames.length];
        for (int i = 0; i < constructors.length; i++) {
            constructorData[i][0] = InterpretTools.stringifyModifiers(constructors[i].getModifiers());
            constructorData[i][1] = constructors[i].getDeclaringClass().getSimpleName();
            constructorData[i][2] = InterpretTools.stringifyParams(constructors[i].getGenericParameterTypes());
        }
 
        final JTable table = new JTable(new InterpretTableModel(constructorData, columnNames));
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
        Tab classTab = new Tab(cls, documentIcon, "Class Tree", "Classes available here.");
        Tab instanceTab = new Tab(instance, cubeIcon, "Variable Tree", "Variables available here.");
        Tab[] tabs = {classTab, instanceTab};
        return createPaneWithTabbedPanes(tabs, false);
    }
    
    /**
     * Return a member pane filled with tabbed pane of specified class.
     */
    public static JPanel createMemberPane(Component general, Component nested, Component field, Component constructor, Component method) {
        ImageIcon generalIcon = createImageIcon("images/icon_info.png");
        ImageIcon nestIcon = createImageIcon("images/icon_nest.png");
        ImageIcon fieldIcon = createImageIcon("images/icon_zoom.png");
        ImageIcon constIcon = createImageIcon("images/icon_factory.png");
        ImageIcon methodIcon = createImageIcon("images/icon_toolbox.png");
        Tab generalTab = new Tab(general, generalIcon, "General", "General information");
        Tab nestedTab = new Tab(nested, nestIcon, "Nested", "Nested class/interface");
        Tab fieldTab = new Tab(field, fieldIcon, "Field", "Field summary");
        Tab constTab = new Tab(constructor, constIcon, "Constructor", "Constructor summary");
        Tab methodTab = new Tab(method, methodIcon, "Method", "Method summary");
        Tab[] tabs = {generalTab, nestedTab, fieldTab, constTab, methodTab};
        return createPaneWithTabbedPanes(tabs, true);
    }
    
    /**
     * Return a lab tab pane filled with components of specified static method.
     */
    public static JPanel createLabTabPane(Member element, UserVariable userVariable) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(410, 50));
        if (element == null) {
            System.out.println("Element is null; Something wrong happened in createLabTabPane");
            // todo: let the user to choose a method
        }
        JLabel returnType = null;
        JLabel methodInvokingPart = null;
        Type[] parameterTypes = null;
        if (element instanceof Method) {
            Method method = (Method)element;
            returnType = new JLabel(InterpretTools.getSimpleName(method.getGenericReturnType()) + " ");
            String methodOrigin = userVariable == null? InterpretTools.getSimpleName(method.getDeclaringClass()) : userVariable.getName();
            methodInvokingPart = new JLabel(" = " + methodOrigin + "." + method.getName() + "(");
            parameterTypes = method.getGenericParameterTypes();      
        } else if (element instanceof Constructor<?>){
            Constructor<?> constructor = (Constructor<?>)element;
            String constName = InterpretTools.getSimpleName(constructor.getDeclaringClass());
            returnType = new JLabel(constName + " ");
            methodInvokingPart = new JLabel(" = new " + constName + "(");
            parameterTypes = constructor.getGenericParameterTypes();     
        } else {
            System.out.println("something wrong in createLabTabPane!");        
        }
        
        JPanel firstLine = new JPanel();
        returnVariableName = new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        firstLine.add(returnType);
        firstLine.add(returnVariableName);
        firstLine.add(methodInvokingPart);
        panel.add(firstLine);
        
        JPanel paramPane = new JPanel();
        paramPane.setLayout(new BoxLayout(paramPane, BoxLayout.Y_AXIS));
        addParametersAndLabels(paramPane, parameterTypes);
        panel.add(paramPane);
        panel.add(new JLabel(");"));
        
        JPanel buttons = new JPanel();
        Button cancel = new Button("cancel");
        cancel.addActionListener(new Interpret.ButtonActionListener());
        buttons.add(cancel);
        Button invoke = new Button("invoke");
        invoke.addActionListener(new Interpret.ButtonActionListener());
        buttons.add(invoke);
        panel.add(buttons);
        return panel;
    }
    
    private static void addParametersAndLabels(JPanel to, Type[] params) {
        labParamComponents = new Component[params.length];
        labParamTypeNames = new String[params.length];
        if (params.length == 0) {
            JLabel explanation = new JLabel("// None");
            explanation.setForeground(COMMENT_COLOR);
            to.add(explanation);
            return;
        }
        for (int i = 0; i < params.length; i++) {
            JPanel row = new JPanel();
            labParamTypeNames[i] = InterpretTools.getSimpleName(params[i]);
            labParamComponents[i] = createInputComp(labParamTypeNames[i], params[i]);
            row.add(labParamComponents[i]);
            JLabel explanation = new JLabel("// " + labParamTypeNames[i]);
            explanation.setForeground(COMMENT_COLOR);
            row.add(explanation);
            to.add(row);
        }
    }
    
    private static Component createInputComp(String typeName, Type type) {
        if (typeName.equals("char")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        if (typeName.equals("byte")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        if (typeName.equals("short")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        if (typeName.equals("int")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        if (typeName.equals("long")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        if (typeName.equals("double")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        if (typeName.equals("float")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        if (typeName.equals("boolean")) {
            Boolean[] booleans = {Boolean.TRUE, Boolean.FALSE};
            return new JComboBox<>(booleans);
        }
        if (typeName.equals("String")) {
            return new JTextField(DEFAULT_TEXT_FIELD_COLUMN);
        }
        
        return createUneditableTextField("Object");
    }
    
    public static String checkAndColllectLabInput() {
        String result = "";
        labParamInputs = new ArrayList<>();
        // returVariableName
        if (!Pattern.matches(REGEX_VALID_VARIABLE_NAME, returnVariableName.getText())) {
            result += "Variable name is invalid. \n";
        }
        if (isJavaKeyword(returnVariableName.getText())) {
            result += "Variable name is reserved. \n";
        }
        // todo: check already created variable names
        for (int i = 0; i < labParamComponents.length; i++) {
            String paramResult = checkLabParamInput(labParamComponents[i], labParamTypeNames[i]);
            if (paramResult != null) {
                result += "Param " + (i + 1) + ": " + paramResult + "\n";
            }
        }
        
        // FOR DEBUG
        for (Object object : labParamInputs) {
            System.out.println(object.getClass() + " : " + object);
        }
        
        
        return result.length() == 0 ? null : result;
    }
    
    private static boolean isJavaKeyword(String word) {
        return Arrays.binarySearch(JAVA_KEYWORDS, word) >= 0;
    }
    
    private static String checkLabParamInput(Component component, String typeName) {
        String result = "";
        switch (typeName) {
        case "char":
            String inputStr = ((JTextField)component).getText();
            // Single quoted input
            if (Pattern.matches(REGEX_SINGLE_QUOTED, inputStr)) {
                if (inputStr.length() == 3) {
                    labParamInputs.add(inputStr.charAt(1));                
                } else if (inputStr.length() == 8 && inputStr.charAt(1) == '\\' && inputStr.charAt(2) == 'u') {
                    labParamInputs.add((char)Integer.parseInt(inputStr.substring(3, 7), 16));                
                } else {
                    result += "Invalid char value.";
                }
            // Plain number literal
            } else {
                try {
                    labParamInputs.add((char)Integer.parseInt(inputStr));
                } catch (NumberFormatException e) {
                    result += "Invalid char value.";
                }
            }
            break;

        case "byte":
            try {
                labParamInputs.add(Byte.parseByte(((JTextField)component).getText()));
            } catch (NumberFormatException e) {
                result += "Invalid byte value.";
            }
            break;

        case "short":
            try {
                labParamInputs.add(Short.parseShort(((JTextField)component).getText()));
            } catch (NumberFormatException e) {
                result += "Invalid short value.";
            }
            break;

        case "int":
            try {
                labParamInputs.add(Integer.parseInt(((JTextField)component).getText()));
            } catch (NumberFormatException e) {
                result += "Invalid int value.";
            }
            break;

        case "long":
            String longInput = ((JTextField)component).getText();
            if (longInput.endsWith("L") || longInput.endsWith("l")) {
                longInput = longInput.substring(0, longInput.length() - 1);
            }
            try {
                labParamInputs.add(Long.parseLong(longInput));
            } catch (NumberFormatException e) {
                result += "Invalid long value.";
            }
            break;

        case "float":
            try {
                labParamInputs.add(Float.parseFloat(((JTextField)component).getText()));
            } catch (NumberFormatException e) {
                result += "Invalid float value.";
            }
            break;

        case "double":
            try {
                labParamInputs.add(Double.parseDouble(((JTextField)component).getText()));
            } catch (NumberFormatException e) {
                result += "Invalid double value.";
            }
            break;

        case "boolean":
            // No need to validate.
            Boolean inputBool = (Boolean) ((JComboBox<?>)component).getSelectedItem();
            labParamInputs.add(inputBool.booleanValue());
            break;

        case "String":
            String str = ((JTextField)component).getText();
            if (Pattern.matches(REGEX_DOUBLE_QUOTED, str)) {
                labParamInputs.add(str.substring(1, str.length() - 1));
            } else {
                result += "Invalid String value.";
            }
            break;

        default:
            break;
        }
        return result.length() == 0 ? null : result;
    }
    
    /**
     * Return a lab tab pane filled with components of specified method.
     */
    /*
    public static JPanel createLabTabPane(JPanel panel, Method method) {
        JPanel uneditablePane = new JPanel();
        uneditablePane.setBorder(BorderFactory.createTitledBorder("Target"));
        Component[] labelsForUneditablePane = {new JLabel("Claaaaaaaaaaaaass"), new JLabel("Method")};
        Component[] textFieldsForUneditablePane = new Component[2];
        textFieldsForUneditablePane[0] = createUneditableTextField(method.getDeclaringClass().getCanonicalName());
        textFieldsForUneditablePane[1] = createUneditableTextField(method.getName());
        addTwoColumns(uneditablePane, labelsForUneditablePane, textFieldsForUneditablePane);
        panel.add(uneditablePane);
        JPanel paramPane = new JPanel();
        paramPane.setBorder(BorderFactory.createTitledBorder("Parameters"));
        if (method.getParameterCount() == 0) {
            paramPane.add(createUneditableTextField("None"));
        } else {
            paramPane.add(createUneditableTextField("Add params!!!!"));
        }
        panel.add(paramPane);
        Button invoke = new Button("invoke");
        invoke.addActionListener(new Interpret.ButtonActionListener());
        panel.add(invoke);
        Button cancel = new Button("cancel");
        cancel.addActionListener(new Interpret.ButtonActionListener());
        panel.add(cancel);
        return panel;
    }
    */

    /**
     * Return a lab pane filled with single tabbed pane of specified field or constructors..
     */
    public static JPanel createLabPane(Component labTabPanel) {
        ImageIcon labIcon = createImageIcon("images/icon_lab.png");
        Tab labTab = new Tab(labTabPanel, labIcon, "Lab", "General information");
        Tab[] tabs = {labTab};
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
     * Returns an uneditable text field. 
     */
    public static JTextField createUneditableTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setEditable(false);
        return textField;
    }
    
    /**
     * Return a pane with a right sided child panel on the left side and a left sided child panel on the right side.
     */
    public static void addTwoColumns(JPanel targetPanel, JLabel[] leftComponents, Component[] rightComponents) {
        JPanel leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));
        for (Component comp: leftComponents) {
            leftPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
            leftPane.add(comp);
            leftPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        }
        targetPanel.add(leftPane);
        
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        for (Component comp: rightComponents) {
            rightPane.add(comp);
        }
        targetPanel.add(rightPane);
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
    
    @SuppressWarnings("serial")
    public static class InterpretTableModel extends DefaultTableModel {
    
        public InterpretTableModel(Object[][] data, String[] columNames) {
            super(data, columNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
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
