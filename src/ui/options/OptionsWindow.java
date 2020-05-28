package ui.options;

import events.IEventSubscriber;
import events.ShowOptionsEvent;
import events.UpdateMinePanelEvent;
import ui.components.button.LightButton;
import ui.components.button.PrimaryButton;
import ui.options.styles.Material;
import ui.options.styles.Classic;
import ui.utils.ColorConverter;
import ui.utils.HexToRgb;
import java.awt.*;
import javax.swing.*;
import models.Difficulty;
import services.OptionsService;
import state.GameState;

public class OptionsWindow {
	private GameState gameState;
	private OptionsService optionsService;
	private IEventSubscriber eventSubscriber;
	private ImageIcon confirmationIcon;

	private OptionsFrame frame;
	private boolean optionsHaveChanged;
	private ButtonGroup difficultyRadioButtonGroup;
	private JRadioButton easy, medium, hard;
	private JLabel jSquareColor, jSquareAltColor, jClickedColor, jClickedAltColor, jClickedFailColor,
		jMineNumOneColor, jMineNumTwoColor, jMineNumThreeColor, jMineNumFourColor, jMineNumFiveColor,
		jMineNumSixColor, jMineNumSevenColor, jMineNumEightColor;

	public OptionsWindow(
		GameState gameState,
		OptionsService optionsService,
		IEventSubscriber eventSubscriber,
		ImageIcon confirmationIcon,
		Image windowIcon
	) {
		System.out.println("Creating new option window...");
		this.gameState = gameState;
		this.optionsService = optionsService;
		this.eventSubscriber = eventSubscriber;
		this.confirmationIcon = confirmationIcon;

		frame = new OptionsFrame(mainPanel());
		frame.setIconImage(windowIcon);

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
		panel.add(themePanel());
		panel.add(minefieldColorsPanel());
		panel.add(mineColorsPanel());
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
		var saveButton = new PrimaryButton("Save", event -> {
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
		var difficultyPanel = new JPanel(new GridLayout(0, 3));
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

	private Box themePanel() {
		var descriptionPanel = new JPanel();
		descriptionPanel.setOpaque(false);
		descriptionPanel.add(createJLabel("Choosing a theme will set all color options.", SwingConstants.LEFT));

		var materialButton = new PrimaryButton("Material", evt -> {
			optionsService.setSquareColor(ColorConverter.convertBack(Material.MINE_BACKGROUND_COLOR)); 
			jSquareColor.setBackground(Material.MINE_BACKGROUND_COLOR);
			optionsService.setSquareAltColor(ColorConverter.convertBack(Material.MINE_ALT_BACKGROUND_COLOR));
			jSquareAltColor.setBackground(Material.MINE_ALT_BACKGROUND_COLOR);
			optionsService.setClickedColor(ColorConverter.convertBack(Material.MINE_CLICKED_BACKGROUND_COLOR));
			jClickedColor.setBackground(Material.MINE_CLICKED_BACKGROUND_COLOR);
			optionsService.setClickedAltColor(ColorConverter.convertBack(Material.MINE_CLICKED_ALT_BACKGROUND_COLOR));
			jClickedAltColor.setBackground(Material.MINE_CLICKED_ALT_BACKGROUND_COLOR);
			optionsService.setClickedFailColor(ColorConverter.convertBack(Material.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			jClickedFailColor.setBackground(Material.FAILED_MINE_CLICKED_BACKGROUND_COLOR);
			
			optionsService.setMineNumOneColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[0]));
			jMineNumOneColor.setBackground(Material.MINE_NUMBER_COLORS[0]);
			optionsService.setMineNumTwoColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[1]));
			jMineNumTwoColor.setBackground(Material.MINE_NUMBER_COLORS[1]);
			optionsService.setMineNumThreeColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[2]));
			jMineNumThreeColor.setBackground(Material.MINE_NUMBER_COLORS[2]);
			optionsService.setMineNumFourColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[3]));
			jMineNumFourColor.setBackground(Material.MINE_NUMBER_COLORS[3]);
			optionsService.setMineNumFiveColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[4]));
			jMineNumFiveColor.setBackground(Material.MINE_NUMBER_COLORS[4]);
			optionsService.setMineNumSixColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[5]));
			jMineNumSixColor.setBackground(Material.MINE_NUMBER_COLORS[5]);
			optionsService.setMineNumSevenColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[6]));
			jMineNumSevenColor.setBackground(Material.MINE_NUMBER_COLORS[6]);
			optionsService.setMineNumEightColor(ColorConverter.convertBack(Material.MINE_NUMBER_COLORS[7]));
			jMineNumEightColor.setBackground(Material.MINE_NUMBER_COLORS[7]);
			eventSubscriber.notify(new UpdateMinePanelEvent());
		});
		materialButton.setPreferredSize(new Dimension(100, 40));

		var buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.add(materialButton);

		// buttonPanel.add(new PrimaryButton("Classic", evt -> {
		// 	optionsService.setSquareColor(ColorConverter.convertBack(Classic.MINE_BACKGROUND_COLOR));
		// 	optionsService.setSquareAltColor(ColorConverter.convertBack(Classic.MINE_ALT_BACKGROUND_COLOR));
		// 	optionsService.setClickedColor(ColorConverter.convertBack(Classic.MINE_CLICKED_BACKGROUND_COLOR));
		// 	optionsService.setClickedAltColor(ColorConverter.convertBack(Classic.MINE_CLICKED_ALT_BACKGROUND_COLOR));
		// 	optionsService.setClickedFailColor(ColorConverter.convertBack(Classic.FAILED_MINE_CLICKED_BACKGROUND_COLOR));
			
		// 	optionsService.setMineNumOneColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[0]));
		// 	optionsService.setMineNumTwoColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[1]));
		// 	optionsService.setMineNumThreeColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[2]));
		// 	optionsService.setMineNumFourColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[3]));
		// 	optionsService.setMineNumFiveColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[4]));
		// 	optionsService.setMineNumSixColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[5]));
		// 	optionsService.setMineNumSevenColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[6]));
		// 	optionsService.setMineNumEightColor(ColorConverter.convertBack(Classic.MINE_NUMBER_COLORS[7]));
		// 	eventSubscriber.notify(new UpdateMinePanelEvent());
		// }));

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
		var currentSquareColor = ColorConverter.convert(optionsService.squareColor());
		p1.add(individualColorPanel(currentSquareColor, "Square Color", jSquareColor, newColor -> {
			optionsService.setSquareColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jSquareAltColor = new JLabel();
		var currentSquareAltColor = ColorConverter.convert(optionsService.squareAltColor());
		p1.add(individualColorPanel(currentSquareAltColor, "Alt Square Color", jSquareAltColor, newColor -> {
			optionsService.setSquareAltColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var p2 = new JPanel(new GridLayout(0, 3));
		p2.setOpaque(false);

		jClickedColor = new JLabel();
		var currentClickedColor = ColorConverter.convert(optionsService.clickedColor());
		p2.add(individualColorPanel(currentClickedColor, "Clicked Color", jClickedColor, newColor -> {
			optionsService.setClickedColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jClickedAltColor = new JLabel();
		var currentClickedAltColor = ColorConverter.convert(optionsService.clickedAltColor());
		p2.add(individualColorPanel(currentClickedAltColor, "Clicked Alt Color", jClickedAltColor, newColor -> {
			optionsService.setClickedAltColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jClickedFailColor = new JLabel();
		var currentClickedFailColor = ColorConverter.convert(optionsService.clickedFailColor());
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
		var currentMineNumOneColor = ColorConverter.convert(optionsService.mineNumOneColor());
		p1.add(individualColorPanel(currentMineNumOneColor, "1", jMineNumOneColor, newColor -> {
			optionsService.setMineNumOneColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumTwoColor = new JLabel();
		var currentMineNumTwoColor = ColorConverter.convert(optionsService.mineNumTwoColor());
		p1.add(individualColorPanel(currentMineNumTwoColor, "2", jMineNumTwoColor, newColor -> {
			optionsService.setMineNumTwoColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var p2 = new JPanel(new GridLayout(0, 3));
		p2.setOpaque(false);

		jMineNumThreeColor = new JLabel();
		var currentMineNumThreeColor = ColorConverter.convert(optionsService.mineNumThreeColor());
		p2.add(individualColorPanel(currentMineNumThreeColor, "3", jMineNumThreeColor, newColor -> {
			optionsService.setMineNumThreeColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumFourColor = new JLabel();
		var currentMineNumFourColor = ColorConverter.convert(optionsService.mineNumFourColor());
		p2.add(individualColorPanel(currentMineNumFourColor, "4", jMineNumFourColor, newColor -> {
			optionsService.setMineNumFourColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumFiveColor = new JLabel();
		var currentMineNumFiveColor = ColorConverter.convert(optionsService.mineNumFiveColor());
		p2.add(individualColorPanel(currentMineNumFiveColor, "5", jMineNumFiveColor, newColor -> {
			optionsService.setMineNumFiveColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));

		var p3 = new JPanel(new GridLayout(0, 3));
		p3.setOpaque(false);

		jMineNumSixColor = new JLabel();
		var currentMineNumSixColor = ColorConverter.convert(optionsService.mineNumSixColor());
		p3.add(individualColorPanel(currentMineNumSixColor, "6", jMineNumSixColor, newColor -> {
			optionsService.setMineNumSixColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumSevenColor = new JLabel();
		var currentMineNumSevenColor = ColorConverter.convert(optionsService.mineNumSevenColor());
		p3.add(individualColorPanel(currentMineNumSevenColor, "7", jMineNumSevenColor, newColor -> {
			optionsService.setMineNumSevenColor(ColorConverter.convertBack(newColor));
			eventSubscriber.notify(new UpdateMinePanelEvent());
		}));
		jMineNumEightColor = new JLabel();
		var currentMineNumEightColor = ColorConverter.convert(optionsService.mineNumEightColor());
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

	private Box individualColorPanel(Color base, String colorName, JLabel colorSwatch, IColorHandler colorHandler) {
		colorSwatch.setPreferredSize(new Dimension(40, 40));
		colorSwatch.setOpaque(true);
		colorSwatch.setBackground(base);

		var color = new JPanel(new GridLayout(0, 1));
		color.add(colorSwatch);
		color.setBorder(BorderFactory.createLineBorder(HexToRgb.convert("#444444"), 1));

		var labelAndButton = new Box(BoxLayout.Y_AXIS);
		labelAndButton.setOpaque(false);
		labelAndButton.add(createJLabel(colorName, SwingConstants.LEFT));
		labelAndButton.add(Box.createVerticalStrut(5));

		labelAndButton.add(new PrimaryButton("Select New Color", evt -> {
			var newColor = JColorChooser.showDialog(null, "Select Base Color", base);
			if (newColor != null) {
				colorSwatch.setBackground(newColor);
				colorHandler.handleSelectedColor(newColor);
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

	private void resetOptions() {
		// System.out.println("Resetting current options...");
	}

	// TODO colors shouldn't set on the board immediately.
	private void setNewOptions() {
		ButtonModel diff = difficultyRadioButtonGroup.getSelection();
		if (diff.equals(easy.getModel())) {
			optionsService.setDifficulty(Difficulty.easy);
		} else if (diff.equals(medium.getModel())) {
			optionsService.setDifficulty(Difficulty.medium);
		} else if (diff.equals(hard.getModel())) {
			optionsService.setDifficulty(Difficulty.hard);
		}
	}

	private int confirmDialog(String message) {
		return JOptionPane.showConfirmDialog(frame, message, "Confirm?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, confirmationIcon);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowOptionsEvent.class, event -> {
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}