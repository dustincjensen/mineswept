package gui.records;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetRecordsEvent;
import events.ShowRecordsEvent;
import gui.HexToRgb;
import gui.components.button.DangerButton;
import gui.components.button.PrimaryButton;
import gui.components.tabbedPane.CustomTabbedPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Difficulty;
import models.records.All;
import services.RecordsService;

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
		recordPanel.add(okReset());

		return recordPanel;
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
		return new PrimaryButton("OK", evt -> frame.setVisible(false));
	}

	private JButton reset() {
		return new DangerButton("Reset", evt -> {
			var difficulty = Difficulty.getDifficulty(tabs.getSelectedIndex());
			eventPublisher.publish(new ResetRecordsEvent(difficulty));
		});
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowRecordsEvent.class, event -> {
			frame.setVisible(true);

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