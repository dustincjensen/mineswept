package gui.options;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionsFrame extends JFrame {
    public OptionsFrame(JPanel content) {
        super("Options");
        setContentPane(content);
        setMinimumSize(new Dimension(500, 0));
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}