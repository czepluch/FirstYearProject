package Model;

import java.util.ArrayList;
import View.ViewValues;

/*
 * This implementation is taken from http://algs4.cs.princeton.edu/92search/QuadTree.java.html
 */

/**
 * The Quad Tree data structure.
 * Each node in the tree represents a square in the map and has four children, 
 * which represent the northwest, northeast, southeast, and southeast
 * sub-squares of their parent square, respectively.
 * Also cntains methods for performing Quad Tree operations.
 */
public class QuadTree {
    private Node root;
    // Store a reference to the instance of the MinAndMaxValues class
 	private ViewValues v = ViewValues.getInstance();

    // helper node data type
    private class Node {
        double x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees
        KEdge edge;           // associated data

        Node(double x, double y, KEdge edge) {
            this.x = x;
            this.y = y;
            this.edge = edge;
        }
    }

    /**
     * Insert the (x, y) point into the appropriate quadrant
     * @param x The x-coordinate of the point
     * @param y The y-coordinate of the point
     * @param edge The edge associated with the point
     */
    public void insert(double x, double y, KEdge edge) {
        root = insert(root, x, y, edge);
    }

    /*
     * Helper method for insert(double, double, Edge)
     */
    private Node insert(Node h, double x, double y, KEdge edge) {
        if (h == null) return new Node(x, y, edge);
        //// if (eq(x, h.x) && eq(y, h.y)) h.edge = edge;  // duplicate
        else if ( less(x, h.x) &&  less(y, h.y)) h.SW = insert(h.SW, x, y, edge);
        else if ( less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, edge);
        else if (!less(x, h.x) &&  less(y, h.y)) h.SE = insert(h.SE, x, y, edge);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, edge);
        return h;
    }


    /**
     * Perform a range search in a 2D interval
     * @param rect The 2D interval in which to perform the range search
     * @param edges The return-arrayList to contain the edges in the given interval
     */
    public void query2D(Interval2D rect, ArrayList<KEdge> edges) {
        query2D(root, rect, edges);
    }

    /*
     * Helper method for query2D(Interval2D, ArrayList<Edge>)
     */
    private void query2D(Node h, Interval2D rect, ArrayList<KEdge> edges) {
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
    *  helper comparison methods
    *************************************************************************/

    private boolean less(double k1, double k2) { return k1 < k2; }
    private boolean eq  (double k1, double k2) { return k1 == k2; }

    
    /*************************************************************************
     *  Methods from the DataStructure interface, though not implemented
     *************************************************************************/
    
	public ArrayList<KEdge> getFilteredEdges() {
		ArrayList<KEdge> filteredEdges = new ArrayList<KEdge>();
		double x1 = 0;
		double x2 = 0;
		double y1 = 0;
		double y2 = 0;
		if (v.getMinX() < v.getMIN_X()) 	x1 = v.getMIN_X();
		else 								x1 = v.getMinX();
		if (v.getMaxX() > v.getMAX_X()) 	x2 = v.getMAX_X();
		else 								x2 = v.getMaxX();
		if (v.getMinY() < v.getMIN_Y()) 	y1 = v.getMIN_Y();
		else 								y1 = v.getMinY();
		if (v.getMaxY() < v.getMAX_Y()) 	y2 = v.getMAX_Y();
		else 								y2 = v.getMaxY();
		Interval xInterval = new Interval(x1, x2);
		Interval yInterval = new Interval(y1, y2);
		Interval2D xyInterval = new Interval2D(xInterval, yInterval);
		query2D(xyInterval, filteredEdges);
		return filteredEdges;
	}

	/**
	 * Adds an edge to the tree
	 * @param e The edge to be added
	 */
	public void addEdge(KEdge e) {
		insert(e.getFromLong(), e.getFromLat(), e);
		insert(e.getToLong(), e.getToLat(), e);
	}
}