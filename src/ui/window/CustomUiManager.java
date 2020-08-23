package ui.window;

import ui.utils.HexToRgb;
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
        UIManager.put("MenuBar.background", HexToRgb.convert("#333333"));
		UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder());

        // Menu
		UIManager.put("Menu.selectionBackground", HexToRgb.convert("#444444"));
		UIManager.put("Menu.selectionForeground", HexToRgb.convert("#ffffff"));
		UIManager.put("Menu.background", HexToRgb.convert("#333333"));
		UIManager.put("Menu.foreground", HexToRgb.convert("#ffffff"));
		UIManager.put("Menu.opaque", false);
		UIManager.put("Menu.borderPainted", false);
		UIManager.put("Menu.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // MenuItem
		UIManager.put("MenuItem.acceleratorForeground", HexToRgb.convert("#eeeeee"));
		UIManager.put("MenuItem.acceleratorSelectionForeground", HexToRgb.convert("#eeeeee"));
		UIManager.put("MenuItem.background", HexToRgb.convert("#333333"));
		UIManager.put("MenuItem.foreground", HexToRgb.convert("#ffffff"));
		UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder(10, 10, 10, 10));
		UIManager.put("MenuItem.borderPainted", false);
		UIManager.put("MenuItem.selectionBackground", HexToRgb.convert("#444444"));
		UIManager.put("MenuItem.selectionForeground", HexToRgb.convert("#ffffff"));

        // PopupMenu
		UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(HexToRgb.convert("#4444444")));
		UIManager.put("PopupMenu.background", HexToRgb.convert("#ff5500"));
    }
}