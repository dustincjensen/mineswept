package logic.files;

import gui.menu.RecordWindow;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Records {
	public static final int RECORD_LIMIT = 10;

	private static File records;
	private static ArrayList<String> beginner;
	private static ArrayList<String> intermediate;
	private static ArrayList<String> advanced;

	public static void init() {
		beginner = new ArrayList();
		intermediate = new ArrayList();
		advanced = new ArrayList();
	}

	public static void setRecordsFile(File rec) {
		records = rec;
	}

	public static boolean createFile() {
		records = new File(FileManagement.getGameDir().toString() + "/records");
		try {
			if (!records.createNewFile()) {
				System.out.println("Unable to create new records file.");
				return false;
			} else
				writeDefaultFile();
		} catch (Exception e) {
			System.out.println("I/O exception: Records File");
			return false;
		}
		return true;
	}

	private static void writeDefaultFile() {
		try {
			FileWriter fw = new FileWriter(records);
			fw.write("MineSwept Records\n");
			fw.write("[Beginner]\n"); // defaultRecordLoop(fw);
			fw.write("[Intermediate]\n"); // defaultRecordLoop(fw);
			fw.write("[Advanced]\n"); // defaultRecordLoop(fw);
			fw.close();
		} catch (Exception e) {
			System.out.println("Error with Record writing");
		}
	}

	public static boolean load() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(records);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.matches("\\[.*\\]")) {
					if (line.equals("[Beginner]")) {
						for (int i = 0; i < (RECORD_LIMIT * 3); i++) {
							if ((line = br.readLine()).equals("[Intermediate]"))
								break;
							else
								beginner.add(line);
						}
					}
					if (line.equals("[Intermediate]")) {
						for (int i = 0; i < (RECORD_LIMIT * 3); i++) {
							if ((line = br.readLine()).equals("[Advanced]"))
								break;
							else
								intermediate.add(line);
						}
					}
					if (line.equals("[Advanced]")) {
						for (int i = 0; i < (RECORD_LIMIT * 3); i++) {
							if ((line = br.readLine()) == null)
								break;
							else
								advanced.add(line);
						}
					}
				}
			}
			br.close();
			fr.close();
			checkArrayLengths();
			refresh();
		} catch (Exception e) {
			System.out.println("Logic: Records Load");
			System.out.println(e);
			return false;
		}
		return true;
	}

	public static boolean addNewRecord(String diff, String name, String time) {
		Calendar cal = Calendar.getInstance();
		String date = "" + cal.get(cal.DAY_OF_MONTH) + "/" + (cal.get(cal.MONTH) + 1) + "/" + cal.get(cal.YEAR);
		if (diff.matches("beginner")) {

		}
		if (diff.matches("intermediate")) {

		}
		if (diff.matches("advanced")) {

		}
		return true;
	}

	// Actually removes them from the record file
	public static void resetRecords(String diff) {
		try {
			String temp = "MineSwept Records\n";
			temp += "[Beginner]\n";
			if (!diff.matches("beginner")) {
				for (int i = 0; i < beginner.size(); i++) {
					temp += beginner.get(i) + "\n";
				}
			} else {
				beginner.clear();
			}
			temp += "[Intermediate]\n";
			if (!diff.matches("intermediate")) {
				for (int i = 0; i < intermediate.size(); i++) {
					temp += intermediate.get(i) + "\n";
				}
			} else {
				intermediate.clear();
			}
			temp += "[Advanced]\n";
			if (!diff.matches("advanced")) {
				for (int i = 0; i < advanced.size(); i++) {
					temp += advanced.get(i) + "\n";
				}
			} else {
				advanced.clear();
			}

			FileWriter fw = new FileWriter(records, false);
			fw.write(temp);
			fw.close();
			checkArrayLengths();
			refresh();
		} catch (Exception e) {
			System.out.println("Logic: Reseting Records");
			System.out.println(e);
		}
	}// End resetRecords

	private static void checkArrayLengths() {
		if (beginner.size() % 3 != 0) {
			System.out.println("Possible Error in Beginner records, unloading.");
			beginner.clear();
		}
		if (intermediate.size() % 3 != 0) {
			System.out.println("Possible Error in Intermediate records, unloading.");
			intermediate.clear();
		}
		if (advanced.size() % 3 != 0) {
			System.out.println("Possible Error in Advanced records, unloading");
			advanced.clear();
		}
	}

	private static void refresh() {
		RecordWindow.setBeginnerRecords(Arrays.copyOf(beginner.toArray(), beginner.size(), String[].class));
		RecordWindow.setIntermediateRecords(Arrays.copyOf(intermediate.toArray(), intermediate.size(), String[].class));
		RecordWindow.setAdvancedRecords(Arrays.copyOf(advanced.toArray(), advanced.size(), String[].class));
	}

}