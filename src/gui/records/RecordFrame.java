package gui.records;

import javax.swing.JFrame;
import javax.swing.JPanel;
import services.RecordsService;

public class RecordFrame extends JFrame {
    public RecordFrame(JPanel content) {
        super("Records");
        setContentPane(content);
        setSize(300, 175 + RecordsService.RECORD_LIMIT * 15);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}