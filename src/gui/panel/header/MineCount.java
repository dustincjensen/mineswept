package gui.panel.header;

import gui.events.GetHintEvent;
import gui.events.IEventPublisher;
import gui.FontChange;
import java.awt.*;
import javax.swing.*;
import logic.game.MineField;

/**
 * Renders the mine count panel in the header.
 */
public class MineCount extends JPanel {
	private IEventPublisher eventPublisher;
	private ImageIcon mineImage;
	private JButton mineIcon;
	// TODO make non-static
	private static JLabel mineCount;

	public MineCount(IEventPublisher publisher) {
		eventPublisher = publisher;
		setLayout(new FlowLayout(FlowLayout.LEADING));
		setupPanel();
	}

	// TODO make non-static
	public static void setMineCount(int minesLeft) {
		mineCount.setText("" + minesLeft);
	}

	// TODO make non-static
	public static void reset() {
		setMineCount(MineField.getMineCount());
	}

	private void setupPanel() {
		mineIcon = new JButton(loadImage());
		mineIcon.setToolTipText("Get a hint");
		mineIcon.setBorderPainted(false);
		mineIcon.setContentAreaFilled(false);
		mineIcon.addActionListener(evt -> {
			eventPublisher.publish(new GetHintEvent());
		});
		add(mineIcon);

		mineCount = new JLabel("");
		reset();
		FontChange.setFont(mineCount, 24);
		add(mineCount);
	}

	private ImageIcon loadImage() {
		try {
			return new ImageIcon(getClass().getResource("/icons/bomb.png"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}