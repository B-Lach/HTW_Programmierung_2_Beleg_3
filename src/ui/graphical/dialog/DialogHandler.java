package ui.graphical.dialog;

import javax.swing.JOptionPane;

public class DialogHandler {
		
	public static String showInputDialog(String description) {
		String input = JOptionPane.showInputDialog(description);
		
		return input;
	}
	
}
