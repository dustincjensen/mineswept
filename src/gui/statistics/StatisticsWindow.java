package gui.statistics;

import events.IEventSubscriber;
import events.ShowStatisticsEvent;
import javax.swing.*;

public class StatisticsWindow {
	private IEventSubscriber eventSubscriber;
	private JFrame statisticWindow;

	public StatisticsWindow(IEventSubscriber subscriber) {
		eventSubscriber = subscriber;
		System.out.println("Creating: STATISTICS WINDOW");

		statisticWindow = new JFrame("Statistics");
		statisticWindow.setContentPane(setupPanel());

		// statisticWindow.setSize(300,175+Statistics.RECORD_LIMIT*15);
		statisticWindow.pack();
		statisticWindow.setLocationRelativeTo(null);
		statisticWindow.setResizable(false);
		statisticWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setupSubscriptions();
	}

	private JPanel setupPanel() {
		var panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JButton("Statistics"));
		return panel;
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowStatisticsEvent.class, event -> {
			statisticWindow.setVisible(true);
		});
	}
}