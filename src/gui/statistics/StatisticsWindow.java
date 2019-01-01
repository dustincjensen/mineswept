package gui.statistics;

import events.IEventSubscriber;
import events.ShowStatisticsEvent;
import gui.components.button.DangerButton;
import gui.HexToRgb;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class StatisticsWindow {
	private IEventSubscriber eventSubscriber;
	private StatisticsFrame frame;
	private StatisticsPanel panel;

	public StatisticsWindow(IEventSubscriber subscriber) {
		eventSubscriber = subscriber;
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
		reset.add(new DangerButton("Reset", evt -> {
			System.out.println("Reset button pressed in statistics.");
			// var difficulty = Difficulty.getDifficulty(tabs.getSelectedIndex());
			// eventPublisher.publish(new ResetRecordsEvent(difficulty));
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
	}
}