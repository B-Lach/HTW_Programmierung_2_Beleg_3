package tests;

import static org.junit.Assert.*;

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
	public void test_findValidNode() {
		BinaryTree tree = new BinaryTree(false);
		tree.addNode("90");
		
		assertNotNull("Found invalid node", tree.findNode("90"));
	}
}
