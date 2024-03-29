package View;
import javax.swing.*;
import Model.Trip;
import Model.TripEdge;
import Model.MapLocation;
import java.util.List;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * The panel on which the map or sections of the map is drawn.
 * Extends the JPanel class
 */
public class MapPanel extends JPanel {
	private int[][][] lines;
	private MapListener listener;
	private Trip<Integer> trip;
	private MapLocation<Integer> location;
	// Variables used when dragging
	private int lastEndX;
	private int lastEndY;
	private int endX;
	private int endY;
	// Variable for the Rainbow Road easter egg
	private boolean rainbow;
	
	/**
	 * Constructor
	 * @param lines The edges to be drawn
	 * @param listener The MapListener
	 */
	public MapPanel(int[][][] lines, MapListener listener) {
		this.lines = lines;
		this.listener = listener;
		trip = null;
		location = null;
		rainbow = false;
		addListeners();
	}
	
	/*
	 * Paints lines according to the data stored in the lines field
	 */
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		// doLayout();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < lines.length; i++) {
			switch(i) {
				// Feries
				case 0:	g2.setColor(Color.pink);
				g2.setStroke(new BasicStroke((3),
										 			BasicStroke.CAP_ROUND,
										 			BasicStroke.JOIN_ROUND)); break;
			
				// Other roads
				case 1:	g2.setColor(Color.green);
						g2.setStroke(new BasicStroke((1),
													 BasicStroke.CAP_ROUND,
													 BasicStroke.JOIN_ROUND)); break;
				// Secondary roads
				case 2: g2.setColor(Color.black);
						g2.setStroke(new BasicStroke((2),
								 					 BasicStroke.CAP_ROUND,
								 					 BasicStroke.JOIN_ROUND)); break;
				// Primary roads
				case 3: g2.setColor(Color.blue);
						g2.setStroke(new BasicStroke((3),
								 					 BasicStroke.CAP_ROUND,
								 					 BasicStroke.JOIN_ROUND)); break;
				// Highways
				case 4: g2.setColor(Color.red);
						g2.setStroke(new BasicStroke((5),
								 					 BasicStroke.CAP_ROUND,
								 					 BasicStroke.JOIN_ROUND)); break;
				// Unknown types are not drawn
				default:	break;
			}
			for (int j = 0; j < lines[i].length; j++) {
				g2.drawLine(lines[i][j][0], lines[i][j][1], lines[i][j][2], lines[i][j][3]);
			}
		}
		if (trip != null) {
			g2.setStroke(new BasicStroke((4),
					 					 BasicStroke.CAP_ROUND,
					 					 BasicStroke.JOIN_ROUND));
			List<TripEdge<Integer>> edges = trip.getEdges();
			if (rainbow) {
				for (TripEdge<Integer> e : edges) {
					g2.setColor(RainbowGenerator.nextColor());
					g2.drawLine(e.getFromX(), e.getFromY(), e.getToX(), e.getToY());
				} 
			} else {
				g2.setColor(Color.magenta);
				for (TripEdge<Integer> e : edges) {
					g2.drawLine(e.getFromX(), e.getFromY(), e.getToX(), e.getToY());
				} 
			}
		}
		if (location != null) {
			g2.setColor(Color.magenta);
			int cwh = 5;
			int x = location.getX() - cwh;
			int y = location.getY() - cwh;
			g2.drawOval(x, y, cwh, cwh);
		}
	}
	
	/**
	 * Notifies the listener that the viewbox has changed
	 * (Correct parameters are missing)
	 */
	public void viewboxUpdated() {
		listener.viewboxUpdated();
	}
	
	/**
	 * Updates the map according the the given data and repaints.
	 * @param lines the new data to be displayed
	 * @param trip	the trip of which info is to be displayed
	 * 				null if no trip is to be displayed
	 */
	public void update(int[][][] lines, Trip<Integer> trip, MapLocation<Integer> location) {
		this.lines = lines;
		this.trip = trip;
		this.location = location;
		repaint();
	}
	
	/**
	 * Sets whether or not found trips should be displayed as rainbows.
	 * @param rainbowOn true if the trips are to be displayed as a rainbow,
	 * 					else false
	 */
	public void setRainbow(boolean rainbowOn) {
		rainbow = rainbowOn;
	}
	
	/**
	 * Add the listeners to the components
	 */
	private void addListeners(){
		addMouseWheelListener(new MouseWheelListener(){
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				ZoomHandler.valuesChanged(mwe.getX(), mwe.getY(), mwe.getWheelRotation());
				viewboxUpdated();
			}
		});
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) { }

			@Override
			public void mouseEntered(MouseEvent arg0) { }

			@Override
			public void mouseExited(MouseEvent arg0) { }

			@Override
			public void mousePressed(MouseEvent e) {
				lastEndX = (int) e.getPoint().getX();
				lastEndY = (int) e.getPoint().getY();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) { }
			
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) { }
			
			@Override
			public void mouseDragged(MouseEvent e) {
				endX = (int) e.getPoint().getX();
				endY = (int) e.getPoint().getY();
				int relX = endX - lastEndX;
				int relY = endY - lastEndY;
				lastEndX = endX;
				lastEndY = endY;
				DragHandler.valuesChanged(relX, relY);
				viewboxUpdated();
			}
		});
	}
}
