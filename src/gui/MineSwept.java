package gui;

import gui.events.IEventPublisher;
import gui.events.IEventSubscriber;
import gui.events.RefreshMainWindowEvent;
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
	private IEventSubscriber eventSubscriber;
	// TODO should the window be provided in it's own component?
	private JFrame window;
	
	public MineSwept(
		Menus menus,
		IEventPublisher publisher,
		IEventSubscriber subscriber,
		MainWindowHandler mainWindowHandler,
		ResourceLoader loader,
		GameState gameState,
		ClockTimer clockTimer
	) {
		eventSubscriber = subscriber;

		Logic.init(gameState, clockTimer, publisher);
		MainPanel mp = new MainPanel(subscriber);

		window = new JFrame("MineSwept");
		window.setContentPane(mp);
		window.setJMenuBar(menus);
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addWindowListener(mainWindowHandler);
		window.setIconImage(loader.get(Resource.SmileyCool).getImage());

		setupSubscription();
	}

	public void showWindow() {
		window.setVisible(true);
	}

	private void setupSubscription() {
		eventSubscriber.subscribe(RefreshMainWindowEvent.class, (event) -> {
			window.pack();
			window.getContentPane().repaint();
		});
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