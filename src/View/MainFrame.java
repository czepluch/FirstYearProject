package View;

import javax.swing.*;
import java.awt.*;

/*
 * The main GUI containing the MapPanel and the rest of the GUI
 */
public class MainFrame {
	private JFrame frame;
	private JPanel cp;
	private MapPanel map;
	
	/*
	 * Constructor for the MainFrame class
	 * @param lines the data to be displayed in the map of the MainFrame
	 */
	public MainFrame(int[][][] lines, MapListener listener) {
		frame = new JFrame("Krax");
		frame.setSize(new Dimension(600, 600));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = (JPanel) frame.getContentPane();
		map = new MapPanel(lines, listener);
		map.setSize(new Dimension(600, 600));
		cp.add(map);
		
		frame.setVisible(true);
	}
	
	/*
	 * Updates the map in the MainFrame according to the given data
	 * (passes the method invocation on from the View to the MapPanel)
	 * @param the new data to be displayed
	 */
	public void updateMap(int[][][] lines) {
		map.update(lines);
	}
}
