package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import static Model.TripCriteria.*;

/**
 * Class used to build the graph from files containing graph information.
 * The graph is made upon launching the application.
 */
public class Graph
{
	public static Graph G;
	private final ArrayList<Vertex> fastVertices = new ArrayList<Vertex>();
	private final ArrayList<Vertex> shortVertices = new ArrayList<Vertex>();
	private final ArrayList<GEdge> edges = new ArrayList<GEdge>();
	private final HashMap<String,Vertex> fastMap = new HashMap<String,Vertex>();
	private final HashMap<String,Vertex> shortMap = new HashMap<String,Vertex>();
	private final HashMap<String,ArrayList<GEdge>> emap = new HashMap<String,ArrayList<GEdge>>();
	private static int awake = -1;
	
	/**
	 * Is called at start-up to create the graph.
	 */
	public static void wakeUp() { 
		awake++;
		if (awake < 1) G = new Graph("vertex.txt", "edges.txt");
	}
	
	/**
	 * This is method is strictly for testing purposes
	 * and should only be called in the DijkstraTest class.
	 * @param vpath	The path to the vertex test file
	 * @param epath	The path to the edge test file
	 * @return
	 */
	public static Graph testGraph(String vpath, String epath)
	{
		return new Graph(vpath, epath);
	}
	
	/**
	 * Constructor. Creates the Graph.
	 * @param vpath The path to the file containing the vertex data
	 * @param epath	The path to the file containing the edge data
	 */
	private Graph(String vpath, String epath)
	{
		BufferedReader in = null;
		try {
			// parse vertex input
			in = new BufferedReader(new InputStreamReader(new FileInputStream(vpath), "UTF-8"));
			String line = in.readLine();			// has format: id,x,y,<neighbours, separated by white space>	

			while (line != null)
			{
				String[] tokens = line.split(",");
			 	String id = tokens[0];							// vertex id
				double x = Double.parseDouble(tokens[1]);		// x-coord
				double y = Double.parseDouble(tokens[2]);		// y-coord
				String[] neighbours = tokens[3].split(" ");		// neighbour vertices
				Vertex v = new Vertex(id, x, y, neighbours);
				Vertex w = new Vertex(id, x, y, neighbours);
				fastVertices.add(v);
				shortVertices.add(w);
				fastMap.put(id,v);
				shortMap.put(id,w);
				line = in.readLine();
			}
			
			// parse edge input
			in = new BufferedReader(new InputStreamReader(new FileInputStream(epath), "UTF-8"));
			
			line = in.readLine();					// has format: id1, id2, weight
			
			while (line != null)
			{
				String[] tokens = line.split(",");
				String id1 = tokens[0], id2 = tokens[1];
				double weight = Double.parseDouble(tokens[2]);
				int speed = Integer.parseInt(tokens[3]);
				GEdge e = new GEdge(id1, id2, weight, speed);
				edges.add(e);
				
				// update adjacencies for each endpoint of the edge
				ArrayList<GEdge> adj1, adj2;
				if (emap.containsKey(id1))
					adj1 = emap.get(id1);
				else
					adj1 = new ArrayList<GEdge>();
				if (emap.containsKey(id2))
					adj2 = emap.get(id2);
				else
					adj2 = new ArrayList<GEdge>();
				adj1.add(e);
				adj2.add(e);
				emap.put(id1,adj1);
				emap.put(id2,adj2);
				line = in.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
		
		// build adjacencies for each vertex
		// first build the graph used to find the fastest path
		for (Vertex v : fastVertices)
		{
			ArrayList<GEdge> adj = emap.get(v.id);
			for (GEdge e : adj)
			{
				String s;
				if (v.id.equals(e.id1))	s = e.id2;
				else					s = e.id1;
				Vertex w = fastMap.get(s);
				v.adjacencies.add(new Edge(w, e.weight, e.speed));
			}
		}
		
		// then build the graph used to find the shortest path
		for (Vertex v : shortVertices) 
		{	
			String[] nb = v.neighbours;
			ArrayList<Vertex> adj = new ArrayList<Vertex>();
			for (String s : nb)
			{
				Vertex w = shortMap.get(s);
				adj.add(w);
			}
					
			for (Vertex w : adj)
			{
				double dist = Vertex.distance(v, w);
				v.adjacencies.add(new Edge(w, dist, 5));
			}
		}
	}
	
	/**
	 * Return the ArrayList of vertices in the graph used to find fastest path.
	 * @return The ArrayList of vertices in the graph used to find fastest path.
	 */
	public ArrayList<Vertex> getFastVertices()
	{
		return fastVertices;
	}
	
	/**
	 * Return the ArrayList of vertices in the graph used to find shortest path.
	 * @return The ArrayList of vertices in the graph used to find shortest path.
	 */
	public ArrayList<Vertex> getShortVertices()
	{
		return shortVertices;
	}
	
	/**
	 * Return the map of all vertices for the given type of path.
	 * @param type Determines which path is to be drawn. Can be FAST or SHORT.
	 * @return The map of all vertices for the given type of path.
	 */
	public HashMap<String,Vertex> getMap(TripCriteria type)
	{
		if (type == FAST) return fastMap;
		else return shortMap;
	}
	
	/**
	 * Return the coordinates of a node.
	 * @param nodeId The id of the node.
	 * @return The coordinates of a node as a double Array.
	 */
	public double[] getNodeCoordinates(int nodeId) {
		Vertex v = fastMap.get(nodeId + "");
		double[] coords = { v.getX(), v.getY() };
		return coords;
	}
}