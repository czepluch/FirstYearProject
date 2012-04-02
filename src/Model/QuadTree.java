package Model;

import java.util.ArrayList;

import Cleanup.Point;
import static Global.MinAndMaxValues.*;


// http://algs4.cs.princeton.edu/92search/QuadTree.java.html

/*************************************************************************
 *  Compilation:  javac QuadTree.java
 *  Execution:    java QuadTree M N
 *
 *  Quad tree.
 * 
 *************************************************************************/

public class QuadTree {
    private Node root;

    // helper node data type
    private class Node {
        double x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees
        Edge edge;           // associated data

        Node(double x, double y, Edge edge) {
            this.x = x;
            this.y = y;
            this.edge = edge;
        }
    }


  /***********************************************************************
    *  Insert (x, y) into appropriate quadrant
    ***********************************************************************/
    public void insert(double x, double y, Edge edge) {
        root = insert(root, x, y, edge);
    }

    private Node insert(Node h, double x, double y, Edge edge) {
        if (h == null) return new Node(x, y, edge);
        //// if (eq(x, h.x) && eq(y, h.y)) h.edge = edge;  // duplicate
        else if ( less(x, h.x) &&  less(y, h.y)) h.SW = insert(h.SW, x, y, edge);
        else if ( less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, edge);
        else if (!less(x, h.x) &&  less(y, h.y)) h.SE = insert(h.SE, x, y, edge);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, edge);
        return h;
    }


  /***********************************************************************
    *  Range search.
    ***********************************************************************/

    public void query2D(Interval2D rect, ArrayList<Edge> edges) {
        query2D(root, rect, edges);
    }

    private void query2D(Node h, Interval2D rect, ArrayList<Edge> edges) {
        if (h == null) return;
        double xmin = rect.intervalX.low;
        double ymin = rect.intervalY.low;
        double xmax = rect.intervalX.high;
        double ymax = rect.intervalY.high;
        if (rect.contains(h.x, h.y)) edges.add(h.edge);
        if ( less(xmin, h.x) &&  less(ymin, h.y)) query2D(h.SW, rect, edges);
        if ( less(xmin, h.x) && !less(ymax, h.y)) query2D(h.NW, rect, edges);
        if (!less(xmax, h.x) &&  less(ymin, h.y)) query2D(h.SE, rect, edges);
        if (!less(xmax, h.x) && !less(ymax, h.y)) query2D(h.NE, rect, edges);
    }


   /*************************************************************************
    *  helper comparison functions
    *************************************************************************/

    private boolean less(double k1, double k2) { return k1 < k2; }
    private boolean eq  (double k1, double k2) { return k1 == k2; }

    
    /*************************************************************************
     *  Methods from the DataStructure interface, though not implemented
     *************************************************************************/
    
	public ArrayList<Edge> getFilteredEdges() {
		ArrayList<Edge> filteredEdges = new ArrayList<Edge>();
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		if (minX < MIN_X) 	x1 = MIN_X;
		else 				x1 = minX;
		if (maxX > MAX_X) 	x2 = MAX_X;
		else 				x2 = maxX;
		if (minY < MIN_Y) 	y1 = MIN_Y;
		else 				y1 = minY;
		if (maxY < MAX_Y) 	y2 = MAX_Y;
		else 				y2 = maxY;
		Interval xInterval = new Interval(x1, x2);
		Interval yInterval = new Interval(y1, y2);
		Interval2D xyInterval = new Interval2D(xInterval, yInterval);
		query2D(xyInterval, filteredEdges);
		return filteredEdges;
	}

	public void addEdge(Edge e) {
		insert(e.getFromLong(), e.getFromLat(), e);
		insert(e.getToLong(), e.getToLat(), e);
	}
	
	public void addPoint(Point p) {
		insert(p.getX(), p.getY(), p.getEdge());
	}
	
//	public int getTreeHeight() {
//		return getTreeHeight(root);
//	}
//	
//	private int getTreeHeight(Node n) {
//		if (n != null) {
//			return 1 + Math.max(Math.max(getTreeHeight(n.NW), getTreeHeight(n.NE)),
//								Math.max(getTreeHeight(n.SE), getTreeHeight(n.SW)));
//		}
//		return 0;
//	}


//   /*************************************************************************
//    *  test client
//    *************************************************************************/
//    public static void main(String[] args) {
//        int M = Integer.parseInt(args[0]);   // queries
//        int N = Integer.parseInt(args[1]);   // points
//
//        QuadTree<Integer, String> st = new QuadTree<Integer, String>();
//
//        // insert N random points in the unit square
//        for (int i = 0; i < N; i++) {
//            Integer x = (int) (100 * Math.random());
//            Integer y = (int) (100 * Math.random());
//            // System.out.println("(" + x + ", " + y + ")");
//            st.insert(x, y, "P" + i);
//        }
//        System.out.println("Done preprocessing " + N + " points");
//
//        // do some range searches
//        for (int i = 0; i < M; i++) {
//            Integer xmin = (int) (100 * Math.random());
//            Integer ymin = (int) (100 * Math.random());
//            Integer xmax = xmin + (int) (10 * Math.random());
//            Integer ymax = ymin + (int) (20 * Math.random());
//            Interval<Integer> intX = new Interval<Integer>(xmin, xmax);
//            Interval<Integer> intY = new Interval<Integer>(ymin, ymax);
//            Interval2D<Integer> rect = new Interval2D<Integer>(intX, intY);
//            System.out.println(rect + " : ");
//            st.query2D(rect);
//        }
//    }
}