package interpret;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import interpret.ComponentTools.Tab;
import interpret.LabData.LabType;

public class InterpretView {
    private static final Dimension MIN_FRAME_SIZE = new Dimension(500, 300);
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(1200, 800);
    private static final Dimension DEFAULT_MAIN_PANE_SIZE = new Dimension(DEFAULT_FRAME_SIZE.width, (int)(DEFAULT_FRAME_SIZE.height * 0.75));
    private static final Dimension DEFAULT_PILLAR_SIZE = new Dimension(50, (int)(DEFAULT_FRAME_SIZE.height * 0.75));
    private static final Dimension MAX_PILLAR_SIZE = new Dimension(50, 10000);
    private static final Dimension DEFAULT_COLUMN_PANE_SIZE = new Dimension((int)((DEFAULT_FRAME_SIZE.width - DEFAULT_PILLAR_SIZE.width * 2) * 0.3), (int)(DEFAULT_FRAME_SIZE.height * 0.75));
    private static final Dimension DEFAULT_LOG_PANE_SIZE = new Dimension(DEFAULT_FRAME_SIZE.width, (int)(DEFAULT_FRAME_SIZE.height * 0.25));
    private static final Color FRAME_BG_COLOR = new Color(205, 221, 247);
    private static final Color PANEL_BG_COLOR = Color.WHITE;
    private static final ImageIcon ICON_DOCUMENT = ComponentTools.createImageIcon("images/icon_document.png");
    private static final ImageIcon ICON_CUBE = ComponentTools.createImageIcon("images/icon_cube.png");
    private static final ImageIcon ICON_INFO = ComponentTools.createImageIcon("images/icon_info.png");
    private static final ImageIcon ICON_ZOOM = ComponentTools.createImageIcon("images/icon_zoom.png");
    private static final ImageIcon ICON_FACTORY = ComponentTools.createImageIcon("images/icon_factory.png");
    private static final ImageIcon ICON_TOOLBOX = ComponentTools.createImageIcon("images/icon_toolbox.png");
    private static final ImageIcon ICON_LAB = ComponentTools.createImageIcon("images/icon_lab.png");
    private static final ImageIcon ICON_ARROW = ComponentTools.createImageIcon("images/icon_arrow.png");
    private static final String[] FIELD_COLUMNS = {"Modifier", "Type", "Field", "Value"};
    private static final String[] CONST_COLUMNS = {"Modifier", "Method", "Parameter"};
    private static final String[] METHOD_COLUMNS = {"Modifier", "Type", "Method", "Parameter"};
    private static final String DEFAULT_MESSAGE_CENTER_PANE = "Double click on a class!";
    
    
    private JFrame frame;
    private JPanel mainPane;
    private JPanel leftPane;
    private JPanel centerPane;
    private JPanel rightPane;
    private JPanel logPane;
    
    private JTextArea hitoryArea;
    
    private Tab classTab;
    private Tab variableTab;
    private JTabbedPane leftTabbedPane;
    private JTabbedPane centerTabbedPane;
    private JTabbedPane rightTabbedPane;
    
    private JTree classTree;
    private JTree variableTree;
    private JTable fieldTable;
    private JTable constructorTable;
    private JTable methodTable;
    
    private MouseListener treeMouseListener;
    private MouseListener tableMouseListener;
    private ActionListener createVariableBtnInInfoActionListener;
    private ActionListener createArrayBtnInInfoActionListener;
    private ActionListener changeValueBtnInInfoActionListener;
    private ActionListener applyBtnInLabActionListener;
    private ActionListener cancelBtnInLabActionListener;
    
    private boolean isInitialized = false;

    public void init(Map<String, List<Class<?>>> classMap, MouseListener classTreeMouseListener, MouseListener tableMouseListener,
                        ActionListener[] btnActionListeners) {
        if (frame != null) {
            throw new IllegalStateException("Use this method only once.");
        }
        frame = new JFrame("Interpret");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setPreferredSize(DEFAULT_FRAME_SIZE);
        frame.setMinimumSize(MIN_FRAME_SIZE);
        frame.setBackground(FRAME_BG_COLOR);
        
        this.treeMouseListener = classTreeMouseListener;
        this.tableMouseListener = tableMouseListener;
        this.createVariableBtnInInfoActionListener = btnActionListeners[0];
        this.createArrayBtnInInfoActionListener = btnActionListeners[1];
        this.changeValueBtnInInfoActionListener = btnActionListeners[2];
        this.applyBtnInLabActionListener = btnActionListeners[3];
        this.cancelBtnInLabActionListener = btnActionListeners[4];
        
        initializeMainPane(classMap);
        initializeLogPane();
        
        frame.add(mainPane);
        frame.add(logPane);
        frame.pack();
        isInitialized = true;
    }
    
