package ui.options;

import events.IEventSubscriber;
import events.UpdateMinePanelEvent;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import models.options.BorderType;
import models.options.Options;
import services.OptionsService;
import state.GameState;
import ui.components.button.LightButton;
import ui.components.button.PrimaryButton;
import ui.components.dialog.CustomDialog;
import ui.components.radioButton.RadioButtonFactory;
import ui.options.styles.Classic;
import ui.options.styles.Forest;
import ui.options.styles.IStyle;
import ui.options.styles.Material;
import ui.utils.JLabelFactory;
import ui.window.Window;

public class OptionsWindow {
	private GameState gameState;
	private OptionsService optionsService;
	private RadioButtonFactory radioButtonFactory;
	private IEventSubscriber eventSubscriber;
	private Window window;

	private OptionsFrame frame;
	private DifficultyPanel difficultyPanel;
	private BorderPanel borderPanel;
	private CustomDialog changeOptionsDialog;
	private ColorOption squareColor, squareAltColor, clickedColor, clickedAltColor, clickedFailColor;
	private ColorOption[] mineNumbers;

	public OptionsWindow(
		GameState gameState,
		OptionsService optionsService,
		RadioButtonFactory radioButtonFactory,
		IEventSubscriber eventSubscriber,
		ImageIcon windowIcon,
		Window window
	) {
		this.gameState = gameState;
		this.optionsService = optionsService;
		this.radioButtonFactory = radioButtonFactory;
		this.eventSubscriber = eventSubscriber;
		this.window = window;

		frame = new OptionsFrame(mainPanel());
		frame.setIconImage(windowIcon.getImage());
		frame.addWindowListener(new OptionsWindowHandler(this));

		changeOptionsDialog = new CustomDialog(frame, CustomDialog.Type.YES_NO);
	}

	/**
	 * Method that is invoked to display the options window.
	 */
	public void show() {
		if (!frame.isVisible()) {
			frame.pack();
			frame.setLocationRelativeTo(window);
		}
		frame.setVisible(true);
	}

	/**
	 * Method that should be invoked when the window is closed.
	 */
	public void closed() {
		if (haveOptionsChanged()) {
			resetOptions();
		}
	}

