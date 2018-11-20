package logic.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import logic.files.records.All;
import logic.files.records.Record;

public class Records {
	public static final int RECORD_LIMIT = 10;

	private File records;
	private All allRecords;

	public Records() {
		load(FileManagement.get("records.json"));
	}

	// TODO is this the best place for this?
	public All getAllRecords() {
		return this.allRecords;
	}

	public boolean load(Optional<File> recordsFile) {
		if (recordsFile.isPresent()) {
			records = recordsFile.get();
		} else {
			var newFile = FileManagement.createFile("records.json");
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
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void writeDefaultJson(File recordsFile) {
		All all = new All();
		all.beginner = new Record[0];
		all.intermediate = new Record[0];
		all.advanced = new Record[0];

		// TODO move into the file management class?
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();

		FileManagement.writeFile(recordsFile, new String[] {gson.toJson(all)});
	}
}