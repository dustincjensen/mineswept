package ui.about;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.components.button.PrimaryButton;
import ui.utils.FontChange;
import ui.window.Window;

// TODO create a custom dialog then just use that instead?
// TODO going to need for yes/no dialogs.
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
    private JLabel messageLabel;

    public AboutDialog() {
        setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        setMinimumSize(new Dimension(300, 150));
        setResizable(false);
        
        // Keep a reference to this label, as it will be set
        // with text when we are told to display!
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.decode("#ffffff"));
        FontChange.setFont(messageLabel, 14);
        
        var okButton = new PrimaryButton("Ok", l -> {
            dispose();
        });

        var panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(Color.decode("#333333"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
        add(panel);
    }
    
    public void show(Window window, String title, String message) {
        setTitle(title);
        messageLabel.setText(message);
        
        pack();
        setLocationRelativeTo(window);
        setVisible(true);
    }
}