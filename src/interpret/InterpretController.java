package interpret;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;

import interpret.Info.InfoType;

public class InterpretController {
    private static final String MSG_RIGHT_PANE = "Double click on a member.";
    private InterpretView view;
    private ClassCollector classCollector;
    
    private Map<String, List<Variable>> variableMap = new HashMap<>();
    
    private Class<?> clsOnCenterPane;
    private Variable variableOnCenterPane;
    private Info infoOnCenterPane;
    private Field[] fieldsOnCenterPane;
    private Constructor<?>[] constructorsOnCenterPane;
    private Method[] methodsOnCenterPane;
    
    private LabData labDataOnRightPane;
    
    public InterpretController(ClassCollector classCollector) {
        this.classCollector = classCollector;
        view = new InterpretView();
    }
    
    public void start() {
        if (view == null) {
            throw new IllegalStateException("view is null.");
        }
        TreeMouseListener treeMouseListener = new TreeMouseListener();
        TableMouseListener tableMouseListener = new TableMouseListener();
        ApplyBtnActionListener applyBtnActionListener = new ApplyBtnActionListener();
        CancelBtnActionListener cancelBtnActionListener = new CancelBtnActionListener();
        view.init(classCollector.map(), treeMouseListener, tableMouseListener, applyBtnActionListener, cancelBtnActionListener);
        setStream();
        view.show();
    }
    
    private void setStream() {
        JTextAreaStream stream = new JTextAreaStream(view.getHistoryArea());
        PrintStream printStream = new PrintStream(stream, true);
        System.setOut(printStream);
        System.setErr(printStream);  
    }
    
