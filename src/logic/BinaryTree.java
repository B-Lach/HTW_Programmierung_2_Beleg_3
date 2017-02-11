package logic;

import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.OutputStreamWriter;

/**
 * binary tree class
 * 
 * @author Rico
 *
 */
public class BinaryTree {
	/**
	 * Enum to identify which rotation has to be performed to re-balance the tree,
	 * @author Benny Lach
	 *
	 */
	private enum RotationType {
		// +2 +1
		ClockwiseSmall,
		// -2 +1
		ClockwiseBig,
		// -2 -1
		CounterClockwiseSmall,
		// +2 -1
		CounterClockwiseBig	
	}
	// path to the file the tree was loaded from/saved to
	private String stringPath;
	// root node
	private Node root;
	// Boolean to identify if the tree behaves as an AVL tree
	private Boolean useAvl;
	// Encoding identifier for the file
	final static Charset ENCODING = StandardCharsets.UTF_8;
	// File extension type
	public final static String FILE_EXTENSION = "btv";
	
	/**
	 * Static Function to initialize a new Tree from a given file
	 * @param stringPath Path to the file to use
	 * @return Instance of BinaryTree if the file is valid. Otherwise null
	 */
	public static BinaryTree loadTreeFromFile(String stringPath) {
		// check if the file to load is valid
		if(pathIsValid(stringPath)) {
			Path path = Paths.get(stringPath);
			
			if (Files.isReadable(path)) {
				try {
					// fetch data as List<String> from file
					List<String> treeList = Files.readAllLines(path, ENCODING);
					// validate content
					if (validateFileContent(treeList)) { 
						// create new tree
						// TODO Update handling to initialize not always with AVL turned off
						BinaryTree tree = new BinaryTree(false);
						for(String data: treeList) {
							tree.addNode(data);
						}
						// store the current used path to be able to save to that file in the future again
						tree.stringPath = stringPath;

						return tree;
					}
				} catch (IOException e) {
					return null;
				}
			}
		}
		return null;
	}
	
	/**
	 * Function to check if a given string is a valid file path
	 * @param path The path to check
	 * @return True if the path is valid. Otherwise false
	 */
	private static Boolean pathIsValid(String path) {
		return path.endsWith("." + FILE_EXTENSION);
	}
	
	/**
	 * Function to validate content of a loaded file
	 * @param content The content to validate
	 * @return True if content is valid. Otherwise false
	 */
	private static Boolean validateFileContent(List<String> content) {
		for(int i = 0; i < content.size(); i++) {
			String s = content.get(i);
			// fist string has to be an string representation of the AVL option
			if (i == 0) {
				if (s != "false" && s != "true") {
					return false;
				}
			// rest of the strings are nodes to load
			} else {
				if (s.length() < 1 || s.length() > 3) {
					return false;
				}
			}	
		}
		return true;
	}
	
	/**
	 * Constructor to initialize a new BinaryTree instance
	 * @param useAvl Option to identify if the tree has to behave as AVL Tree
	 */
	public BinaryTree(Boolean useAvl) {
		this.useAvl = useAvl;
	}
	
	/**
	 * Method to get the current path of the used file
	 * @return The path to the current used file. Is null, if there is no path available
	 */
	public String getStringPath(){
		return stringPath;
	}
	
