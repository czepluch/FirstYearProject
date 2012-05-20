package Model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Class used to create the files that are used to create the graph.
 */
public class GraphDataCreator
{
	private ArrayList<Vertex> vertices;
	private ArrayList<GEdge> edges;
	private ArrayList<Vertex>[] adj;
	
	@SuppressWarnings("unchecked")
	public GraphDataCreator(String vertex_path, String edge_path)
	{
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<GEdge>();
		
		BufferedReader in = null;
		try {
			// read vertices
			in = new BufferedReader(new InputStreamReader(new FileInputStream(vertex_path), "UTF-8"));
			String line = in.readLine(); 	// skip first line
			line = in.readLine();
			
			while (line != null)
			{
				String[] tokens = line.split(",");
				int id = Integer.parseInt(tokens[2]);
				double x = Double.parseDouble(tokens[3]), y = Double.parseDouble(tokens[4]);
				vertices.add(new Vertex(id, x, y));
				line = in.readLine();
			}
			adj = (ArrayList<Vertex>[]) new ArrayList[vertices.size()]; // warning supressed
			
			// read edges
			in = new BufferedReader(new InputStreamReader(new FileInputStream(edge_path), "UTF-8"));
			in.readLine(); 	// skip first line
			line = in.readLine();
			
			while (line != null)
			{
				String[] tokens = line.split(",");
				int id1 = Integer.parseInt(tokens[0]), id2 = Integer.parseInt(tokens[1]);
				double speed = Double.parseDouble(tokens[25]);
				
				Vertex v1 = vertices.get(id1-1), v2 = vertices.get(id2-1);		// vertices (endpoints) of the edge
				ArrayList<Vertex> adj1, adj2;									// adjacencies for each of the two vertices
				
				double dist = Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
				if (speed <= 0) speed = 5;		// speed is set to 5 if undefined
				edges.add(new GEdge(tokens[0], tokens[1], (100 * dist) / speed, (int)speed));
				
				// add v2 as adjacent to v1
				if (adj[id1-1] != null) adj1 = adj[id1-1];
				else adj1 = new ArrayList<Vertex>();
				adj1.add(v2);
				adj[id1-1] = adj1;
				
				// add v1 as adjacent to v2
				if (adj[id2-1] != null) adj2 = adj[id2-1];
				else adj2 = new ArrayList<Vertex>();
				adj2.add(v1);
				adj[id2-1] = adj2;
				
				line = in.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!!!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
				
		try
		{
		  	// create vertex file 
		  	FileWriter fstream = new FileWriter("vertex.txt");
		  	BufferedWriter out = new BufferedWriter(fstream);
		
			// write to file
			for (int i = 0; i < vertices.size(); i++)
			{
				String nb = "";
				Vertex v = vertices.get(i);
				for (Vertex w : adj[i]) nb += w.id + " ";
				nb.trim();
				out.write((i+1) + "," + v.x + "," + v.y + "," + nb + "\n");	// a line is written
			}
		  	// close the output stream
		  	out.close();
		
			// create edge file
			fstream = new FileWriter("edges.txt");
			out = new BufferedWriter(fstream);
			
			// write to file
			for (int i = 0; i < edges.size(); i++)
			{
				GEdge e = edges.get(i);
				out.write(e.id1 + "," + e.id2 + "," + e.weight + "," + e.speed + "\n");
			}
			out.close();
		} catch (Exception e) {
		  	System.err.println("Oh noes!: " + e.getMessage());
		}
	}
	
	/**
	 * Inner class for temprarily storing the vertex
	 * data read from the input file
	 */
	private class Vertex
	{
	 	int id;
		double x, y;
		
		public Vertex(int id, double x, double y)
		{
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * Main method
	 * Runs the methods of the class using files obtained from Krak
	 * @param args - Unused
	 */
	public static void main(String[] args) {
		new GraphDataCreator("kdv_node_unload.txt", "kdv_unload.txt");
	}
}