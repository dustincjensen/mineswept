package services;

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
		return _fileService.withFile(FILE_NAME, new AllStats(), file -> { 
			return _fileService.readFile(file, AllStats.class);
		});
	}

	/**
	 * Reset the statistics in the json file.
	 * 
	 * @return the reset statistics.
	 */
	public AllStats resetStatistics() {
		return _fileService.withFile(FILE_NAME, new AllStats(), file -> {
			var resetStats = new AllStats();
			_fileService.writeFile(file, resetStats);
			return resetStats;
		});
	}

	/**
	 * Increments the games played and games won counts.
	 */
	public void gameWon(Difficulty difficulty) {
		_fileService.withFile(FILE_NAME, new AllStats(), file -> {
			var stats = _fileService.readFile(file, AllStats.class);

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
		_fileService.withFile(FILE_NAME, new AllStats(), file -> {
			var stats = _fileService.readFile(file, AllStats.class);

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
}