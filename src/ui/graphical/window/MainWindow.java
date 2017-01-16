package ui.graphical.window;

import java.io.File;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	
	private JMenuItem saveItem;
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
		// On macOS we are using CMD, on Windows people are using CTRL 
		// Instead of InputEvent.CTRL_MASK what always is CTRL we are using
		// ShortcutKeyMask to respect the different keys on macOS and Windows
		int ctrl = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		JMenuItem newItem = new JMenuItem("New");
		newItem.addActionListener(new MenuItemActionListener(MenuItemType.New, this));
		// CTRL+N shortcut
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ctrl));
		
		JMenuItem newFromFileItem = new JMenuItem("Open...");
		newFromFileItem.addActionListener(new MenuItemActionListener(MenuItemType.NewFromFile, this));
		// CTRL+O shortcut
		newFromFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ctrl));
		
		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new MenuItemActionListener(MenuItemType.Save, this));
		// CTRL+S
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ctrl));
		saveItem.setEnabled(false);
		
		JMenuItem saveToItem = new JMenuItem("Save to..."); 
		saveToItem.addActionListener(new MenuItemActionListener(MenuItemType.SaveTo, this));
		// SHIFT+CTRL+S
		saveToItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ctrl | InputEvent.SHIFT_MASK));
		
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
		// On macOS we are using CMD, on Windows people are using CTRL 
		// Instead of InputEvent.SHIFT_MASK what always is CTRL we are using
		// ShortcutKeyMask to respect the different keys on macOS and Windows
		int ctrl = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		JMenuItem addItem = new JMenuItem("Add node");
		addItem.addActionListener(new MenuItemActionListener(MenuItemType.AddNode, this));
		// CTRL+A shortcut
		addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ctrl));
		
		JMenuItem deleteItem = new JMenuItem("Delete node");
		deleteItem.addActionListener(new MenuItemActionListener(MenuItemType.DeleteNode, this));
		// CTRL+D shortcut
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ctrl));
		
		JMenuItem deleteAllItem = new JMenuItem("Delete all");
		deleteAllItem.addActionListener(new MenuItemActionListener(MenuItemType.DeleteAll, this));
		// SHIFT+CTRL+D shortcut
		deleteAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ctrl | InputEvent.SHIFT_MASK));
		
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
		JMenuItem documentationItem = new JMenuItem("Documentation");
		documentationItem.addActionListener(new MenuItemActionListener(MenuItemType.Documentation, this));
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(documentationItem);
		
		return helpMenu;
	}
	
	/**
	 * Method to handle the new action of the JMenuItem
	 */
	private void newAction() {
		tree = new BinaryTree();
		treePanel.drawTree(tree);
		
		updateSaveItemUsable();
	}
	
	/**
	 * Method to handle the new from file action of the JMenuItem
	 */
	private void newFromFileAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		setFileFilter(fileChooser);
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String pathString = fileChooser.getSelectedFile().getAbsolutePath();
			if (tree == null) { tree = new BinaryTree();}
			
			if (tree.loadTreeFromFile(pathString)) {
				treePanel.drawTree(tree);
				
				updateSaveItemUsable();
			} else {
				updateStatus("Can not load tree from " + pathString);
			}
		}
	}
	
	/**
	 * Method to update the save item usage
	 */
	private void updateSaveItemUsable() {
		saveItem.setEnabled(tree.getStringPath() != null);
	}
	/**
	 * Method to handle the save action of the JMenuItem
	 */
	private void saveAction() {
		if (tree != null && tree.getStringPath() != null) {
			if (tree.saveTreeToFile(tree.getStringPath())) {
				updateStatus("Tree saved sucessfully");
			} else {
				updateStatus("Wasn't able to save");
			}
			updateSaveItemUsable();
		} else {
			// This line of code shouldn't be executed at any time
			updateStatus("Wasn't able to save");
		}
	}
	
	/**
	 * Method to handle the save to action of the JMenuItem
	 */
	private void saveToAction() {
		
		if (tree != null && tree.getRootNode() != null) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			setFileFilter(fileChooser);
			
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				String path = fileChooser.getSelectedFile().getAbsolutePath();
				if (tree.saveTreeToFile(path)) {
					updateStatus("Tree saved sucessfully");
				} else {
					updateStatus("Wasn't able to save");
				}
				updateSaveItemUsable();
			}
		} else {
			updateStatus("There is nothing to save");
		}
	}
	
	/**
	 * Method to set accepted file filters for a specific file chooser
	 * @param chooser The file chooser to set the filters for
	 */
	private void setFileFilter(JFileChooser chooser) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*." + BinaryTree.FILE_EXTENSION, BinaryTree.FILE_EXTENSION);
		// remove the all files filter
		chooser.setAcceptAllFileFilterUsed(false);
		// add the BinaryTree file extension filter
		chooser.setFileFilter(filter);
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
		if (tree != null && tree.getRootNode() != null) {
			Boolean delete = DialogHandler.showConfirmDialog("Do you really want to delete the tree?", "Delete All");
			
			if (delete) {
				tree.deleteAll();
				treePanel.drawTree(tree);
			}
		} else {
			updateStatus("There is nothing to delete");
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
	 * Method to handle the documentation action of the JMenuItem
	 */
	private void documentationAction() {
		System.out.println("Perform Documentation action");
		// TODO Add implementation
	}
}

