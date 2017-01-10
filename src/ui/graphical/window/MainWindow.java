package ui.graphical.window;

import java.io.File;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import ui.graphical.dialog.DialogHandler;
import ui.graphical.menuItem.MenuItemActionListener;
import ui.graphical.menuItem.MenuItemDelegate;
import ui.graphical.menuItem.MenuItemType;
import ui.graphical.tree.TreePanel;

import logic.BinaryTree;

/**
 * Class that represents the main window of the application
 * @author Benny Lach
 *
 */
public class MainWindow extends JFrame implements MenuItemDelegate {
	private static final long serialVersionUID = 2061144514357325585L;
	
	private final int WIDTH = 1440;
	private final int HEIGHT = 900;
	
	private BottomBar statusBar;
	private TreePanel treePanel;
	private BinaryTree tree; 
	
	private Timer timer;
	
	/**
	 * Constructor to create a main window
	 */
	public MainWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Binary Tree Visualizer");
		// center the frame on screen
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		
		addMenuBar();
		addStatusBar();
		addTreePanel();
	}
	
	/**
	 * Delegate Method called from the JMenuItem's ActionListener
	 */
	@Override
	public void performAction(MenuItemType type) {
		switch (type) {
		case New:
			newAction();
			break;
		case NewFromFile:
			newFromFileAction();
			break;
		case Save:
			saveAction();
			break;
		case SaveTo:
			saveToAction();
			break;
		case Quit:
			quitAction();
			break;
		case AddNode:
			addNodeAction();
			break;
		case DeleteNode:
			deleteNodeAction();
			break;
		case DeleteAll:
			deleteAllAction();
			break;
		case CheatSheet:
			cheatSheetAction();
			break;
		case Documentation:
			documentationAction();
			break;
		default:
			System.out.println("Type " + type + " is not implemented");
			break;
		}
	}
	
	/**
	 * Method to add the menu bar to the frame
	 */
	private void addMenuBar() {
		JMenuBar bar = new JMenuBar();
		bar.add(getFileMenu());
		bar.add(getEditMenu());
		bar.add(getHelpMenu());
		
		setJMenuBar(bar);
	}
	
	/**
	 * Method to add a status bar on the bottom of the frame
	 */
	private void addStatusBar() {
		statusBar = new BottomBar(this);
		add(statusBar, BorderLayout.SOUTH);
	}
	
	/**
	 * Method to add the tree panel to the frame
	 */
	private void addTreePanel() {
		treePanel = new TreePanel(getWidth(), getHeight() - 16); 
		add(treePanel);
	}
	
	/**
	 * Method to get the file sub menu for the menu bar
	 * @return The file menu
	 */
	private JMenu getFileMenu() {
		JMenuItem newItem = new JMenuItem("New");
		newItem.addActionListener(new MenuItemActionListener(MenuItemType.New, this));
		
		JMenuItem newFromFileItem = new JMenuItem("Open...");
		newFromFileItem.addActionListener(new MenuItemActionListener(MenuItemType.NewFromFile, this));
		
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new MenuItemActionListener(MenuItemType.Save, this));
		saveItem.setEnabled(false);
		
		JMenuItem saveToItem = new JMenuItem("Save to..."); 
		saveToItem.addActionListener(new MenuItemActionListener(MenuItemType.SaveTo, this));
		
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new MenuItemActionListener(MenuItemType.Quit, this));
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(newItem);
		fileMenu.add(newFromFileItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveToItem);
		fileMenu.addSeparator();
		fileMenu.add(quitItem);
		
		return fileMenu;
	}
	
	/**
	 * Method to get the edit sub menu for the menu bar
	 * @return The edit menu
	 */
	private JMenu getEditMenu() {
		JMenuItem addItem = new JMenuItem("Add node");
		addItem.addActionListener(new MenuItemActionListener(MenuItemType.AddNode, this));
		
		JMenuItem deleteItem = new JMenuItem("Delete node");
		deleteItem.addActionListener(new MenuItemActionListener(MenuItemType.DeleteNode, this));
		
		JMenuItem deleteAllItem = new JMenuItem("Delete all");
		deleteAllItem.addActionListener(new MenuItemActionListener(MenuItemType.DeleteAll, this));
		
		JMenu editMenu = new JMenu("Edit");
		editMenu.add(addItem);
		editMenu.addSeparator();
		editMenu.add(deleteItem);
		editMenu.add(deleteAllItem);
		
		return editMenu;
	}
	
	/**
	 * Method to get the help sub menu for the menu bar
	 * @return The help menu
	 */
	private JMenu getHelpMenu() {
		JMenuItem cheatSheetItem = new JMenuItem("Cheat Sheet");
		cheatSheetItem.addActionListener(new MenuItemActionListener(MenuItemType.CheatSheet, this));
		
		JMenuItem documentationItem = new JMenuItem("Documentation");
		documentationItem.addActionListener(new MenuItemActionListener(MenuItemType.Documentation, this));
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(cheatSheetItem);
		helpMenu.add(documentationItem);
		
		return helpMenu;
	}
	
	/**
	 * Method to handle the new action of the JMenuItem
	 */
	private void newAction() {
		tree = new BinaryTree();
		treePanel.drawTree(tree);
	}
	
	/**
	 * Method to handle the new from file action of the JMenuItem
	 */
	private void newFromFileAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		int result = fileChooser.showOpenDialog(this);
		System.out.println("File Chooser result: " + result);
		// TODO implement logic to init tree from file
	}
	
	/**
	 * Method to handle the save action of the JMenuItem
	 */
	private void saveAction() {
		System.out.println("Perform Save action");
	}
	
	/**
	 * Method to handle the save to action of the JMenuItem
	 */
	private void saveToAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		int result = fileChooser.showSaveDialog(this);
		
		System.out.println("File Chooser Save result: " + result);
	}
	
	/**
	 * Method to handle the quit action of the JMenuItem
	 */
	private void quitAction() {
		System.exit(0);
	}
	
	/**
	 * Method to handle the add node action of the JMenuItem
	 */
	private void addNodeAction() {
		String nodeName = DialogHandler.showInputDialog("Enter the string you want to add");
		if (nodeName != null) {
			if (nodeName.isEmpty() || nodeName.length() > 3) {
				updateStatus("The given String is not valid");
			} else {
				if (tree == null) { tree = new BinaryTree(); }
				if (tree.addNode(nodeName)) {
					treePanel.drawTree(tree);
				} else {
					updateStatus("Node with string <" + nodeName + "> is already part of the tree");
				}		
			}
		}
	}
	
	/**
	 * Method to handle the delete node action of the JMenuItem
	 */
	private void deleteNodeAction() {
		String nodeName = DialogHandler.showInputDialog("Enter the string of the node you want to delete");
		if (nodeName != null) {
			if (nodeName.isEmpty() || nodeName.length() > 3) {
				updateStatus("The given String is not valid");
			} else {
				if (tree != null) {
					if (tree.deleteNode(nodeName)) {
						treePanel.drawTree(tree);
					} else {
						updateStatus("Node with string <" + nodeName + "> not found");
					}
				}
			}
		} else {
			System.out.println("User did cancel");
		}
	}
	
	/**
	 * Method to handle the delete all action of the JMenuItem
	 */
	private void deleteAllAction() {
		Boolean delete = DialogHandler.showConfirmDialog("Do you really want to delete the tree?", "Delete All");
		
		if (delete) {
			// TODO Call binary tree function to delete all
		}
	}
	
	/**
	 * Method to update the status message of the message bar
	 * @param status The status to show
	 */
	private void updateStatus(String status) {
		statusBar.updateStatus(status);
		// remove the status after three seconds
		clearStatusAfterTime(3000);
	}
	/**
	 * Method to clear the current status label of the bottom bar after a specific time
	 * @param time The given delay in milliseconds after the status label should be cleared.  
	 */
	private void clearStatusAfterTime(int time) {
		if (timer != null) { timer.stop(); }
		
		timer = new Timer(time, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statusBar.updateStatus("");
			}
		});
		timer.setRepeats(false); 
		timer.start();
	}
	
	/**
	 * Method to handle the cheat sheet action of the JMenuItem
	 */
	private void cheatSheetAction() {
		System.out.println("Perform Cheat Sheet action");
		// TODO Add implementation
	}
	
	/**
	 * Method to handle the documentation action of the JMenuItem
	 */
	private void documentationAction() {
		System.out.println("Perform Documentation action");
		// TODO Add implementation
	}
}

