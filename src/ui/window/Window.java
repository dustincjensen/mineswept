package ui.window;

import events.IEventSubscriber;
import events.RefreshMainWindowEvent;
import ui.menu.Menus;
import ui.layout.MainLayout;
import ui.ResourceLoader;
import javax.swing.JFrame;
import models.Resource;

public class Window extends JFrame {
    private IEventSubscriber eventSubscriber;

    public Window(
        MainLayout mainLayout,
        Menus menus,
        WindowHandler mainWindowHandler,
        ResourceLoader loader,
        IEventSubscriber eventSubscriber
    ) {
        super("MineSwept");
		setContentPane(mainLayout);
		setJMenuBar(menus);
        setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(loader.get(Resource.SmileyCool).getImage());
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