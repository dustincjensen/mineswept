package services;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Optional;

public class FileService {
	private static final String USER_DIRECTORY = "user.home";
	private static final String MINE_SWEPT_DIRECTORY = ".MineSwept";

	/**
	 * Create a file with the given name.
	 * 
	 * @param fileName the name of the file to create.
	 * @return a reference to the file that was created, otherwise empty. 
	 */
	public Optional<File> createFile(String fileName) {
		var fileToCreate = new File(getGameDir().toString() + "/" + fileName);
		try {
			if (!fileToCreate.createNewFile()) {
				throw new Exception();
			}
		} catch (Exception ex) {
			System.err.println("Unable to create new " + fileName + " file.");
			return Optional.empty();
		}
		return Optional.of(fileToCreate);
	}

	/**
	 * Write a set of lines to the given file.
	 * 
	 * @param file the file to write the lines to.
	 * @param lines the lines to write to the file.
	 * @return true if lines were written successfully, otherwise false.
	 */
	public boolean writeFile(File file, String[] lines) {
		try {
			var writer = new FileWriter(file);
			for (var line : lines) {
				writer.write(line);
			}
			writer.close();
			return true;
		} catch (Exception ex) {
			System.err.println("Error writing " + file.getName() + ".");
			return false;
		}
	}

	/**
	 * Return the file reference for the given file name.
	 * 
	 * @param fileName the name of the file to retrieve.
	 * @return reference to the file if the game directory exists, otherwise empty.
	 */
	public Optional<File> get(String fileName) {
		var gameDir = getGameDir();
		if (gameDir != null) {
			var files = gameDir.listFiles();
			return get(files, fileName);
		}
		return Optional.empty();
	}

	/**
	 * Return a file reference from a list of files by the file name.
	 * 
	 * @param files the files to check for the file name.
	 * @param fileName the file name to check for.
	 * @return reference to the file if it exists in the list, otherwise empty.
	 */
	private Optional<File> get(File[] files, String fileName) {
		return Arrays.stream(files)
			.filter(f -> f.getName().matches(fileName))
			.findAny();
	}

	/**
	 * Get the game directory.
	 * This will create the directory if it does not yet exist.
	 * 
	 * @return the game directory where files should be saved.
	 */
	private File getGameDir() {
		var userDir = System.getProperty(USER_DIRECTORY);
		var gameDir = new File(userDir + "/" + MINE_SWEPT_DIRECTORY);
		
		if (!gameDir.exists() && !gameDir.mkdir()) {
			System.err.println("Directory creation failed...");
			return null;
		}
		return gameDir;
	}
}