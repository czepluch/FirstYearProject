package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Model.Interval;
import Model.Interval2D;
import Model.KEdge;
import Model.QuadTree;

/*
 * This class tests whether the QuadTree is correctly constructed,
 * mainly by performing range searches (query2D() method) with different input parameters.
 */
public class QuadTreeTest {
	private QuadTree qt = new QuadTree();
	
	@Test
	public void testQuery() {
		// edges
		KEdge e1 = new KEdge(0, 0, 4, 3, 5, 30, 8);
		KEdge e2 = new KEdge(0, 0, 6, 8, 10, 30, 8);
		KEdge e3 = new KEdge(4, 2, 6, 8, 6.3245532, 30, 8);
		KEdge e4 = new KEdge(4, 3, 4, 2, 1, 30, 8);
		
		// filtered edges go here
		ArrayList<KEdge> filteredEdges = new ArrayList<KEdge>();
		
		// insert edges and their endpoint into the QuadTree
		qt.insert(0, 0, e1);
		qt.insert(0, 0, e2);
		qt.insert(4, 2, e3);
		qt.insert(4, 2, e4);
		qt.insert(4, 3, e1);
		qt.insert(4, 3, e4);
		qt.insert(6, 8, e2);
		qt.insert(6, 8, e3);
		
		/*
		 * range search one.
		 * should find two edges: e1 and e2.
		 */
		Interval xInt1 = new Interval(0,0), yInt1 = new Interval(0,0);
		Interval2D rect1 = new Interval2D(xInt1, yInt1);
		qt.query2D(rect1, filteredEdges);
		assertTrue(filteredEdges.size() == 2);
		assertTrue(filteredEdges.contains(e1) && filteredEdges.contains(e2));
		
		// clear filtered edges and check they are actually cleared
		filteredEdges.clear();
		assertTrue(filteredEdges.isEmpty());
		
		/*
		 * range search two.
		 * should find three edges: e1, e3, and e4 (appears twice, once per endpoint)
		 */
		Interval xInt2 = new Interval(3,8), yInt2 = new Interval(0,5);
		Interval2D rect2 = new Interval2D(xInt2, yInt2);
		qt.query2D(rect2, filteredEdges);
		assertTrue(filteredEdges.size() == 4);	// size 4 because e4 appears twice
		assertTrue(filteredEdges.contains(e1) && filteredEdges.contains(e3) && filteredEdges.contains(e4));
		
		// clear filtered edges and check they are actually cleared
		filteredEdges.clear();
		assertTrue(filteredEdges.isEmpty());
		
		/*
		 * range search three.
		 * should find no edges.
		 */
		Interval xInt3 = new Interval(3,7), yInt3 = new Interval(5,7);
		Interval2D rect3 = new Interval2D(xInt3, yInt3);
		qt.query2D(rect3, filteredEdges);
		assertTrue(filteredEdges.isEmpty());
		
		// clear filtered edges and check they are actually cleared
		filteredEdges.clear();
		assertTrue(filteredEdges.isEmpty());
		
		/*
		 * range search four:
		 * should find all four edges, with each edge appearing twice (once per endpoint)
		 */
		Interval xInt4 = new Interval(0,8), yInt4 = new Interval(0,8);
		Interval2D rect4 = new Interval2D(xInt4, yInt4);
		qt.query2D(rect4, filteredEdges);
		assertTrue(filteredEdges.size() == 8);	// size 8 because each edge appears twice
		assertTrue(filteredEdges.contains(e1) && filteredEdges.contains(e2) && 
				   filteredEdges.contains(e3) && filteredEdges.contains(e4));
	}
}
