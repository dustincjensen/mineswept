package gui.events;

import javax.swing.JOptionPane;

public class About {

	private static final String title = "About - MineSwept";
	private static final String message = "By: Dustin Jensen\n"+
								   "The King's University College\n"+
								   "September 1st, 2012";

	public static void init() {}
	public static void doEvent() {
		JOptionPane.showMessageDialog(null, message, title,
									  JOptionPane.PLAIN_MESSAGE);
	}
}