	/**
	 * Method to re-balance the tree if needed.
	 */
	private void replaceTreeIfNeeded(Node subRoot) {
		// Reached end of iteration
		if (subRoot == null) { return;}
		// current subtree is balanced
		if (Math.abs(subRoot.getBalance()) < 2) {
			// check right subtree
			replaceTreeIfNeeded(subRoot.getRightChild());
			// check left subtree
			replaceTreeIfNeeded(subRoot.getLeftChild());
		} else {
			// subtree is unbalanced
			if (Math.abs(subRoot.getBalance()) == 2) {
				// right side is unbalanced
				if (subRoot.getBalance() > 0) {
					// found criterion for rotation => sub-root == 2 && rightChild == |1| || 0
					if (Math.abs(subRoot.getRightChild().getBalance()) == 1 || subRoot.getRightChild().getBalance() == 0) {
						// clockwise small rotation found
						if (subRoot.getRightChild().getBalance() >= 0) {
							makeRotation(subRoot,RotationType.ClockwiseSmall);
						} else {
							makeRotation(subRoot, RotationType.CounterClockwiseBig);
						}
						// time to re-balance and iterate again over the tree, start with root
						calculateBalance(root);
						replaceTreeIfNeeded(root);
						
						return;
					}
				} else {
					// // found criterion for rotation => sub-root == -2 && leftChild == |1| || 0
					if (Math.abs(subRoot.getLeftChild().getBalance()) == 1 || subRoot.getLeftChild().getBalance() == 0) {
						// counter clockwise small rotation found
						if (subRoot.getLeftChild().getBalance() <= 0) {
							makeRotation(subRoot,RotationType.CounterClockwiseSmall);
						} else {
							makeRotation(subRoot,RotationType.ClockwiseBig);
						}
						// time to re-balance and iterate again over the tree, start with root
						calculateBalance(root);
						replaceTreeIfNeeded(root);
						
						return;
					}
				}
			}
			// reaching this line means tree is unbalanced but criteria was not found
			replaceTreeIfNeeded(subRoot.getRightChild());
			replaceTreeIfNeeded(subRoot.getLeftChild());
		}
	}
	
	private void makeRotation(Node node, RotationType type) {
		Node newParent;
		
		switch(type) {
		case ClockwiseSmall:
			// right child will become new parent, parent will become left child
			newParent = node.getRightChild();
			// left child of new node will become right child of old root
			node.setRightChild(newParent.getLeftChild());
			if (node.getRightChild() != null) {
				node.getRightChild().setParentNode(node);
			}
			
			newParent.setParentNode(node.getParentNode());
			// if parent is not null update it's child
			// otherwise old parent is root node of the whole tree
			if(newParent.getParentNode() != null) {
				if (newParent.getData().compareTo(newParent.getParentNode().getData()) > 0) {
					newParent.getParentNode().setRightChild(newParent);
				} else {
					newParent.getParentNode().setLeftChild(newParent);
				}
			} else {
				root = newParent;
			}
			// old parent will become left child of new Parent
			node.setParentNode(newParent);
			newParent.setLeftChild(node);
			break;
		case ClockwiseBig:
			// 1. step: rotate clockwise small node.leftChild
			// 2. step:  rotate counter clockwise node
			makeRotation(node.getLeftChild(), RotationType.ClockwiseSmall);
			makeRotation(node, RotationType.CounterClockwiseSmall);
			break;
		case CounterClockwiseSmall:
			// left child will become new parent, parent will become right child
			newParent = node.getLeftChild();
			// right child of new parent will become left child of old parent
			node.setLeftChild(newParent.getRightChild());
			if (node.getLeftChild() != null) {
				node.getLeftChild().setParentNode(node);
			}
			
			newParent.setParentNode(node.getParentNode());
			// if parent is not null update left child
			// otherwise old parent is root node of the whole tree
			if (newParent.getParentNode() != null) {
				if (newParent.getData().compareTo(newParent.getParentNode().getData()) > 0) {
					newParent.getParentNode().setRightChild(newParent);
				} else {
					newParent.getParentNode().setLeftChild(newParent);
				}
			}  else {
				root = newParent;
			}
			// old parent will become right child of new parent
			node.setParentNode(newParent);
			newParent.setRightChild(node);
			break;
		case CounterClockwiseBig:
			// 1. step: rotate counter clockwise small node.rightChild
			// 2. step: rotate clockwise small node
			makeRotation(node.getRightChild(), RotationType.CounterClockwiseSmall);
			makeRotation(node, RotationType.ClockwiseSmall);
			break;
			default:
				return;
		}
	}
	
	
	/**
	 * Method to calculate the balance of the tree and all its subtrees
	 * @param node The root node of the tree
	 */
	private void calculateBalance(Node node) {
		// nothing to calculate at this point
		if (node == null) { return; }
		
		int right = getDepthOfSubtree(node.getRightChild());
		int left = getDepthOfSubtree(node.getLeftChild());
		
		node.setBalance(right - left);
//		System.out.println("Node with data: " + node.getData() + " has balance: " + node.getBalance());
		
		calculateBalance(node.getRightChild());
		calculateBalance(node.getLeftChild());
	}
	
