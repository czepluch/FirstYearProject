package Model;

import java.util.ArrayList;

public interface DataStructure {

	/**
	 * Gets the needed edges filtered according to the values stored
	 * in the MinAndMaxValues class in the Global package
	 */
	public ArrayList<KEdge> getFilteredEdges();
	
	/**
	 * Adds an edge to be stored in the data structure
	 * @param KEdge The edge to be stored
	 */
	public void addEdge(KEdge e);
}
