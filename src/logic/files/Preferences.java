package logic.files;

import gui.panel.mines.MineButton;
import logic.game.MineField;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class Preferences {

	private static File preferences;
	private static String warningMessage;

	public static void init() {
		warningMessage = "";
	}

	public static void setPreferencesFile(File pref) {
		preferences = pref;
	}

	public static boolean createFile() {
		preferences = new File(FileManagement.getGameDir().toString()+"/prefs");
		try {
			if(!preferences.createNewFile()) {
				System.out.println("Unable to create new preferences file.");
				return false;
			}
			else
				writeDefaultFile();
		} catch (Exception e) {
			System.out.println("I/O exception: Preferences File");
			return false;
		}
		return true;
	}

	private static void writeDefaultFile() {
		try {
			FileWriter fw = new FileWriter(preferences);
			fw.write("MineSwept Preferences\n\n");
			fw.write("[MineButton Colours]\n");
			fw.write("R=50\n");
			fw.write("G=125\n");
			fw.write("B=240\n");
			fw.write("[Difficulty]\n");
			fw.write("easy\n");
			fw.close();
		} catch (Exception e) {
			System.out.println("Error with Preferences writing");
		}
	}

	public static void writeWarning(String warning) {
		warningMessage = warningMessage+"\n"+warning;
	}

	public static boolean load() {
		try {
			FileReader fr = new FileReader(preferences);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null) {
				if(line.equals("[MineButton Colours]"))
					MineButton.parseColor(br.readLine(), br.readLine(), br.readLine());
				if(line.equals("[Difficulty]"))
					MineField.parseDifficulty(br.readLine());

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

}