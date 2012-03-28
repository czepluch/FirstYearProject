package View;

import javax.swing.*;

import Global.MinAndMaxValues;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/*
 * The main GUI containing the MapPanel and the rest of the GUI
 */
public class MainFrame {
	private JFrame frame;
	private JPanel cp;
	private MapPanel map;
	private MapListener listener; // Used when window is resized
	
	/*
	 * Constructor for the MainFrame class
	 * @param lines the data to be displayed in the map of the MainFrame
	 */
	public MainFrame(int[][][] lines, MapListener listener) {
		frame = new JFrame("Krax");
		frame.setSize(new Dimension(600, 600));
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = (JPanel) frame.getContentPane();
		map = new MapPanel(lines, listener);
		map.setSize(new Dimension(600, 600));
		cp.add(map);
		
		this.listener = listener;
		addListeners();
		
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
	
	/*
	 * Method adding the needed listeners to the components
	 */
	public void addListeners() {
		frame.addComponentListener(new ComponentListener() {
			
			@Override public void componentShown(ComponentEvent arg0) { }
			@Override public void componentMoved(ComponentEvent arg0) { }
			@Override public void componentHidden(ComponentEvent arg0) { }
			
			@Override public void componentResized(ComponentEvent e) {
				Dimension newSize = e.getComponent().getSize();
				MinAndMaxValues.height = (int) newSize.getHeight();
				MinAndMaxValues.width = (int) newSize.getWidth();
				System.out.println(newSize.getHeight() + " " + newSize.getWidth());
				map.setSize(newSize);
				
				// MinAndMaxValues.repaint = false;
				listener.viewboxUpdated();
			}
		});
	}
}