	private JPanel mainPanel() {
		var panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.decode("#333333"));
		panel.add(difficultyPanel());
		panel.add(themePanel());
		panel.add(minefieldColorsPanel());
		panel.add(mineColorsPanel());
		panel.add(mineBorderPanel());
		panel.add(saveCancel());
		return panel;
	}

	private TitledPanel difficultyPanel() {
		var currentPuzzle = gameState.getCurrentPuzzleDifficulty();
		difficultyPanel = new DifficultyPanel(radioButtonFactory, currentPuzzle);
		return new TitledPanel("Difficulty", difficultyPanel);
	}

	private TitledPanel themePanel() {
		var descriptionPanel = new JPanel();
		descriptionPanel.setOpaque(false);
		descriptionPanel.add(JLabelFactory.create("Choosing a theme will set all color options."));

		var materialButton = new PrimaryButton("Material", evt -> setAllColors(new Material()));
		var classicButton = new PrimaryButton("Classic", evt -> setAllColors(new Classic()));
		var forestButton = new PrimaryButton("Forest", evt -> setAllColors(new Forest()));
		
		materialButton.setPreferredSize(new Dimension(100, 40));
		classicButton.setPreferredSize(new Dimension(100, 40));
		forestButton.setPreferredSize(new Dimension(100, 40));

		var buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.add(classicButton);
		buttonPanel.add(materialButton);
		buttonPanel.add(forestButton);

		return new TitledPanel("Themes", descriptionPanel, buttonPanel);
	}

	private void setAllColors(IStyle style) {
		squareColor.setSelectedColor(Color.decode(style.mineBackgroundColor()));
		squareAltColor.setSelectedColor(Color.decode(style.mineAltBackgroundColor()));
		clickedColor.setSelectedColor(Color.decode(style.mineClickedBackgroundColor()));
		clickedAltColor.setSelectedColor(Color.decode(style.mineClickedAltBackgroundColor()));
		clickedFailColor.setSelectedColor(Color.decode(style.failedMineClickedBackgroundColor()));
		
		var styleNumberColors = style.mineNumberColors();
		for (var i = 0; i < 8; i++) {
			mineNumbers[i].setSelectedColor(Color.decode(styleNumberColors[i]));
		}
		
		borderPanel.setRaisedBorder(style.raisedBorder());
		borderPanel.setLoweredBorder(style.loweredBorder());
	}

	private TitledPanel minefieldColorsPanel() {
		squareColor = new ColorOption(Color.decode(optionsService.squareColor()), "Square Color");
		squareAltColor = new ColorOption(Color.decode(optionsService.squareAltColor()), "Alt Square Color");
		clickedColor = new ColorOption(Color.decode(optionsService.clickedColor()), "Clicked Color");
		clickedAltColor = new ColorOption(Color.decode(optionsService.clickedAltColor()), "Clicked Alt Color");
		clickedFailColor = new ColorOption(Color.decode(optionsService.clickedFailColor()), "Fail Color");

		var p1 = new JPanel(new GridLayout(0, 3));
		p1.setOpaque(false);
		p1.add(squareColor);
		p1.add(squareAltColor);

		var p2 = new JPanel(new GridLayout(0, 3));
		p2.setOpaque(false);
		p2.add(clickedColor);
		p2.add(clickedAltColor);
		p2.add(clickedFailColor);

		return new TitledPanel("Minefield Colors", p1, p2);
	}

	private TitledPanel mineColorsPanel() {
		mineNumbers = new ColorOption[] {
			new ColorOption(Color.decode(optionsService.mineNumOneColor()), "1"),
			new ColorOption(Color.decode(optionsService.mineNumTwoColor()), "2"),
			new ColorOption(Color.decode(optionsService.mineNumThreeColor()), "3"),
			new ColorOption(Color.decode(optionsService.mineNumFourColor()), "4"),
			new ColorOption(Color.decode(optionsService.mineNumFiveColor()), "5"),
			new ColorOption(Color.decode(optionsService.mineNumSixColor()), "6"),
			new ColorOption(Color.decode(optionsService.mineNumSevenColor()), "7"),
			new ColorOption(Color.decode(optionsService.mineNumEightColor()), "8")
		};

		var p1 = new JPanel(new GridLayout(0, 3));
		p1.setOpaque(false);
		p1.add(mineNumbers[0]);
		p1.add(mineNumbers[1]);

		var p2 = new JPanel(new GridLayout(0, 3));
		p2.setOpaque(false);
		p2.add(mineNumbers[2]);
		p2.add(mineNumbers[3]);
		p2.add(mineNumbers[4]);

		var p3 = new JPanel(new GridLayout(0, 3));
		p3.setOpaque(false);
		p3.add(mineNumbers[5]);
		p3.add(mineNumbers[6]);
		p3.add(mineNumbers[7]);

		return new TitledPanel("Mine Number Colors", p1, p2, p3);
	}

	private TitledPanel mineBorderPanel() {
		borderPanel = new BorderPanel(optionsService.raisedBorder(), optionsService.loweredBorder());
		return new TitledPanel("Mine Border", borderPanel);
	}

	private JPanel saveCancel() {
		var panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 5, 5));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		panel.add(saveButton());
		panel.add(cancelButton());
		return panel;
	}

	private JButton saveButton() {
		var saveButton = new PrimaryButton("Save", event -> {
			if (haveOptionsChanged()) {
				changeOptionsDialog.show("Confirm?", "Would you like to change the options?");
				if (changeOptionsDialog.getAnswer() == CustomDialog.Answer.YES) {
					setNewOptions();
					frame.setVisible(false);
				}
			} else {
				frame.setVisible(false);
			}
		});
		saveButton.setPreferredSize(new Dimension(0, 40));
		return saveButton;
	}

	private JButton cancelButton() {
		return new LightButton("Cancel", evt -> {
			frame.setVisible(false);
			resetOptions();
		});
	}

	private boolean haveOptionsChanged() {
		return 
		 	optionsService.difficulty() != difficultyPanel.getSelectedDifficulty() ||
			optionsService.raisedBorder() != borderPanel.getRaisedBorder() ||
			optionsService.loweredBorder() != borderPanel.getLoweredBorder() ||
			!optionsService.squareColor().equals(squareColor.getSaveableColor()) ||
			!optionsService.squareAltColor().equals(squareAltColor.getSaveableColor()) ||
			!optionsService.clickedColor().equals(clickedColor.getSaveableColor()) ||
			!optionsService.clickedAltColor().equals(clickedAltColor.getSaveableColor()) ||
			!optionsService.clickedFailColor().equals(clickedFailColor.getSaveableColor()) ||
			!optionsService.mineNumOneColor().equals(mineNumbers[0].getSaveableColor()) ||
			!optionsService.mineNumTwoColor().equals(mineNumbers[1].getSaveableColor()) ||
			!optionsService.mineNumThreeColor().equals(mineNumbers[2].getSaveableColor()) ||
			!optionsService.mineNumFourColor().equals(mineNumbers[3].getSaveableColor()) ||
			!optionsService.mineNumFiveColor().equals(mineNumbers[4].getSaveableColor()) ||
			!optionsService.mineNumSixColor().equals(mineNumbers[5].getSaveableColor()) ||
			!optionsService.mineNumSevenColor().equals(mineNumbers[6].getSaveableColor()) ||
			!optionsService.mineNumEightColor().equals(mineNumbers[7].getSaveableColor());
	}

	private void setNewOptions() {
		var updatedOptions = new Options();
		updatedOptions.difficulty = difficultyPanel.getSelectedDifficulty().toString();
		updatedOptions.squareColor = squareColor.getSaveableColor();
		updatedOptions.squareAltColor = squareAltColor.getSaveableColor();
		updatedOptions.clickedColor = clickedColor.getSaveableColor();
		updatedOptions.clickedAltColor = clickedAltColor.getSaveableColor();
		updatedOptions.clickedFailColor = clickedFailColor.getSaveableColor();
		updatedOptions.mineNumOneColor = mineNumbers[0].getSaveableColor();
		updatedOptions.mineNumTwoColor = mineNumbers[1].getSaveableColor();
		updatedOptions.mineNumThreeColor = mineNumbers[2].getSaveableColor();
		updatedOptions.mineNumFourColor = mineNumbers[3].getSaveableColor();
		updatedOptions.mineNumFiveColor = mineNumbers[4].getSaveableColor();
		updatedOptions.mineNumSixColor = mineNumbers[5].getSaveableColor();
		updatedOptions.mineNumSevenColor = mineNumbers[6].getSaveableColor();
		updatedOptions.mineNumEightColor = mineNumbers[7].getSaveableColor();
		updatedOptions.raisedBorder = (BorderType)borderPanel.getRaisedBorder();
		updatedOptions.loweredBorder = (BorderType)borderPanel.getLoweredBorder();

		// Update the options.
		optionsService.updateOptions(updatedOptions);

		// Notify the mine panel so it can update colors immediately.
		eventSubscriber.notify(new UpdateMinePanelEvent());
	}

	private void resetOptions() {
		difficultyPanel.setSelectedDifficulty(optionsService.difficulty());
		squareColor.setSelectedColor(Color.decode(optionsService.squareColor()));
		squareAltColor.setSelectedColor(Color.decode(optionsService.squareAltColor()));
		clickedColor.setSelectedColor(Color.decode(optionsService.clickedColor()));
		clickedAltColor.setSelectedColor(Color.decode(optionsService.clickedAltColor()));
		clickedFailColor.setSelectedColor(Color.decode(optionsService.clickedFailColor()));
		mineNumbers[0].setSelectedColor(Color.decode(optionsService.mineNumOneColor()));
		mineNumbers[1].setSelectedColor(Color.decode(optionsService.mineNumTwoColor()));
		mineNumbers[2].setSelectedColor(Color.decode(optionsService.mineNumThreeColor()));
		mineNumbers[3].setSelectedColor(Color.decode(optionsService.mineNumFourColor()));
		mineNumbers[4].setSelectedColor(Color.decode(optionsService.mineNumFiveColor()));
		mineNumbers[5].setSelectedColor(Color.decode(optionsService.mineNumSixColor()));
		mineNumbers[6].setSelectedColor(Color.decode(optionsService.mineNumSevenColor()));
		mineNumbers[7].setSelectedColor(Color.decode(optionsService.mineNumEightColor()));
		borderPanel.setRaisedBorder(optionsService.raisedBorder());
		borderPanel.setLoweredBorder(optionsService.loweredBorder());
	}
}