package services;

import com.google.gson.Gson;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import models.Difficulty;
import models.statistics.AllStats;
import models.statistics.LongTermStats;

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
		return load(_fileService.get(FILE_NAME));
	}

	/**
	 * Reset the statistics in the json file.
	 */
	public AllStats resetStatistics() {
		var file = _fileService.get(FILE_NAME);
		if (file.isEmpty()) {
			file = _fileService.createFile(FILE_NAME);
		}
		return writeDefaultJson(file.get());
	}

	private AllStats load(Optional<File> file) {
		AllStats allStats = null;

		if (file.isPresent()) {
			allStats = loadStats(file.get());
		} else {
			var newFile = _fileService.createFile(FILE_NAME);
			if (newFile.isPresent()) {
				allStats = writeDefaultJson(newFile.get());
			}
		}

		return allStats;
	}

	private AllStats loadStats(File file) {
		try {
			var json = new String(Files.readAllBytes(Paths.get(file.toURI())), StandardCharsets.UTF_8);
			return new Gson().fromJson(json, AllStats.class);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	private AllStats writeDefaultJson(File file) {
		var allStats = new AllStats();
		allStats.easy = new LongTermStats();
		allStats.medium = new LongTermStats();
		allStats.hard = new LongTermStats();
		_fileService.writeFile(file, allStats);
		return allStats;
	}
}