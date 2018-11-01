package gui.panel.header;

import gui.events.Hint;
import gui.FontChange;
import logic.game.MineField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Renders the mine count panel in the header.
 */
public class MineCount extends JPanel implements ActionListener {
	private static ImageIcon mineImage;
	private static JButton mineIcon;
	private static JLabel mineCount;

	public MineCount() {
		setLayout(new FlowLayout(FlowLayout.LEADING));
		loadImages();
		setupPanel();
	}

	private void loadImages() {
		try {
			mineImage = new ImageIcon(getClass().getResource("/icons/bomb.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupPanel() {
		mineIcon = new JButton(mineImage);
		mineIcon.setToolTipText("Get a hint");
		mineIcon.setBorderPainted(false);
		mineIcon.setContentAreaFilled(false);
		mineIcon.addActionListener(this);
		mineCount = new JLabel("");
		reset();
		FontChange.setFont(mineCount, 24);
		add(mineIcon);
		add(mineCount);
	}

	public static void setMineCount(int minesLeft) {
		mineCount.setText("" + minesLeft);
	}

	public static void reset() {
		setMineCount(MineField.getMineCount());
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == mineIcon) {
			Hint.doEvent();
		}
	}
}