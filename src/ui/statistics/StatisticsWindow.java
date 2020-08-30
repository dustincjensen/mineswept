package ui.statistics;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetStatisticsEvent;
import events.ShowStatisticsEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import models.Difficulty;
import ui.components.button.DangerButton;
import ui.components.tabbedPane.CustomTabbedPane;

public class StatisticsWindow {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	
	private StatisticsFrame frame;
	private CustomTabbedPane tabs;
	private RecordsPanel easyRecordPanel, mediumRecordPanel, hardRecordPanel;
	private StatisticsPanel easyStatsPanel, mediumStatsPanel, hardStatsPanel;

	public StatisticsWindow(
		IEventPublisher	eventPublisher,
		IEventSubscriber eventSubscriber,
		Image windowIcon
	) {
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		System.out.println("Creating: STATISTICS WINDOW");

		frame = new StatisticsFrame(tabbedPanel());
		frame.setIconImage(windowIcon);

		setupSubscriptions();
	}

	private JPanel tabbedPanel() {
		easyRecordPanel = new RecordsPanel();
		mediumRecordPanel = new RecordsPanel();
		hardRecordPanel = new RecordsPanel();
		
		easyStatsPanel = new StatisticsPanel();
		mediumStatsPanel = new StatisticsPanel();
		hardStatsPanel = new StatisticsPanel();

		tabs = new CustomTabbedPane();
		tabs.add("Easy", difficultyPanel(easyRecordPanel, easyStatsPanel));
		tabs.add("Medium", difficultyPanel(mediumRecordPanel, mediumStatsPanel));
		tabs.add("Hard", difficultyPanel(hardRecordPanel, hardStatsPanel));

		var tabbedPanel = new JPanel();
		tabbedPanel.setBackground(Color.decode("#333333"));
		tabbedPanel.setLayout(new BorderLayout());
		tabbedPanel.add(tabs, BorderLayout.CENTER);
		tabbedPanel.add(reset(), BorderLayout.SOUTH);
		return tabbedPanel;
	}

	private JPanel difficultyPanel(RecordsPanel recordPanel, StatisticsPanel statsPanel) {
		var diff = new JPanel();
		diff.setOpaque(false);
		diff.setLayout(new BorderLayout());
		diff.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.decode("#111111")));
		diff.add(recordPanel, BorderLayout.CENTER);
		diff.add(statsPanel, BorderLayout.LINE_END);
		return diff;
	}

	private JPanel reset() {
		var reset = new JPanel();
		reset.setOpaque(false);
		reset.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		reset.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		reset.setLayout(new GridLayout(0, 1, 5, 5));
		reset.add(new DangerButton("Reset", true, evt -> {
			var difficultyName = Difficulty.getProperName(tabs.getSelectedIndex());
			int answer = confirmDialog("Would you like to reset your '" + difficultyName + "' statistics?");
			if (answer == JOptionPane.YES_OPTION) {
				var difficulty = Difficulty.getDifficulty(tabs.getSelectedIndex());
				eventPublisher.publish(new ResetStatisticsEvent(difficulty));
			}
		}));
		return reset;
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowStatisticsEvent.class, event -> {
			if (event.showWindow) {
				frame.setVisible(true);
			}
						
			var allStats = event.stats;
			easyStatsPanel.setStatistics(allStats.easy);
			mediumStatsPanel.setStatistics(allStats.medium);
			hardStatsPanel.setStatistics(allStats.hard);
			
			var allRecords = event.records;
			easyRecordPanel.setRecords(allRecords.easy);
			mediumRecordPanel.setRecords(allRecords.medium);
			hardRecordPanel.setRecords(allRecords.hard);

			if (event.difficulty != null) {
				tabs.setSelectedIndex(Difficulty.getProperName(event.difficulty));
			}
		});

		eventSubscriber.subscribe(ResetStatisticsEvent.class, event -> {
			easyStatsPanel.setStatistics(event.stats.easy);
			mediumStatsPanel.setStatistics(event.stats.medium);
			hardStatsPanel.setStatistics(event.stats.hard);
			if (event.difficulty == Difficulty.easy) {
				easyRecordPanel.setRecords(event.records);
			} else if (event.difficulty == Difficulty.medium) {
				mediumRecordPanel.setRecords(event.records);
			} else if (event.difficulty == Difficulty.hard) {
				hardRecordPanel.setRecords(event.records);
			}
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