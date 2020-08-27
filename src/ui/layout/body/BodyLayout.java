package ui.layout.body;

import events.IEventSubscriber;
import events.PauseGameEvent;
import events.RefreshMainWindowEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import services.OptionsService;
import ui.utils.ColorConverter;

@SuppressWarnings("serial")
public class BodyLayout extends JPanel {
	private MinePanel minePanel;
	private PausePanel pausePanel;
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
		this.eventSubscriber = eventSubscriber;
		
		setBackground(ColorConverter.convert(options.clickedColor()));
		add(minePanel, new GridBagConstraints());
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
			revalidate();
			repaint();
		});

		// TODO seems like we can remove this event.
		// Going to keep it in for testing.
		eventSubscriber.subscribe(RefreshMainWindowEvent.class, (event) -> {
		// 	doResize();
		// 	repaint();
        //     getContentPane().repaint();
		});
	}
}