package ui.graphical.tree;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * Class to visualize a binary tree
 * @author Benny Lach
 *
 */
public class TreePanel extends JPanel {
	private static final long serialVersionUID = 1299611655636308371L;

	public TreePanel(int width, int height) {
		setBounds(0, 0, width, height);
		setBackground(Color.green);
	}
	
	/**
	 * Method to draw the tree on the panel
	 * @param tree The tree to draw
	 */
	public void drawTree(Object tree) {
		// TODO Implement logic to draw the tree
		// replace object with the correct tree object after implementation
	}
}
