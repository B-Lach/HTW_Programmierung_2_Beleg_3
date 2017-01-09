package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import ui.*;
/**
 * Test Class for the UIHelper class
 * 
 * @author Benny Lach
 *
 */
public class UIHelperTests {
	
	@Test
	public void test_startConsole() {
		UIHelperMock mock = new UIHelperMock();
		mock.startProgram(UIType.Console);
		assertTrue("Stored type is not corret", mock.type == UIType.Console);
	}

	@Test
	public void test_startGraphic() {
		UIHelperMock mock = new UIHelperMock();
		mock.startProgram(UIType.Graphic);
		assertTrue("Stored type is not correct", mock.type == UIType.Graphic);
	}
	
	@Test
	public void test_startWithNull() {
		UIHelperMock mock = new UIHelperMock();
		mock.startProgram(null);
		assertTrue("Stored type is not correct", mock.type == null);
	}
}
