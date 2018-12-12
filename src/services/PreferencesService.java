package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import models.Difficulty;
import models.preferences.Color;
import models.preferences.Preference;
import logic.game.GameState;

public class PreferencesService {
	private FileService fileService;

	private final Map<String, Difficulty> difficultyMap = Map.of(
		"easy", Difficulty.easy,
		"medium", Difficulty.medium,
		"hard", Difficulty.hard);
	private File preferences;
	private Preference preference;

	public PreferencesService(FileService file) {
		fileService = file;

		load(fileService.get("preferences.json"));
	}

	// TODO what happens if preferences are corrupted?
	public int r() { return preference.squareColor.r; }
	public int g() { return preference.squareColor.g; }
	public int b() { return preference.squareColor.b; }
	public Difficulty difficulty() {
		return difficultyMap.get(
			preference.difficulty.toLowerCase()
		);
	}

	public boolean load(Optional<File> preferenceFile) {
		if (preferenceFile.isPresent()) {
			preferences = preferenceFile.get();
		} else {
			var newFile = fileService.createFile("preferences.json");
			if (newFile.isPresent()) {
				preferences = newFile.get();
			}
			writeDefaultJson(preferences);
		}
		
		if (preferences == null) {
			return false;
		}

		// Otherwise load the file...
		loadPreferences();

		return true;
	}

	private void loadPreferences() {
		try {
			String json = new String(Files.readAllBytes(Paths.get(preferences.toURI())), StandardCharsets.UTF_8);
			Gson gson = new Gson();

			// TODO place this on game state? Or just have classes require Preferences...
			preference = gson.fromJson(json, Preference.class);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void writeDefaultJson(File preferencesFile) {
		Preference pref = new Preference();
		pref.difficulty = "easy";
		pref.squareColor = new Color();
		pref.squareColor.r = 50;
		pref.squareColor.g = 125;
		pref.squareColor.b = 240;

		// TODO move into the file management class?
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();

		fileService.writeFile(preferencesFile, new String[] {gson.toJson(pref)});
	}
}