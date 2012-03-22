package View;
/*
 * The View/GUI of the application
 * Contains the controller as a listener, which is notified, when updates in the GUI in
 * need of accessing the stored data are needed
 */
public class View implements MapListener {
	private MainFrame mf;
	private ViewListener listener;
	
	/*
	 * Constructor for the View class
	 */
	public View(ViewListener listener) {
		mf = new MainFrame();
		this.listener = listener;
	}
	
	/*
	 * Is invoked by the MapPanel, when the viewbox is changed.
	 * Invokes the viewboxUpdated on the ViewListener (the Controller)
	 */
	public void viewboxUpdated() {
		listener.viewboxUpdated();
	}
}
