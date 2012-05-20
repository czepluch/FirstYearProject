package Model;

/**
 * Interface for classes capable of finding
 * fastest and shortest paths between two given
 * points in a given graph
 */
public interface PathFinder {

	/**
     * Call this method to compute the shortest path between two vertices
	 * @param source The starting point of the path
	 * @param target The end point of the path
     */
	public Trip<Double> run(String source, String target, TripCriteria type, Graph g);
}
