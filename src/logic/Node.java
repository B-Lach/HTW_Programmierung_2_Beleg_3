package logic;

public class Node {

	String data;
	
	Node leftChild;
	Node rightChild;
	
	Node(String data){
		
		this.data = data;
	}
	
	public String toString(){
		
		return data;
	}
}
