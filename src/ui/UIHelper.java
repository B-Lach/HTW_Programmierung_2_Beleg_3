package ui;
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
	
	protected void showConsoleOutput() {
		//TODO: implement logic
	}
	
	protected void showGraphicOutput() {
		// TODO: implement logic
	}
}
