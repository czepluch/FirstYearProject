package View;

/*
 * Interface allowing the Controller class to be listening to the View class
 */
public interface ViewListener {
	
	/**
	 * Signals to the ViewListener that the "viewbox"
	 * has been updated
	 */
	public void viewboxUpdated();
	
	/**
	 * Retrieves the MapLocation<Integer> object corresponding
	 * to the given node id
	 * @param nodeId	The node id corresponding to the MapLocation
	 * 					to be found
	 */
	public void findLocation(int nodeId);
	
	/**
	 * Retrieves the Trip<Integer> object corresponding
	 * to the given node id
	 * @param nodeId	The node id corresponding to the Trip
	 * 					to be found
	 */
	public void findDirections(int fromId, int toId);
	
	/**
	 * Retrieves the current Trip<Double> (UTM32 coordinates)
	 * and move the current "viewbox" to the found Trip<Double>
	 */
	public void moveToTrip();
	
	/**
	 * Retrieves the current MapLocation<Double> (UTM32 coordinates)
	 * and move the current "viewbox" to the found MapLocation<Double>
	 */
	public void moveToLocation();
	
	/**
	 * Signals to the ViewListener that the following path searches
	 * are to be searches for the shortest trip
	 */
	public void setShortSearch();
	
	/**
	 * Signals to the ViewListener that the following path searches
	 * are to be searches for the fastest trip
	 */
	public void setFastSearch();
}
