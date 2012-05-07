package View;

/*
 * Interface allowing the Controller class to be listening to the View class
 */
public interface ViewListener {
	public void viewboxUpdated();
	
	public void findLocation(int nodeId);
	
	public void findDirections(int fromId, int toId);
	
	public void moveToTrip();
	
	public void moveToLocation();
	
	public void setShortSearch();
	
	public void setFastSearch();
}
