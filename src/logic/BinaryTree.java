package logic;


public class BinaryTree {
	
	Node root;
	
	public void addNode(int key, String name){
		
		//creates a new node
		
		Node newNode = new Node(key, name);
		
		//without a root node this will become root
		
		if (root == null){
			
			root = newNode;
			
		}else{
			
			//sets root as starting point for traversing the tree
			Node focusNode = root;
			
			//future partent for new node
			Node parent;
			
			while(true){
				
				//start at top with root
				
				parent = focusNode;
				
				//check whether new node goes on left or right side
				
				if(key < focusNode.key){
					
					focusNode = focusNode.leftChild;
					
					//if left child has no children
					if(focusNode == null){
						
						//places new node on the left
						parent.leftChild = newNode;
						return;
					}
				}else{
					
					//puts node on right side
					focusNode = focusNode.rightChild;
					
					//if right child has no children
					if(focusNode == null){
						
						//place new ndoe on the right
						parent.rightChild = newNode;
						return;
					}
				}
			}
		}
	}
	
	public Node findNode(int key){
		
		//starts at top of the tree
		Node focusNode = root;
		
		while(focusNode.key != key){
			
			if(key < focusNode.key){
				
				focusNode = focusNode.leftChild;
			}else{
				focusNode = focusNode.rightChild;
			}
			
			//if node is not found
			if(focusNode == null){
				return null;
			}
			
		}
		return focusNode;
	}
}
