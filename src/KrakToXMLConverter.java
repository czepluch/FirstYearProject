import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;
import nu.xom.*;

/*
 * Class used for reading in, storing, and processing info about roads on a map
 */
public class KrakToXMLConverter
{
	// Both the nodes and edges are stored for later use
	ArrayList<Node> nodes;
	ArrayList<Edge> edges;
	// Constants for reading the correct parts of the input
	private final int nFromId = 0;
	private final int nToId = 1;
	private final int eSpeed = 25;
	private final int eType = 5;
	
	/*
	 * Constructor the the KrakToXMLConverter-class
	 */
	public KrakToXMLConverter()
	{	
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	}
	
	/*
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
			int tmpId = 1;
			double fromLat = 0;
			double fromLong = 0;
			double toLat = 0;
			double toLong = 0;
			int speed = 0;
			int type = 0;
			// Continue reading in edges, until no lines are left
			while (inputString != null) {
				line = inputString.split(",");
				fromLat = nodes.get(Integer.parseInt(line[nFromId]) - 1).y;
				fromLong = nodes.get(Integer.parseInt(line[nFromId]) - 1).x;
				toLat = nodes.get(Integer.parseInt(line[nToId]) - 1).y;
				toLong = nodes.get(Integer.parseInt(line[nToId]) - 1).x;
				speed = Integer.parseInt(line[eSpeed]);
				type = Integer.parseInt(line[eType]);
	
				Edge edge = new Edge(tmpId, fromLat, fromLong, toLat, toLong, speed, type);
				edges.add(edge);
				
				tmpId++;
				inputString = in.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
	}
	
	/*
	 * Inner class representing a node
	 * Stores the id, the coordinates and a collection of edges, which it
	 * is connected to other nodes by
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
		

		public Element getXML() {
			Element edge = new Element("edge");
			Attribute id = new Attribute("id", this.getId() + "");
			edge.addAttribute(id);
			
			Element fromLat = new Element("fromLat");
			fromLat.appendChild(this.getFromLat() + "");
			Element fromLong = new Element("fromLong");
			fromLong.appendChild(this.getFromLong() + "");
			
			Element toLat = new Element("toLat");
			toLat.appendChild(this.getToLat() + "");
			Element toLong = new Element("toLong");
			toLong.appendChild(this.getToLong() + "");
			
			Element distance = new Element("distance");
			distance.appendChild(this.getDistance() + "");
			Element speed = new Element("speed");
			speed.appendChild(this.getSpeed() + "");
			Element type = new Element("type");
			type.appendChild(this.getType() + "");
			
			edge.appendChild(fromLat);
			edge.appendChild(fromLong);
			edge.appendChild(toLat);
			edge.appendChild(toLong);
			edge.appendChild(distance);
			edge.appendChild(speed);
			edge.appendChild(type);
			return edge;
		}

	}
	
	public static void format(OutputStream os, Document doc) throws Exception {
		Serializer serializer = new Serializer(os, "UTF-8");
		serializer.setIndent(4);
		serializer.setMaxLength(60);
		serializer.write(doc);
		serializer.flush();
	}
	
	public void outputToXML(String outputFile) {
		Element root = new Element("edges");
		try {
			for (Edge e: edges) {
				root.appendChild(e.getXML());
			}
			Document doc = new Document(root);
			format(new BufferedOutputStream(new FileOutputStream(outputFile)), doc);
		} catch (Exception e) { System.out.println("Error: " + e.getMessage()); }	
	}
	
	
	/*
	 * Main class of the KrakToXMLConverter-class
	 * Checks if the correct amount of arguments has been passes
	 * Then creates a new instance of the KrakToXMLConverter class, callings its
	 * readAndProcessData-method passing the given arguments as parameters
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Invalid number of arguments");
		}
		KrakToXMLConverter pf = new KrakToXMLConverter();
		pf.readData(args[0], args[1]);
		pf.outputToXML(args[2]);
	}
}