    public class TreeMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            boolean isClassTree = true;
            DefaultMutableTreeNode node;
            if (e.getSource() == view.getClassTree()) {
                node = (DefaultMutableTreeNode) view.getClassTree().getLastSelectedPathComponent();
            } else if (e.getSource() == view.getVariableTree()) {
                isClassTree = false;
                node = (DefaultMutableTreeNode) view.getVariableTree().getLastSelectedPathComponent();
            } else {
                throw new IllegalStateException("Something wrong happened!");
            }
            if (node == null) {
                return;
            }
            if (!node.isLeaf()) {
                return;
            }
            if (e.getClickCount() != 2) {
                return;
            }
            if (isClassTree) {
                onClassSelected((Class<?>)node.getUserObject());
            } else {
                onVariableSelected((Variable)node.getUserObject());
            }
        
        }
    }
    private void onClassSelected(Class<?> cls) {
        outputUserChoiceOnHistory(cls.getCanonicalName());
        clearDataOnCenterPane();
        clsOnCenterPane = cls;
        if (cls.isPrimitive()) {
            changeCenterPaneForPrimitiveClass(cls);
        } else {
            changeCenterPaneForClass(cls);
        }    
    }
    private void onVariableSelected(Variable variable) {
        outputUserChoiceOnHistory(variable.getName());
        clearDataOnCenterPane();
        variableOnCenterPane = variable;
        if (variable.getType().isPrimitive()) {
            changeCenterPaneForPrimitiveVariable(variable);
        } else {
            changeCenterPaneForObjectVariable(variable);
        }
    }
        
    private void changeCenterPaneForPrimitiveClass(Class<?> cls) {
        infoOnCenterPane = Info.create(InfoType.PRIMITIVE_TYPE, cls, null);
        try {
            view.changeCenterPane(infoOnCenterPane, null, null, null);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        view.changeRightPane("");
    }
    
    private void changeCenterPaneForPrimitiveVariable(Variable variable) {
        infoOnCenterPane = Info.create(InfoType.PRIMITIVE_VARIABLE, variable.getType(), variable);
        try {
            view.changeCenterPane(infoOnCenterPane, null, null, null);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        view.changeRightPane("");
    }
    
    private void changeCenterPaneForClass(Class<?> cls){
        infoOnCenterPane = Info.create(InfoType.CLASS, cls, null);
        fieldsOnCenterPane = ReflectionTools.getStaticFields(cls);
        constructorsOnCenterPane = ReflectionTools.getDeclaredConstructors(cls);
        methodsOnCenterPane = ReflectionTools.getStaticMethods(cls);
        try {
            view.changeCenterPane(infoOnCenterPane, fieldsOnCenterPane, constructorsOnCenterPane, methodsOnCenterPane);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        view.changeRightPane(MSG_RIGHT_PANE);
    }
    private void changeCenterPaneForObjectVariable(Variable variable){
        infoOnCenterPane = Info.create(InfoType.OBJECT_VARIABLE, variable.getType(), variable);
        fieldsOnCenterPane = ReflectionTools.getFields(variable.getType());
        methodsOnCenterPane = ReflectionTools.getMethods(variable.getType());
        try {
            view.changeCenterPane(infoOnCenterPane, fieldsOnCenterPane, constructorsOnCenterPane, methodsOnCenterPane);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        view.changeRightPane(MSG_RIGHT_PANE);
    }
    private void clearDataOnCenterPane() {
        clsOnCenterPane = null;
        variableOnCenterPane = null;
        infoOnCenterPane = null;
        fieldsOnCenterPane = null;
        constructorsOnCenterPane = null;
        methodsOnCenterPane = null;
    }

    public class TableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getClickCount() != 2) {
                return;
            }
            JTable table = (JTable) e.getSource();
            int tableIndex = table.rowAtPoint(e.getPoint());
            if (table == view.getFieldTable()) {
                changeRightPaneForField(tableIndex);
            } else if (table == view.getConstructorTable()) {
                changeRightPaneForConstructor(tableIndex);
            } else if (table == view.getMethodTable()) {
                changeRightPaneForMethod(tableIndex);
            } else {
                throw new IllegalStateException("Double click reaction for this table has not been implemented yet.");
            }
            
        }
    }
    private void changeRightPaneForField(int tableIndex) {
        Field field = fieldsOnCenterPane[tableIndex];
        outputUserChoiceOnHistory(field.getName());
        view.changeRightPane("Field lab.");
    }
    private void changeRightPaneForConstructor(int tableIndex) {
        Constructor<?> constructor = constructorsOnCenterPane[tableIndex];
        outputUserChoiceOnHistory(ReflectionTools.getSimpleName(constructor.getDeclaringClass()));
        labDataOnRightPane = new ConstructorLabData(clsOnCenterPane, constructor, createParamInputs(constructor.getParameterTypes()));
        view.changeRightPane(labDataOnRightPane);
    }
    private void changeRightPaneForMethod(int tableIndex) {
        Method method = methodsOnCenterPane[tableIndex];
        outputUserChoiceOnHistory(method.getName());
        if (Modifier.isStatic(method.getModifiers())) {
            labDataOnRightPane = new MethodLabData(clsOnCenterPane, method, createParamInputs(method.getParameterTypes()));
        } else {
            labDataOnRightPane = new MethodLabData(variableOnCenterPane, method, createParamInputs(method.getParameterTypes()));
        }
        view.changeRightPane(labDataOnRightPane);
    }
    
    private LabInput[] createParamInputs(Class<?>[] paramTypes) {
        LabInput[] labInputs = new LabInput[paramTypes.length];
        for (int i = 0; i < labInputs.length; i++) {
            labInputs[i] = LabInput.create(paramTypes[i], getVariableOptions(paramTypes[i]));
        }
        return labInputs;
    }
    
    private Variable[] getVariableOptions(Class<?> cls) {
        List<Variable> list = variableMap.get(cls.getCanonicalName());
        if (list == null) {
            return new Variable[0];
        } else {
            return list.toArray(new Variable[list.size()]);
        }
    }

    private void outputUserChoiceOnHistory(String choice) {
        System.out.println("You chose " + choice + ".");
    }
    
    public class ApplyBtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            invokeLabData();
        }
    }
    public class CancelBtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("cancel");
            labDataOnRightPane = null;
            view.changeRightPane(MSG_RIGHT_PANE);
        }
    }
    private void invokeLabData() {
        if (validateLabInput()){
            try {
                Variable variable = labDataOnRightPane.invoke();
                if (variable == null) {
                    onInvokeSuccess();
                } else {
                    onInvokeSuccess(variable);
                }
            } catch (InterpretException e1) {
                String message = "Exception occurred!\nSee Console for details.";
                e1.printStackTrace();
                JOptionPane.showMessageDialog(view.getFrame(), message, "Failed", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            String message = "Invalid input.\nMake sure you enter all the fields correctly.";
            JOptionPane.showMessageDialog(view.getFrame(), message, "Failed", JOptionPane.WARNING_MESSAGE);
        }
    }
    private boolean validateLabInput() {
        return labDataOnRightPane.validate();
    }
    private void onInvokeSuccess(Variable variable){
        addVariable(variable);
        String message = "You created a new variable!" + 
                "\nType: " + variable.getSimpleTypeName() + 
                "\nVariable name: " + variable.getName() + 
                "\ntoString: " + variable.getValue().toString();
        System.out.println("Added variable " + variable.getName() + " to Variable Tree.");
        JOptionPane.showMessageDialog(view.getFrame(), message, "Success", JOptionPane.PLAIN_MESSAGE);
        labDataOnRightPane = null;
        view.changeRightPane(MSG_RIGHT_PANE);
    }
    private void onInvokeSuccess(){
        String message = "Invoke success!";
        System.out.println("Added no variable to Variable Tree.");
        JOptionPane.showMessageDialog(view.getFrame(), message, "Success", JOptionPane.PLAIN_MESSAGE);
        labDataOnRightPane = null;
        view.changeRightPane(MSG_RIGHT_PANE);
    }
    
    private void addVariable(Variable variable) {
        String pathName = null;
        if (variable.getType().isPrimitive()) {
            pathName = "primitive";
        } else {
            pathName = variable.getPathName();
        }
        List<Variable> list = variableMap.get(pathName);
        if (list == null) {
            List<Variable> newList = new ArrayList<>();
            newList.add(variable);
            variableMap.put(pathName, newList);
        } else {
            list.add(variable);
        }
        view.changeVariableTreeTab(variableMap);
    }
}