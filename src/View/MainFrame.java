package View;

import javax.swing.*;

import static Global.MinAndMaxValues.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * The main GUI containing the MapPanel and the rest of the GUI
 */
public class MainFrame {
	private JFrame frame;
	private JPanel cp;
	private MapPanel map;
	private SearchPanel sp;
	private MapListener listener; // Used when window is resized
	
	/**
	 * Constructor for the MainFrame class
	 * @param lines The data to be displayed in the map of the MainFrame
	 * @param listener The MapListener
	 */
	public MainFrame(int[][][] lines, MapListener mapListener, SearchListener searchListener) {
		frame = new JFrame("Krax");
		frame.setSize(new Dimension(width + SEARCH_PANEL_WIDTH + EXTRA_LAYOUT_WIDTH, height));
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = (JPanel) frame.getContentPane();
		cp.setLayout(new BorderLayout(6, 6));
		
		map = new MapPanel(lines, mapListener);
		map.setSize(new Dimension(width, 1));
		cp.add(map, BorderLayout.CENTER);
		
		sp = new SearchPanel(searchListener);
		cp.add(sp, BorderLayout.WEST);
		
		this.listener = mapListener;
		addListeners();
		
		frame.setVisible(true);
	}
	
	/**
	 * Updates the map in the MainFrame according to the given data
	 * (passes the method invocation on from the View to the MapPanel)
	 * @param the new data to be displayed
	 */
	public void updateMap(int[][][] lines) {
		map.update(lines);
	}
	
	/**
	 * Method adding the needed listeners to the components
	 */
	public void addListeners() {
		frame.addComponentListener(new ComponentListener() {
			
			@Override public void componentShown(ComponentEvent arg0) { }
			@Override public void componentMoved(ComponentEvent arg0) { }
			@Override public void componentHidden(ComponentEvent arg0) { }
			
			@Override public void componentResized(ComponentEvent e) {
				Dimension newSize = e.getComponent().getSize();
				height = (int) newSize.getHeight();
				width = (int) newSize.getWidth() - (SEARCH_PANEL_WIDTH + EXTRA_LAYOUT_WIDTH);
				map.setSize(newSize);
				
				repaint = false;
				listener.viewboxUpdated();
			}
		});
	}
}
