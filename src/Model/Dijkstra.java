package Model;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.HashMap;
import static Model.Graph.G;
import static Global.MinAndMaxValues.maxSpeed;

// Class used to represent a vertex in the shortest path clases: Dijkstra, Graph, and GraphInput
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
}

//Class used to represent an edge in the shortest path classes: Dijkstra, Graph, and GraphInput
class GEdge
{
    public final GVertex target;
    public final double weight;
    public GEdge(GVertex argTarget, double argWeight)
    {
		target = argTarget; 
		weight = argWeight;
	}
}

/**
 * Class used to compute the shortest path between two given vertices
 */
public class Dijkstra
{	
	/**
	 * Compute the shortest path from one vertex to another.
	 * Should not be called directly. Instead, call the run()-method
	 * @param source The starting point of the path
	 * @param target The end point of the path
	 * @param vs The graph
	 */
    public static void computePaths(GVertex source, GVertex target, ArrayList<GVertex> vs)
    {
        source.minDistance = 0.;
        PriorityQueue<GVertex> vertexQueue = new PriorityQueue<GVertex>(vs);

		while (!vertexQueue.isEmpty()) 
		{
	    	GVertex u = vertexQueue.poll();		// retrieve vertex with shortest distance to source
			if (u == target)
			{
				LinkedList<GVertex> S = new LinkedList<GVertex>();
				// trace back
				while (u.previous != null)
				{
					S.add(u);
					u = u.previous;
				}
				return;
			}
        	// Visit each edge exiting u
        	for (GEdge e : u.adjacencies)
      		{
        		GVertex v = e.target;
				double weight = e.weight;
            	double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) 
				{
		    		vertexQueue.remove(v);
		    		v.minDistance = distanceThroughU;
		    		v.previous = u;
		    		vertexQueue.add(v);
				}
			}
        }
    }

    /**
     * Call this method to compute the shortest path between two vertices
	 * @param source The starting point of the path
	 * @param target The end point of the path
     */
	public static Trip<Double> run(String source, String target)
	{
		// run the algorithm from j'th to k'th vertex
		ArrayList<GVertex> vs = G.getGraph();
		HashMap<String,GVertex> map = G.getMap();
		GVertex u = map.get(source);
		computePaths(u, map.get(target), vs);
		Trip<Double> trip = new Trip<Double>();
       	for (GVertex v : vs)
		{
       		if (v.id.equals(target)) 
			{
       			List<GVertex> path = getShortestPathTo(v);
       			TripEdge<Double> prevEdge = null;
       			GVertex firstPoint = path.get(0);
       			double fromX = firstPoint.getX();
       			double fromY = firstPoint.getY();
       			for (int i = 1; i < path.size(); i++) {
       				GVertex point = path.get(i);
       				double x = point.getX();
       				double y = point.getY();
       				TripEdge<Double> e = new TripEdge<Double>(fromX,
       														  fromY,
       														  x,
       														  y,
       														  distance(firstPoint, point),
       														  prevEdge,
       														  maxSpeed);
       				trip.addEdge(e);
       				
       				fromX = x;
       				fromY = y;
       				firstPoint = point;
       				prevEdge = e;
       			}
			}
		}
       	return trip;
	}

	/**
	 * Return the shortest path to a given vertex
	 * @param target The vertex to return the shortest path to
	 * @return The shortest path to a given vertex
	 */
    public static List<GVertex> getShortestPathTo(GVertex target)
    {
        List<GVertex> path = new ArrayList<GVertex>();
        for (GVertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
	
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