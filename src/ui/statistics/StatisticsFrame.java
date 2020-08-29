package ui.statistics;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatisticsFrame extends JFrame {
    public StatisticsFrame(JPanel content) {
        super("Statistics");
        setContentPane(content);
        setMinimumSize(new Dimension(500, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}