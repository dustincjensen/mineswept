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
	 * @return the hex string of the color.
	 */
	public String squareColor() {
		return options().squareColor;
	}

	/**
	 * Retrieves the color that should be used to decorate the game board alt squares.
	 * 
	 * @return the hex string of the color.
	 */
	public String squareAltColor() {
		return options().squareAltColor;
	}

	public String clickedColor() {
		return options().clickedColor;
	}

	public String clickedAltColor() {
		return options().clickedAltColor;
	}

	public String clickedFailColor() {
		return options().clickedFailColor;
	}

	public String mineNumOneColor() {
		return options().mineNumOneColor;
	}

	public String mineNumTwoColor() {
		return options().mineNumTwoColor;
	}

	public String mineNumThreeColor() {
		return options().mineNumThreeColor;
	}

	public String mineNumFourColor() {
		return options().mineNumFourColor;
	}

	public String mineNumFiveColor() {
		return options().mineNumFiveColor;
	}

	public String mineNumSixColor() {
		return options().mineNumSixColor;
	}

	public String mineNumSevenColor() {
		return options().mineNumSevenColor;
	}

	public String mineNumEightColor() {
		return options().mineNumEightColor;
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
	 * Retrieves the raised border type.
	 * 
	 * @return the raised border type.
	 */
	public BorderType raisedBorder() {
		return options().raisedBorder;
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
	 * Update the options.
	 * 
	 * @param updatedOptions the updated options.
	 */
	public void updateOptions(Options updatedOptions) {
		setOptions(options -> {
			options.squareColor = updatedOptions.squareColor;
			options.squareAltColor = updatedOptions.squareAltColor;
			options.clickedColor = updatedOptions.clickedColor;
			options.clickedAltColor = updatedOptions.clickedAltColor;
			options.clickedFailColor = updatedOptions.clickedFailColor;
			options.mineNumOneColor = updatedOptions.mineNumOneColor;
			options.mineNumTwoColor = updatedOptions.mineNumTwoColor;
			options.mineNumThreeColor = updatedOptions.mineNumThreeColor;
			options.mineNumFourColor = updatedOptions.mineNumFourColor;
			options.mineNumFiveColor = updatedOptions.mineNumFiveColor;
			options.mineNumSixColor = updatedOptions.mineNumSixColor;
			options.mineNumSevenColor = updatedOptions.mineNumSevenColor;
			options.mineNumEightColor = updatedOptions.mineNumEightColor;
			options.difficulty = updatedOptions.difficulty;
			options.raisedBorder = updatedOptions.raisedBorder;
			options.loweredBorder = updatedOptions.loweredBorder;
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