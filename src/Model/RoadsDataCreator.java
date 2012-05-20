package Model;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Class used for reading, storing, and processing data about roads on a map
 */
public class RoadsDataCreator
{
	// Both the nodes and edges are stored for later use
	ArrayList<Node> nodes;
	ArrayList<Edge> edges;
	// Constants for reading the correct parts of the input
	private final int nFromId = 0;
	private final int nToId = 1;
	private final int eId = 3;
	private final int eSpeed = 25;
	private final int eType = 5;
	
	/**
	 * Constructor of the RoadsDataCreator class
	 */
	public RoadsDataCreator()
	{	
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	}
	
	/**
	 * Reads in data from the given files, then calls removeNodes and
	 * removeEdges methods for "cleaning up" the inread data.
	 * @param edge_path The file name/path of the file containing the edges
	 * @param node_path The file name/path of the file containing the nodes
	 */
	public void readData(String edge_path, String node_path)
	{
		BufferedReader in = null;

		// Read in the nodes
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(node_path), "UTF-8"));
			
			String inputString = in.readLine();
			inputString = in.readLine();
			// Continue reading in nodes, until no lines are left
			while (inputString != null) {
				StringTokenizer tokenizer = new StringTokenizer(inputString, ",");
				tokenizer.nextToken();
				tokenizer.nextToken();
				// Temporarily stores the info to be stored in the node
				int id = Integer.parseInt(tokenizer.nextToken());
				double x = Double.parseDouble(tokenizer.nextToken());
				double y = Double.parseDouble(tokenizer.nextToken());
				// Create a new node using the stored data and add it to the ArrayList of nodes
				nodes.add(new Node(id, x, y));
				inputString = in.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
		// Read in the edges
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(edge_path), "UTF-8"));
			
			String inputString = in.readLine();
			inputString = in.readLine();
			String[] line = null;
			int id = 0;
			double fromLat = 0;
			double fromLong = 0;
			double toLat = 0;
			double toLong = 0;
			int speed = 0;
			int type = 0;
			// Continue reading in edges, until no lines are left
			while (inputString != null) {
				line = inputString.split(",");
				id = Integer.parseInt(line[eId]);
				fromLat = nodes.get(Integer.parseInt(line[nFromId]) - 1).y;
				fromLong = nodes.get(Integer.parseInt(line[nFromId]) - 1).x;
				toLat = nodes.get(Integer.parseInt(line[nToId]) - 1).y;
				toLong = nodes.get(Integer.parseInt(line[nToId]) - 1).x;
				speed = Integer.parseInt(line[eSpeed]);
				type = Integer.parseInt(line[eType]);
	
				Edge edge = new Edge(id, fromLat, fromLong, toLat, toLong, speed, type);
				edges.add(edge);

				inputString = in.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
	}
	
	/**
	 * Prints info about all the edges to the given output file
	 * @param outputFile the name of the file to be created
	 */
	public void createOutputFile(String outputFile) {
		try {
			FileWriter fw = new FileWriter(outputFile);
			BufferedWriter out = new BufferedWriter(fw);
			for (Edge e : edges) out.write(e.toString() + "\n");
			out.close();
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	/*
	 * Inner class representing a node
	 * Stores the id, the coordinates, and a collection of edges 
	 * which it is connected to other nodes by
	 */
	private class Node
	{
		int id;
		double x, y;
		
		/*
		 * Constructor of the Node-class
		 * @param id the id of the node
		 * @param x the x-coordinate of the node
		 * @param y the y-coordinate of the node
		 */
		public Node(int id, double x, double y)
		{
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
	
	/*
	 * Inner class representing an edge
	 * Stores reference to two nodes and the distance (in meters) of the edge
	 */
	private class Edge
	{
		int id, speed, type;
		double distance, fromLat, fromLong, toLat, toLong;
		
		/*
		 * Constructor of the Edge-class
		 * Recieves references to two nodes and computes the distance from the info stored in these
		 * @param from the Node representing the coordinate which is the beginning of the edge
		 * @param to the Node representing the coordinate which is the end of the edge
		 */
		public Edge(int id, double fromLat, double fromLong, double toLat, double toLong, int speed, int type) {
			this.id = id;
			
			
			this.fromLong = fromLong;
			this.fromLat = fromLat;
			this.toLong = toLong;
			this.toLat = toLat;
			
			distance = 0;
			setDistance();
			
			this.speed = speed;
			this.type = type;
		}
	
		/*
		 * Getter methods for the Edge
		 */
		public int getId() { return id; }
		public double getFromLat() { return fromLat; }
		public double getFromLong() { return fromLong; }
		public double getToLat() { return toLat; }
		public double getToLong() { return toLong; }
		public double getDistance() { return distance; }
		public int getSpeed() { return speed; }
		public int getType() { return type; }
		
		/*
		 * Computes the distance using the coordinates stored in the from and to nodes
		 * Takes no parameters but works with the stored nodes
		 */
		public void setDistance()
		{
			// Pythagoras
			double x = toLong - fromLong;
			double y = toLat - fromLat;
			distance = Math.sqrt(x*x + y*y);
		}
		
		@Override
		public String toString() {
			return String.format("%d;%f;%f;%f;%f;%f;%d;%d", id,
															fromLat,
															fromLong,
															toLat,
															toLong,
															distance,
															speed,
															type);
		}
	}
	/**
	 * Main class of the RoadDataCreator-class
	 * Checks if the correct amount of arguments has been passes,
	 * then creates a new instance of the RoadDataCreator class, calling its
	 * readAndProcessData-method passing the given arguments as parameters
	 * @param args [0]: The path the to file containing the edge data
	 * 			   [1]: The path to the file containing the node data
	 * 			   [2]: The name of the output file
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Invalid number of arguments");
		}
		RoadsDataCreator pf = new RoadsDataCreator();
		pf.readData(args[0], args[1]);
		pf.createOutputFile(args[2]);
	}
}
