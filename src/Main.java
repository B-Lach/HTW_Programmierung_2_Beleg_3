import java.util.Scanner;

import ui.*;
/**
 * Entry Point of the program
 * @author Benny Lach
 *
 */
public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {
		
		UIType outputType = getProgramType();
		
		if(outputType != null) {
			UIHelper helper = new UIHelper();
			helper.startProgram(outputType);
		} else {
			System.out.println("The option is not valid. Program will be closed");
		}
	}
	
	private static UIType getProgramType() {
		System.out.println("Which program type do you want to use?");
		System.out.println("Press <1> for console output");
		System.out.println("Press <2> for window output");
		
		int option = getIntInput();
		
		switch(option) {
		case 1:
			return UIType.Console;
		case 2:
			return UIType.Graphic;
		default:
			return null;
		}
	}
	
	private static int getIntInput() {
		scanner = new Scanner(System.in);
		int option = -1;
		// Workaround for scanner.nextInt()
		// See: http://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-nextint-or-other-nextfoo
		try {
			option = Integer.parseInt(scanner.nextLine());
		} catch(Exception e) {
			
		}
		return option;
	}
}
