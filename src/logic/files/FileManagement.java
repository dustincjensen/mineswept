package logic.files;

import java.io.File;

public class FileManagement {

	private static File gameDir;
	private static boolean records, statistics, preferences;

	public static void init() {
		if(gameDirectory()) {
			File[] files = gameDir.listFiles();
			records = statistics = preferences = false;
			if(files.length != 0) {
				for(int i=0; i<files.length; i++) {
					if(files[i].getName().matches("prefs")) {
						preferences = true;
						Preferences.setPreferencesFile(files[i]);
					} 
					else if(files[i].getName().matches("records")) {
						records = true;
						Records.setRecordsFile(files[i]);
					}
					else if(files[i].getName().matches("stats")) {
						statistics = true;
						//Statistics.setStatisticsFile(file[i]);
					}
					else System.out.println("Warning: Erroneous files found in game directory");
				}
			}
			/* Create new files if necessary */
			if(!records) records = Records.createFile();
			//if(!statistics) statistics = Statistics.createFile();
			if(!preferences) preferences = Preferences.createFile();

			/* If new file creation was successful or the file
			   already exists then load the information */
			if(records) records = Records.load();
			//if(statistics) statistics = Statistics.load();
			if(preferences) preferences = Preferences.load();
		}
	}

	public static File getGameDir() {
		return gameDir;
	}

	private static boolean gameDirectory() {
		String userDir = System.getProperty("user.home");
		gameDir = new File(userDir+"/.MineSwept/");
		if(!gameDir.exists()) {
			if(!gameDir.mkdir()) {
				System.out.println("Directory creation failed...");
				return false;
			}
		}
		return true;
	}

	public static void saveFiles() {
		System.out.println("PREPPING SAVE FILES");
	}

}