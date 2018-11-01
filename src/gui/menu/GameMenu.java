package gui.menu;

import gui.events.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
*	GameMenu.java
*	sets up the game menu
*	@author Dustin Jensen
*/
public class GameMenu extends JMenu implements ActionListener {

	private static JMenuItem newGame;
	private static JMenuItem gameOptions;
	private static JMenuItem exitGame;

	/**
	*	Constructor
	*	sets up the JMenu
	*/
	public GameMenu() {
		super("Game");

		//initialize menu items
		newGame = new JMenuItem("New Game");
		gameOptions = new JMenuItem("Options");
		exitGame = new JMenuItem("Close");

		//set mnemonics
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 2));
		gameOptions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,2));
		exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, 2));

		//add actionlisteners
		newGame.addActionListener(this);
		gameOptions.addActionListener(this);
		exitGame.addActionListener(this);

		//add to game menu
		add(newGame);
		addSeparator();
		add(gameOptions);
		addSeparator();
		add(exitGame);
	}//End Constructor

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == newGame)
			New.doEvent();
		if(evt.getSource() == gameOptions)
			Options.doEvent();
		if(evt.getSource() == exitGame)
			Exit.doEvent();
	}//End actionPerformed

}//End class GameMenu