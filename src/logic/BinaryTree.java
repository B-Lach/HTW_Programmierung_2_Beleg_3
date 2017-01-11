package logic;

import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * binary tree class
 * 
 * @author Rico
 *
 */
public class BinaryTree {

	public static void main(String[] args) {

		BinaryTree theTree = new BinaryTree();

		theTree.addNode("50");
		theTree.addNode("25");
		theTree.addNode("15");
		theTree.addNode("30");
		theTree.addNode("75");
		theTree.addNode("85");	
		
		theTree.saveTreeToFile("C:/Users/Rico/Documents/test.txt");
	}

	private Node root;

	public Boolean addNode(String data) {

		// creates a new node

		Node newNode = new Node(data);

		// without a root node this will become root

		if (root == null) {

			root = newNode;
			return true;
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
						return true;
					}
				} else if (data.compareTo(focusNode.getData()) > 0) {

					// puts node on right side
					focusNode = focusNode.getRightChild();

					// if right child has no children
					if (focusNode == null) {

						// place new ndoe on the right
						parent.setRightChild(newNode);
						return true;
					}

					// check if a duplicate node is being added
				} else {
					if (data.compareTo(focusNode.getData()) == 0) {
						System.out.println("add: no duplicate nodes allowed");
						return false;
					}
				}
			}
		}
	}
	public boolean deleteAll(){
		return true;
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
			// leaf node 
			if (focusNode.getLeftChild() == null && focusNode.getRightChild() == null) {

				if (focusNode == root) {
					root = null;
				} else if (isItLeftChild) {
					parent.setLeftChild(null);
				} else {
					parent.setRightChild(null);
				}
			// only left child
			} else if (focusNode.getRightChild() == null) {

				if (focusNode == root) {
					root = focusNode.getLeftChild();
				} else if (isItLeftChild) {
					parent.setLeftChild(focusNode.getLeftChild());
				} else {
					parent.setRightChild(focusNode.getLeftChild());
				}
			// only right child
			} else if (focusNode.getLeftChild() == null) {
				if (focusNode == root) {
					root = focusNode.getRightChild();
				} else if (isItLeftChild) {
					parent.setLeftChild(focusNode.getRightChild());
				} else {
					parent.setRightChild(focusNode.getRightChild());
				}
			// left and right child
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

	public Boolean saveTreeToFile(String stringPath) {
		Node focusNode = root;
		
		ArrayList<String> treeArray = new ArrayList<String>();
		preorderTraverseTree(focusNode, treeArray);
		
		System.out.println(treeArray.toString());
		System.out.println("save to file");
		// TODO replace own code with production when finished
		// fetch path string from console commit path to binary tree object for
		// handling
		Path storePath = Paths.get(stringPath);
		System.out.println("Path to file: " + storePath);

		if (!Files.isWritable(storePath)) {
			try {
				Files.createFile(storePath);
			} catch (IOException e) {
				System.out.println("Failed to create file:\n" + e);
				e.printStackTrace();
				return false;
			}
		}

		try {
			PrintWriter writer = new PrintWriter(new FileWriter(storePath.toString()));
			for (String s : treeArray) {
				writer.println(s);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to create FileWriter: " + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void preorderTraverseTree(Node focusNode, ArrayList<String> list) {
		if (focusNode != null && list != null) {
			list.add(focusNode.getData());
			preorderTraverseTree(focusNode.getLeftChild(), list);

			preorderTraverseTree(focusNode.getRightChild(), list);
		}
		System.out.println(list.toString());
	}

	private Node getReplacementNode(Node replacedNode) {
		System.out.println("Node to replace: " + replacedNode.toString());
		Node replacementParent = replacedNode;
		Node replacement = replacedNode;

		Node focusNode = replacedNode.getRightChild();

		while (focusNode != null) {
			replacementParent = replacement;
			replacement = focusNode;
			focusNode = focusNode.getLeftChild();
		}
		
		System.out.println("Node to replace: " + replacedNode.toString());
		System.out.println("Node to replacement parent: " + replacementParent.toString());
		System.out.println("Node to replacement: " + replacement.toString());
		
		if (replacement != replacedNode.getRightChild()) {
			replacementParent.setLeftChild(replacement.getRightChild());
			replacement.setRightChild(replacedNode.getRightChild());
		}
		return replacement;
	}
}
