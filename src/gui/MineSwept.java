package gui;

import gui.events.EventInitialize;
import gui.events.Exit;
import gui.menu.OptionWindow;
import gui.menu.RecordWindow;
import gui.menu.StatisticsWindow;
import gui.menu.MenuLine;
import gui.panel.MainPanel;
import logic.Logic;
import logic.files.FileManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

/**
*	MineSwept.java
*	runs the JFrame for the game
*	@author Dustin Jensen
*/
public class MineSwept implements WindowListener {

	//private static int screenW = 300;
	//private static int screenH = 300;

	private static JFrame window;
	private static MainPanel mp;
	private static MenuLine mb;

	/**
	*	Constructor
	*	sets up the JFrame
	*/
	public MineSwept() {		
		RecordWindow.init();
		EventInitialize.init();
		StatisticsWindow.init();
		Logic.init();	// loaded after gui starts MOSTLY
		OptionWindow.init();
		mp = new MainPanel();
		mb = new MenuLine();

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
	}//End Constructor

	public static JFrame getWindow() {
		return window;
	}

	public static MainPanel getMainPanel() {
		return mp;
	}

	public static MenuLine getMenuLine() {
		return mb;
	}

	public static void refresh() {
		window.pack();
		window.getContentPane().repaint();
		//window.setLocationRelativeTo(null);
	}

	public void windowActivated(WindowEvent e) {}
 	public void windowClosed(WindowEvent e) {}
 	public void windowClosing(WindowEvent e) {
 		Exit.doEvent();
 	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
 
	/**
	*	main
	*	creates a window of MineSwept
	*	and sets it visible
	*/
	public static void main(String[] args) {
		MineSwept ms = new MineSwept();
		ms.getWindow().setVisible(true);
	}//End main

}//End class MineSwept