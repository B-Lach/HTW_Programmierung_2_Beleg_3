package ui.graphical.dialog;

import javax.swing.JOptionPane;
/**
 * Class that represents a handler for all needed dialogs presented to the user
 * @author Benny Lach
 *
 */
public class DialogHandler {
	
	/**
	 * Function to visualize an input dialog with a given description
	 * @param description The description to present
	 * @return Made input by the user
	 */
	public static String showInputDialog(String description) {
		String input = JOptionPane.showInputDialog(description);
		
		return input;
	}
	
	/**
	 * Function to visualize a yes/no dialog with a given title and description
	 * @param description The description to present
	 * @param title The title of the windos
	 * @return Boolean value to identify the made selection by the user
	 */
	public static Boolean showConfirmDialog(String description, String title) {
		int result = JOptionPane.showConfirmDialog(null, description, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		return result == 0;
	}
	
}