	/**
	 * Method to calculate the depth of a subtree based on a given root node
	 * @param node The root node of the subtree
	 * @return The depth of the subtree as Integer
	 */
	private int getDepthOfSubtree(Node node) {
		// nothing to calculate at this point
		if (node == null) { return 0; }
		
		int leftHeight = getDepthOfSubtree(node.getLeftChild());
		int rightHeight = getDepthOfSubtree(node.getRightChild());
		
		if (leftHeight == 0 && rightHeight == 0) { return 1; }
		return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
		
	}
	
	/**
	 * Public method to add a new Node. Returns a boolean to identify if the new node was added. Returns false if the data is already part of the node.
	 * @param data The data of the node to add
	 * @return Boolean identifier if the node was added
	 */
	public Boolean addNode(String data) {
		// without a root node this will become root

		if (root == null) {
			Node newNode = new Node(data);
			root = newNode;
			newNode.setBalance(0);
			
			return true;
		} else {
			Boolean wasAdded = _addNode(data);
			if (wasAdded) {
				// calculate the new balance
				calculateBalance(root);
				if (useAvl) {
					// if AVL is active, re-balance the tree if needed
					replaceTreeIfNeeded(root);
				}
			}
			return wasAdded;
		}
	}
	
	/**
	 * Private method to add a new node. Return value is a boolean to identify if the new node was added. Returns false if the data is already part of the node.
	 * @param data The data of the node to add
	 * @return Boolean Identifier if the node was added 
	 */
	private Boolean _addNode(String data) {
		// init new node
		Node newNode = new Node(data);
		// sets root as starting point for traversing the tree
		Node focusNode = root;

		// future parent for new node
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
					newNode.setParentNode(parent);

					return true;
				}
			} else if (data.compareTo(focusNode.getData()) > 0) {

				// puts node on right side
				focusNode = focusNode.getRightChild();

				// if right child has no children
				if (focusNode == null) {

					// place new node on the right
					parent.setRightChild(newNode);
					newNode.setParentNode(parent);
					
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
	
	public boolean deleteAll() {
		root = null;
		if (root == null) {
			return true;
		}
		return false;
	}

	/**
	 * public delete method for nodes
	 * 
	 * @param data
	 *            the node that the user wants to delete
	 * @return returns true if deletion was successful and false if it was not
	 */
	public boolean deleteNode(String data) {
		Boolean deleted = _deleteNode(data);
		
		if (deleted) {
			// calculate the new balance
			calculateBalance(root);
			if (useAvl) {
				// if AVL is active, re-balance the tree if needed
				replaceTreeIfNeeded(root);
			}
		}
		return deleted;
	}
	
	/**
	 * private delete method for nodes
	 * 
	 * @param data
	 *            the node that the user wants to delete
	 * @return returns true if deletion was successful and false if it was not
	 */
	private boolean _deleteNode(String data) {

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
					root.setParentNode(null);
				} else if (isItLeftChild) {
					parent.setLeftChild(focusNode.getLeftChild());
					parent.getLeftChild().setParentNode(parent);
				} else {
					parent.setRightChild(focusNode.getLeftChild());
					parent.getRightChild().setParentNode(parent);
				}
				// only right child
			} else if (focusNode.getLeftChild() == null) {
				if (focusNode == root) {
					root = focusNode.getRightChild();
					root.setParentNode(null);
				} else if (isItLeftChild) {
					parent.setLeftChild(focusNode.getRightChild());
					parent.getLeftChild().setParentNode(parent);
				} else {
					parent.setRightChild(focusNode.getRightChild());
					parent.getRightChild().setParentNode(parent);
				}
				// left and right child
			} else {
				Node replacement = getReplacementNode(focusNode);

				if (focusNode == root) {
					root = replacement;
					root.setParentNode(null);
				} else if (isItLeftChild) {
					parent.setLeftChild(replacement);
					replacement.setParentNode(parent);
				} else {
					parent.setRightChild(replacement);
					replacement.setParentNode(parent);
				}
				replacement.setLeftChild(focusNode.getLeftChild());
				replacement.getLeftChild().setParentNode(replacement);
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
		// check if the given path is valid
		// if it isn't add the valid file extension
		if(!pathIsValid(stringPath)) {
			stringPath = addValidFileExtension(stringPath);
		}
		Node focusNode = root;

		ArrayList<String> treeArray = new ArrayList<String>();
		preorderTraverseTree(focusNode, treeArray);
		Path storePath = Paths.get(stringPath);

		if (!Files.isWritable(storePath)) {
			try {
				Files.createFile(storePath);
			} catch (IOException e) {
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
			// store the current used path to be able to save to that file in the future again
			this.stringPath = stringPath;
		} catch (IOException e) {
			System.out.println("Failed to create FileWriter: " + e);

			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean avlSort(){
		ArrayList<String> treeArray = new ArrayList<String>();
		Node focusNode = root;
		preorderTraverseTree(focusNode, treeArray);
		
		return true;
	}
	
	/**
	 * Method to add a valid file extension to a given path
	 * @param path The path to add the file extension
	 * @return The path with valid extension
	 */
	private String addValidFileExtension(String path) {	
		return path += "." + FILE_EXTENSION;
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
			if (replacementParent.getLeftChild() != null) {
				replacementParent.getLeftChild().setParentNode(replacementParent);
			}
			
			replacement.setRightChild(replacedNode.getRightChild());
			if (replacement.getRightChild() != null ) {
				replacement.getRightChild().setParentNode(replacement);
			}
		}
		return replacement;
	}
	
	/**
	 * Method to print the tree on console
	 * Inspired by http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
	 */
	public void printTree() {
		System.out.println("\n\n\n");
		// empty tree found
		if (root == null) {
        	System.out.println("Tree is empty");
        } else {
        	OutputStreamWriter writer = new OutputStreamWriter(System.out);
        	
        	if (root.getRightChild() != null) {
            	printTree(writer, root.getRightChild(), true, "");
            }
            printNodeValue(writer, root);
            if (root.getLeftChild() != null) {
                printTree(writer, root.getLeftChild(), false, "");
            }
        }
        System.out.println("\n\n\n");
	}
    
    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
	/**
	 * Private Method to print the tree on console using an OutputStreamWriter
	 * @param writer The OutputStreamWriter object to use
	 * @param root The current root node to print
	 * @param isRight Boolean value to identify if the current node is right child 
	 * @param indent The current indent string
	 */
    private void printTree(OutputStreamWriter writer, Node root, boolean isRight, String indent) {
        // right child not null
    	if (root.getRightChild() != null) {
        	printTree(writer, root.getRightChild(), true, indent + (isRight ? "        " : " |      "));
        }
    	// write current node
        try {
        	writer.write(indent);
            if (isRight) {
                writer.write(" /");
            } else {
                writer.write(" \\");
            }
            writer.write("----- ");
            writer.flush();
        } catch (Exception e) {}
        
        printNodeValue(writer, root);
        // left child not null
        if (root.getLeftChild() != null) {
            printTree(writer, root.getLeftChild(), false, indent + (isRight ? " |      " : "        "));
        }
    }
    
    /**
     * Private method to print a node's value to an OutputStreamWriter
     * @param out The OutputStreamWriter to use
     * @param node The node to print
     */
    private void printNodeValue(OutputStreamWriter out, Node node) {
    	try {
    		// node is null
    		if (node == null) {
                out.write("<null>");
            } else {
                out.write(node.getData());
            }
            out.write('\n');
            out.flush();
    	} catch (Exception e) {}
    }
}
