package QuadTree;

import java.util.ArrayList;
import static Global.MinAndMaxValues.*;

import Model.DataStructure;
import Model.Edge;

public class QuadTreeDS implements DataStructure {
	private ArrayList<Edge> filteredEdges;
	private final int NUMBER_OF_TYPES = 6;
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
		switch (e.getType()) {
			case 1:	trees[0].addEdge(e); break;
			case 2:	trees[1].addEdge(e); break;
			case 3:	trees[2].addEdge(e); break;
			case 4:	trees[3].addEdge(e); break;
			case 5:	trees[4].addEdge(e); break;
			case 6:	trees[5].addEdge(e); break;
			default: break;
		}
	}
}
