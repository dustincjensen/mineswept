package gui.panel;

import events.IEventSubscriber;
import events.PauseGameEvent;
import gui.panel.header.HeaderPanel;
import gui.panel.mines.MinePanel;
import gui.panel.mines.PausePanel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
	private MinePanel minePanel;
	private PausePanel pausePanel;
	private IEventSubscriber eventSubscriber;

	public MainPanel(
		HeaderPanel header,
		MinePanel mine,
		PausePanel pause,
		IEventSubscriber subscriber
	) {
		minePanel = mine;
		pausePanel = pause;
		eventSubscriber = subscriber;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(header);
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