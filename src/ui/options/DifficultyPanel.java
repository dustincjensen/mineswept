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
        
        easy = factory.create("<html>Easy<br/><i>10 mines<br/>9x9 grid</i></html>");
        medium = factory.create("<html>Medium<br/><i>40 mines<br/>16x16 grid</i></html>");
        hard = factory.create("<html>Hard<br/><i>99 mines<br/>16x30 grid</i></html>");
        
        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easy);
        difficultyGroup.add(medium);
        difficultyGroup.add(hard);
        
        add(easy);
		add(medium);
        add(hard);
        
        setSelectedDifficulty(puzzleDifficulty);
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

    public void setSelectedDifficulty(Difficulty puzzleDifficulty) {
        easy.setSelected(puzzleDifficulty == Difficulty.easy);
        medium.setSelected(puzzleDifficulty == Difficulty.medium);
        hard.setSelected(puzzleDifficulty == Difficulty.hard);
    }
}