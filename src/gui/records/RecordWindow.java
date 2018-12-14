package gui.records;

import events.IEventSubscriber;
import events.ShowRecordsEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.records.All;
import services.RecordsService;

public class RecordWindow {
	private IEventSubscriber eventSubscriber;

	private JFrame recordWindow;
	private JTabbedPane tabs;
	private RecordPanel beginner, intermediate, advanced;

	public RecordWindow(IEventSubscriber subscriber) {
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

		beginner = new RecordPanel("Beginner");
		intermediate = new RecordPanel("Intermediate");
		advanced = new RecordPanel("Advanced");

		recordPanel.add(tabs());
		recordPanel.add(okReset());

		return recordPanel;
	}

	private JTabbedPane tabs() {
		tabs = new JTabbedPane();
		tabs.add(beginner);
		tabs.add(intermediate);
		tabs.add(advanced);
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
			if (tabs.getSelectedIndex() == 0) {
				// Records.resetRecords("beginner");
				beginner.refreshRecords();
			} else if (tabs.getSelectedIndex() == 1) {
				// Records.resetRecords("intermediate");
				intermediate.refreshRecords();
			} else if (tabs.getSelectedIndex() == 2) {
				// Records.resetRecords("advanced");
				advanced.refreshRecords();
			}
		});
		return reset;
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowRecordsEvent.class, event -> {
			recordWindow.setVisible(true);

			All allRecords = event.records;
			beginner.setRecords(allRecords.beginner);
			intermediate.setRecords(allRecords.intermediate);
			advanced.setRecords(allRecords.advanced);
		});
	}
}