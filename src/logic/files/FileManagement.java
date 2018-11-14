package logic.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Optional;

public class FileManagement {
	private static File gameDir;

	public static void init() {
		if (gameDirectory()) {
			var files = gameDir.listFiles();
			Preferences.load(get(files, "prefs"));
			Records.load(get(files, "records"));
			// Statistics.load(get(files, "statistics"));
		}
	}

	public static File getGameDir() {
		return gameDir;
	}
	
	public static void saveFiles() {
		// TODO save to the file system.
		System.out.println("TODO save files to system.");
	}
	
	public static Optional<File> createFile(String fileName) {
		var fileToCreate = new File(getGameDir().toString() + "/" + fileName);
		try {
			if (!fileToCreate.createNewFile()) {
				System.out.println("Unable to create new " + fileName + " file.");
				return Optional.empty();
			}
		} catch (Exception ex) {
			System.out.println("I/O exception: Preferences File");
			return Optional.empty();
		}
		return Optional.of(fileToCreate);
	}

	public static boolean writeFile(File file, String[] lines) {
		try {
			FileWriter writer = new FileWriter(file);
			for (String line : lines) {
				writer.write(line);
			}
			writer.close();
			return true;
		} catch (Exception ex) {
			System.out.println("Error writing " + file.getName() + ".");
			return false;
		}
	}

	private static Optional<File> get(File[] files, String fileName) {
		return Arrays.stream(files)
			.filter(f -> f.getName().matches(fileName))
			.findAny();
	}

	private static boolean gameDirectory() {
		String userDir = System.getProperty("user.home");
		gameDir = new File(userDir + "/.MineSwept/");
		if (!gameDir.exists()) {
			if (!gameDir.mkdir()) {
				System.out.println("Directory creation failed...");
				return false;
			}
		}
		return true;
	}
}