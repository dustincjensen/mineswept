package ui.main;

import events.IEventSubscriber;
import events.RefreshMainWindowEvent;
import ui.menu.Menus;
import ui.layout.body.BodyLayout;
import ui.ResourceLoader;
import javax.swing.JFrame;
import models.Resource;

public class MainWindow extends JFrame {
    private IEventSubscriber eventSubscriber;

    public MainWindow(
        BodyLayout bodyLayout,
        Menus menus,
        MainWindowHandler mainWindowHandler,
        ResourceLoader loader,
        IEventSubscriber eventSubscriber
    ) {
        super("MineSwept");
		setContentPane(bodyLayout);
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