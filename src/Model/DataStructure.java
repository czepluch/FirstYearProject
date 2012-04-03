package Model;

import java.util.ArrayList;

public interface DataStructure {

	/**
	 * Gets the needed edges filtered according to the values stored
	 * in the MinAndMaxValues class in the Global package
	 */
	public ArrayList<Edge> getFilteredEdges();
	
	/**
	 * Adds an edge to be stored in the data structure
	 * @param Edge The edge to be stored
	 */
	public void addEdge(Edge e);
}
