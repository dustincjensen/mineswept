package gui.menu;

import logic.files.Records;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecordWindow {

	private static JFrame recordWindow;
	private static JTabbedPane tabbedPane;
	private static JPanel recordPanel, accept;
	private static RecordPanel begin_, interm_, advan_;
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

		begin_ = new RecordPanel("Beginner");
		interm_ = new RecordPanel("Intermediate");
		advan_ = new RecordPanel("Advanced");

		setupTabbedPane();
		setupAccept();
		recordPanel.add(tabbedPane);
		recordPanel.add(accept);
	}

	private static void setupTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.add(begin_);
		tabbedPane.add(interm_);
		tabbedPane.add(advan_);
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

		recordWindow.setSize(300,175+Records.RECORD_LIMIT*15);
		recordWindow.setLocationRelativeTo(null);
		recordWindow.setResizable(false);
		recordWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public static void show(boolean t) {
		recordWindow.setVisible(t);
	}

	public static void setBeginnerRecords(String[] arr) {
		begin_.setRecords(arr);
	}

	public static void setIntermediateRecords(String[] arr) {
		interm_.setRecords(arr);
	}

	public static void setAdvancedRecords(String[] arr) {
		advan_.setRecords(arr);
	}


	private static class RecordWindowActions implements ActionListener {
		public void actionPerformed(ActionEvent evt) {	
			if(evt.getSource() == okButton)
				show(false);

			else if(evt.getSource() == reset) {
				if (tabbedPane.getSelectedIndex() == 0) {
					Records.resetRecords("beginner");
					begin_.refreshRecords();
				}
				else if(tabbedPane.getSelectedIndex() == 1) {
					Records.resetRecords("intermediate");
					interm_.refreshRecords();
				}
				else if(tabbedPane.getSelectedIndex() == 2) {
					Records.resetRecords("advanced");
					advan_.refreshRecords();
				}	
			}
		}//End actionPerformed
	}


}// End RecordWindow