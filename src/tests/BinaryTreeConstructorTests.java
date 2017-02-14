package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import logic.*;

/**
 * Class to test all the constructor of the BinaryTree class
 * @author Benny Lach
 *
 */
public class BinaryTreeConstructorTests {

	@Test
	public void test_initWithoutFileAVL() {
		BinaryTreeMock tree = new BinaryTreeMock(false);
		assertFalse("AVL is active but it shouldn't", tree.avlActive());
		
		tree = new BinaryTreeMock(true);
		assertTrue("AVL is not active but it should", tree.avlActive());
	}
	
	@Test	
	public void test_initWithInValidFileContent() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("basn");
		list.add("abcd");
		
		try {
			BinaryTree tree = new BinaryTree(list);
			fail("Exception did not throw");
		} catch(Exception e) {
			// nothing to do here
		}
	}
	
	@Test
	public void test_initWithValidFileContent() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("true");
		list.add("asd");
		
		try {
			BinaryTree tree = new BinaryTree(list);
			
		} catch(Exception e) {
			fail("Exception did throw but it shouldn't");
		}
	}

}
