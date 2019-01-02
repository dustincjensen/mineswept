package gui.records;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetRecordsEvent;
import events.ShowRecordsEvent;
import gui.components.button.DangerButton;
import gui.components.tabbedPane.CustomTabbedPane;
import gui.HexToRgb;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.Difficulty;

public class RecordWindow {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;

	private JFrame frame;
	private CustomTabbedPane tabs;
	private RecordPanel easy, medium, hard;

	public RecordWindow(
		IEventPublisher publisher,
		IEventSubscriber subscriber
	) {
		eventPublisher = publisher;
		eventSubscriber = subscriber;

		System.out.println("Creating: RECORD WINDOW");
		
		frame = new RecordFrame(recordPanel());

		setupSubscriptions();
	}

	private JPanel recordPanel() {
		var recordPanel = new JPanel();
		recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));

		easy = new RecordPanel();
		medium = new RecordPanel();
		hard = new RecordPanel();

		tabs = new CustomTabbedPane();
		tabs.add("Easy", easy);
		tabs.add("Medium", medium);
		tabs.add("Hard", hard);

		recordPanel.add(tabs);
		recordPanel.add(reset());

		return recordPanel;
	}

	private JPanel reset() {
		var reset = new JPanel();
		reset.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		reset.setBackground(HexToRgb.convert("#333333"));
		reset.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		reset.setLayout(new GridLayout(0, 1, 5, 5));
		reset.add(new DangerButton("Reset", evt -> {
			var difficulty = Difficulty.getDifficulty(tabs.getSelectedIndex());
			eventPublisher.publish(new ResetRecordsEvent(difficulty));
		}));
		return reset;
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowRecordsEvent.class, event -> {
			frame.setVisible(true);

			var allRecords = event.records;
			easy.setRecords(allRecords.easy);
			medium.setRecords(allRecords.medium);
			hard.setRecords(allRecords.hard);

			if (event.difficulty != null) {
				tabs.setSelectedIndex(Difficulty.getProperName(event.difficulty));
			}
		});

		eventSubscriber.subscribe(ResetRecordsEvent.class, event -> {
			if (event.difficulty == Difficulty.easy) {
				easy.setRecords(event.records);
			} else if (event.difficulty == Difficulty.medium) {
				medium.setRecords(event.records);
			} else if (event.difficulty == Difficulty.hard) {
				hard.setRecords(event.records);
			}
		});
	}
}