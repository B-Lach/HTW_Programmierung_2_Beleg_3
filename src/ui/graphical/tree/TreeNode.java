package ui.graphical.tree;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class that visually represents a node of a Binary Tree
 * @author Benny Lach
 *
 */
public class TreeNode extends JPanel {
	public static int NODE_WIDTH = 35;
	public static int NODE_HEIGHT = 35;
	
	private static final long serialVersionUID = 5324161466546846164L;

	private String data;
	
	/**
	 * Constructor to create a TreeNode with a given String
	 * @param data The given String
	 */
	public TreeNode(String data) {
		setSize(NODE_WIDTH,NODE_HEIGHT);
		this.data = data;
	}
	
	/**
	 * Method to add a String centered on a given Graphics object 
	 * @param g The Graphics object
	 */
	private void addString(Graphics g) {
		FontMetrics metrics = g.getFontMetrics();
		
		int x = (g.getClipBounds().width - metrics.stringWidth(data)) / 2;
		int y = (g.getClipBounds().height - metrics.getHeight()) / 2 + metrics.getAscent();
		
		g.drawString(data, x, y);
	}
	
	/**
	 * Overridden paintComponent(Graphics g) method to do custom painting
	 */
	@Override
    protected void paintComponent(Graphics g) {
		// draw the node
		g.setColor(Color.orange);
        g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
         
        g.setColor(Color.black);
        // draw the data centered on the node
        addString(g);
    }
}
