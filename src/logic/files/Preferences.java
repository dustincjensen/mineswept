package logic.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;
import logic.game.MineField;

public class Preferences {
	private final static String[] defaultFileLines = {
		"[MineButton Colours]\n",
		"R=50\n",
		"G=125\n",
		"B=240\n",
		"[Difficulty]\n",
		"medium\n", // easy, medium, hard
	};
	
	private File preferences;

	// TODO remove when we are sure we only create 1 after all the injection is said and done.
	public Preferences() {
		System.out.println("Creating preferences ref");
	}

	public boolean load(Optional<File> preferenceFile) {
		if (preferenceFile.isPresent()) {
			preferences = preferenceFile.get();
		} else {
			var newFile = FileManagement.createFile("prefs");
			if (newFile.isPresent()) {
				preferences = newFile.get();
			}
			FileManagement.writeFile(preferences, defaultFileLines);
		}
		
		if (preferences == null) {
			return false;
		}

		try {
			FileReader fr = new FileReader(preferences);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("[MineButton Colours]")) {
					r = br.readLine();
					g = br.readLine();
					b = br.readLine();
				}
				if (line.equals("[Difficulty]")) {
					difficulty = br.readLine();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	// TODO these should be pre-parsed instead of relying on MineField and MineButton to parse them
	private String r, g, b;
	public String r() { return r; }
	public String g() { return g; }
	public String b() { return b; }
	
	private String difficulty;
	public String difficulty() { return difficulty; }
}