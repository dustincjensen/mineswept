package services;

import models.Difficulty;
import models.statistics.AllStats;
import models.statistics.LongTermStats;

public class StatisticsService {
	private static final String FILE_NAME = "statistics.json";

	private FileService fileService;

	public StatisticsService(FileService fileService) {
		this.fileService = fileService;
	}

	/**
	 * Gets the statistics from the statistics json file.
	 
	 * @return the statistics from the file.
	 */
	public AllStats getStatistics() {
		return fileService.withFile(FILE_NAME, new AllStats(), file -> { 
			return fileService.readFile(file, AllStats.class);
		});
	}

	/**
	 * Reset the statistics in the json file.
	 * 
	 * @return the reset statistics.
	 */
	public AllStats resetStatistics(Difficulty level) {
		return fileService.withFile(FILE_NAME, new AllStats(), file -> {
			var stats = fileService.readFile(file, AllStats.class);

			// We can decide if resetting restores some computer default stats...
			if (level == Difficulty.easy) {
				stats.easy = new LongTermStats();
			} else if (level == Difficulty.medium) {
				stats.medium = new LongTermStats();
			} else if (level == Difficulty.hard) {
				stats.hard = new LongTermStats();
			}

			fileService.writeFile(file, stats);
			return stats;
		});
	}

	/**
	 * Increments the games played and games won counts.
	 */
	public void gameWon(Difficulty difficulty) {
		fileService.withFile(FILE_NAME, new AllStats(), file -> {
			var stats = fileService.readFile(file, AllStats.class);

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

			fileService.writeFile(file, stats);
		});
	}

	/**
	 * Increments the games played and games lost counts.
	 */
	public void gameLost(Difficulty difficulty) {
		fileService.withFile(FILE_NAME, new AllStats(), file -> {
			var stats = fileService.readFile(file, AllStats.class);

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

			fileService.writeFile(file, stats);
		});
	}
}