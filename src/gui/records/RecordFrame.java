package gui.records;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import services.RecordsService;

public class RecordFrame extends JFrame {
    public RecordFrame(JPanel content) {
        super("Records");
        setContentPane(content);
        setSize(300, 450);
        setMinimumSize(new Dimension(300, 450));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}