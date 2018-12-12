package gui.options;

import gui.Resource;
import gui.ResourceLoader;
import gui.events.IEventSubscriber;
import gui.events.ShowOptionsEvent;
import gui.events.UpdateMinePanelEvent;
import gui.panel.mines.MineButton;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import models.Difficulty;
import state.GameState;

public class OptionWindow {
	private GameState gameState;
	private ResourceLoader resourceLoader;
	private IEventSubscriber eventSubscriber;

	private JFrame optionsWindow;
	private boolean optionsHaveChanged;
	private ButtonGroup difficultyRadioButtonGroup;
	private JRadioButton easy, medium, hard;
	private JButton mineButtonColor;

	public OptionWindow(
		GameState state,
		ResourceLoader loader,
		IEventSubscriber subscriber
	) {
		System.out.println("Creating new option window...");
		gameState = state;
		resourceLoader = loader;
		eventSubscriber = subscriber;

		optionsWindow = new JFrame("Options");
		optionsWindow.setContentPane(mainPanel());
		optionsWindow.setSize(500, 350);
		optionsWindow.setLocationRelativeTo(null);
		optionsWindow.setResizable(false);
		optionsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		optionsHaveChanged = false;

		setupSubscriptions();
	}

	private void hide() {
		// TODO save options...
		// saveCurrentOptions();
		optionsWindow.setVisible(false);
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
		easy.addActionListener(evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.easy);
		difficultyPanel.add(easy);

		medium = new JRadioButton("<html>Intermediate<br/>40 mines<br/>16 x 16 tile grid</html>");
		medium.addActionListener(evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.medium);
		difficultyPanel.add(medium);

		hard = new JRadioButton("<html>Advanced<br/>99 mines<br/>16 x 30 tile grid</html>");
		hard.addActionListener(evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.hard);
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
		Difficulty currentPuzzle = gameState.getCurrentPuzzleDifficulty();
		easy.setSelected(currentPuzzle == Difficulty.easy);
		medium.setSelected(currentPuzzle == Difficulty.medium);
		hard.setSelected(currentPuzzle == Difficulty.hard);
	}

	private JButton confirmButton() {
		var confirm = new JButton("OK");
		confirm.addActionListener(evt -> {
			if (optionsHaveChanged) {
				int yes_no = confirmDialog("Would you like to change the options?");
				if (yes_no == JOptionPane.YES_OPTION) {
					setNewOptions();
					optionsHaveChanged = false;
					hide();
				}
			} else {
				hide();
			}
		});
		return confirm;
	}

	private JButton cancelButton() {
		var cancel = new JButton("Cancel");
		cancel.addActionListener(evt -> {
			hide();
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

			eventSubscriber.notify(new UpdateMinePanelEvent());
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
			gameState.setNextPuzzleDifficulty(Difficulty.easy);
		} else if (diff.equals(medium.getModel())) {
			gameState.setNextPuzzleDifficulty(Difficulty.medium);
		} else if (diff.equals(hard.getModel())) {
			gameState.setNextPuzzleDifficulty(Difficulty.hard);
		}
	}

	private int confirmDialog(String message) {
		return JOptionPane.showConfirmDialog(null, message, "Confirm?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, resourceLoader.get(Resource.SmileyHappy));
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowOptionsEvent.class, event -> {
			optionsWindow.setVisible(true);
		});
	}
}