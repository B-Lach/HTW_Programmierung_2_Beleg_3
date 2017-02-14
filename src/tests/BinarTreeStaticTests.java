package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import logic.BinaryTree;

/**
 * Class to test all the static functions of the BinaryTree class
 * @author Benny Lach
 *
 */
public class BinarTreeStaticTests {

	@Test
	public void test_validFilePath() {
		assertTrue("Path isn't valid but it should", BinaryTree.pathIsValid("valid.btv"));
	}
	
	@Test
	public void test_invalidFilePath() {
		assertFalse("Path is valid but it shouldn't", BinaryTree.pathIsValid("notvalid.bt"));
	}
	
	@Test
	public void test_contentValidation() {
		ArrayList<String> list = new ArrayList<String>();
		// valid content - AVL option false
		list.add("false");
		list.add("abc");
		list.add("ad");
		// Content should be valid at this point
		assertTrue("Content wasn't validated but it should be", BinaryTree.validateFileContent(list));
		
		// valid content - AVL option true
		list.remove(0);
		list.add(0, "true");
		// Content should be valid at this point
		assertTrue("Content wasn't validated but it should be", BinaryTree.validateFileContent(list));
		
		// invalid content - AVL option valid
		list.add("invalid");
		// Content should not be valid at this point
		assertFalse("Content was validated but it shouldn't", BinaryTree.validateFileContent(list));
		
		// valid content - AVL option not valid
		list.remove(0);
		list.remove("invalid");
		// Content should not be valid at this point
		assertFalse("Content was validated but it shouldn't", BinaryTree.validateFileContent(list));
	}
	
	@Test
	public void test_TreeLoadingFromFile() {
	    File valid = new File("src/tests/resources/valid.btv");
	    File invalid = new File("src/tests/resources/invalid.tv");
		
	    // test invalid file 
	    assertNull("Tree should be null", BinaryTree.loadTreeFromFile(invalid.getAbsolutePath()));
	    // test valid file
	    assertNotNull("Tree should not be null", BinaryTree.loadTreeFromFile(valid.getAbsolutePath()));
	}
}
