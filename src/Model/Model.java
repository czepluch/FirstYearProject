package Model;

import static Model.Graph.G;
import static Model.TripCriteria.*;
import static View.MinAndMaxValues.*;

/**
 * The part of the application taking care of data handling
 * Includes classes for reading, storing and filtering data
 */
public class Model {
	private DataStructure edges;
	private PathFinder pathFinder = new Dijkstra();
	private Trip<Double> trip;
	private MapLocation<Double> location;
	private TripCriteria fastOrShort = FAST;
	
	/**
	 * Constructor of the model class
	 * Uses the readXML-method of the XMLReader class
	 */
	public Model() throws RuntimeException {
		try {
		edges = new QuadTreeDS();
		RoadsDataReader.readXML("data.roads", edges);
		Graph.wakeUp();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the data stored in the model filtered according to the given data
	 * @return The filtered edges
	 */
	public int[][][] getFilteredEdges() {
		return FormatConverter.convertData(edges.getFilteredEdges());
	}
	
	/**
	 * Returns the current trip
	 * @return the current trip
	 */
	public Trip<Integer> getTrip() {
		if (trip != null) return FormatConverter.createTrip(trip, maxSpeed);
		return null;
	}
	
	/**
	 * Gets the current MapLocation
	 * @return the current MapLocation
	 */
	public MapLocation<Integer> getLocation() {
		if (location != null) return FormatConverter.createMapLocation(location);
		return null;
	}
	
	/*
	 * Updates the stored trip
	 */
	public void updateTrip(int fromId, int toId) {
		// Update the trip
		trip = pathFinder.run(fromId + "", toId + "", fastOrShort);
		// Set the location to null
		location = null;
	}
	
	/**
	 * Updates the stored MapLocation
	 * @param point the id of the node to be stored
	 */
	public void updateLocation(int nodeId) {
		// Update the location
		double[] coords = G.getNodeCoordinates(nodeId);
		location = new MapLocation<Double>(coords[0], coords[1]);
		// Set the trip to null
		trip = null;
	}
	
	/**
	 * Gets the stored trip containing UTM coordinates
	 * @return the stored trip containing UTM coordinates
	 */
	public Trip<Double> getUTMTrip() {
		return trip;
	}
	
	/**
	 * Gets the stored location containing UTM coordinates
	 * @return the stored location containing UTM coordinates
	 */
	public MapLocation<Double> getUTMLocation() {
		return location;
	}
	
	/**
	 * Signals to the Model to search for fastest path
	 */
	public void setFastSearch() {
		fastOrShort = FAST;
	}
	
	/**
	 * Signals to the Model to search for shortest path
	 */
	public void setShortSearch() {
		fastOrShort = SHORT;
	}
}
