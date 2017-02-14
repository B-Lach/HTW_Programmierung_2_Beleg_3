package ui.graphical;

import ui.graphical.window.MainWindow;

/**
 * Entry Point to present the GUI Output
 * @author Benny Lach
 *
 */
public class GraphilcalUI {
	/**
	 * Function to present the Main Window
	 */
	public static void becomeVisible() {
		MainWindow main = new MainWindow();
		main.setVisible(true);
	}
}
