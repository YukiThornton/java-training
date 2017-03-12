package interpret;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class Interpret {

    private static final Dimension MIN_FRAME_SIZE = new Dimension(500, 300);
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(1000, 800);
    private static JFrame frame;
    
    private static boolean membersAreFromClassTree = true;
    private static JTable methodTable;
    private static JTable constructorTable;
    private static Method[] shownMethods;
    private static Constructor<?>[] shownConstructors;
    
    private static Member currentMemberOnLab = null;
    private static Object currentObjectOnLab = null;
    private static UserVariable recentlyCreatedVariable = null;
    
    private static JTree classTree;
    private static JTree variableTree;
    private static Map<String, List<UserVariable>> variableMap= new HashMap<>();
    
    private static JPanel memberPane;
    private static JPanel explorerPane;
    private static JPanel leftPane;
    private static JPanel rightPane;
    private static JPanel labPane;
    private static JTable fieldTable;

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
        frame = new JFrame("Interpret");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
        frame.setPreferredSize(DEFAULT_FRAME_SIZE);
        frame.setMinimumSize(MIN_FRAME_SIZE);
         
        //Add contents to the frame.
        variableTree = ComponentFactory.createTreeWithSingleNode();
        leftPane = new JPanel();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        setExplorerPane();
        setMemberPane(Object.class, new Object());
        leftPane.setMaximumSize(new Dimension(300, 1000));
        frame.add(leftPane);
        
        rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        try {
            currentMemberOnLab = Object.class.getMethod("toString");
            setBlankLabPane();
            
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
     
    private static void setExplorerPane() {
        if (explorerPane != null) {
            leftPane.remove(explorerPane);
        }
        setClassTree();
        Component instanceScrollPane = null;
        if (variableMap.size() == 0) {
            instanceScrollPane = makeTextPanel("No variable yet.");
        } else {
            variableTree = ComponentFactory.loadTreeWithMap(variableTree, variableMap);
            variableTree.addTreeSelectionListener(new TreeSelectionListener() {
                
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)variableTree.getLastSelectedPathComponent();
                    if (node == null) {
                        return;
                    }
                    if (!node.isLeaf()) {
                        return;
                    }
                    membersAreFromClassTree = false;
                    currentObjectOnLab = ((UserVariable)node.getUserObject()).getObject();
                    setMemberPane(((UserVariable)node.getUserObject()).getType(), currentObjectOnLab);
                }
            });
            instanceScrollPane = ComponentFactory.createJScrollPane(variableTree);
        }
        JScrollPane classScrollPane = ComponentFactory.createJScrollPane(classTree);
        explorerPane = ComponentFactory.createClassExplorerPane(classScrollPane, instanceScrollPane);
        leftPane.add(explorerPane, 0);
        leftPane.validate();
        leftPane.repaint();
    }

    /**
     * Set a new member panel of the specified class.
     */
    private static void setMemberPane(Class<?> cls, Object instance) {
        if (memberPane != null) {
            leftPane.remove(memberPane);
        }

        try {
            setFieldTable(cls, instance);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setConstructorTable(cls);
        setMethodTable(cls);
        Component constScrollPane = ComponentFactory.createJScrollPane(constructorTable);
        Component methodScrollPane = ComponentFactory.createJScrollPane(methodTable);
        memberPane = ComponentFactory.createMemberPane(makeTextPanel("general"), makeTextPanel("nested"), ComponentFactory.createJScrollPane(fieldTable), constScrollPane, methodScrollPane);
        leftPane.add(memberPane);
        leftPane.validate();
        leftPane.repaint();
    }
    
    private static void onInvokingMethodSelected(int index) {
        Method method = shownMethods[index];
        if (membersAreFromClassTree) {
            if (Modifier.isStatic(method.getModifiers())) {
                currentMemberOnLab = method;
                setLabPane(ComponentFactory.createLabTabPane(currentMemberOnLab, null));
            } else {
                JOptionPane.showMessageDialog(frame, "Choose an object from Variable Tree to invoke non-static methods.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            currentMemberOnLab = method;
            setLabPane(ComponentFactory.createLabTabPane(currentMemberOnLab, null));
        }
    }
    
    private static void onInvokingConstructorSelected(int index) {
        Constructor<?> constructor = shownConstructors[index];
        if (membersAreFromClassTree) {
            currentMemberOnLab = constructor;
            setLabPane(ComponentFactory.createLabTabPane(constructor, null));
        } else {
            JOptionPane.showMessageDialog(frame, "Choose a class from Class Tree to invoke constructors.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // todo; constructor, static 
    private static void setLabPane(JPanel labTab) {
        if (labPane != null) {
            rightPane.remove(labPane);
        }
        labPane = ComponentFactory.createLabPane(labTab);
        rightPane.add(labPane, 0);
        rightPane.validate();
        rightPane.repaint();
    }
    
    private static void setBlankLabPane() {
        if (labPane != null) {
            rightPane.remove(labPane);
        }
        JTextField dropZone = ComponentFactory.createUneditableTextField("Double click on a method/constructor.");
        //dropZone.setTransferHandler(new InterpretTransferHandler());
        labPane = ComponentFactory.createLabPane(dropZone);
        rightPane.add(labPane, 0);
        rightPane.validate();
        rightPane.repaint();
        
    }
    
    /**
     * Set a new class tree of all the classes,
     * and add function on it.
     */
    private static void setClassTree() {
        try {
            classTree = ComponentFactory.createPackageTree();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
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
                membersAreFromClassTree = true;
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
        shownMethods = InterpretTools.getAllMethods(cls);
        methodTable = ComponentFactory.createMethodTable(shownMethods);
        methodTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (e.getSource() == methodTable) {
                        int row = methodTable.rowAtPoint(e.getPoint());
                        System.out.println("double" + methodTable.getValueAt(row, 2));
                        onInvokingMethodSelected(row);
                    }
                    
                }
            }
        });
        //methodTable.setDragEnabled(true);
        //System.out.println(methodTable.getTransferHandler());
        //methodTable.setTransferHandler(new InterpretTransferHandler());

    }
    
    /**
     * Set a new constructor table with specified class,
     * and add function on it.
     */
    private static void setConstructorTable(Class<?> cls) {
        shownConstructors = InterpretTools.getDeclaredConstructors(cls);
        constructorTable = ComponentFactory.createConstructorTable(shownConstructors);
        constructorTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (e.getSource() == constructorTable) {
                        int row = constructorTable.rowAtPoint(e.getPoint());
                        System.out.println("double" + constructorTable.getValueAt(row, 2));
                        onInvokingConstructorSelected(row);
                    }
                    
                }
            }
        });
        //methodTable.setDragEnabled(true);
        //System.out.println(methodTable.getTransferHandler());
        //methodTable.setTransferHandler(new InterpretTransferHandler());

    }
    
    private static void addUserVariable() {
        UserVariable userVariable = null;
        String userVariableName = ComponentFactory.getReturnVariableName();
        if (currentMemberOnLab instanceof Method) {
            Method methodToInvoke = (Method)currentMemberOnLab;
            try {
                if (methodToInvoke.getParameterCount() == 0) {
                    userVariable = new UserVariable(methodToInvoke.invoke(membersAreFromClassTree ? null: currentObjectOnLab), methodToInvoke, userVariableName);
                } else {
                    userVariable = new UserVariable(methodToInvoke.invoke(membersAreFromClassTree ? null: currentObjectOnLab, ComponentFactory.getLabParamInputArray()), methodToInvoke, userVariableName);
                }
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        } else if (currentMemberOnLab instanceof Constructor<?>) {
            Constructor<?> constToInvoke = (Constructor<?>)currentMemberOnLab;
            try {
                if (constToInvoke.getParameterCount() == 0) {
                    userVariable = new UserVariable(constToInvoke.newInstance(), constToInvoke, userVariableName);
                } else {
                    userVariable = new UserVariable(constToInvoke.newInstance(ComponentFactory.getLabParamInputArray()), constToInvoke, userVariableName);
                }
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("Something wrong happened in addUserVariable().");
        }
        String nodeName = userVariable.getNonSimpleTypeName();
        if (variableMap.containsKey(nodeName)) {
            variableMap.get(nodeName).add(userVariable);
        } else {
            List<UserVariable> list = new ArrayList<>();
            list.add(userVariable);
            variableMap.put(nodeName, list);
        }
        recentlyCreatedVariable = userVariable;
    
    }
    
    static class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(e.getSource() instanceof Button)) {
                System.out.println("something wrong in ButtonActionListener");
                return;
            }
            Button selectedButton = (Button)e.getSource();
            switch (selectedButton.getLabel()) {
            case "invoke":
                String checkResult = ComponentFactory.checkAndColllectLabInput();
                if (checkResult != null) {
                    JOptionPane.showMessageDialog(frame, checkResult, "Invalid input", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                addUserVariable();
                setExplorerPane();
                ((JTabbedPane)explorerPane.getComponent(0)).setSelectedIndex(1);
                String message = "You created a new variable!" + 
                        "\nType: " + recentlyCreatedVariable.getSimpleTypeName() + 
                        "\nVariable name: " + recentlyCreatedVariable.getName() + 
                        "\ntoString: " + recentlyCreatedVariable.getObject().toString();
                JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.PLAIN_MESSAGE);
                setBlankLabPane();
                leftPane.validate();
                leftPane.repaint();
                break;
                
            case "cancel":
                setBlankLabPane();
                break;

            default:
                System.out.println("unexpectedValue in ButtonActionListener");
                break;
            }
        }
    
    }
    /*
    static class InterpretTransferHandler extends TransferHandler {
        @Override
        public void exportAsDrag(JComponent comp, InputEvent e, int action) {
            super.exportAsDrag(comp, e, action);
            System.out.println("drag");
        }
        @Override
        public boolean importData(JComponent comp, Transferable t) {
            // TODO Auto-generated method stub
            boolean result = super.importData(comp, t);
            System.out.println("drop");
            System.out.println(comp);
            System.out.println(t);
            return result;
        }
        
        
    }
    */
}
