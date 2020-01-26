package ui.layout.body;

import events.IEventSubscriber;
import events.PauseGameEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import ui.layout.body.MinePanel;
import ui.layout.body.PausePanel;

@SuppressWarnings("serial")
public class BodyLayout extends Box {
	private IEventSubscriber eventSubscriber;
	private MinePanel minePanel;
	private PausePanel pausePanel;

	public BodyLayout(
		MinePanel minePanel,
		PausePanel pausePanel,
		IEventSubscriber eventSubscriber
	) {
		super(BoxLayout.Y_AXIS);
		this.eventSubscriber = eventSubscriber;
		this.minePanel = minePanel;
		this.pausePanel = pausePanel;
		
		add(minePanel);
		
		setupSubscriptions();
	}

	private void swap(JComponent panelToRemove, JComponent panelToAdd) {
		remove(panelToRemove);
		add(panelToAdd);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(PauseGameEvent.class, event -> {
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