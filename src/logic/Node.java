package logic;

public class Node {

	private String data;
	
	private Node leftChild;
	private Node rightChild;
	private Node parentNode;
	private int height;
	
	public Node(String data){
		this.data = data;
	}
	
	// setter
	protected void setHeight(int height){
		this.height = height;
	}
	protected void setParentNode(Node node){
		parentNode = node;
	}
	protected void setLeftChild(Node node) {
		leftChild = node;
	}
	
	protected void setRightChild(Node node) {
		rightChild = node;
	}
	
	// getters
	public Node getParentNode(){
		return parentNode;
	}
	public Node getLeftChild() {
		return leftChild;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	public int getHeight(){
		return height;
	}
	
	public String getData() {
		return data;
	}
	
	public String toString(){
		return data;
	}
}
