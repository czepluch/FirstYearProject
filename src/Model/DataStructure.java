package Model;

import java.util.HashSet;

public interface DataStructure {

	/*
	 * Gets the needed edges filtered according to the values stored
	 * in the MinAndMaxValues class in the Global package
	 */
	public HashSet<Edge> getFilteredEdges();
	
	/*
	 * Adds an edge to be stored in the data structure
	 */
	public void addEdge();
}
