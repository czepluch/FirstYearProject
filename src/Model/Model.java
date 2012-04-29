package Model;

import static Global.MinAndMaxValues.*;
import static Model.Graph.G;

/**
 * The part of the application taking care of data handling
 * Includes classes for reading, storing and filtering data
 */
public class Model {
	private DataStructure edges;
	private Trip<Double> trip;
	private MapLocation<Double> location;
	
	/**
	 * Constructor of the model class
	 * Uses the readXML-method of the XMLReader class
	 */
	public Model() throws RuntimeException {
		try {
		edges = new QuadTreeDS();
		XMLReader.readXML("short.xml", edges);
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
		System.out.println("Updating the trip:");	// Testing
		System.out.println("\tFrom:\t" + fromId);	// Testing
		System.out.println("\tTo:\t" + toId);		// Testing
		// Update the trip
		trip = Dijkstra.run(fromId + "", toId + "");
		// Set the location to null
		location = null;
	}
	
	/**
	 * Updates the stored MapLocation
	 * @param point the id of the node to be stored
	 */
	public void updateLocation(int nodeId) {
		System.out.println("Updating the location:");	// Testing
		System.out.println("\tNode:\t" + nodeId);		// Testing
		// Update the location
		double[] coords = G.getNodeCoordinates(nodeId);
		location = new MapLocation(coords[0], coords[1]);
		System.out.println("x:\t" + coords[0]);			// Testing
		System.out.println("y:\t" + coords[1]);			// Testing
		// Set the trip to null
		trip = null;
	}
}
