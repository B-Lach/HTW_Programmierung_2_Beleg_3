import java.io.File;
import java.util.Scanner;

import logic.BinaryTree;
import ui.*;
import ui.console.input.InputHandler;

/**
 * Entry Point of the program
 * @author Benny Lach
 *
 */
public class Main {

	private final static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// only used for testing 
//		testBinaryTreeAddition();
//		testBinaryTreeDeletion();
		UIType outputType = getProgramType();
		
		if(outputType != null) {
			UIHelper helper = new UIHelper();
			helper.startProgram(outputType);
		} else {
			System.out.println("The option is not valid. Program will be closed");
		}
	}
	
	/**
	 * Function to get the preferred output type selected by the user
	 * @return The UIType the user wants to use. Is null if the made input was not correct. 
	 */
	private static UIType getProgramType() {
		System.out.println("Which program type do you want to use?");
		System.out.println("Press <1> for console output");
		System.out.println("Press <2> for window output");
		
		int option = InputHandler.getIntInput(scanner);
		
		switch(option) {
		case 1:
			return UIType.Console;
		case 2:
			return UIType.Graphic;
		default:
			return null;
		}
	}
	
	/**
	 * Function to test addition
	 */
	private static void testBinaryTreeAddition() {
		//predefined tree loaded from file
		File valid = new File("src/tests/resources/addition.btv");
		
		BinaryTree tree = BinaryTree.loadTreeFromFile(valid.getAbsolutePath());
		if (tree != null) {
			System.out.println("Loadad Tree:");
			tree.printTree();
			
			tree.addNode("06");
			tree.addNode("41");
			tree.addNode("55");
			tree.addNode("80");
			
			System.out.println("Tree after addition of 06, 41, 55, 80");
			tree.printTree();
			
		}
	}
	
	/**
	 * Function to test deletion
	 */
	private static void testBinaryTreeDeletion() {
		// predefined tree loaded from file
		File valid = new File("src/tests/resources/deletion.btv");
		
		BinaryTree tree = BinaryTree.loadTreeFromFile(valid.getAbsolutePath());
		
		if (tree != null) {
			System.out.println("Loadad Tree:");
			tree.printTree();
			
			tree.deleteNode("30");
			tree.deleteNode("60");
			
			System.out.println("Tree after deletion of 30, 60");
			tree.printTree();
			
		}
	}
}
