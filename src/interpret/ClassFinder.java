package interpret;

import java.awt.*;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
    private ClassLoader classLoader;
    private Map<Package, List<Class<?>>> map;
    private boolean classLoaded = false;
    
    public Package[] getPackages() {
        Package[] keys = map.keySet().toArray(new Package[map.size() + 1]);
        if (Package.getPackage("interpret") != null)
            keys[keys.length - 1] = Package.getPackage("interpret");
        return keys;
    }

    public List<Class<?>> getClasses(Package pack) throws ClassNotFoundException, IOException {
        if (hasLoadableClasses(pack)) {
            return findClasses(pack.getName());
        } else {
            return map.get(pack);
        }
    }
    
    private boolean hasLoadableClasses(Package pack) {
        return pack.getName().equals("interpret") ? true : 
                pack.getName().startsWith("jpl.") ? true : 
                pack.getName().startsWith("gui.") ? true : false;
    }

    public ClassFinder() {
        classLoader = Thread.currentThread().getContextClassLoader();
        loadClasses();
    }
    
    private void loadClasses() {
        if (classLoaded) {
            throw new IllegalStateException("Do not invoke loadClasses twice.");
        }
        classLoaded = true;
        map = new HashMap<>();
        
        // java.lang
        Class<?>[] langArray = {
                Boolean.class, Byte.class, Character.class, Class.class, ClassLoader.class,
                ClassValue.class, Compiler.class, Double.class, Enum.class, Float.class,
                InheritableThreadLocal.class, Integer.class, Long.class, Math.class, Number.class,
                Object.class, Package.class, Process.class, ProcessBuilder.class, Runtime.class,
                RuntimePermission.class, SecurityManager.class, Short.class, StackTraceElement.class,
                StrictMath.class, String.class, StringBuffer.class, StringBuilder.class,
                Thread.class, ThreadGroup.class, ThreadLocal.class, Void.class,
                ArithmeticException.class, ArrayIndexOutOfBoundsException.class, ArrayStoreException.class,
                ClassCastException.class, ClassNotFoundException.class, CloneNotSupportedException.class,
                EnumConstantNotPresentException.class, Exception.class, IllegalAccessException.class,
                IllegalArgumentException.class, IllegalMonitorStateException.class,
                IllegalStateException.class, IllegalThreadStateException.class,
                IndexOutOfBoundsException.class, InstantiationException.class,
                InterruptedException.class, NegativeArraySizeException.class,
                NoSuchFieldException.class, NoSuchMethodException.class,
                NullPointerException.class, NumberFormatException.class,
                ReflectiveOperationException.class, RuntimeException.class,
                SecurityException.class, StringIndexOutOfBoundsException.class,
                TypeNotPresentException.class, UnsupportedOperationException.class,
                };
        map.put(Package.getPackage("java.lang"), Arrays.asList(langArray));
        
        // java.awt
        Class<?>[] awtArray = {
                AlphaComposite.class, AWTEvent.class, AWTEventMulticaster.class, AWTKeyStroke.class,
                AWTPermission.class, BasicStroke.class, BorderLayout.class, BufferCapabilities.class,
                Button.class, Canvas.class, CardLayout.class, Checkbox.class, CheckboxGroup.class,
                CheckboxMenuItem.class, Choice.class, Color.class, Component.class,
                ComponentOrientation.class, Container.class, ContainerOrderFocusTraversalPolicy.class,
                Cursor.class, DefaultFocusTraversalPolicy.class, DefaultKeyboardFocusManager.class,
                Desktop.class, Dialog.class, Dimension.class, DisplayMode.class, Event.class,
                EventQueue.class, FileDialog.class, FlowLayout.class, FocusTraversalPolicy.class,
                Font.class, FontMetrics.class, Frame.class, GradientPaint.class, Graphics.class,
                Graphics2D.class, GraphicsConfigTemplate.class, GraphicsConfiguration.class,
                GraphicsDevice.class, GraphicsEnvironment.class, GridBagLayout.class, GridBagConstraints.class,
                GridBagLayoutInfo.class, GridLayout.class, Image.class, ImageCapabilities.class,
                Insets.class, JobAttributes.class, KeyboardFocusManager.class, Label.class,
                LinearGradientPaint.class, java.awt.List.class, MediaTracker.class, Menu.class,
                MenuBar.class, MenuComponent.class, MenuItem.class, MenuShortcut.class, MouseInfo.class,
                MultipleGradientPaint.class, PageAttributes.class, Panel.class, Point.class,
                PointerInfo.class, Polygon.class, PopupMenu.class, PrinterJob.class, RadialGradientPaint.class,
                Rectangle.class, RenderingHints.class, Robot.class, Scrollbar.class, ScrollPane.class,
                ScrollPaneAdjustable.class, SplashScreen.class, SystemColor.class, SystemTray.class,
                TextArea.class, TextComponent.class, TextField.class, TexturePaint.class,
                Toolkit.class, TrayIcon.class, Window.class
                };
        map.put(Package.getPackage("java.awt"), Arrays.asList(awtArray));
        
        
    }

    private String fileNameToClassName(String name) {
        return name.substring(0, name.length() - ".class".length());
    }

    private String resourceNameToClassName(String resourceName) {
        return fileNameToClassName(resourceName).replace('/', '.');
    }

    private boolean isClassFile(String fileName) {
        return fileName.endsWith(".class");
    }

    private String packageNameToResourceName(String packageName) {
        return packageName.replace('.', '/');
    }

    public List<Class<?>> findClasses(String rootPackageName) throws ClassNotFoundException, IOException {
        String resourceName = packageNameToResourceName(rootPackageName);
        URL url = classLoader.getResource(resourceName);

        if (url == null) {
            System.out.println(url);
            return new ArrayList<Class<?>>();
        }

        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            return findClassesWithFile(rootPackageName, new File(url.getFile()));
        } else if ("jar".equals(protocol)) {
            return findClassesWithJarFile(rootPackageName, url);
        }

        throw new IllegalArgumentException("Unsupported Class Load Protocol[" + protocol + "]");
    }

    private List<Class<?>> findClassesWithFile(String packageName, File dir) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        for (String path : dir.list()) {
            File entry = new File(dir, path);
            if (entry.isFile() && isClassFile(entry.getName())) {
                classes.add(classLoader.loadClass(packageName + "." + fileNameToClassName(entry.getName())));
            } else if (entry.isDirectory()) {
                classes.addAll(findClassesWithFile(packageName + "." + entry.getName(), entry));
            }
        }

        return classes;
    }

    private List<Class<?>> findClassesWithJarFile(String rootPackageName, URL jarFileUrl) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        JarURLConnection jarUrlConnection = (JarURLConnection)jarFileUrl.openConnection();
        JarFile jarFile = null;

        try {
            jarFile = jarUrlConnection.getJarFile();
            Enumeration<JarEntry> jarEnum = jarFile.entries();

            String packageNameAsResourceName = packageNameToResourceName(rootPackageName);

            while (jarEnum.hasMoreElements()) {
                JarEntry jarEntry = jarEnum.nextElement();
                if (jarEntry.getName().startsWith(packageNameAsResourceName) && isClassFile(jarEntry.getName())) {
                    classes.add(classLoader.loadClass(resourceNameToClassName(jarEntry.getName())));
                }
            }
        } finally {
            if (jarFile != null) {
                jarFile.close();
            }
        }

        return classes;
    }
}