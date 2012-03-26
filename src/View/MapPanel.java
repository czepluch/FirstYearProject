package View;
import javax.swing.*;
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
		doLayout();
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < lines.length; i++) {
			switch(i) {
				// Highways, red
				case 0: g2.setColor(Color.red);
						g2.setStroke(new BasicStroke(5)); break;
				// Large streets, yellow
				case 1: g2.setColor(Color.yellow);
						g2.setStroke(new BasicStroke(4)); break;
				// City streets, blue
				case 2: g2.setColor(Color.blue);
						g2.setStroke(new BasicStroke(3)); break;
				// Small roads, black
				case 3: g2.setColor(Color.black);
						g2.setStroke(new BasicStroke(2)); break;
				// Streets without vehicles, green
				default:g2.setColor(Color.green);
						g2.setStroke(new BasicStroke(1)); break;
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
				viewboxUpdated();
			}
		});
	}
}
