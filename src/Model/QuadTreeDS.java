package Model;

import java.util.ArrayList;
import Cleanup.Point;
import static Global.MinAndMaxValues.*;

/**
 * Used to create the Quad Tree.
 * Contains the data structure itself as well the current filtered edges.
 */
public class QuadTreeDS implements DataStructure {
	private ArrayList<Edge> filteredEdges;
	private QuadTree[] trees;
	
	public QuadTreeDS() {
		trees = new QuadTree[NUMBER_OF_TYPES];
		for (int i = 0; i < NUMBER_OF_TYPES; i++) {
			trees[i] = new QuadTree();
		}
	}

	@Override
	public ArrayList<Edge> getFilteredEdges() {
		if (repaint) {
			filteredEdges = new ArrayList<Edge>();
			for (int i = 0; i < types; i++) {
				filteredEdges.addAll(trees[i].getFilteredEdges());
			}
		}
		return filteredEdges;
	}

	@Override
	public void addEdge(Edge e) {
		trees[e.getType() - 1].addEdge(e);
	}
	
	/**
	 * Adds a point to the tree
	 * @param p The point to be added
	 */
	public void addPoint(Point p) {
		trees[p.getType() - 1].addPoint(p);
	}
}
