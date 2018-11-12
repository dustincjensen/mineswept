package gui.statistics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StatisticsWindow {
	private static JFrame statisticWindow;
	private static JPanel statisticPanel;
	private static StatisticsWindowActions swa;

	public static void init() {
		swa = new StatisticsWindowActions();
		setupPane();
		setupWindow();
	}

	private static void setupPane() {
		statisticPanel = new JPanel();
		statisticPanel.setLayout(new BoxLayout(statisticPanel, BoxLayout.Y_AXIS));
		statisticPanel.add(new JButton("Statistics"));
	}

	public static void setupWindow() {
		statisticWindow = new JFrame("Statistics");
		statisticWindow.setContentPane(statisticPanel);

		// statisticWindow.setSize(300,175+Statistics.RECORD_LIMIT*15);
		statisticWindow.pack();
		statisticWindow.setLocationRelativeTo(null);
		statisticWindow.setResizable(false);
		statisticWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public static void show(boolean t) {
		statisticWindow.setVisible(t);
	}

	private static class StatisticsWindowActions implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
		}
	}
}