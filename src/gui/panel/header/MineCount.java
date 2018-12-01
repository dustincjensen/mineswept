package gui.panel.header;

import gui.events.GetHintEvent;
import gui.events.IEventPublisher;
import gui.FontChange;
import gui.Resource;
import gui.ResourceLoader;
import java.awt.*;
import javax.swing.*;
import logic.game.GameState;
import logic.game.MineField;

/**
 * Renders the mine count panel in the header.
 */
public class MineCount extends JPanel {
	private static GameState gameState;
	private IEventPublisher eventPublisher;
	private ResourceLoader resourceLoader;
	private ImageIcon mineImage;
	private JButton mineIcon;
	// TODO make non-static
	private static JLabel mineCount;

	public MineCount(GameState state, IEventPublisher publisher, ResourceLoader loader) {
		gameState = state;
		eventPublisher = publisher;
		resourceLoader = loader;
		setLayout(new FlowLayout(FlowLayout.LEADING));
		setupPanel();
	}

	// TODO make non-static
	public static void setMineCount(int minesLeft) {
		mineCount.setText("" + minesLeft);
	}

	// TODO make non-static
	public static void reset() {
		setMineCount(gameState.getMineCount());
	}

	private void setupPanel() {
		mineIcon = new JButton(resourceLoader.get(Resource.BombHint));
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
}