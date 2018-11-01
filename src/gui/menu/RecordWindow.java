package gui.menu;

import logic.files.Records;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RecordWindow {
	private static JFrame recordWindow;
	private static JTabbedPane tabbedPane;
	private static JPanel recordPanel, accept;
	private static RecordPanel beginner, intermediate, advanced;
	private static JButton okButton, reset;
	private static RecordWindowActions rwa;

	public static void init() {
		rwa = new RecordWindowActions();
		setupPane();
		setupWindow();
	}

	private static void setupPane() {
		recordPanel = new JPanel();
		recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));

		beginner = new RecordPanel("Beginner");
		intermediate = new RecordPanel("Intermediate");
		advanced = new RecordPanel("Advanced");

		setupTabbedPane();
		setupAccept();
		recordPanel.add(tabbedPane);
		recordPanel.add(accept);
	}

	private static void setupTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.add(beginner);
		tabbedPane.add(intermediate);
		tabbedPane.add(advanced);
	}

	private static void setupAccept() {
		accept = new JPanel();
		accept.setLayout(new BoxLayout(accept, BoxLayout.X_AXIS));

		okButton = new JButton("OK");
		okButton.addActionListener(rwa);
		reset = new JButton("Reset");
		reset.addActionListener(rwa);

		accept.add(Box.createHorizontalGlue());
		accept.add(okButton);
		accept.add(reset);
		accept.add(Box.createHorizontalGlue());
	}

	public static void setupWindow() {
		recordWindow = new JFrame("Records");
		recordWindow.setContentPane(recordPanel);

		recordWindow.setSize(300, 175 + Records.RECORD_LIMIT * 15);
		recordWindow.setLocationRelativeTo(null);
		recordWindow.setResizable(false);
		recordWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public static void show(boolean t) {
		recordWindow.setVisible(t);
	}

	public static void setBeginnerRecords(String[] arr) {
		beginner.setRecords(arr);
	}

	public static void setIntermediateRecords(String[] arr) {
		intermediate.setRecords(arr);
	}

	public static void setAdvancedRecords(String[] arr) {
		advanced.setRecords(arr);
	}

	private static class RecordWindowActions implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == okButton)
				show(false);

			else if (evt.getSource() == reset) {
				if (tabbedPane.getSelectedIndex() == 0) {
					Records.resetRecords("beginner");
					beginner.refreshRecords();
				} else if (tabbedPane.getSelectedIndex() == 1) {
					Records.resetRecords("intermediate");
					intermediate.refreshRecords();
				} else if (tabbedPane.getSelectedIndex() == 2) {
					Records.resetRecords("advanced");
					advanced.refreshRecords();
				}
			}
		}
	}
}