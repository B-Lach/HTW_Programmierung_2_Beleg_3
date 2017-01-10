package logic;

public class Node {

	private String data;
	
	private Node leftChild;
	private Node rightChild;
	
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
	
	public String getData() {
		return data;
	}
	
	public String toString(){
		return data;
	}
}
