package logic.files;

import java.io.File;

public class FileManagement {
	private static File gameDir;

	public static void init() {
		if (gameDirectory()) {
			File[] files = gameDir.listFiles();
			boolean preferences = false,
					records = false;
					//statistics = false;
			
			for (File file : files) {
				String fileName = file.getName();
				
				if (fileName.matches("prefs")) {
					preferences = true;
					Preferences.setPreferencesFile(file);
				} 
				else if (fileName.matches("records")) {
					records = true;
					Records.setRecordsFile(file);
				}
				// else if (fileName.matches("stats")) {
				// 	statistics = true;
				// 	Statistics.setStatisticsFile(file);
				// }
			}
			
			// Create new files if necessary
			if (!preferences) {
				preferences = Preferences.createFile();
			}

			if (!records) {
				records = Records.createFile();
			}

			// if (!statistics) {
			// 	statistics = Statistics.createFile();
			// }

			// If new file creation was successful or the file
			// already exists then load the information
			if (preferences) {
				preferences = Preferences.load();
			}
			
			if (records) { 
				records = Records.load();
			}

			// if (statistics) {
			// 	statistics = Statistics.load();
			// }
		}
	}

	public static File getGameDir() {
		return gameDir;
	}
	
	public static void saveFiles() {
		// TODO save to the file system.
		System.out.println("TODO save files to system.");
	}
	
	private static boolean gameDirectory() {
		String userDir = System.getProperty("user.home");
		gameDir = new File(userDir + "/.MineSwept/");
		if (!gameDir.exists()) {
			if (!gameDir.mkdir()) {
				System.out.println("Directory creation failed...");
				return false;
			}
		}
		return true;
	}
}