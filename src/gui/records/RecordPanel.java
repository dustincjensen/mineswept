package gui.records;

import gui.HexToRgb;
import gui.components.table.Cell;
import gui.components.table.CustomReadonlyTable;
import gui.components.table.ICellHandler;
import gui.components.table.TableRow;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.records.Record;
import services.RecordsService;

public class RecordPanel extends JPanel {
	private CustomReadonlyTable table;
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
		setLayout(new GridLayout(0, 1));
		add(createTable());
	}

	private CustomReadonlyTable createTable() {
		TableRow header = new TableRow();
		header.add((r, c) -> new Cell("Time", false));
		header.add((r, c) -> new Cell("Name", true));
		header.add((r, c) -> new Cell("Date", false));
		table = new CustomReadonlyTable(header);
		return table;
	}

	public void setRecords(Record[] rec) {
		records = rec;
		refreshRecords();
	}

	public void refreshRecords() {
		var listOfRows = new ArrayList<TableRow>();
		for (var record : records) {
			TableRow row = new TableRow();
			row.add((r, c) -> new Cell(String.valueOf(record.time), false));
			row.add((r, c) -> new Cell(record.name, true));
			row.add((r, c) -> new Cell(record.date, false));
			listOfRows.add(row);
		}
		table.setData(listOfRows.toArray(TableRow[]::new));
	}
}