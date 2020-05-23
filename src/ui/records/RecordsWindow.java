package ui.records;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetRecordsEvent;
import events.ShowRecordsEvent;
import ui.components.button.DangerButton;
import ui.components.tabbedPane.CustomTabbedPane;
import ui.utils.HexToRgb;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.Difficulty;
import javax.swing.JOptionPane;

public class RecordsWindow {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;

	private JFrame frame;
	private CustomTabbedPane tabs;
	private RecordsPanel easy, medium, hard;

	public RecordsWindow(
		IEventPublisher publisher,
		IEventSubscriber subscriber
	) {
		eventPublisher = publisher;
		eventSubscriber = subscriber;

		System.out.println("Creating: RECORD WINDOW");
		
		frame = new RecordsFrame(recordPanel());

		setupSubscriptions();
	}

	private JPanel recordPanel() {
		var recordPanel = new JPanel();
		recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));

		easy = new RecordsPanel();
		medium = new RecordsPanel();
		hard = new RecordsPanel();

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
		reset.add(new DangerButton("Reset", true, evt -> {
			var difficultyName = Difficulty.getProperName(tabs.getSelectedIndex());
			int answer = confirmDialog("Would you like to reset your '" + difficultyName + "' records?");
			if (answer == JOptionPane.YES_OPTION) {
				var difficulty = Difficulty.getDifficulty(tabs.getSelectedIndex());
				eventPublisher.publish(new ResetRecordsEvent(difficulty));
			}
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

	// We make a specific option dialog so we can select "No" as the default.
	// This is done by using options[1].
	private int confirmDialog(String message) {
		String[] options = { "Yes", "No" };
		return JOptionPane.showOptionDialog(frame, message, "Confirm?", 
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]
		);
	}
}