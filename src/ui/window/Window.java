package ui.window;

import events.IEventSubscriber;
import events.RefreshMainWindowEvent;
import java.awt.Image;
import javax.swing.JFrame;
import ui.layout.MainLayout;
import ui.menu.Menus;

public class Window extends JFrame {
    private IEventSubscriber eventSubscriber;

    public Window(
        MainLayout mainLayout,
        Menus menus,
        WindowHandler mainWindowHandler,
        IEventSubscriber eventSubscriber,
        Image windowIcon
    ) {
        super("MineSwept");
		setContentPane(mainLayout);
		setJMenuBar(menus);
        setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(windowIcon);
        addWindowListener(mainWindowHandler);

        this.eventSubscriber = eventSubscriber;

        setupSubscription();
    }

    public void showWindow() {
        pack();
        setLocationRelativeTo(null);
		setVisible(true);
    }

    private void setupSubscription() {
		eventSubscriber.subscribe(RefreshMainWindowEvent.class, (event) -> {
			pack();
			getContentPane().repaint();
		});
	}
}