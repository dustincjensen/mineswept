package ui.options;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import ui.components.radioButton.RadioButtonFactory;
import models.Difficulty;

@SuppressWarnings("serial")
public class DifficultyPanel extends JPanel {
    private ButtonGroup difficultyGroup;
	private JRadioButton easy, medium, hard;

    public DifficultyPanel(RadioButtonFactory factory, Difficulty puzzleDifficulty) {
        super(new GridLayout(0, 3));
        setOpaque(false);
        
        difficultyGroup = new ButtonGroup();

        easy = factory.create("<html>Easy<br/><i>10 mines<br/>9x9 grid</i></html>");
        easy.setSelected(puzzleDifficulty == Difficulty.easy);
		difficultyGroup.add(easy);
		add(easy);
        
        medium = factory.create("<html>Medium<br/><i>40 mines<br/>16x16 grid</i></html>");
        medium.setSelected(puzzleDifficulty == Difficulty.medium);
		difficultyGroup.add(medium);
		add(medium);
        
        hard = factory.create("<html>Hard<br/><i>99 mines<br/>16x30 grid</i></html>");
        hard.setSelected(puzzleDifficulty == Difficulty.hard);
		difficultyGroup.add(hard);
		add(hard);
    }

    public Difficulty getSelectedDifficulty() {
        var diff = difficultyGroup.getSelection();
        
        if (diff.equals(easy.getModel())) {
			return Difficulty.easy;
		} else if (diff.equals(medium.getModel())) {
			return Difficulty.medium;
		} else if (diff.equals(hard.getModel())) {
			return Difficulty.hard;
        }
        
        return null;
    }
}