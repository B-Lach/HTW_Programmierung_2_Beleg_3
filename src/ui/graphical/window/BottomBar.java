package ui.graphical.window;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class BottomBar extends JPanel {
	private static final long serialVersionUID = -6074525239267172224L;
	
	private JLabel statusLabel;
	
	public BottomBar(JFrame frame) {
		super();
		
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setPreferredSize(new Dimension(frame.getWidth(), 16));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		statusLabel = new JLabel("");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		add(statusLabel);
	}
	
	public void updateStatus(String status) {
		statusLabel.setText(status);
	}
}
