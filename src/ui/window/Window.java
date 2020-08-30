package ui.window;

import events.AboutEvent;
import events.IEventSubscriber;
import java.awt.Image;
import javax.swing.JFrame;
import ui.about.AboutDialog;
import ui.layout.MainLayout;
import ui.menu.Menus;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private AboutDialog aboutDialog;

    public Window(
        MainLayout mainLayout,
        Menus menus,
        WindowHandler mainWindowHandler,
        Image windowIcon,
        AboutDialog aboutDialog,
        IEventSubscriber eventSubscriber
    ) {
        super("MineSwept");
        this.aboutDialog = aboutDialog;

		setContentPane(mainLayout);
		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(windowIcon);
        addWindowListener(mainWindowHandler);
        setupSubscriptions(eventSubscriber);
    }

    public void showWindow() {
        pack();
        setLocationRelativeTo(null);
		setVisible(true);
    }

    private void setupSubscriptions(IEventSubscriber eventSubscriber) {
        eventSubscriber.subscribe(AboutEvent.class, event -> {
            aboutDialog.show(this, event.getTitle(), event.getMessage());
        });
    }
}
