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
 * Class used to create the file 
 * that we use to make our graph for finding shortest paths.
 * The file created is called "out.txt"
 */
public class GraphInput
{
	private ArrayList<Vertex> vertices;
	private ArrayList<Vertex>[] adj;
	
	/**
	 * Constructor
	 * @param vertex_path Path to vertex file ("kdv_node_unload.txt")
	 * @param edge_path Path to edge file ("kdv_unload.txt")
	 */
	@SuppressWarnings("unchecked")
	public GraphInput(String vertex_path, String edge_path)
	{
		vertices = new ArrayList<Vertex>();
		
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
			adj = (ArrayList<Vertex>[]) new ArrayList[vertices.size()];
			System.out.println(vertices.size() + " - " + adj.length);
			
			in = new BufferedReader(new InputStreamReader(new FileInputStream(edge_path), "UTF-8"));
			in.readLine(); 	// skip first line
			line = in.readLine();
			
			while (line != null)
			{
				String[] tokens = line.split(",", 3);
				int id1 = Integer.parseInt(tokens[0]), id2 = Integer.parseInt(tokens[1]);
				
				Vertex v1 = vertices.get(id1-1), v2 = vertices.get(id2-1);		// vertices (endpoints) of the edge
				ArrayList<Vertex> adj1, adj2;									// adjacencies for each of the two vertices
					
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
			
			if (adj.length != vertices.size()) System.out.println("Something went wrong");
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
				
		try
		{
		  	// create file 
		  	FileWriter fstream = new FileWriter("out.txt");
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
		} catch (Exception e) {
		  	System.err.println("Oh noes!: " + e.getMessage());
		}
	}
	
	private class Vertex
	{
	 	int id;
		double x, y;
		
		public Vertex (int id, double x, double y)
		{
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
	
	public static void main(String[] args) {
		new GraphInput("kdv_node_unload.txt", "kdv_unload.txt");
	}
}