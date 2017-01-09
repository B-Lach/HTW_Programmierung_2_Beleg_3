package ui.graphical.dialog;

import javax.swing.JOptionPane;

public class DialogHandler {
		
	public static String showInputDialog(String description) {
		String input = JOptionPane.showInputDialog(description);
		
		return input;
	}
	
	public static Boolean showConfirmDialog(String description, String title) {
		int result = JOptionPane.showConfirmDialog(null, description, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		return result == 0;
	}
	
}
