package DataStructures;

import java.util.ArrayList;
import static Global.MinAndMaxValues.*;
import Model.Point;

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
	
	public void addPoint(Point p) {
		trees[p.getType() - 1].addPoint(p);
	}
	
//	public void printTreeHeights() {
//		System.out.println("Height of trees:");
//		for (int i = 0; i < NUMBER_OF_TYPES; i++) {
//			System.out.println((i + 1) + ":\t" + trees[i].getTreeHeight());
//		}
//	}
}
