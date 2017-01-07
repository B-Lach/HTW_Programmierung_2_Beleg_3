package logic;

/**
 * binary tree class
 * 
 * @author Rico
 *
 */
public class BinaryTree {

	Node root;

	public void addNode(int key, String name) {

		// creates a new node

		Node newNode = new Node(key, name);

		// without a root node this will become root

		if (root == null) {

			root = newNode;

		} else {

			// sets root as starting point for traversing the tree
			Node focusNode = root;

			// future partent for new node
			Node parent;

			while (true) {

				// start at top with root

				parent = focusNode;

				// check whether new node goes on left or right side

				if (key < focusNode.key) {

					focusNode = focusNode.leftChild;

					// if left child has no children
					if (focusNode == null) {

						// places new node on the left
						parent.leftChild = newNode;
						return;
					}
				} else {

					// puts node on right side
					focusNode = focusNode.rightChild;

					// if right child has no children
					if (focusNode == null) {

						// place new ndoe on the right
						parent.rightChild = newNode;
						return;
					}
				}
			}
		}
	}

	public boolean deleteNode(int key) {

		Node focusNode = root;
		Node parent = root;

		boolean isItLeftChild = true;

		while (focusNode.key != key) {

			parent = focusNode;

			if (key < focusNode.key) {

				isItLeftChild = true;

				focusNode = focusNode.leftChild;
			} else {

				isItLeftChild = false;

				focusNode = focusNode.rightChild;
			}

			if (focusNode == null) {
				return false;
			}
		}

		if (focusNode.leftChild == null && focusNode.rightChild == null) {

			if (focusNode == root) {
				root = null;
			} else if (isItLeftChild) {
				parent.leftChild = null;
			} else {
				parent.rightChild = null;
			}
		} else if (focusNode.rightChild == null) {

			if (focusNode == root) {
				root = focusNode.leftChild;
			} else if (isItLeftChild) {
				parent.leftChild = focusNode.leftChild;
			} else {
				parent.rightChild = focusNode.leftChild;
			}
		} else if (focusNode.leftChild == null) {
			if (focusNode == root) {
				root = focusNode.rightChild;
			} else if (isItLeftChild) {
				parent.leftChild = focusNode.rightChild;
			} else {
				parent.rightChild = focusNode.leftChild;
			}
		} else {
			Node replacement = getReplacementNode(focusNode);

			if (focusNode == root) {
				root = replacement;
			} else if (isItLeftChild) {
				parent.leftChild = replacement;
			} else {
				parent.rightChild = replacement;
			}
			replacement.leftChild = focusNode.leftChild;
		}
		return true;
	}

	public Node findNode(int key) {

		// starts at top of the tree
		Node focusNode = root;

		while (focusNode.key != key) {

			if (key < focusNode.key) {

				focusNode = focusNode.leftChild;
			} else {
				focusNode = focusNode.rightChild;
			}

			// if node is not found
			if (focusNode == null) {
				return null;
			}

		}
		return focusNode;
	}
	
	public void preorderTraverseTree(Node focusNode){
		if(focusNode != null){
			
			System.out.println(focusNode);
			
			preorderTraverseTree(focusNode.leftChild);
			
			preorderTraverseTree(focusNode.rightChild);
		}
	}
	
	private Node getReplacementNode(Node replacedNode) {

		Node replacementParent = replacedNode;
		Node replacement = replacedNode;

		Node focusNode = replacedNode.rightChild;

		while (focusNode != null) {
			replacementParent = replacement;
			replacement = focusNode;
			focusNode = focusNode.leftChild;
		}

		if (replacement != replacedNode.rightChild) {
			replacementParent.leftChild = replacement.rightChild;
			replacement.rightChild = replacedNode.rightChild;
		}
		return replacement;
	}
}
