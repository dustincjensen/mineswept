package ui.layout.body;

import events.IEventSubscriber;
import events.PauseGameEvent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import ui.layout.body.MinePanel;
import ui.layout.body.PausePanel;
import ui.layout.header.HeaderLayout;

public class BodyLayout extends JPanel {
	private MinePanel minePanel;
	private PausePanel pausePanel;
	private IEventSubscriber eventSubscriber;

	public BodyLayout(
		HeaderLayout header,
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