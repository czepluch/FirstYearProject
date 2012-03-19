package View;
/*
 * The View/GUI of the application
 * Contains the controller as a listener, which is notified, when updates in the GUI in
 * need of accessing the stored data are needed
 */
public class View {
	private MainFrame mf;
	private ViewListener listener;
	
	public View(ViewListener listener) {
		mf = new MainFrame();
		this.listener = listener;
	}
}
