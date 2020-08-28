package ui.layout.body;

import events.IEventPublisher;
import events.MineClickedEvent;
import events.SetResetButtonIconEvent;
import events.StartClockTimerEvent;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.Border;
import models.Mine;
import models.MineState;
import models.Resource;
import state.GameState;
import ui.utils.FontChange;

/**
 * Setup a mine button.
 */
@SuppressWarnings("serial")
public class MineButton extends JLabel implements MouseListener {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private ImageIcon mineIcon;
	private ImageIcon mineWrongIcon;
	private ImageIcon flagIcon;

	private int x, y;
	private Color backgroundColor;
	private Color altBackgroundColor;
	private Color clickedBackgroundColor;
	private Color clickedAltBackgroundColor;
	private Color failedBackgroundColor;
	private Border raisedBorder;
	private Border loweredBorder;
	private Color[] mineColors;

	// TODO address these statics....
	private static int dragX, dragY;

	/**
	 * This is used to fix the cursor leaving the buttons and still clicking the
	 * last highlighted mine.
	 */
	private static boolean insideSquares;

	/**
	 * We don't want a click that starts at the top of the screen to start the game
	 * if dragged into the mine field. They need to originate their click inside
	 * the mine field.
	 */
	private static boolean mousePressStartedInsideSquare;

	public MineButton(
		GameState gameState,
		IEventPublisher eventPublisher,
		ImageIcon mineIcon,
		ImageIcon mineWrongIcon,
		ImageIcon flagIcon
	) {
		this.gameState = gameState;
		this.eventPublisher = eventPublisher;
		this.mineIcon = mineIcon;
		this.mineWrongIcon = mineWrongIcon;
		this.flagIcon = flagIcon;

		// TODO allow resizing?
		// Font size 32 when w,h = 48
		// Font size 22 when w,h = 32
		FontChange.setFont(this, 32);
		int w = 48, h = 48;

		// Set all these the same to force the layout to behave...
		var dim = new Dimension(w, h);
		setPreferredSize(dim);
		setMaximumSize(dim);
		setMinimumSize(dim);
		
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(true);
		setBorder(raisedBorder);
		setFocusable(true);
		addMouseListener(this);

		dragX = dragY = -1;
		insideSquares = mousePressStartedInsideSquare = false;
	}

	// TODO could take colors object from MinePanel invocation.
	// TODO could subscribe to colors updated event? Lots of buttons subscribed, but only need to trigger when options change.
	// 	    Would also need to unsubscribe when new mines created.
	public void decorate() {
		Mine t = gameState.getMine(x, y);
		
		// Uncovered
		if (t.uncovered()) {
			// A bomb
			if (t.isBomb()) {
				setText("");
				setIcon(mineIcon);
			}
			// Not a bomb but is has flag, is wrong
			else if (!t.isBomb() && t.isProtected()) {
				setText("");
				setIcon(mineWrongIcon);
			}
			// A regular square or empty
			else {
				int val = t.getSpotValue();
				setText(val > 0 ? "" + val : "");
				setForeground(val > 0 ? mineColors[val - 1] : null);
				setIcon(null);
			}

			// If the mine is uncovered we lower the border.
			setBorder(loweredBorder);
			if (t.blewUp()) {
				setBackground(failedBackgroundColor);
			} else {
				setBackground(clickedBackgroundColor, clickedAltBackgroundColor);
			}
		}

		// Still covered
		else {
			// Might need to reset background color after a left click drag to a right click flagging.
			setBackground(backgroundColor, altBackgroundColor);
			setBorder(raisedBorder);

			if (!gameState.isGameOver()) {
				var newState = t.getMineState();
				setIcon(newState == MineState.Flag ? flagIcon : null);
				setForeground(newState == MineState.QuestionMark ? Color.WHITE : null);
				setText(newState == MineState.QuestionMark ? "?" : "");
			} else {
				if (t.isBomb() && !t.isProtected()) {
					setIcon(mineIcon);
					setText("");
				}
			}
		}
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		setBackground(backgroundColor, altBackgroundColor);
	}

	public void setColors(
		Color bgColor,
		Color altBgColor,
		Color cBgColor,
		Color cAltBgColor,
		Color fBgColor,
		Color[] mColors) {
		backgroundColor = bgColor;
		altBackgroundColor = altBgColor;
		clickedBackgroundColor = cBgColor;
		clickedAltBackgroundColor = cAltBgColor;
		failedBackgroundColor = fBgColor;
		mineColors = mColors;
	}

	public void setBorders(Border raised, Border lowered) {
		raisedBorder = raised;
		loweredBorder = lowered;
	}

	/**
	 * Depending on the x,y coordinates of the mine button,
	 * it will receive a different background color.
	 * 
	 * @param b1 the first background color to use.
	 * @param b2 the second background color to use.
	 */
	private void setBackground(Color b1, Color b2) {
		if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
			setBackground(b1);
		} else {
			setBackground(b2);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (gameState.isGameOver() || !mousePressStartedInsideSquare) {
			return;
		}

		int modifiers = e.getModifiersEx();
		int mask = InputEvent.BUTTON1_DOWN_MASK;
		if ((modifiers & mask) == mask) {
			var mineSpot = gameState.getMine(x, y);
			dragX = x;
			dragY = y;
			insideSquares = true;
			if (!mineSpot.uncovered() && !mineSpot.isProtected()) {
				eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileySurprised));
				setBorder(loweredBorder);
				setBackground(clickedBackgroundColor, clickedAltBackgroundColor);
			} else {
				eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileyHappy));
			}
		}
	}

	public void mouseExited(MouseEvent e) {
		if (gameState.isGameOver() || !mousePressStartedInsideSquare) {
			return;
		}

		int modifiers = e.getModifiersEx();
		int mask = InputEvent.BUTTON1_DOWN_MASK;
		if ((modifiers & mask) == mask) {
			insideSquares = false;
			var mineSpot = gameState.getMine(x, y);
			if (!mineSpot.uncovered()) {
				setBorder(raisedBorder);
				setBackground(backgroundColor, altBackgroundColor);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		// Request focus so the buttons in the header
		// don't stay highlighted when they are not clicked
		// on anymore.
		this.requestFocusInWindow();

		// Don't do anything when the game is over
		if (gameState.isGameOver()) {
			return;
		}

		// Mouse was pressed, start the game if it hasn't already been.
		if (!gameState.isGameStarted()) {
			gameState.setGameStarted(true);
			eventPublisher.publish(new StartClockTimerEvent());
		}

		// Left Mouse Button
		if (e.getButton() == MouseEvent.BUTTON1) {
			mousePressStartedInsideSquare = true;
			insideSquares = true;
			var mineSpot = gameState.getMine(x, y);
			if (!mineSpot.uncovered() && !mineSpot.isProtected()) {
				setBorder(loweredBorder);
				setBackground(clickedBackgroundColor, clickedAltBackgroundColor);
				eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileySurprised));
			}
		}

		// Right mouse button
		else if (e.getButton() == MouseEvent.BUTTON3) {
			eventPublisher.publish(
				new MineClickedEvent(
					false, 
					true, 
					x, y, 
					dragX, dragY));
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			eventPublisher.publish(
				new MineClickedEvent(
					true, 
					insideSquares, 
					x, y, 
					dragX, dragY));

			dragX = dragY = -1;
			insideSquares = false;
			mousePressStartedInsideSquare = false;
		}
	}

	public void mouseClicked(MouseEvent e) {
	}
}
