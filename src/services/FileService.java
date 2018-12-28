package services;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Optional;

public class FileService {
	public FileService() {
		System.out.println("Creating File Service");
	}

	/**
	 * Get the game directory.
	 * This will create the directory if it does not yet exist.
	 * 
	 * @return the game directory where files should be saved.
	 */
	public File getGameDir() {
		String userDir = System.getProperty("user.home");
		File gameDir = new File(userDir + "/.MineSwept/");
		
		if (!gameDir.exists()) {
			if (!gameDir.mkdir()) {
				System.out.println("Directory creation failed...");
				return null;
			}
		}

		return gameDir;
	}
	
	public void saveFiles() {
		// TODO save to the file system.
		System.out.println("TODO save files to system.");
	}
	
	public Optional<File> createFile(String fileName) {
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

	public boolean writeFile(File file, String[] lines) {
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

	public Optional<File> get(String fileName) {
		File gameDir = getGameDir();
		if (gameDir != null) {
			File[] files = gameDir.listFiles();
			return get(files, fileName);
		}
		return Optional.empty();
	}

	private Optional<File> get(File[] files, String fileName) {
		return Arrays.stream(files)
			.filter(f -> f.getName().matches(fileName))
			.findAny();
	}
}