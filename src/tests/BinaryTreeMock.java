package tests;

import java.util.List;
import logic.*;

public class BinaryTreeMock extends BinaryTree {
	
	public BinaryTreeMock(Boolean useAvl) {
		super(useAvl);
		// TODO Auto-generated constructor stub
	}
	
	public BinaryTreeMock(List<String> content) throws Exception {
		super(content);
	}
	
	/**
	 * Method to get the current avl option value
	 * @return
	 */
	public Boolean avlActive() {
		return useAvl;
	}
}
