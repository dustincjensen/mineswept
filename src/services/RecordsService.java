package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import models.Difficulty;
import models.records.AllRecords;
import models.records.Record;

public class RecordsService {
	private static final String FILE_NAME = "records.json";
	private static final int RECORD_LIMIT = 10;

	private FileService _fileService;

	public RecordsService(FileService file) {
		_fileService = file;
	}

	/**
	 * Gets the records from the records json file.
	 * 
	 * @return the records from the file.
	 */
	public AllRecords getAllRecords() {
		return _fileService.withFile(FILE_NAME, new AllRecords(), file -> {
			var records = _fileService.read(file, AllRecords.class);
			records.easy = sort(records.easy);
			records.medium = sort(records.medium);
			records.hard = sort(records.hard);
			return records;
		});
	}

	/**
	 * Add the time to the difficulty list and sort to see if the time was a new record.
	 * 
	 * @param time the time to add to the records list.
	 * @param level the difficulty level the time was achieved at.
	 * @return true if the new time was a record for the difficulty, otherwise false.
	 */
	public boolean checkAndSaveNewRecord(int time, Difficulty level) {
		return _fileService.withFile(FILE_NAME, new AllRecords(), file -> {
			var records = _fileService.read(file, AllRecords.class);

			AddRecord result = null;
			if (level == Difficulty.easy) {
				result = addNewRecord(records.easy, time);
				records.easy = result.records;
			} else if (level == Difficulty.medium) {
				result = addNewRecord(records.medium, time);
				records.medium = result.records;
			} else if (level == Difficulty.hard) {
				result = addNewRecord(records.hard, time);
				records.hard = result.records;
			}
			
			_fileService.writeFile(file, records);
			
			return result != null ? result.wasNewRecord : false;
		});
	}

	/**
	 * Reset the records for a specific difficulty.
	 * 
	 * @param level the difficulty level to reset.
	 * @return the new records that were saved to the file.
	 */
	public Record[] resetRecords(Difficulty level) {
		return _fileService.withFile(FILE_NAME, new AllRecords(), file -> {
			var records = _fileService.read(file, AllRecords.class);

			// We can decide if resetting restores some computer default records...
			var newRecords = new Record[0];

			if (level == Difficulty.easy) {
				records.easy = newRecords;
			} else if (level == Difficulty.medium) {
				records.medium = newRecords;
			} else if (level == Difficulty.hard) {
				records.hard = newRecords;
			}

			_fileService.writeFile(file, records);
			
			return newRecords;
		});
	}

	private AddRecord addNewRecord(Record[] records, int time) {
		var newRecords = new ArrayList<Record>(Arrays.asList(records));
		var newRecord =  new Record();
		newRecord.date = getFormattedDate();
		newRecord.time = time;
		newRecords.add(newRecord);
		
		var returnRecords = sort(newRecords).stream().limit(RECORD_LIMIT).toArray(Record[]::new);
		var wasNewRecord = Arrays
			.stream(returnRecords)
			.anyMatch(p -> p.time == newRecord.time && p.date == newRecord.date);

		return new AddRecord(
			returnRecords,
			wasNewRecord
		);
	}

	private String getFormattedDate() {
		return new SimpleDateFormat("MMM dd, yyyy").format(new Date());
	}

	private Record[] sort(Record[] records) {
		return Arrays.stream(records)
			.sorted(Comparator.comparing(r -> r.time))
			.toArray(Record[]::new);
	}

	private ArrayList<Record> sort(ArrayList<Record> records) {
		records.sort(Comparator.comparing(r -> r.time));
		return records;
	}
}

class AddRecord {
	public Record[] records;
	public boolean wasNewRecord;

	public AddRecord(Record[] records, boolean wasNewRecord) {
		this.records = records;
		this.wasNewRecord = wasNewRecord;
	}
}