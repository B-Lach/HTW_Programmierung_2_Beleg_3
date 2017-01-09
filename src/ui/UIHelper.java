package ui;

import ui.console.*;
import ui.graphical.*;

/**
 * Main entry point for the user interface selection
 * @author Benny Lach
 *
 */
public class UIHelper {
	public void startProgram(UIType type) {
		if (type == null) {
			System.out.println("Null is not a valid UIType");
			return;
		}
		
		switch (type) {
		case Console:
			showConsoleOutput();
			break;
		case Graphic:
			showGraphicOutput();
			break;
		default:
			System.out.println("Mode not implemented: " + type);
			break;
		}
	}
	
	/**
	 * Method to load the console ui
	 */
	protected void showConsoleOutput() {
		ConsoleUI.becomeVisible();
	}
	
	/**
	 * Method to load th graphical ui
	 */
	protected void showGraphicOutput() {
		GraphilcalUI.becomeVisible();
	}
}
