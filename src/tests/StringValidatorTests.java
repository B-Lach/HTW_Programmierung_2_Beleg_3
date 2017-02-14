package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import ui.console.validation.*;

public class StringValidatorTests {
	
	@Test
	public void test_nullStringValidation() {
		StringValidationType type = StringValidator.validateString(null, null, null);
		assertEquals("Validation is not wrong", type, StringValidationType.Wrong);
	}
	
	@Test
	public void test_emptyStringValidation() {
		StringValidationType type = StringValidator.validateString("", null, null);
		assertEquals("Validation is not wrong", type, StringValidationType.Wrong);
	}
	
	@Test
	public void test_validStringValidation() {
		StringValidationType type = StringValidator.validateString("asf", null, null);
		assertEquals("Validation is not correct", type, StringValidationType.Correct);
	}
	
	@Test
	public void test_stringToLongNoShringValidation() {
		StringValidationType type = StringValidator.validateString("tolong", null, false);
		assertEquals("Validation is not CancelSequence", type, StringValidationType.CancelSequence);
	}
	
	@Test
	public void test_stringToLongAndShrinkValidation() {
		InputStream original_in = System.in;
		
		// Simulate user input - yes input 
		ByteArrayInputStream in = new ByteArrayInputStream("y\nn\nfoo\n".getBytes());
		System.setIn(in);
		
		StringValidationType type = StringValidator.validateString("tolong", null, true);
		assertEquals("Validation is not ShrinkInput", type, StringValidationType.ShrinkInput);
		
		// Simulate user input - no parameter 
		type = StringValidator.validateString("tolong", null, true);
		System.out.println("\n" + type + "\n");
		assertEquals("Validation is not CancelSequence", type, StringValidationType.CancelSequence);
		
		// Simulate user input - wrong parameter 
		type = StringValidator.validateString("tolong", null, true);
		assertEquals("Validation is not CancelSequence", type, StringValidationType.CancelSequence);
		System.setIn(original_in);
	}
	
	@Test
	public void test_cancelSequenceTest() {
		StringValidationType type = StringValidator.validateString("cancel", "cancel", false);
		assertEquals("Validation is not CancelSequence", type, StringValidationType.CancelSequence);
	}
}
