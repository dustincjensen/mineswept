package ui.options;

import events.IEventSubscriber;
import events.UpdateMinePanelEvent;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import models.options.BorderType;
import services.OptionsService;
import state.GameState;
import ui.components.button.LightButton;
import ui.components.button.PrimaryButton;
import ui.components.dialog.CustomDialog;
import ui.components.radioButton.RadioButtonFactory;
import ui.options.styles.Classic;
import ui.options.styles.Forest;
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

		changeOptionsDialog = new CustomDialog(frame, CustomDialog.Type.YES_NO);
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

	private JPanel saveCancel() {
		var panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		panel.setBackground(Color.decode("#333333"));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		panel.setLayout(new GridLayout(0, 2, 5, 5));
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
	
	private TitledPanel difficultyPanel() {
		var currentPuzzle = gameState.getCurrentPuzzleDifficulty();
		difficultyPanel = new DifficultyPanel(radioButtonFactory, currentPuzzle);
		return new TitledPanel("Difficulty", difficultyPanel);
	}

	private TitledPanel themePanel() {
		var descriptionPanel = new JPanel();
		descriptionPanel.setOpaque(false);
		descriptionPanel.add(JLabelFactory.create("Choosing a theme will set all color options."));

		// TODO make for now have an interface instead and then this can become 1 method instead of 3.
		var materialButton = new PrimaryButton("Material", evt -> {
			squareColor.setSelectedColor(Color.decode(Material.MINE_BACKGROUND_COLOR));
			squareAltColor.setSelectedColor(Color.decode(Material.MINE_ALT_BACKGROUND_COLOR));
			clickedColor.setSelectedColor(Color.decode(Material.MINE_CLICKED_BACKGROUND_COLOR));
			clickedAltColor.setSelectedColor(Color.decode(Material.MINE_CLICKED_ALT_BACKGROUND_COLOR));
			clickedFailColor.setSelectedColor(Color.decode(Material.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			
			for (var i = 0; i < 8; i++) {
				mineNumbers[i].setSelectedColor(Color.decode(Material.MINE_NUMBER_COLORS[i]));
			}
			
			optionsService.setRaisedBorder(Material.RAISED_BORDER);
			optionsService.setLoweredBorder(Material.LOWERED_BORDER);
			borderPanel.setRaisedBorder(Material.RAISED_BORDER);
			borderPanel.setLoweredBorder(Material.LOWERED_BORDER);
		});
		materialButton.setPreferredSize(new Dimension(100, 40));

		var classicButton = new PrimaryButton("Classic", evt -> {
			squareColor.setSelectedColor(Color.decode(Classic.MINE_BACKGROUND_COLOR));
			squareAltColor.setSelectedColor(Color.decode(Classic.MINE_ALT_BACKGROUND_COLOR));
			clickedColor.setSelectedColor(Color.decode(Classic.MINE_CLICKED_BACKGROUND_COLOR));
			clickedAltColor.setSelectedColor(Color.decode(Classic.MINE_CLICKED_ALT_BACKGROUND_COLOR));
			clickedFailColor.setSelectedColor(Color.decode(Classic.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			
			for (var i = 0; i < 8; i++) {
				mineNumbers[i].setSelectedColor(Color.decode(Classic.MINE_NUMBER_COLORS[i]));
			}

			optionsService.setRaisedBorder(Classic.RAISED_BORDER);
			optionsService.setLoweredBorder(Classic.LOWERED_BORDER);
			borderPanel.setRaisedBorder(Classic.RAISED_BORDER);
			borderPanel.setLoweredBorder(Classic.LOWERED_BORDER);
		});
		classicButton.setPreferredSize(new Dimension(100, 40));

		var forestButton = new PrimaryButton("Forest", evt -> {
			squareColor.setSelectedColor(Color.decode(Forest.MINE_BACKGROUND_COLOR));
			squareAltColor.setSelectedColor(Color.decode(Forest.MINE_ALT_BACKGROUND_COLOR));
			clickedColor.setSelectedColor(Color.decode(Forest.MINE_CLICKED_BACKGROUND_COLOR));
			clickedAltColor.setSelectedColor(Color.decode(Forest.MINE_CLICKED_ALT_BACKGROUND_COLOR));
			clickedFailColor.setSelectedColor(Color.decode(Forest.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			
			for (var i = 0; i < 8; i++) {
				mineNumbers[i].setSelectedColor(Color.decode(Forest.MINE_NUMBER_COLORS[i]));	
			}

			optionsService.setRaisedBorder(Forest.RAISED_BORDER);
			optionsService.setLoweredBorder(Forest.LOWERED_BORDER);
			borderPanel.setRaisedBorder(Forest.RAISED_BORDER);
			borderPanel.setLoweredBorder(Forest.LOWERED_BORDER);
		});
		forestButton.setPreferredSize(new Dimension(100, 40));

		var buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.add(classicButton);
		buttonPanel.add(materialButton);
		buttonPanel.add(forestButton);

		return new TitledPanel("Themes", descriptionPanel, buttonPanel);
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

	private void resetOptions() {
		// System.out.println("Resetting current options...");
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

	// TODO colors shouldn't set on the board immediately.
	private void setNewOptions() {
		// Set difficulty
		var selectedDifficulty = difficultyPanel.getSelectedDifficulty();
		optionsService.setDifficulty(selectedDifficulty);

		// Save mine number colors.
		// TODO since we will be saving mine colors all at once, we should remove
		// the individual color saves.
		optionsService.setSquareColor(squareColor.getSaveableColor());
		optionsService.setSquareAltColor(squareAltColor.getSaveableColor());
		optionsService.setClickedColor(clickedColor.getSaveableColor());
		optionsService.setClickedAltColor(clickedAltColor.getSaveableColor());
		optionsService.setClickedFailColor(clickedFailColor.getSaveableColor());
		optionsService.setMineNumOneColor(mineNumbers[0].getSaveableColor());
		optionsService.setMineNumTwoColor(mineNumbers[1].getSaveableColor());
		optionsService.setMineNumThreeColor(mineNumbers[2].getSaveableColor());
		optionsService.setMineNumFourColor(mineNumbers[3].getSaveableColor());
		optionsService.setMineNumFiveColor(mineNumbers[4].getSaveableColor());
		optionsService.setMineNumSixColor(mineNumbers[5].getSaveableColor());
		optionsService.setMineNumSevenColor(mineNumbers[6].getSaveableColor());
		optionsService.setMineNumEightColor(mineNumbers[7].getSaveableColor());

		// Set border types
		var raisedType = (BorderType)borderPanel.getRaisedBorder();
		optionsService.setRaisedBorder(raisedType);

		var loweredType = (BorderType)borderPanel.getLoweredBorder();
		optionsService.setLoweredBorder(loweredType);

		// Notify the mine panel so it can update colors immediately.
		eventSubscriber.notify(new UpdateMinePanelEvent());
	}

	public void show() {
		if (!frame.isVisible()) {
			frame.pack();
			frame.setLocationRelativeTo(window);
		}
		frame.setVisible(true);
	}
}