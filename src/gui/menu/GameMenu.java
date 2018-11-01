package gui.menu;

import gui.events.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Sets up the game menu.
 */
public class GameMenu extends JMenu implements ActionListener {
	private static JMenuItem newGame;
	private static JMenuItem gameOptions;
	private static JMenuItem exitGame;

	public GameMenu() {
		super("Game");

		// Initialize menu items
		newGame = new JMenuItem("New Game");
		gameOptions = new JMenuItem("Options");
		exitGame = new JMenuItem("Close");

		// Set mnemonics
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 2));
		gameOptions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 2));
		exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, 2));

		// Add actionlisteners
		newGame.addActionListener(this);
		gameOptions.addActionListener(this);
		exitGame.addActionListener(this);

		// Add to game menu
		add(newGame);
		addSeparator();
		add(gameOptions);
		addSeparator();
		add(exitGame);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == newGame)
			New.doEvent();
		if (evt.getSource() == gameOptions)
			Options.doEvent();
		if (evt.getSource() == exitGame)
			Exit.doEvent();
	}
}