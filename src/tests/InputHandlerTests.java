package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

import ui.console.input.*;

/**
 * Class to test all the functions of the InputHandler class 
 * @author Benny Lach
 *
 */
public class InputHandlerTests {

	@Test
	public void test_getValidIntInput() {
		// Simulate user input
		ByteArrayInputStream in = new ByteArrayInputStream("12\r\n".getBytes());
		
		assertEquals("int input is not correct", 12, InputHandler.getIntInput(new Scanner(in)));
	}
	
	@Test
	public void test_getInvalidIntInput() {		
		// Simulate user input
		ByteArrayInputStream in = new ByteArrayInputStream("this_is_not_a_number\r\n".getBytes());
		
		assertEquals("int input is not correct", -1, InputHandler.getIntInput(new Scanner(in)));
	}
	
	@Test
	public void test_getStringInput() {
		// Simulate user input
		ByteArrayInputStream in = new ByteArrayInputStream("string\r\n".getBytes());
		
		assertEquals("String input is not correct", "string", InputHandler.getStringInput(new Scanner(in)));
	}
	
	@Test
	public void test_getBooleanInput() {
		// Simulate user input
		ByteArrayInputStream in = new ByteArrayInputStream("string\r\n".getBytes());
		
		assertTrue("Input does not match commited string but it should", InputHandler.getBooleanInput(new Scanner(in),"", "string"));
	}
}
