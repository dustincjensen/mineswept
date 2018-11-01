package gui.menu;

import logic.game.MineField;
import gui.panel.header.ResetButton;
import gui.panel.mines.MineButton;
import gui.panel.mines.MinePanel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionWindow {

	//Panels
	private static JFrame optionsWindow;
	private static JPanel optionsPane, subPane, difficulty, acceptDecline, otherOptions;
	private static boolean optionsHaveChanged;
	
	//Difficulty variables
	private static JRadioButton easy, medium, hard, custom;
	private static ButtonGroup bg;
	private static JTextField height_t, width_t, mines_t;

	//OtherOptions variables
	private static JCheckBox tempCheckBox;
	private static JButton mineButtonColor;

	//AcceptDecline variables
	private static JButton confirm, cancel;


	public static void init() {
		setupPane();
		setupWindow();
		optionsHaveChanged = false;
	}

	public static void setupPane() {
		optionsPane = new JPanel();
		optionsPane.setLayout(new BoxLayout(optionsPane, BoxLayout.Y_AXIS));

		setupSubPane();
		setupAcceptDecline();
		optionsPane.add(subPane);
		optionsPane.add(acceptDecline);
	}

	public static void setupWindow() {
		optionsWindow = new JFrame("Options");
		optionsWindow.setContentPane(optionsPane);

		optionsWindow.setSize(500,350);
		optionsWindow.setLocationRelativeTo(null);
		optionsWindow.setResizable(false);
		optionsWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public static void setupSubPane() {
		setupDifficulty();
		setupOtherOptions();

		subPane = new JPanel();
		subPane.setLayout(new BoxLayout(subPane, BoxLayout.X_AXIS));
		subPane.add(difficulty);
		subPane.add(otherOptions);
	}

	public static void setupDifficulty() {
		difficulty = new JPanel();
		difficulty.setLayout(new BoxLayout(difficulty, BoxLayout.Y_AXIS));
		difficulty.setBorder(BorderFactory.createTitledBorder("Difficulty"));

		ButtonActions rb = new ButtonActions();
		easy = new JRadioButton("<html>Beginner<br/>10 mines<br/>9 x 9 tile grid</html>");
		medium = new JRadioButton("<html>Intermediate<br/>40 mines<br/>16 x 16 tile grid</html>");
		hard = new JRadioButton("<html>Advanced<br/>99 mines<br/>16 x 30 tile grid</html>");
		easy.addActionListener(rb); medium.addActionListener(rb); hard.addActionListener(rb);
		difficulty.add(easy); difficulty.add(medium); difficulty.add(hard);

		custom = new JRadioButton("Custom");
		custom.addActionListener(rb);

		bg = new ButtonGroup();
		bg.add(easy); bg.add(medium); bg.add(hard); bg.add(custom);

		chooseSelectedDifficulty();

		JPanel hwm = new JPanel();
		SpringLayout sl = new SpringLayout();
		hwm.setLayout(sl);

		JLabel height_label = new JLabel("<html>Height (5-50):</html>");
		JLabel width_label  = new JLabel("<html>Width  (5-50):</html>");
		JLabel mines_label  = new JLabel("<html>Mines (5-500):</html>");
		height_t = new JTextField(10);
		width_t  = new JTextField(10);
		mines_t  = new JTextField(10);
		hwm.add(height_label); hwm.add(height_t);
		hwm.add(width_label); hwm.add(width_t);
		hwm.add(mines_label); hwm.add(mines_t);

		sl.putConstraint(SpringLayout.WEST, height_label, 27, SpringLayout.WEST, hwm);
		sl.putConstraint(SpringLayout.WEST, width_label, 27, SpringLayout.WEST, hwm);
		sl.putConstraint(SpringLayout.WEST, mines_label, 27, SpringLayout.WEST, hwm);

		sl.putConstraint(SpringLayout.NORTH, height_label, 5, SpringLayout.NORTH, hwm);
		sl.putConstraint(SpringLayout.NORTH, width_label, 12, SpringLayout.SOUTH, height_label);
		sl.putConstraint(SpringLayout.NORTH, mines_label, 12, SpringLayout.SOUTH, width_label);

		sl.putConstraint(SpringLayout.WEST, height_t, 4, SpringLayout.EAST, height_label);
		sl.putConstraint(SpringLayout.WEST, width_t,  10, SpringLayout.EAST, width_label);
		sl.putConstraint(SpringLayout.WEST, mines_t,  2, SpringLayout.EAST, mines_label);

		sl.putConstraint(SpringLayout.NORTH, height_t, 0, SpringLayout.NORTH, hwm);
		sl.putConstraint(SpringLayout.NORTH, width_t,  0, SpringLayout.SOUTH, height_t);
		sl.putConstraint(SpringLayout.NORTH, mines_t,  0, SpringLayout.SOUTH, width_t);

		difficulty.add(custom);
		difficulty.add(hwm);
		difficulty.add(Box.createVerticalGlue());
	}

	private static void chooseSelectedDifficulty() {
		int cp = MineField.getCurrentPuzzle();
		if(cp == 0) easy.setSelected(true);
		else if(cp == 1) medium.setSelected(true);
		else if(cp == 2) hard.setSelected(true);
		//else if(cp ==)
	}

	public static void setupAcceptDecline() {
		acceptDecline = new JPanel();
		acceptDecline.setLayout(new BoxLayout(acceptDecline, BoxLayout.X_AXIS));

		ButtonActions ba = new ButtonActions();
		confirm = new JButton("OK");
		cancel = new JButton("Cancel");
		confirm.addActionListener(ba);
		cancel.addActionListener(ba);

		acceptDecline.add(Box.createHorizontalGlue());
		acceptDecline.add(confirm);
		acceptDecline.add(cancel);
	}

	public static void setupOtherOptions() {
		otherOptions = new JPanel();
		otherOptions.setLayout(new BoxLayout(otherOptions, BoxLayout.Y_AXIS));
		otherOptions.setBorder(BorderFactory.createTitledBorder("Other"));
		
		ButtonActions ba = new ButtonActions();

		tempCheckBox = new JCheckBox("TEMP");
		tempCheckBox.addActionListener(ba);
		otherOptions.add(tempCheckBox);

		mineButtonColor = new JButton("Minefield Colour");
		mineButtonColor.addActionListener(ba);
		otherOptions.add(mineButtonColor);

		otherOptions.add(Box.createVerticalGlue());
	}

	public static void show(boolean t) {
		if(t) saveCurrentOptions();
		optionsWindow.setVisible(t);
	}

	public static void saveCurrentOptions() {

	}

	public static void resetOptions() {

	}

	public static void setNewOptions() {
		ButtonModel diff = bg.getSelection();
		if(diff.equals(easy.getModel())) {
			MineField.setNextPuzzle(0);
		} else if(diff.equals(medium.getModel())) {
			MineField.setNextPuzzle(1);
		} else if(diff.equals(hard.getModel())) {
			MineField.setNextPuzzle(2);
		}
	}

	public static int confirmDialog(String message) {
		return JOptionPane.showConfirmDialog(null, message,
									  		 "Confirm?",
									  		 JOptionPane.YES_NO_OPTION,	//CHANGE TO YES_NO_CANCEL and 
									  		 JOptionPane.QUESTION_MESSAGE,
									  		 ResetButton.getSmileyHappy());
	}

	private static class ButtonActions implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == easy) {
				if(MineField.getCurrentPuzzle() != 0) optionsHaveChanged = true;
				else optionsHaveChanged = false;
			} 
			else if(evt.getSource() == medium) {
				if(MineField.getCurrentPuzzle() != 1) optionsHaveChanged = true;
				else optionsHaveChanged = false;
			}
			else if(evt.getSource() == hard) {
				if(MineField.getCurrentPuzzle() != 2) optionsHaveChanged = true;
				else optionsHaveChanged = false;
			}
			else if(evt.getSource() == custom) {
				if(MineField.getCurrentPuzzle() != 3) optionsHaveChanged = true;
				else optionsHaveChanged = false;
			}

			if(evt.getSource() == confirm) {
				if(optionsHaveChanged) {
					int yes_no = confirmDialog("Would you like to change the options?");
					if(yes_no == JOptionPane.YES_OPTION) {
						setNewOptions();
					}
					optionsHaveChanged = false;
				}
				OptionWindow.show(false);
			}
			else if(evt.getSource() == cancel) {
				System.out.println("CANCEL");
				resetOptions();
				OptionWindow.show(false);
			}
			else if(evt.getSource() == tempCheckBox)
				System.out.println("TEMP CHECK BOX");
			else if(evt.getSource() == mineButtonColor) {
				Color temp = JColorChooser.showDialog(null,
                    								  "Choose Minefield Colour",
                    								  MineButton.getBackgroundColor());
				if(temp != null)
					MineButton.setBackgroundColor(temp);
				MinePanel.update();
			}
		}	
	}


}