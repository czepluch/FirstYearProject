package Model;

import java.util.ArrayList;

import Global.MinAndMaxValues;

public class ArrayListDS implements DataStructure {
	private ArrayList<Edge> edges;
	// Used to avoid filtering the same data again
	private ArrayList<Edge> currentFilteredEdges;

	public ArrayListDS() {
		edges = new ArrayList<Edge>();
	}
	
	@Override
	public ArrayList<Edge> getFilteredEdges() {
		// Filter only if needed
		if (MinAndMaxValues.repaint) currentFilteredEdges = DataFilter.filterData(edges);
		return currentFilteredEdges;
	}

	@Override
	public void addEdge(Edge e) {
		edges.add(e);
	}

}
