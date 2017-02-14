package ui.graphical.tree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
/**
 * Class that represents a line object to visually connect two nodes
 * @author Benny Lach
 *
 */
public class Line  extends JPanel {
	private static final long serialVersionUID = -2402740177438876998L;
	
	private int beginX, beginY, endX, endY;
	/**
	 * Constructor to create an object based on given coordinates
	 * @param beginX The start x coordinate of the line
	 * @param beginY The start y coordinate of the line
	 * @param endX The end x coordinate of the line
	 * @param endY The end y coordinate of the line
	 */
	public Line(int beginX, int beginY, int endX, int endY) {
		this.beginX = beginX;
		this.endX = endX;
		this.beginY = beginY;
		this.endY = endY;
		
		setSize();
	}
	
	/**
	 * Method to calculate the size based on the given coordinates
	 */
	private void setSize() {
		int width = beginX > endX ? beginX - endX : endX - beginX;
		int height = beginY > endY ? beginY - endY : endY - beginY;
		
		setSize(width, height);				
	}
	

	/**
	 * Overridden paintComponent(Graphics g) method to do custom painting
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// calculate begin and end positions of the line
		int lineBeginY = beginY < endY ? 0 : (int)g.getClipBounds().getHeight();
		int lineEndY = endY < beginY ? 0 : (int)g.getClipBounds().getHeight();
		
		if (beginX > endX && endY > beginY) { 
			lineBeginY = g.getClipBounds().height;
			lineEndY = 0;
		}
		// create the line
		Line2D.Double line = new Line2D.Double(0, lineBeginY, g.getClipBounds().getWidth(), lineEndY);
		
		Graphics2D g2d = (Graphics2D) g;
		// antialiasing the line
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// draw the line on the context
		g2d.setColor(Color.black);
		g2d.draw(line);
    }
}
