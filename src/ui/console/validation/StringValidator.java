package ui.console.validation;

import java.util.Scanner;

import ui.console.input.InputHandler;

/**
 * Class that is used to validate strings
 * @author Benny Lach
 *
 */
public class StringValidator {
	
	/**
	 * Function to validate a given string
	 * @param input The string to validate
	 * @param cancelSequence The sequence that represents a cancel action
	 * @param shrink Boolean that identifies if a String should be shrinked if it's too long
	 * @return Result of the validation 
	 */
	public static StringValidationType validateString(Scanner scanner, String input, String cancelSequence, Boolean shrink) {
		// string is null
		if (input == null) {
			return StringValidationType.Wrong;
		}
		// string is empty
		if (input.length() == 0) {
			System.out.println("The string must have at least one character!");
			return StringValidationType.Wrong;
		}
		// check cancel sequence
		if (cancelSequence != null && input.equals(cancelSequence)) {
			return StringValidationType.CancelSequence;
		}
		// string is too long
		if (input.length() > 3) {
			// shrinking is accepted
			if (shrink) {
				if (InputHandler.getBooleanInput(scanner,"The string is to long. Do you want to auto shring the string to 3 characters? [y/n]", "y")) {
					return StringValidationType.ShrinkInput;
				}
			}
			return StringValidationType.CancelSequence;
		}
		return StringValidationType.Correct;
	}
}
