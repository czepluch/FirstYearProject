package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class used to build the graph from a file containing graph information.
 * The graph is (intended to be) made upon launching the application.
 */
public class Graph
{
	public static Graph G;
	private static final ArrayList<GVertex> graph = new ArrayList<GVertex>();
	private static final HashMap<String,GVertex> map = new HashMap<String,GVertex>();
	private static int awake = -1;
	
	public static void wakeUp() { 
		awake++;
		if (awake < 1) G = new Graph("out.txt");
	}
	
	/**
	 * Constructor
	 * @param path File path to the file containing graph information
	 */
	private Graph(String path)
	{
		System.out.println("Making graph...");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String line = in.readLine();			// has format: id,x,y,<neighbours, separated by white space>	

			// parse input
			while (line != null)
			{
				String[] tokens = line.split(",");
			 	String id = tokens[0];							// vertex id
				double x = Double.parseDouble(tokens[1]);		// x-coord
				double y = Double.parseDouble(tokens[2]);		// y-coord
				String[] neighbours = tokens[3].split(" ");		// neighbour vertices
				GVertex v = new GVertex(id, x, y, neighbours);
				graph.add(v);
				map.put(id,v);
				line = in.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
		
		// build adjacencies for each vertex
		for (GVertex v : graph) 
		{	
			String[] nb = v.neighbours;
			ArrayList<GVertex> adj = new ArrayList<GVertex>();
			for (String s : nb)
			{
				GVertex w = map.get(s);
				adj.add(w);
			}
					
			for (GVertex w : adj)
			{
				double dist = Dijkstra.distance(v, w);
				v.adjacencies.add(new GEdge(w, dist));
			}
		}
		System.out.println("Graph finished!");
	}
	
	public ArrayList<GVertex> getGraph()
	{
		return graph;
	}
	
	public HashMap<String,GVertex> getMap()
	{
		return map;
	}
	
	public double[] getNodeCoordinates(int nodeId) {
		GVertex v = map.get(nodeId + "");
		double[] coords = { v.getX(), v.getY() };
		return coords;
	}
	
	public static void main(String[] args) {
		wakeUp();
	}
}