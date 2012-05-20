package View;

/**
 * Interface allowing the View class to be listening
 * to the MapPanel and MainFrame classes
 */
public interface MapListener {
	
	/**
	 * Signals to the MapListener that the "viewbox"
	 * has been updated, allowing it to take action
	 */
	public void viewboxUpdated();
}
