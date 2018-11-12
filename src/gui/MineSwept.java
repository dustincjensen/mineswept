package gui;

import gui.events.EventPublisher;
import gui.events.QuitGameEvent;
import gui.menu.Menus;
import gui.options.OptionWindow;
import gui.records.RecordWindow;
import gui.statistics.StatisticsWindow;
import gui.panel.MainPanel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import logic.files.FileManagement;
import logic.Logic;

/**
 * Runs the JFrame for the game.
 */
public class MineSwept implements WindowListener {
	private static JFrame window;
	private static MainPanel mp;
	private static Menus mb;

	public MineSwept() {
		RecordWindow.init();
		// Loaded after gui starts MOSTLY
		Logic.init();
		mp = new MainPanel();

		// TODO replace...
		// mb = new Menus();
		mb = ClassFactory.create(Menus.class);

		window = new JFrame("MineSwept");
		window.setContentPane(mp);
		window.setJMenuBar(mb);
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addWindowListener(this);

		try {
			ImageIcon frameIcon = new ImageIcon((MineSwept.class).getResource("/icons/smiley-cool.png"));
			window.setIconImage(frameIcon.getImage());
		} catch (Exception e) {
			System.out.println("Failed to load JFrame icon");
			System.exit(1);
		}
	}

	public static JFrame getWindow() {
		return window;
	}

	public static MainPanel getMainPanel() {
		return mp;
	}

	public static void refresh() {
		window.pack();
		window.getContentPane().repaint();
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		var pub = new EventPublisher(new OptionWindow(), new StatisticsWindow());
		pub.publish(new QuitGameEvent());
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	/**
	 * Main entry point for the application.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		MineSwept ms = new MineSwept();
		ms.getWindow().setVisible(true);
	}
}