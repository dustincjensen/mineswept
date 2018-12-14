package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;
import models.Difficulty;
import models.records.All;
import models.records.Record;

public class RecordsService {
	public static final int RECORD_LIMIT = 10;

	private FileService fileService;

	private File records;
	private All allRecords;

	public RecordsService(FileService file) {
		fileService = file;

		load(fileService.get("records.json"));
	}

	// TODO is this the best place for this?
	public All getAllRecords() {
		return this.allRecords;
	}

	public boolean checkAndSaveNewRecord(int time, Difficulty level) {
		AddRecord result = null;

		if (level == Difficulty.easy) {
			result = addNewRecord(allRecords.easy, time);
			allRecords.easy = result.records;
		} else if (level == Difficulty.medium) {
			result = addNewRecord(allRecords.medium, time);
			allRecords.medium = result.records;
		} else if (level == Difficulty.hard) {
			result = addNewRecord(allRecords.hard, time);
			allRecords.hard = result.records;
		}
		
		writeFile(records, allRecords);
		return result != null ? result.wasNewRecord : false;
	}

	public Record[] resetRecords(Difficulty level) {
		// We can decide if resetting restores some computer default records...
		Record[] newRecords = new Record[0];

		if (level == Difficulty.easy) {
			allRecords.easy = newRecords;
		} else if (level == Difficulty.medium) {
			allRecords.medium = newRecords;
		} else if (level == Difficulty.hard) {
			allRecords.hard = newRecords;
		}

		writeFile(records, allRecords);
		return newRecords;
	}

	private AddRecord addNewRecord(Record[] records, int time) {
		ArrayList<Record> newRecords = new ArrayList<Record>(Arrays.asList(records));
		var newRecord =  new Record();
		newRecord.date = getFormattedDate();
		newRecord.name = "Dustin Jensen";
		newRecord.time = time;
		newRecords.add(newRecord);
		
		Record[] returnRecords = sort(newRecords).stream().limit(RECORD_LIMIT).toArray(Record[]::new);
		boolean wasNewRecord = Arrays
			.stream(returnRecords)
			.anyMatch(p -> p.time == newRecord.time && p.date == newRecord.date);

		return new AddRecord(
			returnRecords,
			wasNewRecord
		);
	}

	private String getFormattedDate() {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		return formatter.format(now);
	}

	public boolean load(Optional<File> recordsFile) {
		if (recordsFile.isPresent()) {
			records = recordsFile.get();
		} else {
			var newFile = fileService.createFile("records.json");
			if (newFile.isPresent()) {
				records = newFile.get();
			}
			writeDefaultJson(records);
		}
		
		if (records == null) {
			return false;
		}

		// Otherwise load the files..
		loadAllRecords();

		return true;
	}

	private void loadAllRecords() {
		try {
			String json = new String(Files.readAllBytes(Paths.get(records.toURI())), StandardCharsets.UTF_8);
			Gson gson = new Gson();

			// TODO place this on game state? Or just have classes require Record...
			allRecords = gson.fromJson(json, All.class);

			// TODO better way to do this?
			allRecords.easy = sort(allRecords.easy);
			allRecords.medium = sort(allRecords.medium);
			allRecords.hard = sort(allRecords.hard);
		} catch (Exception e) {
			System.out.println(e);
		}
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

	private void writeDefaultJson(File recordsFile) {
		All all = new All();
		all.easy = new Record[0];
		all.medium = new Record[0];
		all.hard = new Record[0];
		writeFile(recordsFile, all);
	}

	private void writeFile(File recordsFile, All records) {
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();

		fileService.writeFile(recordsFile, new String[] {gson.toJson(records)});
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