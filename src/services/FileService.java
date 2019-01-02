package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.FileLoadException;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class FileService {
	private static final String USER_DIRECTORY = "user.home";
	private static final String MINE_SWEPT_DIRECTORY = ".MineSwept";

	/**
	 * Handles the invocation of a method that requires a file to do it's action.
	 * 
	 * @param fileName the name of the file to get or create.
	 * @param defaults the defaults if the file does not exist.
	 * @param method the method to invoke with the loaded file.
	 * @return an object matching the expected type of the method invoked.
	 */
	public <T> T withFile(String fileName, Object defaults, IRequiresFileAndHasReturn<T> method) {
		try {
			var file = getOrCreate(fileName, defaults);
			return method.invoke(file);
		} catch (FileLoadException ex) {
			System.err.println("Could not load the file: " + fileName);
			return null;
		}
	}

	/**
	 * Handles the invocation of a method that requires a file to do it's action.
	 * 
	 * @param fileName the name of the file to get or create.
	 * @param defaults the defaults if the file does not exist.
	 * @param method the method to invoke with the loaded file.
	 */
	public void withFile(String fileName, Object defaults, IRequiresFile method) {
		try {
			var file = getOrCreate(fileName, defaults);
			method.invoke(file);
		} catch (FileLoadException ex) {
			System.err.println("Could not load the file: " + fileName);
		}
	}

	/**
	 * Reads a file as a json object of a specific type.
	 * 
	 * @param file the file to read the json object from.
	 * @param type the type to try and cast the json object to.
	 * @return the json read into an object of type <T>.
	 */
	public <T> T readFile(File file, Class<T> type) {
		try {
			var json = new String(Files.readAllBytes(Paths.get(file.toURI())), StandardCharsets.UTF_8);
			return new Gson().fromJson(json, type);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	/**
	 * Write a json object to a file.
	 * 
	 * @param file the file to write the json object to.
	 * @param object the json object to write.
	 * @return true if the json object was successfully written to file, otherwise false.
	 */
	public boolean writeFile(File file, Object object) {
		var gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();

		try {
			var writer = new FileWriter(file);
			for (var line : new String[] { gson.toJson(object) }) {
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
	 * Get or create the file with the provided name.
	 * If the file does not exist, it is created and is populated 
	 * with the default object provided.
	 * 
	 * @param fileName the name of the file to get or create.
	 * @param defaults the defaults if the file does not exist.
	 * @return the file that matches the file name.
	 * @throws FileLoadException thrown if we cannot load or create a file with the given name.
	 */
	private File getOrCreate(String fileName, Object defaults) throws FileLoadException {
		var file = get(fileName);
		if (file.isPresent()) {
			return file.get();
		}

		file = createFile(fileName);
		if (file.isPresent()) {
			writeFile(file.get(), defaults);
			return file.get();
		}

		throw new FileLoadException();
	}

	/**
	 * Return the file reference for the given file name.
	 * 
	 * @param fileName the name of the file to retrieve.
	 * @return reference to the file if the game directory exists, otherwise empty.
	 */
	private Optional<File> get(String fileName) {
		var gameDir = getGameDir();
		if (gameDir != null) {
			return Arrays.stream(gameDir.listFiles())
				.filter(f -> f.getName().matches(fileName))
				.findAny();
		}
		return Optional.empty();
	}

	/**
	 * Create a file with the given name.
	 * 
	 * @param fileName the name of the file to create.
	 * @return a reference to the file that was created, otherwise empty. 
	 */
	private Optional<File> createFile(String fileName) {
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