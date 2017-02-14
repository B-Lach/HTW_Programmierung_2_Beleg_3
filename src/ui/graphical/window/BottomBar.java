package ui.graphical.window;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * Class that represents a bar on the bottom of Window. Used to present information to the user
 * @author Benny Lach
 *
 */
public class BottomBar extends JPanel {
	private static final long serialVersionUID = -6074525239267172224L;
	// The label of the bar
	private JLabel statusLabel;
	
	/**
	 * Constructor to generate a new instance 
	 * @param frame The frame used to calculate the width of the bar
	 */
	public BottomBar(JFrame frame) {
		super();
		
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setPreferredSize(new Dimension(frame.getWidth(), 16));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		statusLabel = new JLabel("");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		add(statusLabel);
	}
	
	/**
	 * Method to present a new Message 
	 * @param status The message to show
	 */
	public void updateStatus(String status) {
		statusLabel.setText(status);
	}
}
