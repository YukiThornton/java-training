package interpret;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class TabbedPane extends JPanel {
    
    public TabbedPane(Tab[] tabs) {
        super(new GridLayout(1, 1));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        for (Tab tab : tabs) {
            tabbedPane.addTab(tab.getTitle(), tab.getIcon(), tab.getPanel(), tab.getTip());
        }
         
        //Add the tabbed pane to this panel.
        add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

    }
    
    public static class Tab {
        private JPanel panel;
        private ImageIcon icon;
        private String title;
        private String tip;
        
        public Tab(JPanel panel, ImageIcon icon, String title, String tip) {
            this.panel = panel;
            this.icon = icon;
            this.title = title;
            this.tip = tip;
        }
        
        public JPanel getPanel() {
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
