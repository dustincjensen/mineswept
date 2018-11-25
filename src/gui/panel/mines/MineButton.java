package gui.panel.mines;

import logic.game.*;
import logic.files.Preferences;
import gui.FontChange;
import gui.Resource;
import gui.ResourceLoader;
import gui.events.SetResetButtonIconEvent;
import gui.events.IEventPublisher;
import gui.panel.header.MineCount;
import gui.panel.header.ResetButton;

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
	private static int w, h; // 32, 48 //font size, 22 for 32 and 32 for 48
	private int x, y;
	private int fqe; // empty flag question //state of the button

	private static int dragX, dragY;
	private static boolean insideSquares, startedHere;
	/*
	 * inside squares fixes the cursor leaving the buttons and still clicking the
	 * last answered but I need a listener on the panel to know when it leaves to
	 * change the face and to know when the mouse was released from a press that
	 * starts outside of the buttons or else MineButtons don't get the release
	 * event.
	 */

	private static final BevelBorder RAISEDBORDER = new BevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY);
	private static final BevelBorder LOWEREDBORDER = new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY);
	private static final Color[] colors = { Color.BLUE, // One
			new Color(67, 150, 67), // Two
			Color.RED, // Three
			new Color(75, 0, 75), // Four
			new Color(128, 0, 0), // Five
			new Color(64, 224, 208), // Six
			new Color(160, 92, 240), // Seven
			new Color(0, 0, 0) // Eight
	};
	private static Color backgroundColor;
	private static Color defaultColor = new Color(50, 125, 240);

	private static GameState gameState;
	private static ClockTimer clockTimer;
	private static ResourceLoader resourceLoader;
	private static IEventPublisher eventPublisher;
	public MineButton(
		Preferences prefs,
		GameState state,
		ClockTimer timer,
		ResourceLoader loader,
		IEventPublisher publisher
	) {
		gameState = state;
		clockTimer = timer;
		resourceLoader = loader;
		eventPublisher = publisher;

		// TODO the colors from the preferences should be pre-parsed... so for now, just parse if the backgroundColor is null...
		if (backgroundColor == null) {
			parseColor(prefs.r(), prefs.g(), prefs.b());
			System.out.println("Only parsing the Mine Button Color preferences one time.");
		}

		FontChange.setFont(this, 32);
		w = 48;
		h = 48;
		fqe = 0;
		setPreferredSize(new Dimension(w, h));
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(true);
		setBackground(backgroundColor);
		setBorder(RAISEDBORDER);
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
		Mine t = MineField.getMine(x, y);
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
				if (val > 0) {
					setText("" + val);
					setIcon(null);
					setForeground(colors[val - 1]);
				} else {
					setText("");
					setIcon(null);
					setForeground(null);
				}
			}

			// if its uncovered, its LOWEREDBORDER
			setBorder(LOWEREDBORDER);

			// background is always NULL unless BLEWUP is true
			if (t.blewUp())
				setBackground(Color.RED);
			else
				setBackground(null);
		}

		// Still covered
		else {
			setBackground(backgroundColor);

			// check for hint (empty space)
			if (!gameState.isGameOver() && t.isHint()) {
				if (t.isSpecialProtected())
					setIcon(resourceLoader.get(Resource.FlagHint));
				else
					setIcon(resourceLoader.get(Resource.MineHint));
				setText("");
				t.setHint(false);
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

	public static void useDefaultBackgroundColor() {
		backgroundColor = defaultColor;
	}

	public static void setBackgroundColor(Color c) {
		backgroundColor = c;
	}

	public static Color getBackgroundColor() {
		return backgroundColor;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	private static void parseColor(String r, String g, String b) {
		int red, green, blue;
		red = green = blue = -1;
		r = r.toLowerCase();
		g = g.toLowerCase();
		b = b.toLowerCase();

		int pic = parseIndividualColor(r);
		int who = Integer.parseInt(r.substring(r.indexOf("=") + 1).trim());
		if (pic == 1)
			red = who;
		else if (pic == 2)
			green = who;
		else if (pic == 3)
			blue = who;

		pic = parseIndividualColor(g);
		who = Integer.parseInt(g.substring(g.indexOf("=") + 1).trim());
		if (pic == 1 && red == -1)
			red = who;
		else if (pic == 1 && red != -1) {
			useDefaultBackgroundColor();
			return;
		} else if (pic == 2 && green == -1)
			green = who;
		else if (pic == 2 && green != -1) {
			useDefaultBackgroundColor();
			return;
		} else if (pic == 3 && blue == -1)
			blue = who;
		else if (pic == 3 && blue != -1) {
			useDefaultBackgroundColor();
			return;
		}

		pic = parseIndividualColor(b);
		who = Integer.parseInt(b.substring(b.indexOf("=") + 1).trim());
		if (pic == 1 && red == -1)
			red = who;
		else if (pic == 1 && red != -1) {
			useDefaultBackgroundColor();
			return;
		} else if (pic == 2 && green == -1)
			green = who;
		else if (pic == 2 && green != -1) {
			useDefaultBackgroundColor();
			return;
		} else if (pic == 3 && blue == -1)
			blue = who;
		else if (pic == 3 && blue != -1) {
			useDefaultBackgroundColor();
			return;
		}

		if (red > 255 || green > 255 || blue > 255)
			useDefaultBackgroundColor();
		else
			setBackgroundColor(new Color(red, green, blue));
	}

	private static int parseIndividualColor(String c) {
		if (c.indexOf("r") != -1)
			return 1;
		else if (c.indexOf("g") != -1)
			return 2;
		else if (c.indexOf("b") != -1)
			return 3;
		else {
			useDefaultBackgroundColor();
			return -1;
		}
	}

	// ==============================================
	// MOUSE LISTENER
	// ==============================================
	public void mouseEntered(MouseEvent e) {
		if (!gameState.isGameOver() && startedHere) {
			int modifiers = e.getModifiersEx();
			int mask = InputEvent.BUTTON1_DOWN_MASK;
			if ((modifiers & mask) == mask) {
				Mine get = MineField.getMine(x, y);
				dragX = x;
				dragY = y;
				insideSquares = true;
				if (!get.uncovered() && !get.getAnyProtected()) {
					eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileySurprised));
					setBorder(LOWEREDBORDER);
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
				Mine get = MineField.getMine(x, y);
				if (!get.uncovered()) {
					setBorder(RAISEDBORDER);
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
				Mine get = MineField.getMine(x, y);
				if (!get.uncovered() && !get.getAnyProtected()) {
					setBorder(LOWEREDBORDER);
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

			Mine mineSpot = MineField.getMine(x, y);
			if (!mineSpot.uncovered() && !mineSpot.isSpecialProtected()) {
				if (fqe == 2)
					fqe = 0;
				else
					fqe++;

				if (fqe == 1) {
					setIcon(resourceLoader.get(Resource.Flag));
					setText("");
					mineSpot.setProtected(true);

				} else if (fqe == 2) {
					setIcon(null);
					setText("?");
					setForeground(Color.WHITE);
					mineSpot.setProtected(false);

				} else if (fqe == 0) {
					setIcon(null);
					setText("");
					mineSpot.setProtected(false);
				}
				MineCount.setMineCount(MineField.getMineCount());
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
						Mine get = MineField.getMine(x, y);
						if (!get.getAnyProtected())
							MineField.leftClicked(x, y);
					} else {
						Mine get = MineField.getMine(dragX, dragY);
						if (!get.getAnyProtected())
							MineField.leftClicked(dragX, dragY);
					}
				}
				dragX = dragY = -1;
				insideSquares = false;
				startedHere = false;
				MinePanel.update();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}
}
