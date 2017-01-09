package ui.graphical.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

public class MainWindow extends JFrame implements MenuItemDelegate {
	private static final long serialVersionUID = 2061144514357325585L;
	
	private final int WIDTH = 1024;
	private final int HEIGHT = 768;
	
	private BottomBar statusBar;
	private TreePanel treePanel;
	
	private Timer timer;
	
	public MainWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Binary Tree Visualizer");
		// center the frame on screen
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		
		addMenuBar();
		addStatusBar();
		addTreeFrame();
	}
	
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
	
	private void addMenuBar() {
		JMenuBar bar = new JMenuBar();
		bar.add(getFileMenu());
		bar.add(getEditMenu());
		bar.add(getHelpMenu());
		
		setJMenuBar(bar);
	}
	
	private void addStatusBar() {
		statusBar = new BottomBar(this);
		add(statusBar, BorderLayout.SOUTH);
	}
	
	private void addTreeFrame() {
		treePanel = new TreePanel(getWidth(), getHeight() - 16); 
		add(treePanel);
	}
	
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
	
	private void newAction() {
		System.out.println("Perform new action");
	}
	
	private void newFromFileAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		int result = fileChooser.showOpenDialog(this);
		System.out.println("File Chooser result: " + result);
	}
	
	private void saveAction() {
		System.out.println("Perform Save action");
	}
	
	private void saveToAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		int result = fileChooser.showSaveDialog(this);
		
		System.out.println("File Chooser Save result: " + result);
	}
	
	private void quitAction() {
		System.exit(0);
	}
	
	private void addNodeAction() {
		String nodeName = DialogHandler.showInputDialog("Enter the string you want to add");
		if (nodeName != null) {
			if (nodeName.isEmpty() || nodeName.length() > 3) {
				statusBar.updateStatus("The given String is not valid");
				clearStatusAfterTime(3000);
			} else {
				// TODO Call binary tree function to add node
				System.out.println("Commited valid name: " + nodeName);
			}
		} else {
			System.out.println("User did cancel");
		}
	}
	
	private void deleteNodeAction() {
		String nodeName = DialogHandler.showInputDialog("Enter the string of the node you want to delete");
		if (nodeName != null) {
			if (nodeName.isEmpty() || nodeName.length() > 3) {
				statusBar.updateStatus("The given String is not valid");
				clearStatusAfterTime(3000);
			} else {
				// TODO Call binary tree function to delete node
				System.out.println("Commited valid name: " + nodeName);
			}
		} else {
			System.out.println("User did cancel");
		}
	}
	
	private void deleteAllAction() {
		Boolean delete = DialogHandler.showConfirmDialog("Do you really want to delete the tree?", "Delete All");
		
		if (delete) {
			// TODO Call binary tree function to delete all
		}
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
	
	private void cheatSheetAction() {
		System.out.println("Perform Cheat Sheet action");
	}
	
	private void documentationAction() {
		System.out.println("Perform Documentation action");
	}
}

