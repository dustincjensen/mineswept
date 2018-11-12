package gui;

import gui.events.IEventPublisher;
import gui.events.QuitGameEvent;
import gui.menu.Menus;
import gui.records.RecordWindow;
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
	private IEventPublisher eventPublisher;
	private Menus menus;

	// TODO make non-static
	private static JFrame window;
	private static MainPanel mp;

	public MineSwept(Menus menus, IEventPublisher publisher) {
		eventPublisher = publisher;

		RecordWindow.init();
		// Loaded after gui starts MOSTLY
		Logic.init();
		mp = new MainPanel();

		window = new JFrame("MineSwept");
		window.setContentPane(mp);
		window.setJMenuBar(menus);
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
		eventPublisher.publish(new QuitGameEvent());
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
		MineSwept ms = ClassFactory.create(MineSwept.class);
		ms.getWindow().setVisible(true);
	}
}