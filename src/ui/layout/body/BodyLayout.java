package ui.layout.body;

import events.GamePausedEvent;
import events.IEventSubscriber;
import events.ResetMinePanelEvent;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import services.OptionsService;

@SuppressWarnings("serial")
public class BodyLayout extends JPanel {
	private MinePanel minePanel;
	private PausePanel pausePanel;
	private OptionsService options;
	private IEventSubscriber eventSubscriber;

	public BodyLayout(
		MinePanel minePanel,
		PausePanel pausePanel,
		OptionsService options,
		IEventSubscriber eventSubscriber
	) {
		super(new GridBagLayout());
		this.minePanel = minePanel;
		this.pausePanel = pausePanel;
		this.options = options;
		this.eventSubscriber = eventSubscriber;
		
		setBackground(Color.decode(options.clickedColor()));
		add(minePanel, new GridBagConstraints());
		setupSubscriptions();
	}

	private void swap(JComponent panelToRemove, JComponent panelToAdd) {
		remove(panelToRemove);
		add(panelToAdd);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(GamePausedEvent.class, event -> {
			if (event.gamePaused) {
				swap(minePanel, pausePanel);
			} else {
				swap(pausePanel, minePanel);
			}
			revalidate();
			repaint();
		});

		eventSubscriber.subscribe(ResetMinePanelEvent.class, event -> {
			// We refresh the colors on game reset. Options may have changd.
			setBackground(Color.decode(options.clickedColor()));
		});
	}
}