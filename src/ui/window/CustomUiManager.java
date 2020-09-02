package ui.window;

import java.awt.Color;
import ui.window.CustomUiManager;
import javax.swing.BorderFactory;
import javax.swing.UIManager;

public class CustomUiManager {
    /**
     * Use the UIManager static class to modify some of the UI components
     * before we start the application.
     */
    public static void setup() {
        // MenuBar
        UIManager.put("MenuBar.background", Color.decode("#333333"));
		UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder());

        // Menu
		UIManager.put("Menu.selectionBackground", Color.decode("#444444"));
		UIManager.put("Menu.selectionForeground", Color.decode("#ffffff"));
		UIManager.put("Menu.background", Color.decode("#333333"));
		UIManager.put("Menu.foreground", Color.decode("#ffffff"));
		UIManager.put("Menu.opaque", false);
		UIManager.put("Menu.borderPainted", false);
		UIManager.put("Menu.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // MenuItem
		UIManager.put("MenuItem.acceleratorForeground", Color.decode("#eeeeee"));
		UIManager.put("MenuItem.acceleratorSelectionForeground", Color.decode("#eeeeee"));
		UIManager.put("MenuItem.background", Color.decode("#333333"));
		UIManager.put("MenuItem.foreground", Color.decode("#ffffff"));
		UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder(10, 10, 10, 10));
		UIManager.put("MenuItem.borderPainted", false);
		UIManager.put("MenuItem.selectionBackground", Color.decode("#444444"));
		UIManager.put("MenuItem.selectionForeground", Color.decode("#ffffff"));

        // PopupMenu
		UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(Color.decode("#4444444")));
		UIManager.put("PopupMenu.background", Color.decode("#ff5500"));
    }
}