package ui.components.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.components.button.LightButton;
import ui.components.button.PrimaryButton;
import ui.utils.FontChange;

@SuppressWarnings("serial")
public class CustomDialog extends JDialog {
    private Component relativeTo;
    private JLabel messageLabel;
    private Answer answer;

    public CustomDialog(Component relativeTo, Type type) {
        this.relativeTo = relativeTo;

        setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        setMinimumSize(new Dimension(300, 150));
        setResizable(false);
        
        // Keep a reference to this label, as it will be set
        // with text when we are told to display!
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.decode("#ffffff"));
        FontChange.setFont(messageLabel, 14);

        var panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Color.decode("#333333"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(messageLabel, BorderLayout.CENTER);

        if (type == Type.YES_NO) yesNoDialog(panel);
        else if (type == Type.OK) okDialog(panel);
        else if (type == Type.OK_CANCEL) okCancelDialog(panel);
        
        add(panel);
    }

    public Answer getAnswer() {
        return answer;
    }
    
    public void show(String title, String message) {
        answer = null;

        setTitle(title);
        messageLabel.setText(message);
        
        pack();
        setLocationRelativeTo(relativeTo);
        setVisible(true);
    }

    private void yesNoDialog(JPanel panel) {
        positiveNegativeDialog(panel, "Yes", Answer.YES, "No", Answer.NO);
    }

    private void okDialog(JPanel panel) {
        var okButton = new PrimaryButton("Ok", l -> {
            answer = Answer.OK;
            setVisible(false);
        });
        panel.add(okButton, BorderLayout.SOUTH);
    }

    private void okCancelDialog(JPanel panel) {
        positiveNegativeDialog(panel, "Ok", Answer.OK, "Cancel", Answer.CANCEL);
    }

    private void positiveNegativeDialog(
        JPanel panel,
        String positiveText,
        Answer positiveAnswer,
        String negativeText,
        Answer negativeAnswer
    ) {
        var positiveButton = new PrimaryButton(positiveText, l -> {
            answer = positiveAnswer;
            setVisible(false);
        });
        var negativeButton = new LightButton(negativeText, l -> {
            answer = negativeAnswer;
            setVisible(false);
        });

        var buttonPanel = new JPanel(new GridLayout(0, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(positiveButton);
        buttonPanel.add(negativeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public enum Type { YES_NO, OK, OK_CANCEL }
    public enum Answer { NO, YES, CANCEL, OK }
}