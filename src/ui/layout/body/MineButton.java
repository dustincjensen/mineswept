package ui.layout.body;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.MineClickedEvent;
import events.SetResetButtonIconEvent;
import events.StartClockTimerEvent;
import events.UpdateMineCountEvent;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import models.Mine;
import models.Resource;
import services.OptionsService;
import state.GameState;
import ui.utils.ColorConverter;
import ui.utils.FontChange;

/**
 * Setup a mine button.
 */
public class MineButton extends JLabel implements MouseListener {
	private GameState gameState;
	private OptionsService optionsService;
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ImageIcon mineIcon;
	private ImageIcon mineWrongIcon;
	private ImageIcon mineHintIcon;
	private ImageIcon flagIcon;
	private ImageIcon flagHintIcon;

	private int x, y;
	private Color backgroundColor;

	// TODO this should not be an int, it should be an enum.
	/**
	 * 0 = empty
	 * 1 = flag
	 * 2 = question mark
	 * This is the state of the button when not clicked.
	 */
	private int nonClickedState;

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
		OptionsService optionsService,
		IEventPublisher eventPublisher,
		IEventSubscriber eventSubscriber,
		ImageIcon mineIcon,
		ImageIcon mineWrongIcon,
		ImageIcon mineHintIcon,
		ImageIcon flagIcon,
		ImageIcon flagHintIcon
	) {
		this.gameState = gameState;
		this.optionsService = optionsService;
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		this.mineIcon = mineIcon;
		this.mineWrongIcon = mineWrongIcon;
		this.mineHintIcon = mineHintIcon;
		this.flagIcon = flagIcon;
		this.flagHintIcon = flagHintIcon;

		nonClickedState = 0;

		// TODO move this to some shared state?
		backgroundColor = ColorConverter.convert(optionsService.squareColor());

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

		dragX = dragY = -1;
		insideSquares = mousePressStartedInsideSquare = false;
	}

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
				setForeground(val > 0 ? Styles.MINE_NUMBER_COLORS[val - 1] : null);
				setIcon(null);
			}

			// If the mine is uncovered we lower the border.
			setBorder(Styles.LOWERED_BORDER);
			setBackground(t.blewUp() ? Styles.FAILED_MINE_CLICKED_BACKGROUND_COLOR : null);
		}

		// Still covered
		else {
			// TODO if we don't cache the options... this would read from file 16x30 times.
			// TODO This is probably pretty heavy. Any time a mine is clicked, MinePanel handles the UpdateMinePanelEvent
			// which redecorates each mine, and we are creating the color for 16x30 mines on each render.
			backgroundColor = ColorConverter.convert(optionsService.squareColor());
			setBackground(backgroundColor);

			// check for hint (empty space)
			if (!gameState.isGameOver() && t.isHint()) {
				setIcon(t.isSpecialProtected() ? flagHintIcon : mineHintIcon);
				setText("");
			}

			if (gameState.isGameOver() && t.isBomb() && !t.getAnyProtected()) {
				setIcon(mineIcon);
				setText("");
			}
		}
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// ==============================================
	// MOUSE LISTENER
	// ==============================================
	public void mouseEntered(MouseEvent e) {
		if (!gameState.isGameOver() && mousePressStartedInsideSquare) {
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
		if (!gameState.isGameOver() && mousePressStartedInsideSquare) {
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
				eventPublisher.publish(new StartClockTimerEvent());
			}
			if (!gameState.isGameOver()) {
				mousePressStartedInsideSquare = true;
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
			if (gameState.isGameOver()) return;
			
			if (!gameState.isGameStarted()) {
				gameState.setGameStarted(true);
				eventPublisher.publish(new StartClockTimerEvent());
			}

			Mine mineSpot = gameState.getMine(x, y);
			if (!mineSpot.uncovered() && !mineSpot.isSpecialProtected()) {
				if (nonClickedState == 2) {
					nonClickedState = 0;
				}
				else {
					nonClickedState++;
				}

				mineSpot.setProtected(nonClickedState == 1);
				setIcon(nonClickedState == 1 ? flagIcon : null);
				setForeground(nonClickedState == 2 ? Color.WHITE : null);
				setText(nonClickedState == 2 ? "?" : "");

				// TODO should MineButton be notifying directly?
				// instead...
				// publish mousePressedRightClick
				// then gets notified on... mineProtectedEvent?
				eventSubscriber.notify(new UpdateMineCountEvent());
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		eventPublisher.publish(new MineClickedEvent(e.getButton() == MouseEvent.BUTTON1, insideSquares, x, y, dragX, dragY));

		dragX = dragY = -1;
		insideSquares = false;
		mousePressStartedInsideSquare = false;
	}

	public void mouseClicked(MouseEvent e) {
	}
}
