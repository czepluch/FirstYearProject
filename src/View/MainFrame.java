package View;

import javax.swing.*;
import Model.Trip;
import Model.MapLocation;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * The main GUI containing the MapPanel and the rest of the GUI
 */
public class MainFrame {
	// Store a reference to the instance of the MinAndMaxValues class
	private ViewValues v = ViewValues.getInstance();
	// Components for the GUI
	private JFrame frame;
	private JPanel cp;
	private MapPanel map;
	private SearchPanel sp;
	// Other
	private MapListener listener; // Used when window is resized
	
	/**
	 * Constructor for the MainFrame class
	 * @param lines The data to be displayed in the map of the MainFrame
	 * @param listener The MapListener
	 */
	public MainFrame(int[][][] lines, MapListener mapListener, SearchListener searchListener) {
		frame = new JFrame("Krax");
		frame.setSize(new Dimension(v.getWidth() + v.getSEARCH_PANEL_WIDTH() + v.getEXTRA_LAYOUT_WIDTH(), v.getHeight()));
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = (JPanel) frame.getContentPane();
		cp.setLayout(new BorderLayout(6, 6));
		
		map = new MapPanel(lines, mapListener);
		map.setSize(new Dimension(v.getWidth(), 1));
		cp.add(map, BorderLayout.CENTER);
		
		sp = new SearchPanel(searchListener);
		sp.setSize(new Dimension(v.getSEARCH_PANEL_WIDTH(), 10));
		sp.setBorder(BorderFactory.createRaisedBevelBorder());
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
	public void updateMap(int[][][] lines, Trip<Integer> trip, MapLocation<Integer> location) {
		map.update(lines, trip, location);
		sp.updateTrip(trip);
	}
	
	/**
	 * Updates the first list of the search panel according to the given strings
	 * @param listItems the strings to be displayed
	 */
	public void updateFirstList(String[] listItems) {
		sp.updateFirstList(listItems);
	}
	
	/**
	 * Updates the second list of the search panel according to the given strings
	 * @param listItems the strings to be displayed
	 */
	public void updateSecondList(String[] listItems) {
		sp.updateSecondList(listItems);
	}
	
	/**
	 * Updates the first text field of the search panel according to the given string
	 * @param s the string to be inserted
	 */
	public void updateFirstTextField(String s) {
		sp.updateFirstTextField(s);
	}
	
	/**
	 * Updates the second text field of the search panel according to the given string
	 * @param s the string to be inserted
	 */
	public void updateSecondTextField(String s) {
		sp.updateSecondTextField(s);
	}
	
	/**
	 * Sets whether or not found trips should be displayed as rainbows.
	 * @param rainbowOn true if the trips are to be displayed as a rainbow,
	 * 					else false
	 */
	public void setRainbow(boolean rainbowOn) {
		map.setRainbow(rainbowOn);
	}
	
	/**
	 * Method adding the needed listeners to the components
	 */
	private void addListeners() {
		frame.addComponentListener(new ComponentListener() {
			
			@Override public void componentShown(ComponentEvent arg0) { }
			@Override public void componentMoved(ComponentEvent arg0) { }
			@Override public void componentHidden(ComponentEvent arg0) { }
			
			@Override public void componentResized(ComponentEvent e) {
				Dimension newSize = e.getComponent().getSize();
				v.setHeight((int) newSize.getHeight());
				v.setWidth((int) newSize.getWidth() - (v.getSEARCH_PANEL_WIDTH() + v.getEXTRA_LAYOUT_WIDTH()));
				map.setSize(newSize);
				
				v.setRepaint(false);
				listener.viewboxUpdated();
			}
		});
	}
}
