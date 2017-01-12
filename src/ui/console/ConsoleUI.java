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

import logic.BinaryTree;
import ui.console.input.InputHandler;
import ui.console.validation.StringValidationType;
import ui.console.validation.StringValidator;

/**
 * Class for handling the console ui 
 * @author Benny Lach
 *
 */
public class ConsoleUI {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	private static BinaryTree tree;
	
	/**
	 * Function to present the console ui
	 */
	public static void becomeVisible() {
		Boolean quit = false;
		
		while (!quit) {
			showMainMenu();
			int option = InputHandler.getSelectionFromConsole();
			
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
			int option = InputHandler.getSelectionFromConsole();
			
			if (option < 1 || option > 5) {
				logWrongInput();
			} else {
				switch (option) {
				case 1:
					createTree();
					break;
				case 2:
					loadTreeFromFile();
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
	private static void createTree() {	
		tree = new BinaryTree();
		
		System.out.println("Tree was generated!");
	}
	
	/**
	 * Function to create tree from file
	 */
	private static void loadTreeFromFile() {
		System.out.println("******* Load tree ********");
		System.out.print("Input path to the file you want to load.\n<quit> to cancel:");
		
		// TODO Finalize when logic implemented
		System.out.println("Under construction\n");
				
		// fetch path string from console and commit path to binary tree object for handling
//		String pathString = getStringInput();
//		if (pathString.equals("quit")) { return; }
//		
//		if (tree == null) { tree = new BinaryTree(); }
//		if (tree.loadFromFile(pathString)) {
//			System.out.println("Binary Tree was loaded successfully");
//		} else {
//			System.out.println("Failed to load tree from file");
//		}
	}
	
	/**
	 * Function to print tree on console
	 */
	private static void printTreeToConsole() {
		// TODO implement logic
		System.out.println("Under construction\n");
	}
	
	/**
	 * Function to save tree to file
	 */
	private static void saveTreeToFile() {
		// TODO Finalize when logic implemented
		System.out.println("****** Save to file ******");
		System.out.print("Input path to the file <quit> to cancel:");
		
		if(tree == null) {
			System.out.println("There is nothing to save!");
			return;
		}
		System.out.println("Under construction");
//		String pathString = InputHandler.getStringInput();
//		if (!pathString.equals("quit")) {
//			if (tree.loadFromFile(pathString)) {
//				System.out.println("Tree was saved successfully");
//			} else {
//				System.out.println("Failed to save tree to file");
//			}
//		}
	}
	
	/**
	 * Function for manipulation handling
	 */
	private static void manipulationLogic() {
		Boolean back = false;
		
		while(!back) {
			showManipulationMenu();
			int option = InputHandler.getSelectionFromConsole();
			
			if (option < 1 || option > 4) {
				logWrongInput();
			} else {
				switch (option) {
				case 1:
					addNode();
					break;
				case 2:
					deleteNode();
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
		System.out.println("******** Add node ********");
		if (tree == null ) { tree = new BinaryTree();}
		System.out.print("Type the data you want to add\n<quit> to cancel:");
		String input = InputHandler.getStringInput();
		
		StringValidationType validation = StringValidator.validateString(input, "quit", true);
		
		if(validation == StringValidationType.ShrinkInput) {
			input = input.substring(0, 3);
			validation = StringValidationType.Correct;
		}
		if (validation == StringValidationType.Correct) {
			if (tree.addNode(input)) {
				System.out.println("Node with String <" + input + "> was added successfully");
			} else {
				System.out.println("Node with String <" + input + "> is already part of the tree");
			}
		}
	}
	
	/**
	 * Function to remove a node from tree
	 */
	private static void deleteNode() {
		// TODO implement logic
		System.out.println("****** Remove node *******");
		
		if (tree == null) {
			System.out.println("There is nothing to delete");
			return;
		}
		System.out.print("Type the data you want to delete\n<quit> to cancel:");
		String input = InputHandler.getStringInput();
		
		StringValidationType validation = StringValidator.validateString(input, "quit", false);
		if (validation == StringValidationType.Correct) {
			if (tree.deleteNode(input)) {
				System.out.println("Node with String <" + input + "> was deleted.");
			} else {
				System.out.println("Node with String <" + input + "> was not found.");
			}
		} else if (validation != StringValidationType.CancelSequence) {
			System.out.println("Given input is not valid.");
		}
	}
	
	/**
	 * Function to delete the whole tree
	 */
	private static void deleteTree() {
		System.out.println("****** Delete tree *******");
		if(InputHandler.getBooleanInput("Do you want to delete the tree? [y/n]", "y")){
			// TODO implement logic
			System.out.println("Under construction");
//			tree.deleteAll();
		}
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
		System.out.println("* 1. Create                *");
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
		System.out.println("* 2. Delete node           *");
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
}
