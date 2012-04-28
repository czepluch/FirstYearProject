package Model;

/**
 * The part of the application taking care of data handling
 * Includes classes for reading, storing and filtering data
 */
public class Model {
	private DataStructure edges;
	
	/**
	 * Constructor of the model class
	 * Uses the readXML-method of the XMLReader class
	 */
	public Model() throws RuntimeException {
		try {
		edges = new QuadTreeDS();
		XMLReader.readXML("short.xml", edges);
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
	public Trip getTrip() {
		return null;
	}
	
	/**
	 * Gets the current MapLocation
	 * @return the current MapLocation
	 */
	public MapLocation getLocation() {
		return null;
	}
	
	/*
	 * Updates the stored trip
	 */
	public void updateTrip(int fromId, int toId) {
		System.out.println("Updating the trip:");
		System.out.println("\tFrom:\t" + fromId);
		System.out.println("\tTo:\t" + toId);
	}
	
	/**
	 * Updates the stored MapLocation
	 * @param point the id of the node to be stored
	 */
	public void updateLocation(int nodeId) {
		System.out.println("Updating the location:");
		System.out.println("\tNode:\t" + nodeId);
	}
}
