package logic;

/**
 * 
 * @author Rico Stucke
 *
 */
public class Node {

	private String data;
	
	private Node leftChild;
	private Node rightChild;
	private Node parentNode;
	private int balance;
	
	public Node(String data){
		this.data = data;
	}
	
	// setter
	protected void setBalance(int balance){
		this.balance = balance;
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
	
	// getter
	public Node getParentNode(){
		return parentNode;
	}
	public Node getLeftChild() {
		return leftChild;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	public int getBalance(){
		return balance;
	}
	
	public String getData() {
		return data;
	}
	
	public String toString(){
		return data;
	}
}
