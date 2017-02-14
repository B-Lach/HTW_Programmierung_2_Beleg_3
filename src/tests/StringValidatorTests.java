package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;
import ui.console.validation.*;

public class StringValidatorTests {
	
	@Test
	public void test_nullStringValidation() {
		StringValidationType type = StringValidator.validateString(null, null, null, null);
		assertEquals("Validation is not wrong", type, StringValidationType.Wrong);
	}
	
	@Test
	public void test_emptyStringValidation() {
		StringValidationType type = StringValidator.validateString(null, "", null, null);
		assertEquals("Validation is not wrong", type, StringValidationType.Wrong);
	}
	
	@Test
	public void test_validStringValidation() {
		StringValidationType type = StringValidator.validateString(null, "asf", null, null);
		assertEquals("Validation is not correct", type, StringValidationType.Correct);
	}
	
	@Test
	public void test_stringToLongNoShringValidation() {
		StringValidationType type = StringValidator.validateString(null, "tolong", null, false);
		assertEquals("Validation is not CancelSequence", type, StringValidationType.CancelSequence);
	}
	
	@Test
	public void test_stringToLongAndShrinkValidation() {
		InputStream original_in = System.in;
		
		// Simulate user input - yes input 
		ByteArrayInputStream in = new ByteArrayInputStream("y\r\n".getBytes());
		
		StringValidationType type = StringValidator.validateString(new Scanner(in), "tolong", null, true);
		assertEquals("Validation is not ShrinkInput", StringValidationType.ShrinkInput, type);
		
		// Simulate user input - no parameter 
		in = new ByteArrayInputStream("n\r\n".getBytes());
		
		type = StringValidator.validateString(new Scanner(in), "tolong", null, true);
		assertEquals("Validation is not CancelSequence", StringValidationType.CancelSequence, type);
		
		// Simulate user input - wrong parameter
		in = new ByteArrayInputStream("foo\r\n".getBytes());
		
		type = StringValidator.validateString(new Scanner(in), "tolong", null, true);
		assertEquals("Validation is not CancelSequence", StringValidationType.CancelSequence, type);
		
		System.setIn(original_in);
	}
	
	@Test
	public void test_cancelSequenceTest() {
		StringValidationType type = StringValidator.validateString(null, "cancel", "cancel", false);
		assertEquals("Validation is not CancelSequence", StringValidationType.CancelSequence, type);
	}
}
