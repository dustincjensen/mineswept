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
		return options().squareColor;
	}

	/**
	 * Sets the color that should be used to decorate the game board squares.
	 * 
	 * @param color the new color to use.
	 */
	public void setSquareColor(Color color) {
		setOptions(options -> {
			options.squareColor = color;
		});
	}

	/**
	 * Retrieves the color that should be used to decorate the game board alt squares.
	 * 
	 * @return an object with the RGB color for the game board alt squares.
	 */
	public Color squareAltColor() {
		return options().squareAltColor;
	}

	/**
	 * Sets the color that should be used to decorate the game board alt squares.
	 * 
	 * @param color the new color to use.
	 */
	public void setSquareAltColor(Color color) {
		setOptions(options -> {
			options.squareAltColor = color;
		});
	}

	public Color clickedColor() {
		return options().clickedColor;
	}

	public void setClickedColor(Color color) {
		setOptions(options -> {
			options.clickedColor = color;
		});
	}

	public Color clickedAltColor() {
		return options().clickedAltColor;
	}

	public void setClickedAltColor(Color color) {
		setOptions(options -> {
			options.clickedAltColor = color;
		});
	}

	public Color clickedFailColor() {
		return options().clickedFailColor;
	}

	public void setClickedFailColor(Color color) {
		setOptions(options -> {
			options.clickedFailColor = color;
		});
	}

	public Color mineNumOneColor() {
		return options().mineNumOneColor;
	}

	public void setMineNumOneColor(Color color) {
		setOptions(options -> {
			options.mineNumOneColor = color;
		});
	}

	public Color mineNumTwoColor() {
		return options().mineNumTwoColor;
	}

	public void setMineNumTwoColor(Color color) {
		setOptions(options -> {
			options.mineNumTwoColor = color;
		});
	}

	public Color mineNumThreeColor() {
		return options().mineNumThreeColor;
	}

	public void setMineNumThreeColor(Color color) {
		setOptions(options -> {
			options.mineNumThreeColor = color;
		});
	}

	public Color mineNumFourColor() {
		return options().mineNumFourColor;
	}

	public void setMineNumFourColor(Color color) {
		setOptions(options -> {
			options.mineNumFourColor = color;
		});
	}

	public Color mineNumFiveColor() {
		return options().mineNumFiveColor;
	}

	public void setMineNumFiveColor(Color color) {
		setOptions(options -> {
			options.mineNumFiveColor = color;
		});
	}

	public Color mineNumSixColor() {
		return options().mineNumSixColor;
	}

	public void setMineNumSixColor(Color color) {
		setOptions(options -> {
			options.mineNumSixColor = color;
		});
	}

	public Color mineNumSevenColor() {
		return options().mineNumSevenColor;
	}

	public void setMineNumSevenColor(Color color) {
		setOptions(options -> {
			options.mineNumSevenColor = color;
		});
	}

	public Color mineNumEightColor() {
		return options().mineNumEightColor;
	}

	public void setMineNumEightColor(Color color) {
		setOptions(options -> {
			options.mineNumEightColor = color;
		});
	}

	/**
	 * Retrieves the difficulty that the game should be played at.
	 * 
	 * @return the difficulty level.
	 */
	public Difficulty difficulty() {
		return Difficulty.getDifficulty(
			options().difficulty.toLowerCase());
	}

	/**
	 * Sets the difficulty that the game should be played at.
	 * 
	 * @param difficulty the difficulty level.
	 */
	public void setDifficulty(Difficulty difficulty) {
		setOptions(options -> {
			options.difficulty = difficulty.toString();
		});
	}

	private Options options() {
		if (_cachedOptions == null) {
			_fileService.withFile(FILE_NAME, new Options(), file -> {
				_cachedOptions = _fileService.readFile(file, Options.class);
			});
		}
		return _cachedOptions;
	}

	private void setOptions(IOptionsHandler optionsHandler) {
		_fileService.withFile(FILE_NAME, new Options(), file -> {
			var options = _fileService.readFile(file, Options.class);
			optionsHandler.handle(options);

			_cachedOptions = options;

			_fileService.writeFile(file, _cachedOptions);
		});
	}
}