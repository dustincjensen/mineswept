package gui.records;

import logic.files.Records;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RecordWindow {
	private JFrame recordWindow;
	private JTabbedPane tabs;
	// TODO make non-static
	private static RecordPanel beginner, intermediate, advanced;

	public RecordWindow(Records records) {
		System.out.println("Creating: RECORD WINDOW");
		setupWindow();
		beginner.setRecords(records.getAllRecords().beginner);
	}

	public void show(boolean t) {
		recordWindow.setVisible(t);
	}

	// public static void setBeginnerRecords(String[] arr) {
	// 	beginner.setRecords(arr);
	// }

	// public static void setIntermediateRecords(String[] arr) {
	// 	intermediate.setRecords(arr);
	// }

	// public static void setAdvancedRecords(String[] arr) {
	// 	advanced.setRecords(arr);
	// }

	private void setupWindow() {
		recordWindow = new JFrame("Records");
		recordWindow.setContentPane(recordPanel());

		recordWindow.setSize(300, 175 + Records.RECORD_LIMIT * 15);
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
			show(false);
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
}