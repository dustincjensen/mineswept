package gui.records;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetRecordsEvent;
import events.ShowRecordsEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Difficulty;
import models.records.All;
import services.RecordsService;

public class RecordWindow {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;

	private JFrame recordWindow;
	private JTabbedPane tabs;
	private RecordPanel easy, medium, hard;

	public RecordWindow(
		IEventPublisher publisher,
		IEventSubscriber subscriber
	) {
		eventPublisher = publisher;
		eventSubscriber = subscriber;

		System.out.println("Creating: RECORD WINDOW");
		setupWindow();
		setupSubscriptions();
	}

	private void setupWindow() {
		recordWindow = new JFrame("Records");
		recordWindow.setContentPane(recordPanel());

		recordWindow.setSize(300, 175 + RecordsService.RECORD_LIMIT * 15);
		recordWindow.setLocationRelativeTo(null);
		recordWindow.setResizable(false);
		recordWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	private JPanel recordPanel() {
		var recordPanel = new JPanel();
		recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));

		easy = new RecordPanel("Easy");
		medium = new RecordPanel("Medium");
		hard = new RecordPanel("Hard");

		recordPanel.add(tabs());
		recordPanel.add(okReset());

		return recordPanel;
	}

	private JTabbedPane tabs() {
		tabs = new JTabbedPane();
		tabs.add(easy);
		tabs.add(medium);
		tabs.add(hard);
		return tabs;
	}

	private JPanel okReset() {
		var accept = new JPanel();
		accept.setLayout(new BoxLayout(accept, BoxLayout.X_AXIS));
		accept.add(Box.createHorizontalGlue());
		accept.add(ok());
		accept.add(reset());
		accept.add(Box.createHorizontalGlue());
		return accept;
	}

	private JButton ok() {
		var okButton = new JButton("OK");
		okButton.addActionListener(evt -> {
			recordWindow.setVisible(false);
		});
		return okButton;
	}

	private JButton reset() {
		var reset = new JButton("Reset");
		reset.addActionListener(evt -> {
			var tabIndexToDifficulty = Map.of(
				0, Difficulty.easy,
				1, Difficulty.medium,
				2, Difficulty.hard
			);

			Difficulty difficulty = tabIndexToDifficulty.get(tabs.getSelectedIndex());
			eventPublisher.publish(new ResetRecordsEvent(difficulty));
		});
		return reset;
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowRecordsEvent.class, event -> {
			recordWindow.setVisible(true);

			All allRecords = event.records;
			easy.setRecords(allRecords.easy);
			medium.setRecords(allRecords.medium);
			hard.setRecords(allRecords.hard);
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