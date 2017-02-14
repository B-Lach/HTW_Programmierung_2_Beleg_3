package tests;

import ui.*;
/**
 * UIHelper mock to inject custom code
 * USE ONLY FOR TESTING
 * 
 * @author Benny lach
 *
 */
public class UIHelperMock extends UIHelper {
	public UIType type;
	
	@Override
	protected void showConsoleOutput() {
		type = UIType.Console;
		
		super.showConsoleOutput();
	}
	
	@Override
	protected void showGraphicOutput() {
		type = UIType.Graphic;
		
		super.showGraphicOutput();
	}
}