    public JFrame getFrame() {
        return frame;
    }
    public JTextArea getHistoryArea() {
        return hitoryArea;
    }
    
    public JTable getFieldTable() {
        return fieldTable;
    }

    public JTable getConstructorTable() {
        return constructorTable;
    }

    public JTable getMethodTable() {
        return methodTable;
    }

    public void show() {
        if (isInitialized == false) {
            throw new IllegalStateException("Invoke init first.");
        }
        frame.setVisible(true);
    }
    
    public JTree getClassTree() {
        if (isInitialized == false) {
            throw new IllegalStateException("Invoke init first.");
        }
        return classTree;
    }
    
    public JTree getVariableTree() {
        if (isInitialized == false) {
            throw new IllegalStateException("Invoke init first.");
        }
        return variableTree;
    }
    
    private void initializeMainPane(Map<String, List<Class<?>>> classMap) {
        mainPane = new JPanel();
        mainPane.setPreferredSize(DEFAULT_MAIN_PANE_SIZE);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
        initializeLeftPane(classMap);
        initializeCenterPane();
        initializeRightPane();
        
        mainPane.add(leftPane);
        mainPane.add(createPillar());
        mainPane.add(centerPane);
        mainPane.add(createPillar());
        mainPane.add(rightPane);
    }
    private void initializeLogPane() {
        logPane = new JPanel();
        logPane.setPreferredSize(DEFAULT_LOG_PANE_SIZE);
        logPane.setLayout(new BoxLayout(logPane, BoxLayout.X_AXIS));
        logPane.setBackground(FRAME_BG_COLOR);
        
        hitoryArea = new JTextArea();
        hitoryArea.setEditable(false);
        logPane.add(ComponentTools.createJScrollPane(hitoryArea, DEFAULT_LOG_PANE_SIZE));
    }
    private void initializeLeftPane(Map<String, List<Class<?>>> classMap) {
        leftPane = new JPanel();
        leftPane.setPreferredSize(DEFAULT_COLUMN_PANE_SIZE);
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.X_AXIS));
        leftPane.setBackground(FRAME_BG_COLOR);
        
        classTab = createClassTreeTab(classMap);
        variableTab = createVariableTreeTab();
        Tab[] tabs = {classTab, variableTab};
        leftTabbedPane = ComponentTools.createTabbedPane(tabs, false);
        leftPane.add(leftTabbedPane);
    }
    private void initializeCenterPane() {
        centerPane = new JPanel();
        centerPane.setPreferredSize(DEFAULT_COLUMN_PANE_SIZE);
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.X_AXIS));
        centerPane.setBackground(FRAME_BG_COLOR);
        
        Tab infoTab = createInfoTab(DEFAULT_MESSAGE_CENTER_PANE);
        Tab[] tabs = {infoTab};
        centerTabbedPane = ComponentTools.createTabbedPane(tabs, false);
        
        centerPane.add(centerTabbedPane);
    }
    public void changeCenterPane(Info info, Field[] fields, Constructor<?>[] constructors, Method[] methods) throws IllegalArgumentException, IllegalAccessException {
        List<Tab> tabList = new ArrayList<>();
        tabList.add(createInfoTab(info));
        if (info.hasFields()) {
            Object object = null;
            if (info.getVariable() != null) {
                object = info.getVariable().getValue();
            }
            tabList.add(createFieldTab(fields, object));
        }
        if (info.hasConstructors()) {
            tabList.add(createConstructorTab(constructors));        
        }
        if (info.hasMethods()) {
            tabList.add(createMethodTab(methods));
        }
        centerPane.remove(centerTabbedPane);
        centerTabbedPane = ComponentTools.createTabbedPane(tabList.toArray(new Tab[tabList.size()]), false);
        centerPane.add(centerTabbedPane);
        centerPane.validate();
        centerPane.repaint();
    }
    public void clearCenterPane() {
        Tab infoTab = createInfoTab(DEFAULT_MESSAGE_CENTER_PANE);
        Tab[] tabs = {infoTab};
        centerPane.remove(centerTabbedPane);
        centerTabbedPane = ComponentTools.createTabbedPane(tabs, false);
        centerPane.add(centerTabbedPane);
        centerPane.validate();
        centerPane.repaint();
    }
    public void changeCenterPanePage(int page) {
        if (page < 0 || page >= centerTabbedPane.getTabCount()) {
            throw new IllegalArgumentException("page is too small or too big.");
        }
        centerTabbedPane.setSelectedIndex(page);
    }
    private void initializeRightPane() {
        rightPane = new JPanel();
        rightPane.setPreferredSize(DEFAULT_COLUMN_PANE_SIZE);
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.X_AXIS));
        rightPane.setBackground(FRAME_BG_COLOR);

        Tab[] tabs = {createLabTab("")};
        rightTabbedPane = ComponentTools.createTabbedPane(tabs, false);
        rightPane.add(rightTabbedPane);
    }
    public void changeRightPane(LabData labData) {
        changeRightPane(createLabTab(labData));
    }
    public void changeRightPane(String message) {
        changeRightPane(createLabTab(message));
    }
    private void changeRightPane(Tab tab) {
        Tab[] tabs = {tab};
        rightPane.remove(rightTabbedPane);
        rightTabbedPane = ComponentTools.createTabbedPane(tabs, false);
        rightPane.add(rightTabbedPane);
        rightPane.validate();
        rightPane.repaint();
    }
    private JPanel createPillar() {
        JPanel panel = new JPanel(false);
        panel.setPreferredSize(DEFAULT_PILLAR_SIZE);
        panel.setMaximumSize(MAX_PILLAR_SIZE);
        panel.setBackground(FRAME_BG_COLOR);
        JLabel label = new JLabel(ICON_ARROW);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(label);
        
        return panel;
    }
    
    private Tab createClassTreeTab(Map<String, List<Class<?>>> classMap) {
        classTree = createClassTreeWithMap(classMap);
        JScrollPane pane = ComponentTools.createJScrollPane(classTree, DEFAULT_COLUMN_PANE_SIZE);
        return new Tab(pane, ICON_DOCUMENT, "Class Tree", "Classes available here.");
    }
    
    private Tab createVariableTreeTab() {
        return new Tab(ComponentTools.createTextPanel("Make variables and come back here!", DEFAULT_COLUMN_PANE_SIZE), ICON_CUBE, "Variable Tree", "Variables available here.");
    }
    
    public void changeVariableTreeTab(Map<String, List<Variable>> map) {
        variableTree = createVariableTreeWithMap(map);
        JScrollPane pane = ComponentTools.createJScrollPane(variableTree, DEFAULT_COLUMN_PANE_SIZE);
        leftTabbedPane.remove(1);
        leftTabbedPane.addTab("Variable Tree", ICON_CUBE, pane, "Variables available here.");
        leftTabbedPane.setSelectedIndex(1);
        leftTabbedPane.validate();
        leftTabbedPane.repaint();
    }
    
    private Tab createInfoTab(Info info) {
        if (info == null) {
            throw new IllegalArgumentException("Something wrong happened!");
        }
        return new Tab(createInfoCompo(info), ICON_INFO, "Info", "Information");
    }
    private Component createInfoCompo(Info info) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(DEFAULT_COLUMN_PANE_SIZE);
        panel.setBackground(PANEL_BG_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        switch (info.getInfoType()) {
        case CLASS:
            decorateInfoPaneForClass(info, panel, constraints);
            break;
        case PRIMITIVE_TYPE:
            decorateInfoPaneForPrimitiveType(info, panel, constraints);
            break;
        case OBJECT_VARIABLE:
            decorateInfoPaneForObjectVariable(info, panel, constraints);
            break;
        case PRIMITIVE_VARIABLE:
            decorateInfoPaneForPrimitiveVariable(info, panel, constraints);
            break;
        case NULL:
            decorateInfoPaneForNull(info, panel, constraints);
            break;
        default:
            panel = ComponentTools.createTextPanel("Not implemented", DEFAULT_COLUMN_PANE_SIZE);
            break;
        }
        return panel;
    }
    private void decorateInfoPaneForClass(Info info, JPanel panel, GridBagConstraints constraints) {
        addTypeInfoPaneToInfo(panel, constraints, 0, info);
        addBtnPaneToInfo(panel, constraints, 1, info);        
    }
    private void decorateInfoPaneForPrimitiveType(Info info, JPanel panel, GridBagConstraints constraints) {
        addTypeInfoPaneToInfo(panel, constraints, 0, info);
        addBtnPaneToInfo(panel, constraints, 1, info);        
    }
    private void decorateInfoPaneForObjectVariable(Info info, JPanel panel, GridBagConstraints constraints) {
        addVariableInfoPaneToInfo(panel, constraints, 0, info);
        addBtnPaneToInfo(panel, constraints, 1, info);        
    }
    private void decorateInfoPaneForPrimitiveVariable(Info info, JPanel panel, GridBagConstraints constraints) {
        addVariableInfoPaneToInfo(panel, constraints, 0, info);
        addBtnPaneToInfo(panel, constraints, 1, info);        
    }
    private void decorateInfoPaneForNull(Info info, JPanel panel, GridBagConstraints constraints) {
        addVariableInfoPaneToInfo(panel, constraints, 0, info);
        addBtnPaneToInfo(panel, constraints, 1, info);        
    }
    private void addTypeInfoPaneToInfo(JPanel pane, GridBagConstraints constraints, int gridy, Info info) {
        String[] strLabels = {"Type"};
        Component[] components = {new JLabel(info.getSimpleTypeName())};
        addDoubleColumnTitledPaneToLab(pane, constraints, gridy, info.getInfoTitle(), strLabels, components);
    }
    private void addVariableInfoPaneToInfo(JPanel pane, GridBagConstraints constraints, int gridy, Info info) {
        String[] strLabels = {"Type", "Name", "Value", "Created at", "Last modified at"};
        Component[] components = {new JLabel(info.getVariable().getSimpleTypeName()), new JLabel(info.getVariable().toString()),
                new JLabel(info.getVariable().getSimpleValueName()), new JLabel(info.getVariable().getCreatedAtStr()),
                new JLabel(info.getVariable().getLastModifiedAtStr())};
        addDoubleColumnTitledPaneToLab(pane, constraints, gridy, info.getInfoTitle(), strLabels, components);
    }
    private Tab createInfoTab(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Something wrong happened!");
        }
        Component component = ComponentTools.createTextPanel(message, DEFAULT_COLUMN_PANE_SIZE);
        return new Tab(component, ICON_INFO, "Info", "Information");
    }
    private Tab createFieldTab(Field[] fields, Object object) throws IllegalArgumentException, IllegalAccessException {
        fieldTable = createFieldTable(fields, object);
        Component component = ComponentTools.createJScrollPane(fieldTable, DEFAULT_COLUMN_PANE_SIZE);
        return new Tab(component, ICON_ZOOM, "Field", "Field");
    }
    private Tab createConstructorTab(Constructor<?>[] constructors) {
        constructorTable = createConstructorTable(constructors);
        Component component = ComponentTools.createJScrollPane(constructorTable, DEFAULT_COLUMN_PANE_SIZE);
        return new Tab(component, ICON_FACTORY, "Constructor", "Constructor");
    }
    private Tab createMethodTab(Method[] methods) {
        methodTable = createMethodTable(methods);
        Component component = ComponentTools.createJScrollPane(methodTable, DEFAULT_COLUMN_PANE_SIZE);
        return new Tab(component, ICON_TOOLBOX, "Method", "Method");
    }
    private Tab createLabTab(LabData labData) {
        if (labData == null) {
            throw new IllegalArgumentException("labData is null");
        }
        return createLabTab(createLabCompo(labData));        
    }
    private Tab createLabTab(String message) {
        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }
        return createLabTab(ComponentTools.createTextPanel(message, DEFAULT_COLUMN_PANE_SIZE));
    }
    private Tab createLabTab(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("component is null");
        }
        return new Tab(component, ICON_LAB, "Lab", "Lab");
    }
    private Component createLabCompo(LabData labData) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(DEFAULT_COLUMN_PANE_SIZE);
        panel.setBackground(PANEL_BG_COLOR);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        switch (labData.labType) {
        case CONSTRUCTOR:
            return createLabForConstructor(labData, panel, constraints);
        case STATIC_METHOD:
        case NON_STATIC_METHOD:
            return createLabForMethod(labData, panel, constraints);
        case STATIC_FIELD:
        case NON_STATIC_FIELD:
            return createLabForField(labData, panel, constraints);
        case NEW_ARRAY:
            return createLabForNewArray(labData, panel, constraints);
        case VARIABLE:
            return createLabForPrimitive(labData, panel, constraints);
        default:
            return ComponentTools.createTextPanel("Not implemented", DEFAULT_COLUMN_PANE_SIZE);
        }
    }
    private Component createLabForField(LabData labData, JPanel panel, GridBagConstraints constraints) {
        FieldLabData fieldLabData = (FieldLabData)labData;
        addTitleToPane(panel, constraints, 0, "Change the \'" + fieldLabData.getFieldName() + "\' field.");

        if (fieldLabData.getLabType() == LabType.STATIC_FIELD) {
            addStaticFieldInfoPaneToLab(panel, constraints, 1, fieldLabData.getSimpleDeclaredClassName(), fieldLabData.getSimpleFieldTypeName(), fieldLabData.getFieldName(), fieldLabData.getModifiers());
        } else if (fieldLabData.getLabType() == LabType.NON_STATIC_FIELD) {
            addNonStaticFieldInfoPaneToLab(panel, constraints, 1, fieldLabData.getVariable(), fieldLabData.getSimpleFieldTypeName(), fieldLabData.getFieldName(), fieldLabData.getModifiers());
        } else {
            throw new IllegalStateException("Something wrong happened.");
        }
        addModificationPaneToLab(panel, constraints, 2, fieldLabData.getCurrentFieldValueStr(), fieldLabData.getNewFieldValueInput());
        addBtnPaneToLab(panel, constraints, 3, "Cancel", fieldLabData.getActionVerb());
        return panel;
    }
    private Component createLabForConstructor(LabData labData, JPanel panel, GridBagConstraints constraints) {
        ConstructorLabData constLabData = (ConstructorLabData)labData;
        
        if (constLabData.updatesExistingVariable()) {
            addTitleToPane(panel, constraints, 0, "Set new value on \'" + constLabData.getDestObjectVariable().toString() + "\'.");
            addExistingVariableInfoPaneToLab(panel, constraints, 1, constLabData.getSimpleDeclaredClassName(), constLabData.getDestObjectVariable().toString(), constLabData.getDestObjectVariable().getSimpleValueName());
        } else {
            addTitleToPane(panel, constraints, 0, "Create new \'" + constLabData.getSimpleDeclaredClassName() + "\' variable.");
            addNewVariableInfoPaneToLab(panel, constraints, 1, constLabData.getSimpleDeclaredClassName(), constLabData.getNewVariableNameInput().getInputComponents()[0]);
        }
        if (constLabData.hasParameters()) {
            addParamInfoPaneToLab(panel, constraints, 2, constLabData.getParamInputs());
        } else {
            addEmptyTitledPaneToLab(panel, constraints, 2, "Parameters", "None");
        }
        addBtnPaneToLab(panel, constraints, 3, "Cancel", constLabData.getActionVerb());
        return panel;
    }
    private Component createLabForMethod(LabData labData, JPanel panel, GridBagConstraints constraints) {
        MethodLabData methodLabData = (MethodLabData)labData;
        addTitleToPane(panel, constraints, 0, "Invoke the \'" + methodLabData.getMethodName() + "\' method.");
        if (methodLabData.getLabType() == LabType.STATIC_METHOD) {
            addStaticMethodInfoPaneToLab(panel, constraints, 1, methodLabData.getSimpleDeclaredClassName(), methodLabData.getMethodName(), methodLabData.getModifiers());
        } else if (methodLabData.getLabType() == LabType.NON_STATIC_METHOD) {
            addNonStaticMethodInfoPaneToLab(panel, constraints, 1, methodLabData.getVariable(), methodLabData.getMethodName(), methodLabData.getModifiers());        
        } else {
            throw new IllegalStateException("Something wrong happened.");
        }
        if (methodLabData.hasReturnType()) {
            addNewVariableInfoPaneToLab(panel, constraints, 2, methodLabData.getReturnTypeSimpleName(), methodLabData.getNewVariableNameInput().getInputComponents()[0]);
        } else {
            addEmptyTitledPaneToLab(panel, constraints, 2, "Return Info", "void");
        }
        if (methodLabData.hasParameters()) {
            addParamInfoPaneToLab(panel, constraints, 3, methodLabData.getParamInputs());
        } else {
            addEmptyTitledPaneToLab(panel, constraints, 3, "Parameters", "None");
        }
        addBtnPaneToLab(panel, constraints, 4, "Cancel", methodLabData.getActionVerb());
        return panel;
    }
    private Component createLabForNewArray(LabData labData, JPanel panel, GridBagConstraints constraints) {
        ArrayLabData arrayLabData = (ArrayLabData)labData;
        addTitleToPane(panel, constraints, 0, "Create new \'" + arrayLabData.getSimpleArrayTypeName() + "\' array.");
        addNewArrayInfoPaneToLab(panel, constraints, 1, arrayLabData.getSimpleArrayTypeName(), arrayLabData.getNewVariableNameInput().getInputComponents()[0], arrayLabData.getArrayLengthInput().getInputComponents()[0]);
        addBtnPaneToLab(panel, constraints, 2, "Cancel", arrayLabData.getActionVerb());
        return panel;
    }
    private Component createLabForPrimitive(LabData labData, JPanel panel, GridBagConstraints constraints) {
        VariableLabData primitiveLabData = (VariableLabData)labData;
        addTitleToPane(panel, constraints, 0, primitiveLabData.getActionVerb());
        if (primitiveLabData.createsNewValue()) {
            addNewVariableInfoPaneToLab(panel, constraints, 1, primitiveLabData.getSimplePrimitiveTypeName(), primitiveLabData.getNewVariableNameInput().getInputComponents()[0]);
        } else {
            addExistingVariableInfoPaneToLab(panel, constraints, 1, primitiveLabData.getSimplePrimitiveTypeName(), primitiveLabData.getVariable().toString(), primitiveLabData.getVariable().getSimpleValueName());
        }
        addNewValueInfoPaneToLab(panel, constraints, 2, primitiveLabData.getNewValueInput());
        addBtnPaneToLab(panel, constraints, 3, "Cancel", primitiveLabData.getActionVerb());
        return panel;
    }
    private void addTitleToPane(JPanel pane, GridBagConstraints constraints, int gridy, String title) {
        JLabel titleLabel = new JLabel(title);
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        constraints.gridy = gridy;
        pane.add(titleLabel, constraints);        
    }
    private void addStaticMethodInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String declaredClassName, String methodName, String modifiers) {
        String[] strLabels = {"Declared class", "Method"};
        Component[] components = {new JLabel(declaredClassName), new JLabel(modifiers + " " + methodName)};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "Method Info", strLabels, components);
    }
    private void addNonStaticMethodInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, Variable variable, String methodName, String modifiers) {
        String[] strLabels = {"Variable", "Method"};
        Component[] components = {new JLabel(variable.getSimpleTypeName() + " " + variable.getName()), new JLabel(modifiers + " " + methodName)};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "Method Info", strLabels, components);
    }
    private void addStaticFieldInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String declaredClassName, String fieldTypeName, String fieldName, String modifiers) {
        String[] strLabels = {"Declared class", "Modifiers", "Field type", "Field name"};
        Component[] components = {new JLabel(declaredClassName), new JLabel(modifiers), new JLabel(fieldTypeName), new JLabel(fieldName)};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "Field Info", strLabels, components);
    }
    private void addNonStaticFieldInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, Variable variable, String fieldTypeName, String fieldName, String modifiers) {
        String[] strLabels = {"Variable", "Modifiers", "Field type", "Field name"};
        Component[] components = {new JLabel(variable.getSimpleTypeName() + " " + variable.getName()), new JLabel(modifiers), new JLabel(fieldTypeName), new JLabel(fieldName)};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "Field Info", strLabels, components);
    }
    private void addNewVariableInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String typeName, Component newVariableNameComponent) {
        String[] strLabels = {"Type", "New variable name"};
        Component[] components = {new JLabel(typeName), newVariableNameComponent};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "New Variable Info", strLabels, components);
    }
    private void addExistingVariableInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String typeName, String variableName, String currentValueStr) {
        String[] strLabels = {"Type", "Variable name", "Current value"};
        Component[] components = {new JLabel(typeName), new JLabel(variableName), new JLabel(currentValueStr)};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "Current Variable Info", strLabels, components);
    }
    private void addNewValueInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, LabInput newValueInput) {
        String[] strLabels = {newValueInput.getSimpleTypeName()};
        Component[] components = {createLinedInput(newValueInput.getInputComponents())};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "New Value", strLabels, components);
    }
    private void addEmptyTitledPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String title, String message) {
        JPanel pane = ComponentTools.createTitledPane(title, message, PANEL_BG_COLOR);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        constraints.gridy = gridy;
        constraints.weighty = 1.0;
        labPane.add(pane, constraints);    
    }
    private void addParamInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, LabInput[] paramInputs) {
        String[] strLabelsForParameters = new String[paramInputs.length];
        Component[] componentsForParameters = new Component[paramInputs.length];
        for (int i = 0; i < strLabelsForParameters.length; i++) {
            strLabelsForParameters[i] = paramInputs[i].getSimpleTypeName();
            componentsForParameters[i] = createLinedInput(paramInputs[i].getInputComponents());
        }
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "Parameters", strLabelsForParameters, componentsForParameters);
    }
    private void addModificationPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String currentValueStr, LabInput newValueInput) {
        String[] strLabels = {"Before", "After"};
        Component[] components = {new JLabel(currentValueStr), createLinedInput(newValueInput.getInputComponents())};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "Values", strLabels, components);
    }
    private void addNewArrayInfoPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String arrayTypeName, Component newVariableNameComponent, Component arrayLengthComponent) {
        String[] strLabels = {"Array type", "New variable name", "Array length"};
        Component[] components = {new JLabel(arrayTypeName + "[]"), newVariableNameComponent, arrayLengthComponent};
        addDoubleColumnTitledPaneToLab(labPane, constraints, gridy, "New Array Info", strLabels, components);
    }
    private void addDoubleColumnTitledPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String title, String[] left, Component[] right) {
        JPanel paramInfo = ComponentTools.createDoubleColumnTitledPane(title, left, right, PANEL_BG_COLOR);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridwidth = 3;
        constraints.gridy = gridy;
        constraints.weighty = 1.0;
        labPane.add(paramInfo, constraints);    
    }
    private void addBtnPaneToLab(JPanel labPane, GridBagConstraints constraints, int gridy, String cancelTxt, String proceedTxt) {
        JPanel buttonPane = createLabButtonPane();
        buttonPane.setBackground(PANEL_BG_COLOR);
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.PAGE_END;
        GridBagConstraints btnConstraints = new GridBagConstraints();
        btnConstraints.fill = GridBagConstraints.HORIZONTAL;

        Button cancelBtn = new Button(cancelTxt);
        btnConstraints.fill = GridBagConstraints.NONE;
        btnConstraints.gridx = 0;       //aligned with button 2
        btnConstraints.gridwidth = 1;   //2 columns wide
        btnConstraints.gridy = 0;       //third row
        buttonPane.add(cancelBtn, btnConstraints);
        cancelBtn.addActionListener(cancelBtnInLabActionListener);
        
        Button applyBtn = new Button(proceedTxt);
        btnConstraints.fill = GridBagConstraints.NONE;
        btnConstraints.gridx = 1;       //aligned with button 2
        btnConstraints.gridwidth = 1;   //2 columns wide
        btnConstraints.gridy = 0;       //third row
        buttonPane.add(applyBtn, btnConstraints);
        applyBtn.addActionListener(applyBtnInLabActionListener);

        labPane.add(buttonPane, constraints);
    }
    private void addBtnPaneToInfo(JPanel infoPane, GridBagConstraints constraints, int gridy, Info info) {
        JPanel buttonPane = createLabButtonPane();
        buttonPane.setBackground(PANEL_BG_COLOR);
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.PAGE_END;
        GridBagConstraints btnConstraints = new GridBagConstraints();
        btnConstraints.fill = GridBagConstraints.HORIZONTAL;
        
        Button[] buttons = createButtonsForInfo(info);
        for (int i = 0; i < buttons.length; i++) {
            btnConstraints.fill = GridBagConstraints.NONE;
            btnConstraints.gridx = 0;
            btnConstraints.gridwidth = 1;
            btnConstraints.gridy = i;
            buttonPane.add(buttons[i], btnConstraints);
        }
        infoPane.add(buttonPane, constraints);
    }
    private Button[] createButtonsForInfo(Info info) {
        Button[] buttons = null;
        switch(info.getInfoType()) {
        case CLASS:
        case PRIMITIVE_TYPE:
            buttons = new Button[2];
            Button variableBtn = new Button("Create variable");
            variableBtn.addActionListener(createVariableBtnInInfoActionListener);
            buttons[0] = variableBtn;
            Button arrayBtn = new Button("Create array");
            arrayBtn.addActionListener(createArrayBtnInInfoActionListener);
            buttons[1] = arrayBtn;
            break;
        case OBJECT_VARIABLE:
        case PRIMITIVE_VARIABLE:
        case NULL:
            buttons = new Button[1];
            Button changeValueBtn = new Button("Change the value");
            changeValueBtn.addActionListener(changeValueBtnInInfoActionListener);
            buttons[0] = changeValueBtn;
            break;
        default:
            throw new IllegalArgumentException("Something wrong happened.");
        }
        return buttons;
    }
    private JPanel createLabButtonPane() {
        JPanel panel = new JPanel(new GridBagLayout());
        return panel;
    }
    private Component createLinedInput(Component[] components) {
        if (components.length == 1) {
            return components[0];
        }
        JPanel panel = new JPanel();
        panel.setBackground(PANEL_BG_COLOR);
        for (Component component : components) {
            panel.add(component);
        }
        return panel;
    }
    
    
    
    /**
     * Return a tree with only one node.
     */
    private JTree createClassTreeWithMap(Map<String, List<Class<?>>> map) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        for (String key : map.keySet()) {
            DefaultMutableTreeNode keyNode = new DefaultMutableTreeNode(key);
            root.add(keyNode);
     
            for (Class<?> cls : map.get(key)) {
                keyNode.add(new DefaultMutableTreeNode(cls));
            }
        }
        JTree tree = ComponentTools.initializeTree(root);
        tree.addMouseListener(treeMouseListener);
        return tree;
    }
    /**
     * Return a tree with only one node.
     */
    private JTree createVariableTreeWithMap(Map<String, List<Variable>> map) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        for (String key : map.keySet()) {
            DefaultMutableTreeNode keyNode = new DefaultMutableTreeNode(key);
            root.add(keyNode);
     
            for (Variable variable : map.get(key)) {
                if (variable.isArray()) {
                    DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(variable);
                    keyNode.add(arrayNode);
                    
                    for (Variable element : variable.getArrayElements()) {
                        arrayNode.add(new DefaultMutableTreeNode(element));
                    }
                } else {
                    keyNode.add(new DefaultMutableTreeNode(variable));
                }
            }
        }
        JTree tree = ComponentTools.initializeTree(root);
        tree.addMouseListener(treeMouseListener);
        return tree;
    }
    private JTable createFieldTable(Field[] fields, Object object) throws IllegalArgumentException, IllegalAccessException {
        Object[][] fieldData = new Object[fields.length][FIELD_COLUMNS.length];
        for (int i = 0; i < fields.length; i++) {
            fieldData[i][0] = ReflectionTools.stringifyModifiers(fields[i].getModifiers());
            fieldData[i][1] = ReflectionTools.getSimpleName(fields[i].getGenericType());
            fieldData[i][2] = fields[i].getName();
            fieldData[i][3] = ReflectionTools.getFieldValue(fields[i], object);
        }
        return createTable(fieldData, FIELD_COLUMNS);
    }
    private JTable createConstructorTable(Constructor<?>[] constructors) {
        Object[][] constructorData = new Object[constructors.length][CONST_COLUMNS.length];
        for (int i = 0; i < constructors.length; i++) {
            constructorData[i][0] = ReflectionTools.stringifyModifiers(constructors[i].getModifiers());
            constructorData[i][1] = ReflectionTools.getSimpleName(constructors[i].getDeclaringClass());
            constructorData[i][2] = ReflectionTools.stringifyParams(constructors[i].getGenericParameterTypes());
        }
        return createTable(constructorData, CONST_COLUMNS);
    }
    
    private JTable createMethodTable(Method[] methods) {
        Object[][] methodData = new Object[methods.length][METHOD_COLUMNS.length];
        for (int i = 0; i < methods.length; i++) {
            methodData[i][0] = ReflectionTools.stringifyModifiers(methods[i].getModifiers());
            methodData[i][1] = ReflectionTools.getSimpleName(methods[i].getGenericReturnType());
            methodData[i][2] = methods[i].getName();
            methodData[i][3] = ReflectionTools.stringifyParams(methods[i].getGenericParameterTypes());
        }
        return createTable(methodData, METHOD_COLUMNS);
    }
    private JTable createTable(Object[][] data, String[] columns) {
        JTable table = new JTable(new InterpretTableModel(data, columns));
        table.setPreferredScrollableViewportSize(DEFAULT_COLUMN_PANE_SIZE);
        table.setFillsViewportHeight(true);
        table.addMouseListener(tableMouseListener);
        return table;
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
    
}
