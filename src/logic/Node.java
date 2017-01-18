package logic;
/**
 * class for binary tree node object
 * @author Rico Stucke
 *
 */
public class Node {

	private String data;
	
	private Node leftChild;
	private Node rightChild;
	
	/**
	 * constructor for binary tree node
	 * @param data value of the node
	 */
	public Node(String data){
		this.data = data;
	}
	
	// setter
	
	protected void setLeftChild(Node node) {
		leftChild = node;
	}
	
	protected void setRightChild(Node node) {
		rightChild = node;
	}
	
	// getters
	public Node getLeftChild() {
		return leftChild;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	/**
	 * getter for node values
	 * @return returns node value
	 */
	public String getData() {
		return data;
	}
	/**
	 * print method for node values
	 */
	public String toString(){
		return data;
	}
}
