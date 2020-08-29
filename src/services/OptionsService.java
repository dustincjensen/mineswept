package services;

import java.util.function.Consumer;
import models.Difficulty;
import models.options.BorderType;
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
	public String squareColor() {
		return options().squareColor;
	}

	/**
	 * Sets the color that should be used to decorate the game board squares.
	 * 
	 * @param color the new color to use.
	 */
	public void setSquareColor(String color) {
		setOptions(options -> {
			options.squareColor = color;
		});
	}

	/**
	 * Retrieves the color that should be used to decorate the game board alt squares.
	 * 
	 * @return an object with the RGB color for the game board alt squares.
	 */
	public String squareAltColor() {
		return options().squareAltColor;
	}

	/**
	 * Sets the color that should be used to decorate the game board alt squares.
	 * 
	 * @param color the new color to use.
	 */
	public void setSquareAltColor(String color) {
		setOptions(options -> {
			options.squareAltColor = color;
		});
	}

	public String clickedColor() {
		return options().clickedColor;
	}

	public void setClickedColor(String color) {
		setOptions(options -> {
			options.clickedColor = color;
		});
	}

	public String clickedAltColor() {
		return options().clickedAltColor;
	}

	public void setClickedAltColor(String color) {
		setOptions(options -> {
			options.clickedAltColor = color;
		});
	}

	public String clickedFailColor() {
		return options().clickedFailColor;
	}

	public void setClickedFailColor(String color) {
		setOptions(options -> {
			options.clickedFailColor = color;
		});
	}

	public String mineNumOneColor() {
		return options().mineNumOneColor;
	}

	public void setMineNumOneColor(String color) {
		setOptions(options -> {
			options.mineNumOneColor = color;
		});
	}

	public String mineNumTwoColor() {
		return options().mineNumTwoColor;
	}

	public void setMineNumTwoColor(String color) {
		setOptions(options -> {
			options.mineNumTwoColor = color;
		});
	}

	public String mineNumThreeColor() {
		return options().mineNumThreeColor;
	}

	public void setMineNumThreeColor(String color) {
		setOptions(options -> {
			options.mineNumThreeColor = color;
		});
	}

	public String mineNumFourColor() {
		return options().mineNumFourColor;
	}

	public void setMineNumFourColor(String color) {
		setOptions(options -> {
			options.mineNumFourColor = color;
		});
	}

	public String mineNumFiveColor() {
		return options().mineNumFiveColor;
	}

	public void setMineNumFiveColor(String color) {
		setOptions(options -> {
			options.mineNumFiveColor = color;
		});
	}

	public String mineNumSixColor() {
		return options().mineNumSixColor;
	}

	public void setMineNumSixColor(String color) {
		setOptions(options -> {
			options.mineNumSixColor = color;
		});
	}

	public String mineNumSevenColor() {
		return options().mineNumSevenColor;
	}

	public void setMineNumSevenColor(String color) {
		setOptions(options -> {
			options.mineNumSevenColor = color;
		});
	}

	public String mineNumEightColor() {
		return options().mineNumEightColor;
	}

	public void setMineNumEightColor(String color) {
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

	/**
	 * Retrieves the raised border type.
	 * 
	 * @return the raised border type.
	 */
	public BorderType raisedBorder() {
		return options().raisedBorder;
	}

	/**
	 * Sets the raised border type.
	 * 
	 * @param type the raised border type.
	 */
	public void setRaisedBorder(BorderType type) {
		setOptions(options -> {
			options.raisedBorder = type;
		});
	}

	/**
	 * Retrieves the lowered border type.
	 * 
	 * @return the lowered border type.
	 */
	public BorderType loweredBorder() {
		return options().loweredBorder;
	}

	/**
	 * Sets the lowered border type.
	 * 
	 * @param type the lowered border type.
	 */
	public void setLoweredBorder(BorderType type) {
		setOptions(options -> {
			options.loweredBorder = type;
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

	private void setOptions(Consumer<Options> optionsHandler) {
		_fileService.withFile(FILE_NAME, new Options(), file -> {
			var options = _fileService.readFile(file, Options.class);
			optionsHandler.accept(options);

			_cachedOptions = options;

			_fileService.writeFile(file, _cachedOptions);
		});
	}
}