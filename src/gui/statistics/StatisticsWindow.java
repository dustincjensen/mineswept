package gui.statistics;

import java.awt.*;
import javax.swing.*;

public class StatisticsWindow {
	private JFrame statisticWindow;

	public StatisticsWindow() {
		statisticWindow = new JFrame("Statistics");
		statisticWindow.setContentPane(setupPanel());

		// statisticWindow.setSize(300,175+Statistics.RECORD_LIMIT*15);
		statisticWindow.pack();
		statisticWindow.setLocationRelativeTo(null);
		statisticWindow.setResizable(false);
		statisticWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public void show(boolean t) {
		statisticWindow.setVisible(t);
	}

	private JPanel setupPanel() {
		var panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JButton("Statistics"));
		return panel;
	}
}