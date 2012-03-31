package DataStructures;

import java.util.ArrayList;
import static Global.MinAndMaxValues.*;

import Model.DataStructure;
import Model.Edge;

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
}
