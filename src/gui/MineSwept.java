package gui;

import gui.events.IEventPublisher;
import gui.events.IEventSubscriber;
import gui.events.QuitGameEvent;
import gui.menu.Menus;
import gui.panel.MainPanel;
import gui.Resource;
import java.awt.*;
import javax.swing.*;
import logic.files.*;
import logic.Logic;
import logic.game.*;

/**
 * Runs the JFrame for the game.
 */
public class MineSwept {
	// TODO make non-static
	private static JFrame window;
	private static MainPanel mp;

	public MineSwept(
		Menus menus,
		IEventPublisher publisher,
		IEventSubscriber subscriber,
		MainWindowHandler mainWindowHandler,
		ResourceLoader loader,
		GameState gameState,
		ClockTimer clockTimer
	) {
		Logic.init(gameState, clockTimer, publisher);
		mp = new MainPanel(subscriber);

		window = new JFrame("MineSwept");
		window.setContentPane(mp);
		window.setJMenuBar(menus);
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addWindowListener(mainWindowHandler);
		window.setIconImage(loader.get(Resource.SmileyCool).getImage());
	}

	public void showWindow() {
		window.setVisible(true);
	}

	public static void refresh() {
		window.pack();
		window.getContentPane().repaint();
	}

	/**
	 * Main entry point for the application.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		MineSwept mineSwept = ClassFactory.create(MineSwept.class);
		mineSwept.showWindow();
	}
}