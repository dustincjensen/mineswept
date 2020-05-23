package ui.statistics;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetStatisticsEvent;
import events.ShowStatisticsEvent;
import ui.components.button.DangerButton;
import ui.utils.HexToRgb;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class StatisticsWindow {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private StatisticsFrame frame;
	private StatisticsPanel panel;

	public StatisticsWindow(IEventPublisher	eventPublisher, IEventSubscriber eventSubscriber) {
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		System.out.println("Creating: STATISTICS WINDOW");

		panel = new StatisticsPanel();
		frame = new StatisticsFrame(statisticsPanel());

		setupSubscriptions();
	}

	private JPanel statisticsPanel() {
		var statisticsPanel = new JPanel();
		statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.Y_AXIS));
		statisticsPanel.add(panel);
		statisticsPanel.add(reset());
		return statisticsPanel;
	}

	private JPanel reset() {
		var reset = new JPanel();
		reset.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		reset.setBackground(HexToRgb.convert("#333333"));
		reset.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		reset.setLayout(new GridLayout(0, 1, 5, 5));
		reset.add(new DangerButton("Reset", true, evt -> {
			int answer = confirmDialog("Would you like to reset your statistics?");
			if (answer == JOptionPane.YES_OPTION) {
				eventPublisher.publish(new ResetStatisticsEvent());
			}
		}));
		return reset;
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowStatisticsEvent.class, event -> {
			panel.setStatistics(event.stats);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});

		eventSubscriber.subscribe(ResetStatisticsEvent.class, event -> {
			panel.setStatistics(event.stats);
			frame.pack();
		});
	}

	// We make a specific option dialog so we can select "No" as the default.
	// This is done by using options[1].
	private int confirmDialog(String message) {
		String[] options = { "Yes", "No" };
		return JOptionPane.showOptionDialog(frame, message, "Confirm?", 
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]
		);
	}
}