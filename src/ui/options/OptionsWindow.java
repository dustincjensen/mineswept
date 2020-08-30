package ui.options;

import events.IEventSubscriber;
import events.UpdateMinePanelEvent;
import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import models.Difficulty;
import models.options.BorderType;
import services.OptionsService;
import state.GameState;
import ui.components.button.LightButton;
import ui.components.button.PrimaryButton;
import ui.components.comboBox.DefaultComboBox;
import ui.components.dialog.CustomDialog;
import ui.components.radioButton.RadioButtonFactory;
import ui.options.styles.Classic;
import ui.options.styles.Forest;
import ui.options.styles.Material;
import ui.utils.ColorConverter;
import java.awt.Color;
import ui.window.Window;

public class OptionsWindow {
	private GameState gameState;
	private OptionsService optionsService;
	private RadioButtonFactory radioButtonFactory;
	private IEventSubscriber eventSubscriber;
	private Window window;

	private OptionsFrame frame;
	private boolean optionsHaveChanged;
	private ButtonGroup difficultyRadioButtonGroup;
	private JRadioButton easy, medium, hard;
	private JLabel jSquareColor, jSquareAltColor, jClickedColor, jClickedAltColor, jClickedFailColor,
		jMineNumOneColor, jMineNumTwoColor, jMineNumThreeColor, jMineNumFourColor, jMineNumFiveColor,
		jMineNumSixColor, jMineNumSevenColor, jMineNumEightColor;
	private DefaultComboBox<BorderType> raisedBorder, loweredBorder;
	private CustomDialog changeOptionsDialog;

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

