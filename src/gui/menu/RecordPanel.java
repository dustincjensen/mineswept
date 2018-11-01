package gui.menu;

import logic.files.Records;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecordPanel extends JPanel {

	private JTable bodyTable;
	private DefaultTableModel tableModel;
	private String[] records;

	public RecordPanel(String level) {
		setupPane(level);
	}

	private void setupPane(String level) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setName(level);
		
		JPanel headerPane = new JPanel();
		headerPane.setLayout(new BoxLayout(headerPane, BoxLayout.X_AXIS));
		JLabel header = new JLabel(level+" Records");//font change
		headerPane.add(header);
		
		JPanel bodyPane = new JPanel();
		bodyPane.setPreferredSize(new Dimension(300,Records.RECORD_LIMIT*10));
		bodyPane.setLayout(new BorderLayout());
		createTable();
		bodyPane.add(bodyTable, BorderLayout.CENTER);

		this.add(headerPane);
		this.add(bodyTable.getTableHeader());
		this.add(bodyPane);
	}

	private void createTable() {
		tableModel = new DefaultTableModel();
		tableModel.addColumn(" ");
		tableModel.addColumn("Name");
		tableModel.addColumn("Time");
		tableModel.addColumn("Date");

		// Don't need to set up the model until we have data
		// records is null at this time

		bodyTable = new JTable(tableModel);
		bodyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		bodyTable.getColumnModel().getColumn(0).setMaxWidth(30);
		bodyTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		bodyTable.getColumnModel().getColumn(2).setMaxWidth(50);
		bodyTable.getColumnModel().getColumn(3).setPreferredWidth(25);
	}

	public void setRecords(String[] rec) {
		records = rec;
		refreshRecords();
	}

	public void refreshRecords() {
		tableModel.setRowCount(0);
		if(records.length % 3 == 0) {
			for(int i=0; i<records.length; i=i+3) {
				String[] row = new String[4];
				row[0] = Integer.toString((i/3)+1);
				row[1] = records[i];
				row[2] = records[i+1];
				row[3] = records[i+2];
				tableModel.addRow(row);
			}
		}
	}

}// End RecordPanel