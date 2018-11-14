package logic.files;

import gui.records.RecordWindow;
import java.io.File;
import java.util.Optional;

public class Records {
	public static final int RECORD_LIMIT = 10;
	private static String[] defaultFileLines = {
		"MineSwept Records\n",
		"[Beginner]\n",
		"[Intermediate]\n",
		"[Advanced]\n"
	};

	private static File records;

	public static boolean load(Optional<File> recordsFile) {
		if (recordsFile.isPresent()) {
			records = recordsFile.get();
		} else {
			var newFile = FileManagement.createFile("records");
			if (newFile.isPresent()) {
				records = newFile.get();
			}
			FileManagement.writeFile(records, defaultFileLines);
		}
		
		if (records == null) {
			return false;
		}

		return true;
	}
}