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
	private String stringPath;
	private Node root;
	private Boolean useAvl = false;
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public final static String FILE_EXTENSION = "btv";
	
	/**
	 * Method to get the current path of the used file
	 * @return The path to the current used file. Is null, if there is no path available
	 */
	public String getStringPath(){
		return stringPath;
	}
	
	/**
	 * Method to set the avl option. If true, a tree is balanced automatically
	 * @param useAvl The boolean to set 
	 */
	public void setUseAvl(Boolean useAvl) {
		this.useAvl = useAvl;
		if (useAvl) {
			rebalanceTreeIfNeeded();
		}
	}
	
	/**
	 * Method to re-balance the tree if needed.
	 */
	private void rebalanceTreeIfNeeded() {
		// TODO Implement logic
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
		System.out.println("Right has depth: " + right);
		System.out.println("Left has depth: " + left);
		node.setBalance(right - left);
		System.out.println("Node with data: " + node.getData() + " has balance: " + node.getBalance());
		
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
					rebalanceTreeIfNeeded();
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
				rebalanceTreeIfNeeded();
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
	
	public Boolean loadTreeFromFile(String stringPath) {
		// check if the file to load is valid
		if(pathIsValid(stringPath)) {
			Path path = Paths.get(stringPath);
			
			if (Files.isReadable(path)) {
				try {
					// fetch data as List<String> from file
					List<String> treeList = Files.readAllLines(path, ENCODING);
					// validate content
					if (validateFileContent(treeList)) {
						// clear current stored hierarchy 
						deleteAll();
						// create new tree
						for(String data: treeList) {
							addNode(data);
						}
						// store the current used path to be able to save to that file in the future again
						this.stringPath = stringPath;

						return true;
					}
				} catch (IOException e) {
					return false;
				}
			}
		}
		return false;
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
	 * Method to check if a given string is a valid file path
	 * @param path The path to check
	 * @return True if the path is valid. Otherwise false
	 */
	private Boolean pathIsValid(String path) {
		return path.endsWith("." + FILE_EXTENSION);
	}
	
	/**
	 * Method to add a valid file extension to a given path
	 * @param path The path to add the file extension
	 * @return The path with valid extension
	 */
	private String addValidFileExtension(String path) {	
		return path += "." + FILE_EXTENSION;
	}
	
	/**
	 * Method to validate content of a loaded file
	 * @param content The content to validate
	 * @return True if content is valid. Otherwise false
	 */
	private Boolean validateFileContent(List<String> content) {
		for(String s: content) {
			if (s.length() < 1 || s.length() > 3) {
				return false;
			}
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
