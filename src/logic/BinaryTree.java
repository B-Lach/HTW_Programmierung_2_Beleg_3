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
//
//	public static void main(String[] args) {
//
//		BinaryTree theTree = new BinaryTree();
//
//		theTree.addNode("50");
//		theTree.addNode("25");
//		theTree.addNode("15");
//		theTree.addNode("30");
//		theTree.addNode("75");
//		theTree.addNode("85");
//
//		theTree.saveTreeToFile("C:/Users/Rico/Documents/test.txt");
//		theTree.loadTreeFromFile("C:/Users/Rico/Documents/test.txt");
//	}

	private Node root;
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public final static String FILE_EXTENSION = "btv";
	
	private String stringPath;
	
	/**
	 * Method to get the current path of the used file
	 * @return The path to the current used file. Is null, if there is no path available
	 */
	public String getStringPath(){
		return stringPath;
	}

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

	public boolean deleteAll() {
		root = null;
		if (root == null) {
			return true;
		}
		return false;
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
	
	public Boolean loadTreeFromFile(String stringPath) {
		// check if the file to load is valid
		if(!pathIsValid(stringPath)) {
			return false;
		}
		Path path = Paths.get(stringPath);
		
		if (Files.isReadable(path)) {
			try {
				// fetch data as List<String> from file
				List<String> treeList = Files.readAllLines(path, ENCODING);
				// clear current stored hierarchy 
				deleteAll();
				// create new tree
				for(String data: treeList) {
					addNode(data);
				}
				// store the current used path to be able to save to that file in the future again
				this.stringPath = stringPath;
				
				return true;
			} catch (IOException e) {
				return false;
			}
		} else {
			return false;
		}
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
