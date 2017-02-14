package ui.console.input;

import java.util.Scanner;

/**
 * Class to handle User input made on console
 * @author Benny Lach
 *
 */
public class InputHandler {
	/**
	 * Function to get user selection from console
	 * @return The selection by the user
	 */
	public static int getSelectionFromConsole(Scanner scanner) {
		System.out.println("Select option:");
		return getIntInput(scanner);
	}
	
	/**
	 * Function to get an integer input from console
	 * @return The input made by the user
	 */
	public static int getIntInput(Scanner scanner) {
		int option = -1;
		// Workaround for scanner.nextInt()
		// See: http://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-nextint-or-other-nextfoo
		try {
			option = Integer.parseInt(scanner.nextLine());

		} catch(Exception e) {}
		return option;
	}
	
	/**
	 * Function to get a string input from console
	 * @return The input made by the user
	 */
	public static String getStringInput(Scanner scanner) {
		String input = scanner.nextLine();
		
		return input;
	}
	
	/**
	 * Function to check if a input string equals a given String
	 * @param message The message to show on console
	 * @param trueSequence The sequence to check
	 * @return True if input equals the given string. Otherwise false
	 */
	public static Boolean getBooleanInput(Scanner scanner, String message, String trueSequence) {
		System.out.print(message);
		String input = getStringInput(scanner);
		
		return (input.equals(trueSequence));
	}
}
