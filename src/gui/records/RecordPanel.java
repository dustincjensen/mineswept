package gui.records;

import gui.HexToRgb;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.records.Record;
import services.RecordsService;

public class RecordPanel extends JPanel {
	private JTable bodyTable;
	private DefaultTableModel tableModel;
	private Record[] records = {};
	private String title;

	public RecordPanel(String title) {
		this.title = title;
		setupPane();
	}

	public String getPanelTitle() {
		return title;
	}

	private void setupPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(HexToRgb.convert("#333333"));

		JPanel bodyPane = new JPanel();
		bodyPane.setPreferredSize(new Dimension(300, RecordsService.RECORD_LIMIT * 10));
		bodyPane.setLayout(new BorderLayout());
		createTable();
		bodyPane.add(bodyTable, BorderLayout.CENTER);

		add(bodyTable.getTableHeader());
		add(bodyPane);
	}

	private void createTable() {
		tableModel = new DefaultTableModel();
		tableModel.addColumn(" ");
		tableModel.addColumn("Name");
		tableModel.addColumn("Time");
		tableModel.addColumn("Date");

		bodyTable = new JTable(tableModel);
		bodyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		bodyTable.getColumnModel().getColumn(0).setMaxWidth(30);
		bodyTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		bodyTable.getColumnModel().getColumn(2).setMaxWidth(50);
		bodyTable.getColumnModel().getColumn(3).setPreferredWidth(25);
	}

	public void setRecords(Record[] rec) {
		records = rec;
		refreshRecords();
	}

	public void refreshRecords() {
		tableModel.setRowCount(0);

		int i = 0;
		for (var record : records) {
			String[] row = new String[4];
			row[0] = String.valueOf(++i);
			row[1] = record.name;
			row[2] = String.valueOf(record.time);
			row[3] = record.date;
			tableModel.addRow(row);
		}
	}
}