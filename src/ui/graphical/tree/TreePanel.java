package ui.graphical.tree;

import javax.swing.JLayeredPane;

import logic.BinaryTree;
import logic.Node;

/**
 * Class to visualize a binary tree
 * @author Benny Lach
 *
 */
public class TreePanel extends JLayeredPane {
	private static final long serialVersionUID = 1299611655636308371L;
	
	private int treeDepth;
	private Node root;
	
	/**
	 * Constructor to create a new TreePanel with a given size
	 * @param width The width of the panel
	 * @param height The height of the panel
	 */
	public TreePanel(int width, int height) {
		super();
		setBounds(0, 0, width, height);
	}
	
	/**
	 * Method to draw the tree on the panel
	 * @param tree The tree to draw
	 */
	public void drawTree(BinaryTree tree) {
		// remove all components to have a clear state
		removeAll();
		// draw the tree
		root = tree.getRootNode();
		treeDepth = calculateDepth(root);
		
		drawTree(root, getWidth()/2 - TreeNode.NODE_WIDTH/2, 100, -1, -1);
		
		repaint();
	}
	
	/**
	 * Method to draw a node on the panel
	 * @param node The node to draw
	 * @param x The x start position of the node
	 * @param y The y start position of the node
	 * @param oldX The start x position of the parent node 
	 * @param oldY The start y position of the parent node
	 */
	private void drawTree(Node node, int x, int y, int oldX, int oldY) {
		if (node != null) {
			if (node.getLeftChild() != null) {
				int newY = y + 100 + TreeNode.NODE_HEIGHT;
				int newX  = oldX == -1 ? x/2 : x - (oldX > x ? (oldX - x) : (x - oldX)) /2;
				
				drawTree(node.getLeftChild(), newX, newY, x, y);
			}
			if (node.getRightChild() != null) {
				int newY = y + 100 + TreeNode.NODE_HEIGHT;
				int newX  = oldX == -1 ? x + x/2 : x + ( x > oldX ? (x - oldX) : (oldX - x))/2;
				
				drawTree(node.getRightChild(), newX, newY, x, y);
			}
			
			// Nodes with a depth > 6 are not visible due to bound limitations
			// They are still part of the binary tree but not visible. 
			// This could be improved by using a scroll view
			if (treeDepth < 7 ) {
				TreeNode n = new TreeNode(node.getData());
				n.setLocation(x, y);
				add(n,2,0);
				
				drawLine(x, y, oldX, oldY);
			} else {
				if(getDepthOfNode(node, root, 1) < 7) {
					TreeNode n = new TreeNode(node.getData());
					n.setLocation(x, y);
					add(n,2,0);
					
					drawLine(x, y, oldX, oldY);
				}
			}
		}
	}
	
	/**
	 * Method to draw a line between two nodes
	 * @param endX The end x position of the line
	 * @param endY The end y position of the line
	 * @param beginX The begin x position of the line
	 * @param beginY The begin y position of the line
	 */
	private void drawLine(int endX, int endY, int beginX, int beginY) {
		if (beginX > -1 && beginY > -1) {
			Line line = new Line(beginX, beginY, endX, endY);
			line.setLocation((beginX < endX ? beginX : endX) + TreeNode.NODE_WIDTH/2, (beginY < endY ? beginY : endY) + TreeNode.NODE_WIDTH/2);
			add(line,1,0);
		}
	}
	
	/**
	 * Method to calculate the depth of a given tree's root node
	 * @param node The root node of the tree
	 * @return The calculated depth of the tree. this value is zero if the root node is null
	 */
	private int calculateDepth(Node node) {
		if (node == null) { return 0; }
		
		int leftHeight = calculateDepth(node.getLeftChild());
		int rightHeight = calculateDepth(node.getRightChild());
		
		return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
	}
	
	/**
	 * Method to calculate the depth of a given node in a given tree
	 * @param nodeToFind The node to find
	 * @param rootNode The root node of the tree
	 * @param depth the start depth 
	 * @return The depth of the node. This value is negative if the node wasn't found in the given root node of the tree
	 */
	private int getDepthOfNode(Node nodeToFind, Node rootNode, int depth) {
		// if one of the both nodes are null return immediately
		if (rootNode == null || nodeToFind == null) {
			return -1;
		}
		
		int compareResult = nodeToFind.getData().compareTo(rootNode.getData()); 
		// current root is the node to find
		if (compareResult == 0) {
			return depth;
		}
		// 
		if (compareResult < 0) {
			return getDepthOfNode(nodeToFind, rootNode.getLeftChild(), depth+1);
		}
		return getDepthOfNode(nodeToFind, rootNode.getRightChild(), depth+1);
	}
}
