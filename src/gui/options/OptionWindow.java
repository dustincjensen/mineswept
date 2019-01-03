package gui.options;

import events.IEventSubscriber;
import events.ShowOptionsEvent;
import events.UpdateMinePanelEvent;
import gui.components.button.LightButton;
import gui.components.button.PrimaryButton;
import gui.HexToRgb;
import gui.Resource;
import gui.ResourceLoader;
import java.awt.*;
import javax.swing.*;
import models.Difficulty;
import services.PreferencesService;
import state.GameState;

public class OptionWindow {
	private GameState gameState;
	private ResourceLoader resourceLoader;
	private PreferencesService preferencesService;
	private IEventSubscriber eventSubscriber;

	private OptionsFrame frame;
	private boolean optionsHaveChanged;
	private ButtonGroup difficultyRadioButtonGroup;
	private JRadioButton easy, medium, hard;

	public OptionWindow(
		GameState state,
		ResourceLoader loader,
		PreferencesService prefs,
		IEventSubscriber subscriber
	) {
		System.out.println("Creating new option window...");
		gameState = state;
		resourceLoader = loader;
		preferencesService = prefs;
		eventSubscriber = subscriber;

		frame = new OptionsFrame(mainPanel());

		optionsHaveChanged = false;

		setupSubscriptions();
	}

