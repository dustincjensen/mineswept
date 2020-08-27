package ui.window;

import java.awt.Image;
import javax.swing.JFrame;
import ui.layout.MainLayout;
import ui.menu.Menus;

@SuppressWarnings("serial")
public class Window extends JFrame {
    public Window(
        MainLayout mainLayout,
        Menus menus,
        WindowHandler mainWindowHandler,
        Image windowIcon
    ) {
        super("MineSwept");
		setContentPane(mainLayout);
		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(windowIcon);
        addWindowListener(mainWindowHandler);
    }

    public void showWindow() {
        pack();
        setLocationRelativeTo(null);
		setVisible(true);
    }
}