		optionsHaveChanged = false;
		changeOptionsDialog = new CustomDialog(frame, CustomDialog.Type.YES_NO);
	}

	private JPanel mainPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(Color.decode("#333333"));
		mainPanel.add(subPanePanel());
		mainPanel.add(saveCancel());
		return mainPanel;
	}

	private Box subPanePanel() {
		var panel = new Box(BoxLayout.Y_AXIS);
		panel.setOpaque(false);
		panel.add(difficultyPanel());
		panel.add(themePanel());
		panel.add(minefieldColorsPanel());
		panel.add(mineColorsPanel());
		panel.add(mineBorderPanel());
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
			if (optionsHaveChanged) {
				changeOptionsDialog.show("Confirm?", "Would you like to change the options?");
				if (changeOptionsDialog.getAnswer() == CustomDialog.Answer.YES) {
					setNewOptions();
					optionsHaveChanged = false;
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

	private JPanel header(String title) {
        var header = new JPanel(new BorderLayout());
		header.setBackground(Color.decode("#111111"));
		header.add(createJLabel(title, SwingConstants.LEFT), BorderLayout.LINE_START);
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return header;
	}
	
	private JLabel createJLabel(String text, int alignment) {
        var label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
		label.setForeground(Color.decode("#ffffff"));
		label.setOpaque(false);
        return label;
	}
	
	private Box difficultyPanel() {
		var difficultyPanel = new JPanel(new GridLayout(0, 3));
		difficultyPanel.setOpaque(false);
		
		easy = radioButtonFactory.create(
			"<html>Easy<br/><i>10 mines<br/>9x9 grid</i></html>",
			evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.easy);
		difficultyPanel.add(easy);

		medium = radioButtonFactory.create(
			"<html>Medium<br/><i>40 mines<br/>16x16 grid</i></html>",
			evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.medium);
		difficultyPanel.add(medium);

		hard = radioButtonFactory.create(
			"<html>Hard<br/><i>99 mines<br/>16x30 grid</i></html>",
			evt -> optionsHaveChanged = gameState.getCurrentPuzzleDifficulty() != Difficulty.hard);
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

	private Box themePanel() {
		var descriptionPanel = new JPanel();
		descriptionPanel.setOpaque(false);
		descriptionPanel.add(createJLabel("Choosing a theme will set all color options.", SwingConstants.LEFT));

		var materialButton = new PrimaryButton("Material", evt -> {
			optionsService.setSquareColor(Material.MINE_BACKGROUND_COLOR); 
			optionsService.setSquareAltColor(Material.MINE_ALT_BACKGROUND_COLOR);
			optionsService.setClickedColor(Material.MINE_CLICKED_BACKGROUND_COLOR);
			optionsService.setClickedAltColor(Material.MINE_CLICKED_ALT_BACKGROUND_COLOR);
			optionsService.setClickedFailColor(Material.FAILED_MINE_CLICKED_BACKGROUND_COLOR);
			jSquareColor.setBackground(Color.decode(Material.MINE_BACKGROUND_COLOR));
			jSquareAltColor.setBackground(Color.decode(Material.MINE_ALT_BACKGROUND_COLOR));
			jClickedColor.setBackground(Color.decode(Material.MINE_CLICKED_BACKGROUND_COLOR));
			jClickedAltColor.setBackground(Color.decode(Material.MINE_CLICKED_ALT_BACKGROUND_COLOR));
			jClickedFailColor.setBackground(Color.decode(Material.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			
			optionsService.setMineNumOneColor(Material.MINE_NUMBER_COLORS[0]);
			optionsService.setMineNumTwoColor(Material.MINE_NUMBER_COLORS[1]);
			optionsService.setMineNumThreeColor(Material.MINE_NUMBER_COLORS[2]);
			optionsService.setMineNumFourColor(Material.MINE_NUMBER_COLORS[3]);
			optionsService.setMineNumFiveColor(Material.MINE_NUMBER_COLORS[4]);
			optionsService.setMineNumSixColor(Material.MINE_NUMBER_COLORS[5]);
			optionsService.setMineNumSevenColor(Material.MINE_NUMBER_COLORS[6]);
			optionsService.setMineNumEightColor(Material.MINE_NUMBER_COLORS[7]);
			jMineNumOneColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[0]));
			jMineNumTwoColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[1]));
			jMineNumThreeColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[2]));
			jMineNumFourColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[3]));
			jMineNumFiveColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[4]));
			jMineNumSixColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[5]));
			jMineNumSevenColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[6]));
			jMineNumEightColor.setBackground(Color.decode(Material.MINE_NUMBER_COLORS[7]));
			
			optionsService.setRaisedBorder(Material.RAISED_BORDER);
			optionsService.setLoweredBorder(Material.LOWERED_BORDER);
			raisedBorder.setSelectedItem(Material.RAISED_BORDER);
			loweredBorder.setSelectedItem(Material.LOWERED_BORDER);

			eventSubscriber.notify(new UpdateMinePanelEvent());
		});
		materialButton.setPreferredSize(new Dimension(100, 40));

		var classicButton = new PrimaryButton("Classic", evt -> {
			optionsService.setSquareColor(Classic.MINE_BACKGROUND_COLOR);
			optionsService.setSquareAltColor(Classic.MINE_ALT_BACKGROUND_COLOR);
			optionsService.setClickedColor(Classic.MINE_CLICKED_BACKGROUND_COLOR);
			optionsService.setClickedAltColor(Classic.MINE_CLICKED_ALT_BACKGROUND_COLOR);
			optionsService.setClickedFailColor(Classic.FAILED_MINE_CLICKED_BACKGROUND_COLOR);
			jSquareColor.setBackground(Color.decode(Classic.MINE_BACKGROUND_COLOR));
			jSquareAltColor.setBackground(Color.decode(Classic.MINE_ALT_BACKGROUND_COLOR));
			jClickedColor.setBackground(Color.decode(Classic.MINE_CLICKED_BACKGROUND_COLOR));
			jClickedAltColor.setBackground(Color.decode(Classic.MINE_CLICKED_ALT_BACKGROUND_COLOR));
			jClickedFailColor.setBackground(Color.decode(Classic.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			
			optionsService.setMineNumOneColor(Classic.MINE_NUMBER_COLORS[0]);
			optionsService.setMineNumTwoColor(Classic.MINE_NUMBER_COLORS[1]);
			optionsService.setMineNumThreeColor(Classic.MINE_NUMBER_COLORS[2]);
			optionsService.setMineNumFourColor(Classic.MINE_NUMBER_COLORS[3]);
			optionsService.setMineNumFiveColor(Classic.MINE_NUMBER_COLORS[4]);
			optionsService.setMineNumSixColor(Classic.MINE_NUMBER_COLORS[5]);
			optionsService.setMineNumSevenColor(Classic.MINE_NUMBER_COLORS[6]);
			optionsService.setMineNumEightColor(Classic.MINE_NUMBER_COLORS[7]);
			jMineNumOneColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[0]));
			jMineNumTwoColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[1]));
			jMineNumThreeColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[2]));
			jMineNumFourColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[3]));
			jMineNumFiveColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[4]));
			jMineNumSixColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[5]));
			jMineNumSevenColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[6]));
			jMineNumEightColor.setBackground(Color.decode(Classic.MINE_NUMBER_COLORS[7]));

			optionsService.setRaisedBorder(Classic.RAISED_BORDER);
			optionsService.setLoweredBorder(Classic.LOWERED_BORDER);
			raisedBorder.setSelectedItem(Classic.RAISED_BORDER);
			loweredBorder.setSelectedItem(Classic.LOWERED_BORDER);

			eventSubscriber.notify(new UpdateMinePanelEvent());
		});
		classicButton.setPreferredSize(new Dimension(100, 40));

		var forestButton = new PrimaryButton("Forest", evt -> {
			optionsService.setSquareColor(Forest.MINE_BACKGROUND_COLOR);
			optionsService.setSquareAltColor(Forest.MINE_ALT_BACKGROUND_COLOR);
			optionsService.setClickedColor(Forest.MINE_CLICKED_BACKGROUND_COLOR);
			optionsService.setClickedAltColor(Forest.MINE_CLICKED_ALT_BACKGROUND_COLOR);
			optionsService.setClickedFailColor(Forest.FAILED_MINE_CLICKED_BACKGROUND_COLOR);
			jSquareColor.setBackground(Color.decode(Forest.MINE_BACKGROUND_COLOR));
			jSquareAltColor.setBackground(Color.decode(Forest.MINE_ALT_BACKGROUND_COLOR));
			jClickedColor.setBackground(Color.decode(Forest.MINE_CLICKED_BACKGROUND_COLOR));
			jClickedAltColor.setBackground(Color.decode(Forest.MINE_CLICKED_ALT_BACKGROUND_COLOR));
			jClickedFailColor.setBackground(Color.decode(Forest.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			
			optionsService.setMineNumOneColor(Forest.MINE_NUMBER_COLORS[0]);
			optionsService.setMineNumTwoColor(Forest.MINE_NUMBER_COLORS[1]);
			optionsService.setMineNumThreeColor(Forest.MINE_NUMBER_COLORS[2]);
			optionsService.setMineNumFourColor(Forest.MINE_NUMBER_COLORS[3]);
			optionsService.setMineNumFiveColor(Forest.MINE_NUMBER_COLORS[4]);
			optionsService.setMineNumSixColor(Forest.MINE_NUMBER_COLORS[5]);
			optionsService.setMineNumSevenColor(Forest.MINE_NUMBER_COLORS[6]);
			optionsService.setMineNumEightColor(Forest.MINE_NUMBER_COLORS[7]);
			jMineNumOneColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[0]));
			jMineNumTwoColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[1]));
			jMineNumThreeColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[2]));
			jMineNumFourColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[3]));
			jMineNumFiveColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[4]));
			jMineNumSixColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[5]));
			jMineNumSevenColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[6]));
			jMineNumEightColor.setBackground(Color.decode(Forest.MINE_NUMBER_COLORS[7]));

			optionsService.setRaisedBorder(Forest.RAISED_BORDER);
			optionsService.setLoweredBorder(Forest.LOWERED_BORDER);
			raisedBorder.setSelectedItem(Forest.RAISED_BORDER);
			loweredBorder.setSelectedItem(Forest.LOWERED_BORDER);

			eventSubscriber.notify(new UpdateMinePanelEvent());
		});
		forestButton.setPreferredSize(new Dimension(100, 40));

		var buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.add(classicButton);
		buttonPanel.add(materialButton);
		buttonPanel.add(forestButton);

		var headerWithColorPanel = new Box(BoxLayout.Y_AXIS);
		headerWithColorPanel.setBorder(BorderFactory.createEmptyBorder(5,5,15,5));
		headerWithColorPanel.add(header("Themes"));
		headerWithColorPanel.add(descriptionPanel);
		headerWithColorPanel.add(buttonPanel);
		return headerWithColorPanel;
	}

	private Box minefieldColorsPanel() {
		var p1 = new JPanel(new GridLayout(0, 3));
		p1.setOpaque(false);

		jSquareColor = new JLabel();
		var currentSquareColor = Color.decode(optionsService.squareColor());
		p1.add(individualColorPanel(currentSquareColor, "Square Color", jSquareColor, newColor -> {
			optionsService.setSquareColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jSquareAltColor = new JLabel();
		var currentSquareAltColor = Color.decode(optionsService.squareAltColor());
		p1.add(individualColorPanel(currentSquareAltColor, "Alt Square Color", jSquareAltColor, newColor -> {
			optionsService.setSquareAltColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var p2 = new JPanel(new GridLayout(0, 3));
		p2.setOpaque(false);

		jClickedColor = new JLabel();
		var currentClickedColor = Color.decode(optionsService.clickedColor());
		p2.add(individualColorPanel(currentClickedColor, "Clicked Color", jClickedColor, newColor -> {
			optionsService.setClickedColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jClickedAltColor = new JLabel();
		var currentClickedAltColor = Color.decode(optionsService.clickedAltColor());
		p2.add(individualColorPanel(currentClickedAltColor, "Clicked Alt Color", jClickedAltColor, newColor -> {
			optionsService.setClickedAltColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jClickedFailColor = new JLabel();
		var currentClickedFailColor = Color.decode(optionsService.clickedFailColor());
		p2.add(individualColorPanel(currentClickedFailColor, "Fail Color", jClickedFailColor, newColor -> {
			optionsService.setClickedFailColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var headerWithColorPanel = new Box(BoxLayout.Y_AXIS);
		headerWithColorPanel.setBorder(BorderFactory.createEmptyBorder(5,5,15,5));
		headerWithColorPanel.add(header("Minefield Colors"));
		headerWithColorPanel.add(p1);
		headerWithColorPanel.add(p2);
		return headerWithColorPanel;
	}

	private Box mineColorsPanel() {
		var p1 = new JPanel(new GridLayout(0, 3));
		p1.setOpaque(false);

		jMineNumOneColor = new JLabel();
		var currentMineNumOneColor = Color.decode(optionsService.mineNumOneColor());
		p1.add(individualColorPanel(currentMineNumOneColor, "1", jMineNumOneColor, newColor -> {
			optionsService.setMineNumOneColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumTwoColor = new JLabel();
		var currentMineNumTwoColor = Color.decode(optionsService.mineNumTwoColor());
		p1.add(individualColorPanel(currentMineNumTwoColor, "2", jMineNumTwoColor, newColor -> {
			optionsService.setMineNumTwoColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var p2 = new JPanel(new GridLayout(0, 3));
		p2.setOpaque(false);

		jMineNumThreeColor = new JLabel();
		var currentMineNumThreeColor = Color.decode(optionsService.mineNumThreeColor());
		p2.add(individualColorPanel(currentMineNumThreeColor, "3", jMineNumThreeColor, newColor -> {
			optionsService.setMineNumThreeColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumFourColor = new JLabel();
		var currentMineNumFourColor = Color.decode(optionsService.mineNumFourColor());
		p2.add(individualColorPanel(currentMineNumFourColor, "4", jMineNumFourColor, newColor -> {
			optionsService.setMineNumFourColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumFiveColor = new JLabel();
		var currentMineNumFiveColor = Color.decode(optionsService.mineNumFiveColor());
		p2.add(individualColorPanel(currentMineNumFiveColor, "5", jMineNumFiveColor, newColor -> {
			optionsService.setMineNumFiveColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var p3 = new JPanel(new GridLayout(0, 3));
		p3.setOpaque(false);

		jMineNumSixColor = new JLabel();
		var currentMineNumSixColor = Color.decode(optionsService.mineNumSixColor());
		p3.add(individualColorPanel(currentMineNumSixColor, "6", jMineNumSixColor, newColor -> {
			optionsService.setMineNumSixColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumSevenColor = new JLabel();
		var currentMineNumSevenColor = Color.decode(optionsService.mineNumSevenColor());
		p3.add(individualColorPanel(currentMineNumSevenColor, "7", jMineNumSevenColor, newColor -> {
			optionsService.setMineNumSevenColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumEightColor = new JLabel();
		var currentMineNumEightColor = Color.decode(optionsService.mineNumEightColor());
		p3.add(individualColorPanel(currentMineNumEightColor, "8", jMineNumEightColor, newColor -> {
			optionsService.setMineNumEightColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var headerWithColorPanel = new Box(BoxLayout.Y_AXIS);
		headerWithColorPanel.setBorder(BorderFactory.createEmptyBorder(5,5,15,5));
		headerWithColorPanel.add(header("Mine Number Colors"));
		headerWithColorPanel.add(p1);
		headerWithColorPanel.add(p2);
		headerWithColorPanel.add(p3);
		return headerWithColorPanel;
	}

	private Box individualColorPanel(Color base, String colorName, JLabel colorSwatch, Consumer<Color> colorHandler) {
		colorSwatch.setPreferredSize(new Dimension(40, 40));
		colorSwatch.setOpaque(true);
		colorSwatch.setBackground(base);

		var color = new JPanel(new GridLayout(0, 1));
		color.add(colorSwatch);
		color.setBorder(BorderFactory.createLineBorder(Color.decode("#444444"), 1));

		var labelAndButton = new Box(BoxLayout.Y_AXIS);
		labelAndButton.setOpaque(false);
		labelAndButton.add(createJLabel(colorName, SwingConstants.LEFT));
		labelAndButton.add(Box.createVerticalStrut(5));

		labelAndButton.add(new PrimaryButton("Select New Color", evt -> {
			var newColor = JColorChooser.showDialog(null, "Select Base Color", base);
			if (newColor != null) {
				colorSwatch.setBackground(newColor);
				colorHandler.accept(newColor);
			}
		}));

		var panel = new Box(BoxLayout.X_AXIS);
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
		panel.add(color);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(labelAndButton);
		return panel;
	}

	private Box mineBorderPanel() {
		var p1 = new JPanel(new GridLayout(0, 2, 10, 0));
		p1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		p1.setOpaque(false);

		raisedBorder = new DefaultComboBox<BorderType>(new BorderType[] { 
			BorderType.BEVEL_RAISED,
			BorderType.CLASSIC_BEVEL_RAISED,
			BorderType.EMPTY
		});
		raisedBorder.addActionListener(evt -> optionsHaveChanged = optionsService.raisedBorder() != raisedBorder.getSelectedItem());
		raisedBorder.setSelectedItem(optionsService.raisedBorder());
		var raisePanel = new JPanel(new GridLayout(0, 1));
		raisePanel.setOpaque(false);
		raisePanel.add(createJLabel("Default Border", SwingConstants.LEFT));
		raisePanel.add(raisedBorder);

		loweredBorder = new DefaultComboBox<BorderType>(new BorderType[] {
			BorderType.BEVEL_LOWERED,
			BorderType.CLASSIC_BEVEL_LOWERED,
			BorderType.EMPTY
		});
		loweredBorder.addActionListener(evt -> optionsHaveChanged = optionsService.loweredBorder() != loweredBorder.getSelectedItem());
		loweredBorder.setSelectedItem(optionsService.loweredBorder());
		var lowerPanel = new JPanel(new GridLayout(0, 1));
		lowerPanel.setOpaque(false);
		lowerPanel.add(createJLabel("Clicked Border", SwingConstants.LEFT));
		lowerPanel.add(loweredBorder);
		
		p1.add(raisePanel);
		p1.add(lowerPanel);

		var headerWithColorPanel = new Box(BoxLayout.Y_AXIS);
		headerWithColorPanel.setBorder(BorderFactory.createEmptyBorder(5,5,15,5));
		headerWithColorPanel.add(header("Mine Border"));
		headerWithColorPanel.add(p1);
		return headerWithColorPanel;
	}

	private void resetOptions() {
		// System.out.println("Resetting current options...");
	}

	// TODO colors shouldn't set on the board immediately.
	private void setNewOptions() {
		// Set difficulty
		ButtonModel diff = difficultyRadioButtonGroup.getSelection();
		if (diff.equals(easy.getModel())) {
			optionsService.setDifficulty(Difficulty.easy);
		} else if (diff.equals(medium.getModel())) {
			optionsService.setDifficulty(Difficulty.medium);
		} else if (diff.equals(hard.getModel())) {
			optionsService.setDifficulty(Difficulty.hard);
		}

		// Set border types
		var raisedType = (BorderType)raisedBorder.getSelectedItem();
		optionsService.setRaisedBorder(raisedType);

		var loweredType = (BorderType)loweredBorder.getSelectedItem();
		optionsService.setLoweredBorder(loweredType);
	}

	public void show() {
		if (!frame.isVisible()) {
			frame.pack();
			frame.setLocationRelativeTo(window);
		}
		frame.setVisible(true);
	}
}