	private JPanel mainPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(HexToRgb.convert("#333333"));
		mainPanel.add(subPanePanel());
		mainPanel.add(saveCancel());
		return mainPanel;
	}

	private Box subPanePanel() {
		var panel = new Box(BoxLayout.Y_AXIS);
		panel.setOpaque(false);
		panel.add(difficultyPanel());
		panel.add(otherOptionsPanel());
		return panel;
	}

	private JPanel saveCancel() {
		var panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		panel.setBackground(HexToRgb.convert("#333333"));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		panel.setLayout(new GridLayout(0, 2, 5, 5));
		panel.add(saveButton());
		panel.add(cancelButton());
		return panel;
	}

	private JButton saveButton() {
		return new PrimaryButton("Save", event -> {
			if (optionsHaveChanged) {
				int answer = confirmDialog("Would you like to change the options?");
				if (answer == JOptionPane.YES_OPTION) {
					setNewOptions();
					optionsHaveChanged = false;
					frame.setVisible(false);
				}
			} else {
				frame.setVisible(false);
			}
		});
	}

	private JButton cancelButton() {
		return new LightButton("Cancel", evt -> {
			frame.setVisible(false);
			resetOptions();
		});
	}

	private JPanel header(String title) {
        var header = new JPanel(new FlowLayout());
        header.setBackground(HexToRgb.convert("#007bff"));
        header.add(createJLabel(title, SwingConstants.LEFT));
        return header;
	}
	
	private JLabel createJLabel(String text, int alignment) {
        var label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
		label.setForeground(HexToRgb.convert("#ffffff"));
		label.setOpaque(false);
        return label;
    }

	private Box difficultyPanel() {
		var difficultyPanel = new JPanel(new GridLayout(3, 1));
		difficultyPanel.setOpaque(false);
        
		easy = new JRadioButton("<html>Easy<br/><i>10 mines<br/>9x9 grid</i></html>");
		easy.setOpaque(false);
		easy.setForeground(HexToRgb.convert("#ffffff"));
		easy.addActionListener(evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.easy);
		difficultyPanel.add(easy);

		medium = new JRadioButton("<html>Medium<br/><i>40 mines<br/>16x16 grid</i></html>");
		medium.setOpaque(false);
		medium.setForeground(HexToRgb.convert("#ffffff"));
		medium.addActionListener(evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.medium);
		difficultyPanel.add(medium);

		hard = new JRadioButton("<html>Hard<br/><i>99 mines<br/>16x30 grid</i></html>");
		hard.setOpaque(false);
		hard.setForeground(HexToRgb.convert("#ffffff"));
		hard.addActionListener(evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.hard);
		difficultyPanel.add(hard);

		difficultyRadioButtonGroup = new ButtonGroup();
		difficultyRadioButtonGroup.add(easy);
		difficultyRadioButtonGroup.add(medium);
		difficultyRadioButtonGroup.add(hard);

		chooseSelectedDifficulty();

		var headerWithDifficultyPanel = new Box(BoxLayout.Y_AXIS);
		headerWithDifficultyPanel.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
		headerWithDifficultyPanel.add(header("Difficulty"));
		headerWithDifficultyPanel.add(difficultyPanel);
		return headerWithDifficultyPanel;
	}

	private void chooseSelectedDifficulty() {
		Difficulty currentPuzzle = gameState.getCurrentPuzzleDifficulty();
		easy.setSelected(currentPuzzle == Difficulty.easy);
		medium.setSelected(currentPuzzle == Difficulty.medium);
		hard.setSelected(currentPuzzle == Difficulty.hard);
	}

	private Box otherOptionsPanel() {
		var colorPanel = new JPanel(new GridLayout(0, 1));
		colorPanel.setOpaque(false);

		var prefColor = preferencesService.squareColor();
		var current = new Color(prefColor.r, prefColor.g, prefColor.b);
		colorPanel.add(individualColorPanel(current));
		
		var headerWithColorPanel = new Box(BoxLayout.Y_AXIS);
		headerWithColorPanel.setBorder(BorderFactory.createEmptyBorder(5,5,15,5));
		headerWithColorPanel.add(header("Minefield Colors"));
		headerWithColorPanel.add(colorPanel);
		return headerWithColorPanel;
	}

	private Box individualColorPanel(Color base) {
		var color = new JPanel();
		color.setBorder(BorderFactory.createLineBorder(HexToRgb.convert("#444444"), 1));
		color.setBackground(base);

		var labelAndButton = new JPanel(new GridLayout(2, 1));
		labelAndButton.setOpaque(false);
		labelAndButton.add(createJLabel("Base Color", SwingConstants.LEFT));
		labelAndButton.add(Box.createVerticalStrut(5));

		// TODO this is hardcoded to one color... we need to build an interface to call another lambda when the color is executed.
		labelAndButton.add(new PrimaryButton("Select New Color", evt -> {
			var newMineColor = JColorChooser.showDialog(null, "Select Base Color", base);

			if (newMineColor != null) {
				preferencesService.setSquareColor(newMineColor.getRed(), newMineColor.getGreen(), newMineColor.getBlue());
				color.setBackground(newMineColor);
			}

			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var panel = new Box(BoxLayout.X_AXIS);
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
		panel.add(color);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(labelAndButton);
		panel.add(Box.createHorizontalGlue());
		return panel;
	}

	private void resetOptions() {
		// System.out.println("Resetting current options...");
	}

	private void setNewOptions() {
		ButtonModel diff = difficultyRadioButtonGroup.getSelection();
		if (diff.equals(easy.getModel())) {
			// TODO this should set the preference service value...
			// TODO this should not access game state directly, but set this through
			// an event publish.
			// TODO perhaps game state should not "know" the next puzzle difficulty?
			// Perhaps it is just loaded from the preferences?
			gameState.setNextPuzzleDifficulty(Difficulty.easy);
			preferencesService.setDifficulty(Difficulty.easy);
		} else if (diff.equals(medium.getModel())) {
			gameState.setNextPuzzleDifficulty(Difficulty.medium);
			preferencesService.setDifficulty(Difficulty.medium);
		} else if (diff.equals(hard.getModel())) {
			gameState.setNextPuzzleDifficulty(Difficulty.hard);
			preferencesService.setDifficulty(Difficulty.hard);
		}
	}

	private int confirmDialog(String message) {
		return JOptionPane.showConfirmDialog(null, message, "Confirm?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, resourceLoader.get(Resource.SmileyHappy));
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowOptionsEvent.class, event -> {
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}