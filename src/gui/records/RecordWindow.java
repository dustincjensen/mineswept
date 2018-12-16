package gui.records;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetRecordsEvent;
import events.ShowRecordsEvent;
import gui.HexToRgb;
import gui.components.button.DangerButton;
import gui.components.button.PrimaryButton;
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
		accept.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		accept.setBackground(HexToRgb.convert("#333333"));
		accept.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		accept.setLayout(new GridLayout(0, 2, 5, 5));
		accept.add(ok());
		accept.add(reset());
		return accept;
	}

	private JButton ok() {
		return new PrimaryButton("OK", evt -> recordWindow.setVisible(false));
	}

	private JButton reset() {
		return new DangerButton("Reset", evt -> {
			// TODO move this somewhere else?
			var tabIndexToDifficulty = Map.of(
				0, Difficulty.easy,
				1, Difficulty.medium,
				2, Difficulty.hard
			);

			Difficulty difficulty = tabIndexToDifficulty.get(tabs.getSelectedIndex());
			eventPublisher.publish(new ResetRecordsEvent(difficulty));
		});
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