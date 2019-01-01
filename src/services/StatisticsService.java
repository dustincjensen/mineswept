package services;

import java.io.File;
import models.Difficulty;
import models.statistics.AllStats;

public class StatisticsService {
	private static final String FILE_NAME = "statistics.json";

	private FileService _fileService;

	public StatisticsService(FileService fileService) {
		_fileService = fileService;
	}

	/**
	 * Gets the statistics from the statistics json file.
	 
	 * @return the statistics from the file.
	 */
	public AllStats getStatistics() {
		return withFile(file -> { 
			return load(file);
		});
	}

	/**
	 * Reset the statistics in the json file.
	 * 
	 * @return the reset statistics.
	 */
	public AllStats resetStatistics() {
		return withFile(file -> {
			var resetStats = new AllStats();
			_fileService.writeFile(file, resetStats);
			return resetStats;
		});
	}

	/**
	 * Increments the games played and games won counts.
	 */
	public void gameWon(Difficulty difficulty) {
		withFile(file -> {
			var stats = load(file);

			// Set games played and won.
			if (difficulty == Difficulty.easy) {
				stats.easy.gamesPlayed++;
				stats.easy.gamesWon++;
			} else if (difficulty == Difficulty.medium) {
				stats.medium.gamesPlayed++;
				stats.medium.gamesWon++;
			} else if (difficulty == Difficulty.hard) {
				stats.hard.gamesPlayed++;
				stats.hard.gamesWon++;
			}

			_fileService.writeFile(file, stats);
		});
	}

	/**
	 * Increments the games played and games lost counts.
	 */
	public void gameLost(Difficulty difficulty) {
		withFile(file -> {
			var stats = load(file);

			// Set games played and lost.
			if (difficulty == Difficulty.easy) {
				stats.easy.gamesPlayed++;
				stats.easy.gamesLost++;
			} else if (difficulty == Difficulty.medium) {
				stats.medium.gamesPlayed++;
				stats.medium.gamesLost++;
			} else if (difficulty == Difficulty.hard) {
				stats.hard.gamesPlayed++;
				stats.hard.gamesLost++;
			}

			_fileService.writeFile(file, stats);
		});
	}

	// Wrapping the file service generic methods that require multiple arguments.
	// When these are invoke here in statistics service, they always are invoked
	// with the same parameters.
	private AllStats load(File file) {
		return _fileService.read(file, AllStats.class);
	}

	private AllStats withFile(IRequiresFileAndHasReturn<AllStats> method) {
		return _fileService.withFile(FILE_NAME, new AllStats(), method);
	}

	private void withFile(IRequiresFile method) {
		_fileService.withFile(FILE_NAME, new AllStats(), method);
	}
}