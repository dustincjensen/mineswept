package gui.main;

import events.IEventSubscriber;
import events.RefreshMainWindowEvent;
import gui.menu.Menus;
import gui.panel.MainPanel;
import gui.ResourceLoader;
import javax.swing.JFrame;
import models.Resource;

public class MainWindow extends JFrame {
    private IEventSubscriber eventSubscriber;

    public MainWindow(
        MainPanel mainPanel,
        Menus menus,
        MainWindowHandler mainWindowHandler,
        ResourceLoader loader,
        IEventSubscriber eventSubscriber
    ) {
        super("MineSwept");
		setContentPane(mainPanel);
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