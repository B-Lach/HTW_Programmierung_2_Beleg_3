package ui.console;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class for handling the console ui 
 * @author Benny Lach
 *
 */
public class ConsoleUI {
	private final static Scanner scanner = new Scanner(System.in);
	final static Charset ENCODING = StandardCharsets.UTF_8;
	// TODO Remove tree array when BinaryTree code is ready - it's just a placeholder
	private static String[] treeArray;
	
	/**
	 * Function to present the console ui
	 */
	public static void becomeVisible() {
		Boolean quit = false;
		
		while (!quit) {
			showMainMenu();
			int option = getSelectionFromConsole();
			
			if (option < 1 || option > 3) {
				logWrongInput();
			} else {
				switch (option) {
				case 1:
					ioLogic();
					break;
				case 2:
					manipulationLogic();
					break;
				case 3:
					quit = true;
					System.out.println("Good bye");
					break;
				}
			}
		}
	}
	
	/**
	 * Function for io handling
	 */
	private static void ioLogic() {
		Boolean back = false;
		
		while(!back) {
			showIOMenu();
			int option = getSelectionFromConsole();
			
			if (option < 1 || option > 5) {
				logWrongInput();
			} else {
				switch (option) {
				case 1:
					createTreeFromKeyboard();
					break;
				case 2:
					createTreeFromFile();
					break;
				case 3:
					printTreeToConsole();
					break;
				case 4:
					saveTreeToFile();
					break;
				case 5:
					back = true;
					break;
				}
			}
		}
	}
	
	/**
	 * Function to create a tree from user input
	 */
	private static void createTreeFromKeyboard() {
		System.out.println("******* Create tree ********");
		System.out.println("write <quit> to stop");
		
		boolean quit = false;
		int nodeCount = 0;
		
		String[] dataArray = new String[20];
		
		while (!quit && nodeCount < 20) {
			String input = getStringInput();
			
			// input is not long enough
			if (input.length() == 0) {
				System.out.println("The string have to has at least one character");
				input = null;
			// input is to long
			} else if (input.length() > 3) {
				// cancel action
				if (input.equals("quit")) {
					System.out.println("User wants to quit");
					quit = true;
					input = null;
				// auto shrink if wanted
				} else {
					System.out.println("The string is to long. Do you want to auto shring the string to 3 characters? [y/n]");
					String choice = getStringInput();
					
					if (choice.equals("y")) {
						input = input.substring(0, 3);
						System.out.println("Shrinked: " + input);
					} else {
						input = null;
					}
				}
			}
			// add new node if valid
			if (input != null) {
				dataArray[nodeCount] = input;
				nodeCount++;
			}
		}
		System.out.println("Stored nodes");
		// TODO: Init binary tree
		for (String s:dataArray) {
			if (s != null) {
				System.out.println(s);
			}
		}
		treeArray = dataArray;
	}
	
	/**
	 * Function to create tree from file
	 */
	private static void createTreeFromFile() {
		System.out.println("******* Read tree ********");
		System.out.println("Write path to the file");
		System.out.println("Write <quit> to cancel");
		
		// TODO replace own code with production when finished
		// fetch path string from console and commit path to binary tree object for handling
		String pathString = getStringInput();
		if (pathString.equals("quit")) { return; }
		
		Path path = Paths.get(pathString);
		
		if (Files.isReadable(path)) {
			try {
				treeArray = new String[20];
				Files.readAllLines(path, ENCODING).toArray(treeArray);
			} catch (IOException e) {
				System.out.println("Wasn't able to get content from file");
				e.printStackTrace();
			}
		} else {
			System.out.println("The path isn't valid");
		}
	}
	
	/**
	 * Function to print tree on console
	 */
	private static void printTreeToConsole() {
		// TODO implement logic
		System.out.println("print to console");
		if (treeArray != null) {
			for(String s: treeArray) {
				if ( s != null && !s.equals("null") ) { System.out.println(s); }
			}
		}
	}
	
