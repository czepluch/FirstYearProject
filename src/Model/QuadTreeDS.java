package Model;

import java.util.ArrayList;
import Model.Point;
import static View.MinAndMaxValues.*;

/**
 * Used to create the Quad Tree.
 * Contains the data structure itself as well the current filtered edges.
 */
public class QuadTreeDS implements DataStructure {
	private ArrayList<KEdge> filteredEdges;
	private QuadTree[] trees;
	private ArrayList<KEdge> feries;
	
	public QuadTreeDS() {
		trees = new QuadTree[NUMBER_OF_TYPES];
		for (int i = 0; i < NUMBER_OF_TYPES; i++) {
			trees[i] = new QuadTree();
		}
		feries = new ArrayList<KEdge>();
	}

	@Override
	public ArrayList<KEdge> getFilteredEdges() {
		if (repaint) {
			filteredEdges = new ArrayList<KEdge>();
			for (int i = 0; i < types; i++) {
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
	
	/**
	 * Adds a point to the tree
	 * @param p The point to be added
	 */
	public void addPoint(Point p) {
		trees[p.getType() - 1].addPoint(p);
	}
}
