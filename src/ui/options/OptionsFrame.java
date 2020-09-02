package ui.options;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OptionsFrame extends JFrame {
    public OptionsFrame(JPanel content) {
        super("Options");
        setContentPane(content);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}