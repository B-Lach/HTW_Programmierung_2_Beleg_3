package logic;

/**
 * binary tree class
 * 
 * @author Rico
 *
 */
public class BinaryTree {

	private Node root;

	public void addNode(String data) {

		// creates a new node

		Node newNode = new Node(data);

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

				if (data.compareTo(focusNode.getData()) < 0) {

					focusNode = focusNode.getLeftChild();

					// if left child has no children
					if (focusNode == null) {

						// places new node on the left
						parent.setLeftChild(newNode);
						return;
					}
				} else if (data.compareTo(focusNode.getData()) > 0) {

					// puts node on right side
					focusNode = focusNode.getRightChild();

					// if right child has no children
					if (focusNode == null) {

						// place new ndoe on the right
						parent.setRightChild(newNode);
						return;
					}

					// check if a duplicate node is being added
				} else {
					if (data.compareTo(focusNode.getData()) == 0) {
						System.out.println("add: no duplicate nodes allowed");
						return;
					}
				}
			}
		}
	}

	/**
	 * delete method for nodes
	 * 
	 * @param data
	 *            the node that the user wants to delete
	 * @return returns true if deletion was successful and false if it was not
	 */
	public boolean deleteNode(String data) {

		Node focusNode = root;
		Node parent = root;

		boolean isItLeftChild = true;
		if (root != null) {
			while (focusNode.getData().compareTo(data) != 0) {

				parent = focusNode;

				if (data.compareTo(focusNode.getData()) < 0) {

					isItLeftChild = true;

					focusNode = focusNode.getLeftChild();
				} else if (data.compareTo(focusNode.getData()) > 0) {

					isItLeftChild = false;

					focusNode = focusNode.getRightChild();
				}

				if (focusNode == null) {
					return false;
				}
			}

			if (focusNode.getLeftChild() == null && focusNode.getRightChild() == null) {

				if (focusNode == root) {
					root = null;
				} else if (isItLeftChild) {
					parent.setLeftChild(null);
				} else {
					parent.setRightChild(null);
				}
			} else if (focusNode.getRightChild() == null) {

				if (focusNode == root) {
					root = focusNode.getLeftChild();
				} else if (isItLeftChild) {
					parent.setLeftChild(focusNode.getLeftChild());
				} else {
					parent.setRightChild(focusNode.getLeftChild());
				}
			} else if (focusNode.getLeftChild() == null) {
				if (focusNode == root) {
					root = focusNode.getRightChild();
				} else if (isItLeftChild) {
					parent.setLeftChild(focusNode.getRightChild());
				} else {
					parent.setRightChild(focusNode.getLeftChild());
				}
			} else {
				Node replacement = getReplacementNode(focusNode);

				if (focusNode == root) {
					root = replacement;
				} else if (isItLeftChild) {
					parent.setLeftChild(replacement);
				} else {
					parent.setRightChild(replacement);
				}
				replacement.setLeftChild(focusNode.getLeftChild());
			}
			return true;
		}
		System.out.println("delete: node not found");
		return false;
	}

	public Node findNode(String data) {

		// starts at top of the tree
		Node focusNode = root;

		while (focusNode.getData().compareTo(data) != 0) {

			if (data.compareTo(focusNode.getData()) < 0) {

				focusNode = focusNode.getLeftChild();
			} else if (data.compareTo(focusNode.getData()) > 0) {
				focusNode = focusNode.getRightChild();
			}

			// if node is not found
			if (focusNode == null) {
				return null;
			}

		}
		return focusNode;
	}

	public Node getRootNode() {
		return root;
	}
	
	public void preorderTraverseTree(Node focusNode) {
		if (focusNode != null) {

			System.out.println(focusNode);

			preorderTraverseTree(focusNode.getLeftChild());

			preorderTraverseTree(focusNode.getRightChild());
		}
	}

	private Node getReplacementNode(Node replacedNode) {

		Node replacementParent = replacedNode;
		Node replacement = replacedNode;

		Node focusNode = replacedNode.getRightChild();

		while (focusNode != null) {
			replacementParent = replacement;
			replacement = focusNode;
			focusNode = focusNode.getLeftChild();
		}

		if (replacement != replacedNode.getRightChild()) {
			replacementParent.setLeftChild(replacement.getRightChild());
			replacement.setRightChild(replacedNode.getRightChild());
		}
		return replacement;
	}
}