	/**
	 * Function to save tree to file
	 */
	private static void saveTreeToFile() {
		System.out.println("save to file");
		// TODO replace own code with production when finished
		// fetch path string from console commit path to binary tree object for handling 
		Path storePath = Paths.get("/Coding/test.txt");
		System.out.println("Path to file: " + storePath);
		
		if(!Files.isWritable(storePath)) {
			try {
				Files.createFile(storePath);
			} catch (IOException e) {
				System.out.println("Failed to create file:\n" + e);
				e.printStackTrace();
				return;
			}
		}
		
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(storePath.toString()));
			for (String s:treeArray) {
				writer.println(s);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to create FileWriter: " + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO implement logic
	}
	
	/**
	 * Function for manipulation handling
	 */
	private static void manipulationLogic() {
		Boolean back = false;
		
		while(!back) {
			showManipulationMenu();
			int option = getSelectionFromConsole();
			
			if (option < 1 || option > 4) {
				logWrongInput();
			} else {
				switch (option) {
				case 1:
					addNode();
					break;
				case 2:
					removeNodeByIndex();
					break;
				case 3:
					deleteTree();
					break;
				case 4:
					back = true;
					break;
				}
			}
		}
	}
	
	/**
	 * Function to add a node to the tree
	 */
	private static void addNode() {
		// TODO implement logic
		System.out.println("Add node");
	}
	
	/**
	 * Function to remove a node from tree
	 */
	private static void removeNodeByIndex() {
		// TODO implement logic
		System.out.println("Remove node by index");
	}
	
	/**
	 * Function to delete the whole tree
	 */
	private static void deleteTree() {
		System.out.println("Delete tree");
		// TODO implement logic
	}
	
	// Console output stuff
	/**
	 * Function to print main menu to console
	 */
	private static void showMainMenu() {
		System.out.println("*********** Main ***********");
		System.out.println("*                          *");
		System.out.println("* 1. Input/Output          *");
		System.out.println("* 2. Manipulation          *");
		System.out.println("* 3. Quit program          *");
		System.out.println("*                          *");
		System.out.println("****************************");
	}
	
	/**
	 * Function to print input / output menu to console
	 */
	private static void showIOMenu() {
		System.out.println("****** Input / Output ******");
		System.out.println("*                          *");
		System.out.println("* 1. Create from keyboard  *");
		System.out.println("* 2. Create from file      *");
		System.out.println("* 3. Print to console      *");
		System.out.println("* 4. Save to file          *");
		System.out.println("* 5. Back                  *");
		System.out.println("*                          *");
		System.out.println("****************************");
	}
	
	/**
	 * Function to print manipulation menu to console
	 */
	private static void showManipulationMenu() {
		System.out.println("******* Manipulation *******");
		System.out.println("*                          *");
		System.out.println("* 1. Add node              *");
		System.out.println("* 2. Remove node by index  *");
		System.out.println("* 3. Delete tree           *");
		System.out.println("* 4. Back                  *");
		System.out.println("*                          *");
		System.out.println("****************************");
	}
	
	/**
	 * Function to print wrong made input 
	 */
	private static void logWrongInput() {
		System.out.println("Wrong input. Please try again!\n");
	}
	
	/**
	 * Function to get user selection from console
	 * @return The selection by the user
	 */
	private static int getSelectionFromConsole() {
		System.out.println("Select option:");
		return getIntInput();
	}
	
	/**
	 * Function to get an integer input from console
	 * @return The input made by the user
	 */
	private static int getIntInput() {
		int option = -1;
		// Workaround for scanner.nextInt()
		// See: http://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-nextint-or-other-nextfoo
		try {
			option = Integer.parseInt(scanner.nextLine());
		} catch(Exception e) {
	
		}
		return option;
	}
	
	/**
	 * Function to get a string input from console
	 * @return The input made by the user
	 */
	private static String getStringInput() {
		String input = scanner.nextLine();
		
		return input;
	}
}
