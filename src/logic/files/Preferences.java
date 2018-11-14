package logic.files;

import gui.panel.mines.MineButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;
import logic.game.MineField;

public class Preferences {
	private static File preferences;
	private static String[] defaultFileLines = {
		"[MineButton Colours]\n",
		"R=50\n",
		"G=125\n",
		"B=240\n",
		"[Difficulty]\n",
		"easy\n",
	};

	public static boolean load(Optional<File> preferenceFile) {
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
				if (line.equals("[MineButton Colours]"))
					MineButton.parseColor(br.readLine(), br.readLine(), br.readLine());
				if (line.equals("[Difficulty]"))
					MineField.parseDifficulty(br.readLine());

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}
}