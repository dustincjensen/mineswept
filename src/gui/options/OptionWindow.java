package gui.options;

import logic.game.Difficulty;
// TODO these static dependencies need to have their information placed into an "Options" dependency and then retrieve the information and set the information to that.
import logic.game.MineField;
import gui.panel.header.ResetButton;
import gui.panel.mines.MineButton;
import gui.panel.mines.MinePanel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class OptionWindow {
	private JFrame optionsWindow;
	private boolean optionsHaveChanged;
	private ButtonGroup difficultyRadioButtonGroup;
	private JRadioButton easy, medium, hard;
	private JButton mineButtonColor;

	public OptionWindow() {
		optionsWindow = new JFrame("Options");
		optionsWindow.setContentPane(mainPanel());
		optionsWindow.setSize(500, 350);
		optionsWindow.setLocationRelativeTo(null);
		optionsWindow.setResizable(false);
		optionsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		optionsHaveChanged = false;
	}

	public void show(boolean showOptionsWindow) {
		if (!showOptionsWindow) {
			saveCurrentOptions();
		}
		optionsWindow.setVisible(showOptionsWindow);
	}

	private JPanel mainPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(subPanePanel());
		mainPanel.add(acceptDeclinePanel());
		return mainPanel;
	}

	private JPanel subPanePanel() {
		var subPane = new JPanel();
		subPane.setLayout(new BoxLayout(subPane, BoxLayout.X_AXIS));
		subPane.add(difficultyPanel());
		subPane.add(otherOptionsPanel());
		return subPane;
	}

	private JPanel acceptDeclinePanel() {
		var acceptDecline = new JPanel();
		acceptDecline.setLayout(new BoxLayout(acceptDecline, BoxLayout.X_AXIS));
		acceptDecline.add(Box.createHorizontalGlue());
		acceptDecline.add(confirmButton());
		acceptDecline.add(cancelButton());
		return acceptDecline;
	}

	private JPanel difficultyPanel() {
		var difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
		difficultyPanel.setBorder(BorderFactory.createTitledBorder("Difficulty"));

		easy = new JRadioButton("<html>Beginner<br/>10 mines<br/>9 x 9 tile grid</html>");
		easy.addActionListener(evt -> optionsHaveChanged = MineField.getCurrentPuzzle() != Difficulty.easy);
		difficultyPanel.add(easy);

		medium = new JRadioButton("<html>Intermediate<br/>40 mines<br/>16 x 16 tile grid</html>");
		medium.addActionListener(evt -> optionsHaveChanged = MineField.getCurrentPuzzle() != Difficulty.intermediate);
		difficultyPanel.add(medium);

		hard = new JRadioButton("<html>Advanced<br/>99 mines<br/>16 x 30 tile grid</html>");
		hard.addActionListener(evt -> optionsHaveChanged = MineField.getCurrentPuzzle() != Difficulty.advanced);
		difficultyPanel.add(hard);

		difficultyRadioButtonGroup = new ButtonGroup();
		difficultyRadioButtonGroup.add(easy);
		difficultyRadioButtonGroup.add(medium);
		difficultyRadioButtonGroup.add(hard);

		chooseSelectedDifficulty();

		difficultyPanel.add(Box.createVerticalGlue());
		return difficultyPanel;
	}

	private void chooseSelectedDifficulty() {
		Difficulty currentPuzzle = MineField.getCurrentPuzzle();
		easy.setSelected(currentPuzzle == Difficulty.easy);
		medium.setSelected(currentPuzzle == Difficulty.intermediate);
		hard.setSelected(currentPuzzle == Difficulty.advanced);
	}

	private JButton confirmButton() {
		var confirm = new JButton("OK");
		confirm.addActionListener(evt -> {
			if (optionsHaveChanged) {
				int yes_no = confirmDialog("Would you like to change the options?");
				if (yes_no == JOptionPane.YES_OPTION) {
					setNewOptions();
					optionsHaveChanged = false;
					show(false);
				}
			} else {
				show(false);
			}
		});
		return confirm;
	}

	private JButton cancelButton() {
		var cancel = new JButton("Cancel");
		cancel.addActionListener(evt -> {
			show(false);
			resetOptions();
		});
		return cancel;
	}

	private JPanel otherOptionsPanel() {
		var otherOptions = new JPanel();
		otherOptions.setLayout(new BoxLayout(otherOptions, BoxLayout.Y_AXIS));
		otherOptions.setBorder(BorderFactory.createTitledBorder("Other"));

		mineButtonColor = new JButton("Minefield Colour");
		mineButtonColor.addActionListener(evt -> {
			Color newMineColor = JColorChooser.showDialog(null, "Choose Minefield Colour",
					MineButton.getBackgroundColor());
			if (newMineColor != null) {
				MineButton.setBackgroundColor(newMineColor);
			}
			MinePanel.update();
		});

		otherOptions.add(mineButtonColor);
		otherOptions.add(Box.createVerticalGlue());
		return otherOptions;
	}

	private void saveCurrentOptions() {
		// System.out.println("Saving current options...");
	}

	private void resetOptions() {
		// System.out.println("Resetting current options...");
	}

	private void setNewOptions() {
		ButtonModel diff = difficultyRadioButtonGroup.getSelection();
		if (diff.equals(easy.getModel())) {
			MineField.setNextPuzzle(Difficulty.easy);
		} else if (diff.equals(medium.getModel())) {
			MineField.setNextPuzzle(Difficulty.intermediate);
		} else if (diff.equals(hard.getModel())) {
			MineField.setNextPuzzle(Difficulty.advanced);
		}
	}

	private int confirmDialog(String message) {
		return JOptionPane.showConfirmDialog(null, message, "Confirm?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, ResetButton.getSmileyHappy());
	}
}