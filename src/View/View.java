package View;

/**
 * The View/GUI of the application
 * Contains the controller as a listener, which is notified, when updates in the GUI in
 * need of accessing the stored data are needed
 */
public class View implements MapListener {
	private MainFrame mf;
	private ViewListener listener;
	
	/**
	 * Constructor for the View class
	 * @param listener The ViewListener of the view
	 * @param lines The edges to be drawn
	 */
	public View(ViewListener listener, int[][][] lines) {
		mf = new MainFrame(lines, this);
		this.listener = listener;
	}
	
	/**
	 * Is invoked by the MapPanel, when the viewbox is changed.
	 * Invokes the viewboxUpdated on the ViewListener (the Controller)
	 */
	public void viewboxUpdated() {
		listener.viewboxUpdated();
	}
	
	/**
	 * Updates the map in the view according to the given data
	 */
	public void updateView(int[][][] lines) {
		mf.updateMap(lines);
	}
}
