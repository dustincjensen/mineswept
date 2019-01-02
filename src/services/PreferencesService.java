package services;

import models.Difficulty;
import models.preferences.Color;
import models.preferences.Preference;

public class PreferencesService {
	private static final String FILE_NAME = "preferences.json";

	private FileService _fileService;
	private Preference _cachedPreferences;

	public PreferencesService(FileService file) {
		_fileService = file;
	}

	/**
	 * Retrieves the color that should be used to decorate the game board squares.
	 * 
	 * @return an object with the RGB color for the game board squares.
	 */
	public Color squareColor() {
		if (_cachedPreferences == null) {
			_fileService.withFile(FILE_NAME, new Preference(), file -> {
				var preferences = _fileService.readFile(file, Preference.class);
				_cachedPreferences = preferences;
			});
		}
		return _cachedPreferences.squareColor;
	}

	/**
	 * Sets the color that should be used to decorate the game board squares.
	 * 
	 * @param r the red component of the color.
	 * @param g the green component of the color.
	 * @param b the blue component of the color.
	 */
	public void setSquareColor(int r, int g, int b) {
		_fileService.withFile(FILE_NAME, new Preference(), file -> {
			var preferences = _fileService.readFile(file, Preference.class);
			preferences.squareColor.r = r;
			preferences.squareColor.g = g;
			preferences.squareColor.b = b;
			
			_cachedPreferences = preferences;
			
			_fileService.writeFile(file, _cachedPreferences);
		});
	}

	/**
	 * Retrieves the difficulty that the game should be played at.
	 * 
	 * @return the difficulty level.
	 */
	public Difficulty difficulty() {
		if (_cachedPreferences == null) {
			_fileService.withFile(FILE_NAME, new Preference(), file -> {
				var preferences = _fileService.readFile(file, Preference.class);
				_cachedPreferences = preferences;
			});
		}
		return Difficulty.getDifficulty(_cachedPreferences.difficulty.toLowerCase());
	}

	/**
	 * Sets the difficulty that the game should be played at.
	 * 
	 * @param difficulty the difficulty level.
	 */
	public void setDifficulty(Difficulty difficulty) {
		_fileService.withFile(FILE_NAME, new Preference(), file -> {
			var preferences = _fileService.readFile(file, Preference.class);
			preferences.difficulty = difficulty.toString();

			_cachedPreferences = preferences;

			_fileService.writeFile(file, _cachedPreferences);
		});
	}
}