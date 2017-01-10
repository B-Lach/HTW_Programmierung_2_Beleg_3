package ui.graphical.keyboard;

/**
 * Delegate interface to handle valid keyboard shortcuts
 * @author Benny Lach
 *
 */
public interface KeyboardDelegate {
	/**
	 * Method to notify the delegate about a valid keyboard shortcut
	 * @param action The action to perform
	 */
	public void performKeyboardAction(KeyboardEnum action);
}
