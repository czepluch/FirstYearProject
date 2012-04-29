package Model;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a trip from one location to another
 */
public class Trip<T> {
	private int time;
	private double distance;
	private ArrayList<TripEdge<T>> edges;
	
	/*
	 * Constructor
	 */
	public Trip() {
		edges = new ArrayList<TripEdge<T>>();
	}
	
	/*
	 * Add an edge to the trip
	 * @param edge TripEdge to be added
	 */
	public void addEdge(TripEdge<T> edge) {
		edges.add(edge);
	}
	
	// Getter methods
	// ______________________________________________________________
	
	/*
	 * Gets the stored time
	 * @return the stored time
	 */
	public int getTime() {
		return time;
	}
	
	/*
	 * Gets the stored distance
	 * @return the stored distance
	 */
	public double getDistance() {
		return distance;
	}
	
	/*
	 * Gets the list of edges stored
	 * @return the list of edges stored
	 */
	public List<TripEdge<T>> getEdges() {
		return edges;
	}
	
	/*
	 * Helper method
	 * Computes the total time and distance according to the
	 * times and distances of the stored edges
	 */
	public void computeTimeAndDistance() {
		double distance = 0;
		int time = 0;
		for (TripEdge<T> e : edges) {
			distance += e.getDistance(); 
			time += e.getTime();
		}
	}
}
