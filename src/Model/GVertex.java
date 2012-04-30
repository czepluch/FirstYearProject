package Model;

import java.util.ArrayList;

//Class used to represent a vertex in the shortest path clases: Dijkstra, Graph, and GraphInput
class GVertex implements Comparable<GVertex>
{
	public final double x, y;
 public final String id;
 public ArrayList<GEdge> adjacencies;
	public String[] neighbours;
 public double minDistance = Double.POSITIVE_INFINITY;
 public GVertex previous;

	/**
	 * Constructor
	 * @param id Vertex id
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param neighbours String array of neighbour id's
	 */
 public GVertex(String id, double x, double y, String[] neighbours) 
	{ 
		this.id = id;
		this.x = x;
		this.y = y;
		this.neighbours = neighbours;
		adjacencies = new ArrayList<GEdge>();
	}
 
 public String toString()
 {
 	return id;
 }
 
 public int compareTo(GVertex other)
 {
     return Double.compare(minDistance, other.minDistance);
 }
 
 public double getX() { return x; }
 public double getY() { return y; }

/**
 * Compute the distance between two vertices
 * @param v1 The first vertex
 * @param v2 The second vertex
 * @return The distance between two vertices
 */
public static double distance(GVertex v1, GVertex v2)
{
	return Math.sqrt(Math.pow((v2.x - v1.x), 2) + Math.pow((v2.y - v1.y), 2));
}
}