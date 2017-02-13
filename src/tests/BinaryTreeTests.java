package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import logic.*;

/**
 * Tests for instance methods of a BinaryTree object
 * @author Benny Lach
 *
 */
public class BinaryTreeTests {

	@Test
	public void test_rootAfterInit() {
		BinaryTree tree = new BinaryTree(false);
		
		assertNull("root node is not null", tree.getRootNode());
	}
	
	@Test
	public void test_rootAfterAddition() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("abc");
		
		assertNotNull("Root node is null", tree.getRootNode());
	}
	
	@Test
	public void test_invalidLengthAddition() {
		BinaryTree tree = new BinaryTree(false);
		assertFalse("Node should not be added", tree.addNode("abcf"));
	}
	
	@Test
	public void test_nullAddition() {
		BinaryTree tree = new BinaryTree(false);
		assertFalse("Node should not be added", tree.addNode(null));
	}
	
	@Test
	public void test_dublicateAddition() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("abc");
		
		assertFalse("Dublicate node was added", tree.addNode("abc"));
	}
	
	@Test
	public void test_leftChildAddition() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		tree.addNode("30");
		
		// right child should be null
		assertNull("Right child should be null", tree.getRootNode().getRightChild());
		// left child should not be null
		assertNotNull("Left child should not be null", tree.getRootNode().getLeftChild());
		// parent nodes data of left child should be 40
		assertTrue("Parent nodes data should be 40", tree.getRootNode().getLeftChild().getParentNode().getData() == "40");
	}
	
	@Test
	public void test_rightChildAddition() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		tree.addNode("50");
		// left child should be null
		assertNull("Left child should be null", tree.getRootNode().getLeftChild());
		// right child should not be null
		assertNotNull("Right child should not be null", tree.getRootNode().getRightChild());
		// parent nodes data of right child should be 40
		assertTrue("Parent nodes data should be 40", tree.getRootNode().getRightChild().getParentNode().getData() == "40");
	}
	
	@Test
	public void test_invalidChildDeletion() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		
		assertFalse("Deletion should return false", tree.deleteNode("50"));
	}
	
	@Test
	public void test_nullDeletation() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		tree.addNode("50");
		// removing null should return false
		assertFalse("Delte null is not valid", tree.deleteNode(null));
	}
	
	@Test
	public void test_validChildDeletion() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		tree.addNode("50");
		
		// removing valid node should return true
		assertTrue("Deletion should return true", tree.deleteNode("50"));
		// right child should be removed automatically
		assertNull("Right child should be empty now", tree.getRootNode().getRightChild());
	}
	
	@Test
	public void test_rootDeletationWithoutChild() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		
		tree.deleteNode("40");
		
		assertNull("Root node is not null", tree.getRootNode());
	}
	
	@Test
	public void test_rootDeletationLeftChild() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		tree.addNode("30");
		
		tree.deleteNode("40");
		
		// Root node should be 30 now
		assertTrue("Node with data 30 should be root now", tree.getRootNode().getData() == "30");
		// Parent of root should be null
		assertNull("Root node has a parent", tree.getRootNode().getParentNode());
		// Left child should be null
		assertNull("Root node has a left child", tree.getRootNode().getLeftChild());
		// Right child should be null
		assertNull("Root node has a right child", tree.getRootNode().getRightChild());
	}

	@Test
	public void test_rootDeletationRightChild() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		tree.addNode("50");
		
		tree.deleteNode("40");
		
		// Root node should be 50 now
		assertTrue("Node with data 50 should be root now", tree.getRootNode().getData() == "50");
		// Parent of root should be null
		assertNull("Root node has a parent", tree.getRootNode().getParentNode());
		// Left child should be null
		assertNull("Root node has a left child", tree.getRootNode().getLeftChild());
		// Right child should be null
		assertNull("Root node has a right child", tree.getRootNode().getRightChild());
	}
	

	@Test
	public void test_rootDeletationLeftAndRightChild() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("40");
		tree.addNode("30");
		tree.addNode("50");
		
		tree.deleteNode("40");
		
		// Root node should be 30 now
		assertTrue("Node with data 50 should be root now", tree.getRootNode().getData() == "50");
		// Parent of root should be null
		assertNull("Root node has a parent", tree.getRootNode().getParentNode());
		// Left child should not be null
		assertNotNull("Root node has no left child", tree.getRootNode().getLeftChild());
		// Right child should be null
		assertNull("Root node has a right child", tree.getRootNode().getRightChild());
	}
	
	@Test
	public void test_deletaAll() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("50");
		tree.addNode("90");
		tree.deleteAll();
		
		assertNull("Root is not null after delete all call", tree.getRootNode());
	}
	
	@Test
	public void test_findInvalidNode() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("90");
		
		assertNull("Found invalid node", tree.findNode("30"));
	}
	
	@Test
	public void test_findNullNode() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("90");
		
		assertNull("Found null node", tree.findNode(null));
	}
	
	@Test
	public void test_findValidNode() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("90");
		
		assertNotNull("Found invalid node", tree.findNode("90"));
	}
	
	@Test
	public void test_stringPathWithoutFile() {
		BinaryTree tree = new BinaryTree(false);
		assertNull("String path should be null", tree.getStringPath());
	}
	
	@Test
	public void test_stringPathWithFile() {
		File valid = new File("src/tests/resources/valid.btv");
		BinaryTree tree = BinaryTree.loadTreeFromFile(valid.getAbsolutePath());
		// loading from file should set path automatically
		assertNotNull("String path should be null", tree.getStringPath());
		// stored path has to be equal to the used path
		assertTrue("Paths are not equal", tree.getStringPath().compareTo(valid.getAbsolutePath()) == 0);
	}
	
	@Test
	public void test_saveToNullFile() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("50");

		assertFalse("Saving to null should return false", tree.saveTreeToFile(null));
	}
	
	@Test
	public void test_saveToInvalidFile() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("50");

		assertFalse("Saving should return false", tree.saveTreeToFile("/this&is?not/valid"));
	}
	
	@Test
	public void test_saveToValidFile() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("50");
		
		File valid = new File("src/tests/resources/save_valid.btv");
		assertTrue("Saving should return true", tree.saveTreeToFile(valid.getAbsolutePath()));
	}
	
	@Test
	public void test_smallClockwiseRotation() {
		BinaryTree tree = new BinaryTree(true);
		tree.addNode("50");
		tree.addNode("60");
		tree.addNode("70");
		// 60 should be the root node now
		assertTrue("Data of root is not correct", tree.getRootNode().getData() == "60");
		// balance of root should be 0
		assertTrue("Balance is not correct", tree.getRootNode().getBalance() == 0);
		assertNull("Root should not have a parent", tree.getRootNode().getParentNode());
		// left child should be 50 now
		assertNotNull("Root must have a left child", tree.getRootNode().getLeftChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getLeftChild().getData() == "50");
		assertTrue("Parent of left child is not correct", tree.getRootNode().getLeftChild().getParentNode().getData() == "60");
		assertTrue("Balance of left child is not correct", tree.getRootNode().getLeftChild().getBalance() == 0);
		assertNull("Left child of left leaf has to be null", tree.getRootNode().getLeftChild().getLeftChild());
		assertNull("Right child of left leaf has to be null", tree.getRootNode().getLeftChild().getRightChild());
		// right child should be 70 now
		assertNotNull("Root must have a rightchild",tree.getRootNode().getRightChild());
		assertTrue("Data of right child is not correct", tree.getRootNode().getRightChild().getData() == "70");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getRightChild().getParentNode().getData() == "60");
		assertTrue("Balance of right child is not correct", tree.getRootNode().getRightChild().getBalance() == 0);
		assertNull("Left child of right leaf has to be null", tree.getRootNode().getRightChild().getLeftChild());
		assertNull("Right child of right leaf has to be null", tree.getRootNode().getRightChild().getRightChild());
	}
	
	@Test
	public void test_smallCounterClockwiseRotation() {
		BinaryTree tree = new BinaryTree(true);
		tree.addNode("50");
		tree.addNode("40");
		tree.addNode("30");
		// 60 should be the root node now
		assertTrue("Data of root is not correct", tree.getRootNode().getData() == "40");
		assertTrue("Balance is not correct", tree.getRootNode().getBalance() == 0);
		assertNull("Root should not have a parent", tree.getRootNode().getParentNode());
		// left child should be 50 now
		assertNotNull("Root must have a left child", tree.getRootNode().getLeftChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getLeftChild().getData() == "30");
		assertTrue("Parent of left child is not correct", tree.getRootNode().getLeftChild().getParentNode().getData() == "40");
		assertTrue("Balance of left child is not correct", tree.getRootNode().getLeftChild().getBalance() == 0);
		assertNull("Left child of left leaf has to be null", tree.getRootNode().getLeftChild().getLeftChild());
		assertNull("Right child of left leaf has to be null", tree.getRootNode().getLeftChild().getRightChild());
		// right child should be 70 now
		assertNotNull("Root must have a rightchild",tree.getRootNode().getRightChild());
		assertTrue("Data of right child is not correct", tree.getRootNode().getRightChild().getData() == "50");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getRightChild().getParentNode().getData() == "40");
		assertTrue("Balance of right child is not correct", tree.getRootNode().getRightChild().getBalance() == 0);
		assertNull("Left child of right leaf has to be null", tree.getRootNode().getRightChild().getLeftChild());
		assertNull("Right child of right leaf has to be null", tree.getRootNode().getRightChild().getRightChild());
	}
	
	@Test
	public void test_bigCounterClockwiseRotation() {
		BinaryTree tree = new BinaryTree(true);
		tree.addNode("70");
		tree.addNode("60");
		tree.addNode("85");
		tree.addNode("90");
		tree.addNode("80");
		tree.addNode("83");
		// just a visual help
		tree.printTree();
		
		// 60 should be the root node now
		assertTrue("Data of root is not correct", tree.getRootNode().getData() == "80");
		assertTrue("Balance is not correct", tree.getRootNode().getBalance() == 0);
		assertNull("Root should not have a parent", tree.getRootNode().getParentNode());
		// left child of root should be 70 now
		assertNotNull("Root must have a left child", tree.getRootNode().getLeftChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getLeftChild().getData() == "70");
		assertTrue("Parent of left child is not correct", tree.getRootNode().getLeftChild().getParentNode().getData() == "80");
		assertTrue("Balance of left child is not correct", tree.getRootNode().getLeftChild().getBalance() == -1);
		// left child of 70 should be 60
		assertNotNull("Left child of left should not be null", tree.getRootNode().getLeftChild().getLeftChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getLeftChild().getLeftChild().getData() == "60");
		assertTrue("Parent of left child is not correct", tree.getRootNode().getLeftChild().getLeftChild().getParentNode().getData() == "70");
		assertTrue("Balance of left leaf is not correct", tree.getRootNode().getLeftChild().getLeftChild().getBalance() == 0);
		assertNull("Leaf node should not have children",tree.getRootNode().getLeftChild().getLeftChild().getLeftChild());
		// right child of 70 should be null		
		assertNull("Right child of left should be null", tree.getRootNode().getLeftChild().getRightChild());
		// right child of root should be 85
		assertNotNull("Root must have a right child",tree.getRootNode().getRightChild());
		assertTrue("Data of right child is not correct", tree.getRootNode().getRightChild().getData() == "85");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getRightChild().getParentNode().getData() == "80");
		assertTrue("Balance of right child is not correct", tree.getRootNode().getRightChild().getBalance() == 0);
		// left child of 85 should be 83
		assertNotNull("Left child of 85 should not be null", tree.getRootNode().getRightChild().getLeftChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getRightChild().getLeftChild().getData() == "83");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getRightChild().getLeftChild().getParentNode().getData() == "85");
		assertTrue("Balance of right child is not correct", tree.getRootNode().getRightChild().getLeftChild().getBalance() == 0);
		assertNull("Leaf node should not have children", tree.getRootNode().getRightChild().getLeftChild().getLeftChild());
		// right child of 85 should be 90
		assertNotNull("Right child of 85 should not be null", tree.getRootNode().getRightChild().getRightChild());
		assertTrue("Data of right child is not correct", tree.getRootNode().getRightChild().getRightChild().getData() == "90");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getRightChild().getRightChild().getParentNode().getData() == "85");
		assertTrue("Balance of right child is not correct", tree.getRootNode().getRightChild().getRightChild().getBalance() == 0);
		assertNull("Leaf node should not have children",tree.getRootNode().getRightChild().getRightChild().getLeftChild());
	}
	
	@Test
	public void test_bigClockwiseRotation() {
		BinaryTree tree = new BinaryTree(true);
		tree.addNode("40");
		tree.addNode("42");
		tree.addNode("05");
		tree.addNode("20");
		tree.addNode("01");
		tree.addNode("10");
		// just a visual help
		tree.printTree();
		
		// 20 should be the root node now
		assertTrue("Data of root is not correct", tree.getRootNode().getData() == "20");
		assertTrue("Balance is not correct", tree.getRootNode().getBalance() == 0);
		assertNull("Root should not have a parent", tree.getRootNode().getParentNode());
		// left child of root should be 05 now
		assertNotNull("Root must have a left child", tree.getRootNode().getLeftChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getLeftChild().getData() == "05");
		assertTrue("Parent of left child is not correct", tree.getRootNode().getLeftChild().getParentNode().getData() == "20");
		assertTrue("Balance of left child is not correct", tree.getRootNode().getLeftChild().getBalance() == 0);
		// left child of 05 should be 01
		assertNotNull("Left child of left should not be null", tree.getRootNode().getLeftChild().getLeftChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getLeftChild().getLeftChild().getData() == "01");
		assertTrue("Parent of left child is not correct", tree.getRootNode().getLeftChild().getLeftChild().getParentNode().getData() == "05");
		assertTrue("Balance of left leaf is not correct", tree.getRootNode().getLeftChild().getLeftChild().getBalance() == 0);
		assertNull("Leaf node should not have children",tree.getRootNode().getLeftChild().getLeftChild().getLeftChild());
		// right child of 05 should be 10		
		assertNotNull("Right child of left should not be null", tree.getRootNode().getLeftChild().getRightChild());
		assertTrue("Data of left child is not correct", tree.getRootNode().getLeftChild().getRightChild().getData() == "10");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getLeftChild().getRightChild().getParentNode().getData() == "05");
		assertTrue("Balance of right leaf is not correct", tree.getRootNode().getLeftChild().getRightChild().getBalance() == 0);
		assertNull("Leaf node should not have children",tree.getRootNode().getLeftChild().getRightChild().getLeftChild());
		// right child of root should be 40
		assertNotNull("Root must have a right child",tree.getRootNode().getRightChild());
		assertTrue("Data of right child is not correct", tree.getRootNode().getRightChild().getData() == "40");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getRightChild().getParentNode().getData() == "20");
		assertTrue("Balance of right child is not correct", tree.getRootNode().getRightChild().getBalance() == 1);
		// left child of 40 should be null
		assertNull("Left child of 20 should be null", tree.getRootNode().getRightChild().getLeftChild());
		// right child of 40 should be 42
		assertNotNull("Right child of 85 should not be null", tree.getRootNode().getRightChild().getRightChild());
		assertTrue("Data of right child is not correct", tree.getRootNode().getRightChild().getRightChild().getData() == "42");
		assertTrue("Parent of right child is not correct", tree.getRootNode().getRightChild().getRightChild().getParentNode().getData() == "40");
		assertTrue("Balance of right child is not correct", tree.getRootNode().getRightChild().getRightChild().getBalance() == 0);
		assertNull("Leaf node should not have children",tree.getRootNode().getRightChild().getRightChild().getLeftChild());
	}
}
