package ui.graphical.menuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * ActionListener that is used for all MenuItem objects
 * @author Benny Lach
 *
 */
public class MenuItemActionListener implements ActionListener {
	private MenuItemType type;
	private MenuItemDelegate delegate;
	
	/**
	 * Constructor with a given MenuItemType and a delegate that is called
	 * @param type The type of the action
	 * @param delegate The delegate to notify when the action was performed
	 */
	public MenuItemActionListener(MenuItemType type, MenuItemDelegate delegate) {
		this.delegate = delegate;
		this.type = type;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		delegate.performAction(type);
	}
}
