package Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import static Model.TripCriteria.*;

/**
 * Class used to compute the shortest path between two given vertices
 */
public class Dijkstra implements PathFinder
{
	/**
	 * Compute the shortest and fastest path from one vertex to another.
	 * Should not be called directly. Instead, call the run()-method
	 * @param source The starting point of the path
	 * @param target The end point of the path
	 * @param vs The graph
	 */
    private void computePaths(Vertex source, Vertex target, ArrayList<Vertex> vs)
    {
    	for (Vertex v : vs)
    	{
    		v.minDistance = Double.POSITIVE_INFINITY;
    		v.previous = null;
    	}
        source.minDistance = 0.;		// distance to self is zero
        IndexMinPQ<Vertex> vertexQueue = new IndexMinPQ<Vertex>(vs.size());
        
        for (Vertex v : vs) vertexQueue.insert(Integer.parseInt(v.id), v);

		while (!vertexQueue.isEmpty()) 
		{
	    	// retrieve vertex with shortest distance to source
	    	Vertex u = vertexQueue.minKey();
	    	vertexQueue.delMin();
			if (u == target)
			{
				// trace back
				while (u.previous != null)
				{;
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
					int vid = Integer.parseInt(v.id);
		    		vertexQueue.delete(vid);
		    		v.minDistance = distanceThroughU;
		    		v.previous = u;
		    		vertexQueue.insert(vid,v);
				}
			}
        }
    }

    /**
     * Call this method to get the shortest or fastest path between two vertices
	 * @param source The starting point of the path
	 * @param target The end point of the path
	 * @param type Determines whether to compute the fastest or shortest path.
     */
	public Trip<Double> run(String source, String target, TripCriteria type, Graph g)
	{
		ArrayList<Vertex> vs;
		if (type == FAST) vs = g.getFastVertices();
		else 			  vs = g.getShortVertices();
		HashMap<String,Vertex> map = g.getMap(type);
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
       			Edge firstEdge = null;
       			// get first edge in path
       			// used for calculating travel time
       			for (Edge e : firstPoint.adjacencies)
       				if (e.target.id.equals(path.get(1).id)) firstEdge = e;
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
       														  firstEdge.speed);
       				trip.addEdge(e);
       				
       				fromX = x;
       				fromY = y;
       				firstPoint = point;
       				prevEdge = e;
       				// get next edge in path
       				if (i + 1 >= path.size()) continue;
           			for (Edge ed : firstPoint.adjacencies)
           				if (ed.target.id.equals(path.get(i+1).id)) firstEdge = ed;
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