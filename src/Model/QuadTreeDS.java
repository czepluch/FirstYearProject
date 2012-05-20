package Model;

import java.util.ArrayList;
import View.ViewValues;

/**
 * Data structure using four QuadTrees to store different types of edges
 * Contains the data structure itself as well the current filtered edges.
 */
public class QuadTreeDS implements DataStructure {
	private ArrayList<KEdge> filteredEdges;
	private QuadTree[] trees;
	private ArrayList<KEdge> feries;
	// Store a reference to the instance of the MinAndMaxValues class
	private ViewValues v = ViewValues.getInstance();
	
	/**
	 * Constructor of the QuadTreeDS class
	 * Creates the needed number of QuadTree instances
	 * and stores them
	 */
	public QuadTreeDS() {
		trees = new QuadTree[v.getNUMBER_OF_TYPES()];
		for (int i = 0; i < v.getNUMBER_OF_TYPES(); i++) {
			trees[i] = new QuadTree();
		}
		feries = new ArrayList<KEdge>();
	}

	@Override
	public ArrayList<KEdge> getFilteredEdges() {
		if (v.isRepaint()) {
			filteredEdges = new ArrayList<KEdge>();
			for (int i = 0; i < v.getTypes(); i++) {
				filteredEdges.addAll(trees[i].getFilteredEdges());
			}
		}
		// Add the feries
		filteredEdges.addAll(feries);
		return filteredEdges;
	}

	@Override
	public void addEdge(KEdge e) {
		if (e.getType() == 5) feries.add(e);
		else trees[e.getType() - 1].addEdge(e);
	}
}
