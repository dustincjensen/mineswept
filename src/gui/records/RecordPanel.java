package gui.records;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import models.records.Record;
import services.RecordsService;

public class RecordPanel extends JPanel {
	private JTable bodyTable;
	private DefaultTableModel tableModel;
	private Record[] records = {};

	public RecordPanel(String level) {
		setupPane(level);
	}

	private void setupPane(String level) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setName(level);

		JPanel headerPane = new JPanel();
		headerPane.setLayout(new BoxLayout(headerPane, BoxLayout.X_AXIS));
		JLabel header = new JLabel(level + " Records");// font change
		headerPane.add(header);

		JPanel bodyPane = new JPanel();
		bodyPane.setPreferredSize(new Dimension(300, RecordsService.RECORD_LIMIT * 10));
		bodyPane.setLayout(new BorderLayout());
		createTable();
		bodyPane.add(bodyTable, BorderLayout.CENTER);

		add(headerPane);
		add(bodyTable.getTableHeader());
		add(bodyPane);
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

	public void setRecords(Record[] rec) {
		records = rec;
		refreshRecords();
	}

	public void refreshRecords() {
		tableModel.setRowCount(0);

		var sortedRecords = Arrays.stream(records)
			.sorted(Comparator.comparing(r -> r.time))
			.toArray(Record[]::new);

		int i = 0;
		for (var record : sortedRecords) {
			String[] row = new String[4];
			row[0] = String.valueOf(++i);
			row[1] = record.name;
			row[2] = String.valueOf(record.time);
			row[3] = record.date;
			tableModel.addRow(row);
		}
	}
}