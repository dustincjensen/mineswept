package gui.panel.mines;

import logic.game.*;
import logic.files.Preferences;
import gui.FontChange;
import gui.Resource;
import gui.ResourceLoader;
import gui.events.SetResetButtonIconEvent;
import gui.events.UpdateMineCountEvent;
import gui.events.UpdateMinePanelEvent;
import gui.events.IEventPublisher;
import gui.events.IEventSubscriber;
import gui.panel.header.MineCount;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;

/**
 * Setup a mine button.
 */
public class MineButton extends JLabel implements MouseListener {
	private int x, y;

	/**
	 * 0 = empty
	 * 1 = flag
	 * 2 = question mark
	 * This is the state of the button when not clicked.
	 */
	private int nonClickedState;

	private static int dragX, dragY;
	private static boolean insideSquares, startedHere;
	/*
	 * inside squares fixes the cursor leaving the buttons and still clicking the
	 * last answered but I need a listener on the panel to know when it leaves to
	 * change the face and to know when the mouse was released from a press that
	 * starts outside of the buttons or else MineButtons don't get the release
	 * event.
	 */

	private static Color backgroundColor;

	private static GameState gameState;
	private static ClockTimer clockTimer;
	private static ResourceLoader resourceLoader;
	private static IEventPublisher eventPublisher;
	private static IEventSubscriber eventSubscriber;
	public MineButton(
		Preferences prefs,
		GameState state,
		ClockTimer timer,
		ResourceLoader loader,
		IEventPublisher publisher,
		IEventSubscriber subscriber
	) {
		gameState = state;
		clockTimer = timer;
		resourceLoader = loader;
		eventPublisher = publisher;
		eventSubscriber = subscriber;

		// TODO when this is no longer static, you will need to do this for each mine button.
		if (backgroundColor == null) {
			setBackgroundColor(new Color(prefs.r(), prefs.g(), prefs.b()));
			System.out.println("Only setting the Mine Button Color preferences one time.");
		}
		
		nonClickedState = 0;

		// TODO allow resizing?
		// Font size 32 when w,h = 48
		// Font size 22 when w,h = 32
		FontChange.setFont(this, 32);
		int w = 48, h = 48;
		setPreferredSize(new Dimension(w, h));
		
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(true);
		setBackground(backgroundColor);
		setBorder(Styles.RAISED_BORDER);
		addMouseListener(this);
	}

	public static void init() {
		reset();
	}

	public static void reset() {
		dragX = -1;
		dragY = -1;
	}

	public void decorate() {
		Mine t = gameState.getMine(x, y);
		
		// Uncovered
		if (t.uncovered()) {
			// A bomb
			if (t.isBomb()) {
				setText("");
				setIcon(resourceLoader.get(Resource.Mine));
			}
			// Not a bomb but is has flag, is wrong
			else if (!t.isBomb() && t.isProtected()) {
				setText("");
				setIcon(resourceLoader.get(Resource.MineWrong));
			}
			// A regular square or empty
			else {
				int val = t.getSpotValue();
				setText(val > 0 ? "" + val : "");
				setForeground(val > 0 ? Styles.MINE_NUMBER_COLORS[val - 1] : null);
				setIcon(null);
			}

			// If the mine is uncovered we lower the border.
			setBorder(Styles.LOWERED_BORDER);
			setBackground(t.blewUp() ? Styles.FAILED_MINE_CLICKED_BACKGROUND_COLOR : null);
		}

		// Still covered
		else {
			setBackground(backgroundColor);

			// check for hint (empty space)
			if (!gameState.isGameOver() && t.isHint()) {
				setIcon(t.isSpecialProtected() 
					? resourceLoader.get(Resource.FlagHint) 
					: resourceLoader.get(Resource.MineHint));
				setText("");
			}

			// allows the changing of color if the options changes it
			if (gameState.isGameOver() && t.isBomb() && !t.getAnyProtected()) {
				setIcon(resourceLoader.get(Resource.Mine));
				setText("");
			}
		}
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static void setBackgroundColor(Color c) {
		backgroundColor = c;
	}

	public static Color getBackgroundColor() {
		return backgroundColor;
	}

	// ==============================================
	// MOUSE LISTENER
	// ==============================================
	public void mouseEntered(MouseEvent e) {
		if (!gameState.isGameOver() && startedHere) {
			int modifiers = e.getModifiersEx();
			int mask = InputEvent.BUTTON1_DOWN_MASK;
			if ((modifiers & mask) == mask) {
				Mine get = gameState.getMine(x, y);
				dragX = x;
				dragY = y;
				insideSquares = true;
				if (!get.uncovered() && !get.getAnyProtected()) {
					eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileySurprised));
					setBorder(Styles.LOWERED_BORDER);
					setBackground(null);
				} else {
					eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileyHappy));
				}
			}
		}
	}

	public void mouseExited(MouseEvent e) {
		if (!gameState.isGameOver() && startedHere) {
			int modifiers = e.getModifiersEx();
			int mask = InputEvent.BUTTON1_DOWN_MASK;
			if ((modifiers & mask) == mask) {
				insideSquares = false;
				Mine get = gameState.getMine(x, y);
				if (!get.uncovered()) {
					setBorder(Styles.RAISED_BORDER);
					setBackground(backgroundColor);
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		// Left Mouse Button
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!gameState.isGameStarted()) {
				gameState.setGameStarted(true);
				clockTimer.start();
			}
			if (!gameState.isGameOver()) {
				startedHere = true;
				insideSquares = true;
				Mine get = gameState.getMine(x, y);
				if (!get.uncovered() && !get.getAnyProtected()) {
					setBorder(Styles.LOWERED_BORDER);
					setBackground(null);
					eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileySurprised));
				}
			}
		}

		// Right mouse button
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (!gameState.isGameStarted()) {
				gameState.setGameStarted(true);
				clockTimer.start();
			}
			if (gameState.isGameOver())
				return;

			Mine mineSpot = gameState.getMine(x, y);
			if (!mineSpot.uncovered() && !mineSpot.isSpecialProtected()) {
				if (nonClickedState == 2) {
					nonClickedState = 0;
				}
				else {
					nonClickedState++;
				}

				if (nonClickedState == 1) {
					setIcon(resourceLoader.get(Resource.Flag));
					setText("");
					mineSpot.setProtected(true);
				} else if (nonClickedState == 2) {
					setIcon(null);
					setText("?");
					setForeground(Color.WHITE);
					mineSpot.setProtected(false);
				} else if (nonClickedState == 0) {
					setIcon(null);
					setText("");
					mineSpot.setProtected(false);
				}

				// TODO should MineButton be notifying directly?
				// instead...
				// publish mousePressedRightClick
				// then gets notified on... mineProtectedEvent?
				eventSubscriber.notify(new UpdateMineCountEvent());
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		// Left Mouse Button
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!gameState.isGameOver()) {
				eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileyHappy));
				if (insideSquares == true) {
					if (dragX == -1 && dragY == -1) {
						Mine get = gameState.getMine(x, y);
						if (!get.getAnyProtected())
							MineField.leftClicked(x, y);
					} else {
						Mine get = gameState.getMine(dragX, dragY);
						if (!get.getAnyProtected())
							MineField.leftClicked(dragX, dragY);
					}
				}
				dragX = dragY = -1;
				insideSquares = false;
				startedHere = false;

				eventSubscriber.notify(new UpdateMinePanelEvent());
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}
}
