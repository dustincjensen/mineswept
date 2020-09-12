package ui.window;

import events.AboutEvent;
import events.IEventSubscriber;
import java.awt.Image;
import javax.swing.JFrame;
import ui.components.dialog.CustomDialog;
import ui.layout.MainLayout;
import ui.menu.Menus;

@SuppressWarnings("serial")
public class Window extends JFrame {
    private CustomDialog aboutDialog;

    public Window(
        MainLayout mainLayout,
        Menus menus,
        WindowHandler mainWindowHandler,
        Image windowIcon,
        IEventSubscriber eventSubscriber
    ) {
        super("MineSwept");
		setContentPane(mainLayout);
		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(windowIcon);
        addWindowListener(mainWindowHandler)
        setupSubscriptions(eventSubscriber);

        aboutDialog = new CustomDialog(this, CustomDialog.Type.OK);
    }

    public void showWindow() {
        pack();
        setLocationRelativeTo(null);
		setVisible(true);
    }

    private void setupSubscriptions(IEventSubscriber eventSubscriber) {
        eventSubscriber.subscribe(AboutEvent.class, event -> {
            aboutDialog.show(event.getTitle(), event.getMessage());
        });
    }
}
