package ui.console.validation;

import ui.console.input.InputHandler;

public class StringValidator {
	public static StringValidationType validateString(String input, String cancelSequence) {
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
			if (InputHandler.getBooleanInput("The string is to long. Do you want to auto shring the string to 3 characters? [y/n]", "y")) {
				return StringValidationType.ShrinkInput;
			}
			return StringValidationType.CancelSequence;
		}
		return StringValidationType.Correct;
	}
}
