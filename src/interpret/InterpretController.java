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
    private Variable destObjectVariable;
    
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
        ActionListener[] btnActionListeners = {new CreateVariableBtnInInfoActionListener(), new CreateArrayBtnInInfoActionListener(), new ChangeValueBtnInInfoActionListener(), new ApplyBtnInLabActionListener(), new CancelBtnInLabActionListener()};
        view.init(classCollector.map(), treeMouseListener, tableMouseListener, btnActionListeners);
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
        outputUserChoiceOnHistory(variable.toString());
        clearDataOnCenterPane();
        variableOnCenterPane = variable;
        if (variable.getType().isPrimitive()) {
            changeCenterPaneForPrimitiveVariable(variable);
        } else if (variable.isNull()) {
            changeCenterPaneForNullVariable(variable);
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
    
    private void changeCenterPaneForNullVariable(Variable variable) {
        infoOnCenterPane = Info.create(InfoType.NULL, variable.getType(), variable);
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
        if (variable.getType().isArray()) {
            fieldsOnCenterPane = null;
            methodsOnCenterPane = null;
        } else {
            fieldsOnCenterPane = ReflectionTools.getFields(variable.getType());
            methodsOnCenterPane = ReflectionTools.getMethods(variable.getType());
        }
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
        destObjectVariable = null;
    }

    public class CreateVariableBtnInInfoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            onCreateVariableBtnPressed(clsOnCenterPane);
        }
    }
    private void onCreateVariableBtnPressed(Class<?> cls) {
        if (cls.isPrimitive()) {
            changeRightPaneToCreatePrimitiveValue(cls);
        } else {
            view.changeCenterPanePage(2);
            JOptionPane.showMessageDialog(view.getFrame(), "Choose a constructor.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public class CreateArrayBtnInInfoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            onCreateArrayBtnPressed(clsOnCenterPane);
        }
    }
    private void onCreateArrayBtnPressed(Class<?> cls) {
        changeRightPaneForNewArray(cls);
    }
    public class ChangeValueBtnInInfoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            onChangeValueBtnPressed(variableOnCenterPane);
        }
    }
    private void onChangeValueBtnPressed(Variable variable) {
        if (variable.getType().isPrimitive()) {
            changeRightPaneToChangePrimitiveValue(variable);
        } else {
            destObjectVariable = variable;
            changeCenterPaneForClass(variable.getType());
            view.changeCenterPanePage(2);
            JOptionPane.showMessageDialog(view.getFrame(), "Choose a constructor.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public class TableMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getClickCount() != 2) {
                return;
            }
            JTable table = (JTable) e.getSource();
            int tableIndex = table.rowAtPoint(e.getPoint());
            if (table == view.getFieldTable()) {
                onFieldSelected(fieldsOnCenterPane[tableIndex]);
            } else if (table == view.getConstructorTable()) {
                changeRightPaneForConstructor(tableIndex);
            } else if (table == view.getMethodTable()) {
                changeRightPaneForMethod(tableIndex);
            } else {
                throw new IllegalStateException("Double click reaction for this table has not been implemented yet.");
            }
            
        }
    }
    private void onFieldSelected(Field field) {
        if (canChangeRightPaneForField(field)) {
            changeRightPaneForField(field);
        } else {
            String message = "You cannot change this field.";
            JOptionPane.showMessageDialog(view.getFrame(), message, "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void changeRightPaneForNewArray(Class<?> cls) {
        outputUserChoiceOnHistory("to create array of " + ReflectionTools.getSimpleName(cls) + ".");
        labDataOnRightPane = new ArrayLabData(cls);
        view.changeRightPane(labDataOnRightPane);
    }
    private void changeRightPaneToCreatePrimitiveValue(Class<?> cls) {
        outputUserChoiceOnHistory("to create new " + ReflectionTools.getSimpleName(cls) + " variable.");
        labDataOnRightPane = new VariableLabData(cls, LabInput.create(cls, getVariableOptions(cls)));
        view.changeRightPane(labDataOnRightPane);
    }
    private void changeRightPaneToChangePrimitiveValue(Variable variable) {
        outputUserChoiceOnHistory("to change the value of " + variable.toString() + ".");
        labDataOnRightPane = new VariableLabData(variable, LabInput.create(variable.getType(), getVariableOptions(variable.getType())));
        view.changeRightPane(labDataOnRightPane);
    }
    private boolean canChangeRightPaneForField(Field field) {
          return true;
  //      int modifierType = field.getModifiers();
  //      return !(Modifier.isStatic(modifierType) && Modifier.isFinal(modifierType));
    }
    private void changeRightPaneForField(Field field) {
        outputUserChoiceOnHistory(field.getName());
        if (Modifier.isStatic(field.getModifiers())) {
            labDataOnRightPane = new FieldLabData(field, LabInput.create(field.getType(), getVariableOptions(field.getType())));
        } else {
            labDataOnRightPane = new FieldLabData(variableOnCenterPane, field, LabInput.create(field.getType(), getVariableOptions(field.getType())));
        }
        view.changeRightPane(labDataOnRightPane);
    }
    private void changeRightPaneForConstructor(int tableIndex) {
        Constructor<?> constructor = constructorsOnCenterPane[tableIndex];
        if (destObjectVariable == null) {
            outputUserChoiceOnHistory(ReflectionTools.getSimpleName(constructor.getDeclaringClass()));
            labDataOnRightPane = new ConstructorLabData(constructor, createParamInputs(constructor.getParameterTypes()));
        } else {
            outputUserChoiceOnHistory(ReflectionTools.getSimpleName(constructor.getDeclaringClass()) + "to change " + destObjectVariable.toString());
            labDataOnRightPane = new ConstructorLabData(constructor, destObjectVariable, createParamInputs(constructor.getParameterTypes()));
        }
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
        List<Variable> list = variableMap.get(cls.isPrimitive() ? Variable.PATH_NAME_FOR_PRIMITIVE_TYPES : cls.getCanonicalName());
        if (list == null) {
            return new Variable[0];
        } else {
            return list.toArray(new Variable[list.size()]);
        }
    }

    private void outputUserChoiceOnHistory(String choice) {
        System.out.println("You chose " + choice + ".");
    }
    
    public class ApplyBtnInLabActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            destObjectVariable = null;
            invokeLabData();
        }
    }
    public class CancelBtnInLabActionListener implements ActionListener {
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
                    onInvokeSuccess(labDataOnRightPane.getActionVerb());
                } else {
                    onInvokeSuccess(variable);
                }
            } catch (InterpretException e1) {
                String message = "Exception occurred!\nSee Console for details.";
                e1.getCause().printStackTrace();
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
        String message = "You created new variable!" + 
                "\nType: " + variable.getSimpleTypeName() + 
                "\nVariable name: " + variable.getName() + 
                "\ntoString: " + variable.getValue().toString();
        System.out.println("Added the variable " + variable.getName() + " to Variable Tree.");
        JOptionPane.showMessageDialog(view.getFrame(), message, "Success", JOptionPane.PLAIN_MESSAGE);
        labDataOnRightPane = null;
        view.clearCenterPane();
        view.changeRightPane(MSG_RIGHT_PANE);
    }
    private void onInvokeSuccess(String actionVerb){
        String message = "Succeeded to " + actionVerb.toLowerCase() + "!";
        System.out.println(message);
        JOptionPane.showMessageDialog(view.getFrame(), message, "Success", JOptionPane.PLAIN_MESSAGE);
        labDataOnRightPane = null;
        view.clearCenterPane();
        view.changeRightPane(MSG_RIGHT_PANE);
    }
    
    private void addVariable(Variable variable) {
        String pathName = variable.getPathName();
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
