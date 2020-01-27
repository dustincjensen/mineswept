package services;

import models.Difficulty;
import models.options.Color;
import models.options.Options;

public class OptionsService {
	private static final String FILE_NAME = "options.json";

	private FileService _fileService;
	private Options _cachedOptions;

	public OptionsService(FileService file) {
		_fileService = file;
	}

	/**
	 * Retrieves the color that should be used to decorate the game board squares.
	 * 
	 * @return an object with the RGB color for the game board squares.
	 */
	public Color squareColor() {
		if (_cachedOptions == null) {
			_fileService.withFile(FILE_NAME, new Options(), file -> {
				_cachedOptions = _fileService.readFile(file, Options.class);
			});
		}
		return _cachedOptions.squareColor;
	}

	/**
	 * Sets the color that should be used to decorate the game board squares.
	 * 
	 * @param r the red component of the color.
	 * @param g the green component of the color.
	 * @param b the blue component of the color.
	 */
	public void setSquareColor(int r, int g, int b) {
		_fileService.withFile(FILE_NAME, new Options(), file -> {
			var options = _fileService.readFile(file, Options.class);
			options.squareColor.r = r;
			options.squareColor.g = g;
			options.squareColor.b = b;
			
			_cachedOptions = options;
			
			_fileService.writeFile(file, _cachedOptions);
		});
	}

	/**
	 * Retrieves the color that should be used to decorate the game board alt squares.
	 * 
	 * @return an object with the RGB color for the game board alt squares.
	 */
	public Color squareAltColor() {
		if (_cachedOptions == null) {
			_fileService.withFile(FILE_NAME, new Options(), file -> {
				_cachedOptions = _fileService.readFile(file, Options.class);
			});
		}
		return _cachedOptions.squareAltColor;
	}

	/**
	 * Sets the color that should be used to decorate the game board alt squares.
	 * 
	 * @param r the red component of the color.
	 * @param g the green component of the color.
	 * @param b the blue component of the color.
	 */
	public void setSquareAltColor(int r, int g, int b) {
		_fileService.withFile(FILE_NAME, new Options(), file -> {
			var options = _fileService.readFile(file, Options.class);
			options.squareAltColor.r = r;
			options.squareAltColor.g = g;
			options.squareAltColor.b = b;
			
			_cachedOptions = options;
			
			_fileService.writeFile(file, _cachedOptions);
		});
	}

	/**
	 * Retrieves the difficulty that the game should be played at.
	 * 
	 * @return the difficulty level.
	 */
	public Difficulty difficulty() {
		if (_cachedOptions == null) {
			_fileService.withFile(FILE_NAME, new Options(), file -> {
				_cachedOptions = _fileService.readFile(file, Options.class);
			});
		}
		return Difficulty.getDifficulty(_cachedOptions.difficulty.toLowerCase());
	}

	/**
	 * Sets the difficulty that the game should be played at.
	 * 
	 * @param difficulty the difficulty level.
	 */
	public void setDifficulty(Difficulty difficulty) {
		_fileService.withFile(FILE_NAME, new Options(), file -> {
			var options = _fileService.readFile(file, Options.class);
			options.difficulty = difficulty.toString();

			_cachedOptions = options;

			_fileService.writeFile(file, _cachedOptions);
		});
	}
}