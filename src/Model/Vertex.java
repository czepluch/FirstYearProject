package Model;

import java.util.ArrayList;

/**
 * Class used to represent a vertex in the shortest path clases: Dijkstra, Graph, and GraphInput
 */
public class Vertex implements Comparable<Vertex>
{
	public final double x, y;
	public final String id;
	public ArrayList<Edge> adjacencies;
	public String[] neighbours;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous = null;

	/**
	 * Constructor.
	 * @param id Vertex id
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param neighbours String array of neighbour id's
	 */
	public Vertex(String id, double x, double y, String[] neighbours) 
	{ 
		this.id = id;
		this.x = x;
		this.y = y;
		this.neighbours = neighbours;
		adjacencies = new ArrayList<Edge>();
	}
	
	@Override
	public String toString()
	{
		return id;
	}

	@Override
	public int compareTo(Vertex other)
	{
		return Double.compare(minDistance, other.minDistance);
	}

	/**
	 * Get the stored x-coordinate
	 * @return the stored x-coordinate
	 */
	public double getX() { return x; }
	
	/**
	 * Get the stored y-coordinate
	 * @return the stored y-coordinate
	 */
	public double getY() { return y; }

	/**
	 * Compute the distance between two vertices
	 * @param v1 The first vertex
	 * @param v2 The second vertex
	 * @return The distance between two vertices
	 */
	public static double distance(Vertex v1, Vertex v2)
	{
		return Math.sqrt(Math.pow((v2.x - v1.x), 2) + Math.pow((v2.y - v1.y), 2));
	}
}
