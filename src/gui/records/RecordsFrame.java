package gui.records;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RecordsFrame extends JFrame {
    public RecordsFrame(JPanel content) {
        super("Records");
        setContentPane(content);
        setSize(300, 450);
        setMinimumSize(new Dimension(300, 450));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}