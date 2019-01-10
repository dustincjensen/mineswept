package ui.layout.body;

import events.IEventSubscriber;
import events.PauseGameEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import ui.layout.body.MinePanel;
import ui.layout.body.PausePanel;
import ui.layout.header.HeaderLayout;

public class BodyLayout extends Box {
	private IEventSubscriber eventSubscriber;
	private MinePanel minePanel;
	private PausePanel pausePanel;

	public BodyLayout(
		HeaderLayout header,
		MinePanel minePanel,
		PausePanel pausePanel,
		IEventSubscriber eventSubscriber
	) {
		super(BoxLayout.Y_AXIS);
		
		this.minePanel = minePanel;
		this.pausePanel = pausePanel;
		this.eventSubscriber = eventSubscriber;
		
		add(header);
		add(minePanel);
		
		setupSubscriptions();
	}

	private void swap(JPanel panelToRemove, JPanel panelToAdd) {
		remove(panelToRemove);
		add(panelToAdd);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(PauseGameEvent.class, (event) -> {
			if (event.pause) {
				swap(minePanel, pausePanel);
			} else {
				swap(pausePanel, minePanel);
			}
			repaint();
			revalidate();
		});
	}
}