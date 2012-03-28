package View;
import javax.swing.*;
import Global.MinAndMaxValues;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/*
 * The panel on which the map or sections of the map is drawn.
 * Extends the JPanel
 */
public class MapPanel extends JPanel {
	private int[][][] lines;
	private MapListener listener;
	
	/*
	 * Constructor
	 * @param lines the data of the lines to be drawn
	 */
	public MapPanel(int[][][] lines, MapListener listener) {
		this.lines = lines;
		this.listener = listener;
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
		for (int i = 0; i < lines.length; i++) {
			switch(i) {
				// Streets without vehicles, green
				case 0:	g2.setColor(Color.green);
						g2.setStroke(new BasicStroke(1 * MinAndMaxValues.lineWidth)); break;
				// Small roads, black
				case 1: g2.setColor(Color.black);
						g2.setStroke(new BasicStroke(2 * MinAndMaxValues.lineWidth)); break;
				// City streets, blue
				case 2: g2.setColor(Color.blue);
						g2.setStroke(new BasicStroke(3 * MinAndMaxValues.lineWidth)); break;
				// Large streets, yellow
				case 3: g2.setColor(Color.yellow);
						g2.setStroke(new BasicStroke(4 * MinAndMaxValues.lineWidth)); break;
				// Highways, red
				case 4: g2.setColor(Color.red);
						g2.setStroke(new BasicStroke(5 * MinAndMaxValues.lineWidth)); break;
				// Unknown types are not drawn
				default:	break;
			}
			for (int j = 0; j < lines[i].length; j++) {
				g2.drawLine(lines[i][j][0], lines[i][j][1], lines[i][j][2], lines[i][j][3]);
			}
		}
	}
	
	/*
	 * Notifies the listener, that the "viewbox" has been changed
	 * (Correct parameters are missing)
	 */
	public void viewboxUpdated () {
		listener.viewboxUpdated();
	}
	
	/*
	 * Updates the map according the the given data and repaints.
	 * @param lines the new data to be displayed
	 */
	public void update(int[][][] lines) {
		this.lines = lines;
		repaint();
	}
	
	//Circumstantial methods:
	private void addListeners(){
		addMouseWheelListener(new MouseWheelListener(){
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				boolean upd = MinAndMaxValues.valuesChanged(mwe.getX(), mwe.getY(), mwe.getWheelRotation());
				if (upd) viewboxUpdated();
			}
		});
	}
}
