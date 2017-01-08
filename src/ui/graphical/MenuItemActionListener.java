package ui.graphical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemActionListener implements ActionListener {
	private MenuItemType type;
	private MenuItemDelegate delegate;
	
	public MenuItemActionListener(MenuItemType type, MenuItemDelegate delegate) {
		this.delegate = delegate;
		this.type = type;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		delegate.performAction(type);
	}
}
