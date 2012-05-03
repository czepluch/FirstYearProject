package Model;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.HashMap;
import static Model.Graph.G;
import static Global.MinAndMaxValues.maxSpeed;

/**
 * Class used to compute the shortest path between two given vertices
 */
public class Dijkstra implements PathFinder
{	
	/**
	 * Compute the shortest path from one vertex to another.
	 * Should not be called directly. Instead, call the run()-method
	 * @param source The starting point of the path
	 * @param target The end point of the path
	 * @param vs The graph
	 */
    private void computePaths(Vertex source, Vertex target, ArrayList<Vertex> vs)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>(vs);

		while (!vertexQueue.isEmpty()) 
		{
	    	Vertex u = vertexQueue.poll();		// retrieve vertex with shortest distance to source
			if (u == target)
			{
				LinkedList<Vertex> S = new LinkedList<Vertex>();
				// trace back
				while (u.previous != null)
				{
					S.add(u);
					u = u.previous;
				}
				return;
			}
        	// Visit each edge exiting u
        	for (Edge e : u.adjacencies)
      		{
        		Vertex v = e.target;
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
	 * @param type Determines whether to compute the fastest or shortest path. If 0, compute fastets, otherwise shortest.
     */
	public Trip<Double> run(String source, String target, int type)
	{
		// run the algorithm from j'th to k'th vertex
		ArrayList<Vertex> vs;
		if (type == 0) vs = G.getFastVertices();
		else vs = G.getShortVertices();
		HashMap<String,Vertex> map = G.getMap();
		Vertex u = map.get(source);
		computePaths(u, map.get(target), vs);
		Trip<Double> trip = new Trip<Double>();
       	for (Vertex v : vs)
		{
       		if (v.id.equals(target)) 
			{
       			List<Vertex> path = getShortestPathTo(v);
       			TripEdge<Double> prevEdge = null;
       			Vertex firstPoint = path.get(0);
       			double fromX = firstPoint.getX();
       			double fromY = firstPoint.getY();
       			for (int i = 1; i < path.size(); i++) {
       				Vertex point = path.get(i);
       				double x = point.getX();
       				double y = point.getY();
       				TripEdge<Double> e = new TripEdge<Double>(fromX,
       														  fromY,
       														  x,
       														  y,
       														  Vertex.distance(firstPoint, point),
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
    public List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
}