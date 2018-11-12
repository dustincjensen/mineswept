package gui.menu;

import gui.events.IEventPublisher;
import gui.events.QuitGameEvent;
import gui.events.ResetGameEvent;
import gui.events.ShowOptionsEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Sets up the game menu.
 */
public class GameMenu extends JMenu {
	private IEventPublisher eventPublisher;

	public GameMenu(IEventPublisher publisher) {
		super("Game");
		eventPublisher = publisher;
		add(newGame());
		addSeparator();
		add(options());
		addSeparator();
		add(quit());
	}

	/**
	 * Return a menu item that allows the player to start a new game.
	 * 
	 * @return the new game menu item.
	 */
	private JMenuItem newGame() {
		var newGame = new JMenuItem("New Game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 2));
		newGame.addActionListener(evt -> eventPublisher.publish(new ResetGameEvent()));
		return newGame;
	}

	/**
	 * Return a menu item that allows the player to open the options window.
	 * 
	 * @return the options menu item.
	 */
	private JMenuItem options() {
		var gameOptions = new JMenuItem("Options");
		gameOptions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 2));
		gameOptions.addActionListener(evt -> eventPublisher.publish(new ShowOptionsEvent(true)));
		return gameOptions;
	}

	/**
	 * Return a menu item that allows the player to quit the game.
	 * 
	 * @return the quit menu item.
	 */
	private JMenuItem quit() {
		var quitGame = new JMenuItem("Quit");
		quitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, 2));
		quitGame.addActionListener(evt -> eventPublisher.publish(new QuitGameEvent()));
		return quitGame;
	}
}