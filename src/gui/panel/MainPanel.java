package gui.panel;


import gui.ClassFactory;
import gui.events.IEventSubscriber;
import gui.events.PauseGameEvent;
import gui.panel.header.HeaderPanel;
import gui.panel.mines.MinePanel;
import gui.panel.mines.PausePanel;
import java.awt.*;
import javax.swing.*;

/**
 * Sets up the JFrame content pane.
 */
public class MainPanel extends JPanel {
	private IEventSubscriber eventSubscriber;
	private MinePanel minePanel;
	private PausePanel pausePanel;

	public MainPanel(IEventSubscriber subscriber) {
		eventSubscriber = subscriber;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// TODO this should be provided to the main panel...
		minePanel = ClassFactory.create(MinePanel.class);
		pausePanel = ClassFactory.create(PausePanel.class);
		add(ClassFactory.create(HeaderPanel.class));
		add(minePanel);

		setupSubscriptions();
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(PauseGameEvent.class, (event) -> {
			if (event.pause) {
				remove(minePanel);
				add(pausePanel);
			} else {
				remove(pausePanel);
				add(minePanel);
			}
			repaint();
			revalidate();
		});
	}
}