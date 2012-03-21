package View;
import javax.swing.*;
import java.awt.*;

/*
 * The panel on which the map or sections of the map is drawn.
 * Extends the JPanel
 */
public class MapPanel extends JPanel {
	private int[][][] lines;
	
	public MapPanel(int[][][] lines) {
		if (lines.length != 3) throw new IllegalArgumentException();
		this.lines = lines;
		this.setSize(new Dimension(600, 600));
	}
	
	public void paint(Graphics g) {
		doLayout();
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < lines.length; i++) {
			switch(i) {
				case 0: g2.setColor(Color.black);
						g2.setStroke(new BasicStroke(5)); break;
				case 1: g2.setColor(Color.red);
						g2.setStroke(new BasicStroke(3)); break;
				default:g2.setColor(Color.green);
						g2.setStroke(new BasicStroke(2)); break;
			}
			for (int j = 0; j < lines[i].length; j++) {
				g2.drawLine(lines[i][j][0], lines[i][j][1], lines[i][j][2], lines[i][j][3]);
			}
		}
	}
}
