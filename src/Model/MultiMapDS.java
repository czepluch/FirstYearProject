package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import static Global.MinAndMaxValues.*;

public class MultiMapDS implements DataStructure {
	// 1. HashMap = types
	// 2. HashMap = x-values
	// 3. HashMap = y-values
	// 4. HashSet = the set of edges corresponding the the type, x- and y-value
	private HashMap<Integer, HashMap<Integer, HashMap<Integer, HashSet<Edge>>>> edges;
	private ArrayList<Edge> filteredEdges;
	// The size of the intervals
	private final int X_INTERVAL = 10000;
	private final int Y_INTERVAL = 10000;
	// The number of types
	private final int NUMBER_OF_TYPES = 6;

	/*
	 * Constructor
	 */
	public MultiMapDS() {
		edges = new HashMap<Integer, HashMap<Integer, HashMap<Integer, HashSet<Edge>>>>();
		filteredEdges = new ArrayList<Edge>();
		
		// Compute max and min index values for 2. HashMap and 3. HashMap
		int minXIndex = xCoordinateToIndex(MIN_X);
		int maxXIndex = xCoordinateToIndex(MAX_X);
		int minYIndex = xCoordinateToIndex(MIN_Y);
		int maxYIndex = xCoordinateToIndex(MAX_Y);
		
		// For each type
		for (int i = 1; i <= NUMBER_OF_TYPES; i++) {
			
			HashMap<Integer, HashMap<Integer, HashSet<Edge>>> x =
						new HashMap<Integer, HashMap<Integer, HashSet<Edge>>>();
			
			// For each x-value interval
			for (int j = minXIndex; j <= maxXIndex; j++) {
				HashMap<Integer, HashSet<Edge>> y = new HashMap<Integer, HashSet<Edge>>();
				
				// For each y-value interval
				for (int k = minYIndex; k <= maxYIndex; k++) {
					y.put(k, new HashSet<Edge>());
				}
				
				x.put(j, y);
			}
			edges.put(i, x);
		}
	}
	
	/*
	 * Converts a given x-coordinate to the needed index using the stored values,
	 * both from this class and the global MinAndMaxValues class
	 */
	public int xCoordinateToIndex(double c) {
		return (int) (c / X_INTERVAL);
	}
	
	/*
	 * Converts a given y-coordinate to the needed index using the stored values,
	 * both from this class and the global MinAndMaxValues class
	 */
	public int yCoordinateToIndex(double c) {
		return (int) (c / Y_INTERVAL);
	}
	
	@Override
	public ArrayList<Edge> getFilteredEdges() {
		filteredEdges = new ArrayList<Edge>();
		
		// Compute max and min index values
		int minXIndex = 0;
		int maxXIndex = 0;
		int minYIndex = 0;
		int maxYIndex = 0;
		if (!((minX - X_INTERVAL) < MIN_X)) minXIndex = xCoordinateToIndex(minX) - 1;
		else minXIndex = xCoordinateToIndex(MIN_X);
		if (!((maxX + X_INTERVAL) > MAX_X)) maxXIndex = xCoordinateToIndex(maxX) + 1;
		else maxXIndex = xCoordinateToIndex(MAX_X);
		if (!((minY - Y_INTERVAL) < MIN_Y)) minYIndex = yCoordinateToIndex(minY) - 1;
		else minYIndex = yCoordinateToIndex(MIN_Y);
		if (!((maxY + Y_INTERVAL) > MAX_Y)) maxYIndex = yCoordinateToIndex(maxY) + 1;
		else maxYIndex = yCoordinateToIndex(MAX_Y);
		
		// Types
		for (int t = 1; t <= types; t++) {
			
			// x-values
			for (int x = minXIndex; x <= maxXIndex; x++) {
				
				// y-values
				for (int y = minYIndex; y <= maxYIndex; y++) {
					filteredEdges.addAll(edges.get(t).get(x).get(y));
				}
			}
		}
		
		return filteredEdges;
	}

	@Override
	public void addEdge(Edge e) {
		int type = e.getType();
		int x1 = xCoordinateToIndex(e.getFromLong());
		int y1 = yCoordinateToIndex(e.getFromLat());
		int x2 = xCoordinateToIndex(e.getToLong());
		int y2 = yCoordinateToIndex(e.getToLat());
		edges.get(type).get(x1).get(y1).add(e);
		edges.get(type).get(x2).get(y2).add(e);
	}
	
	public static void main(String[] args) {
		MultiMapDS edges = new MultiMapDS();
		System.out.println("Creating the DS was a success!");
		XMLReader.readXML("krax_complete.xml", edges);
		System.out.println("The reading from the file was a success!");
		for (Edge e : edges.getFilteredEdges()) {
			System.out.println(e.getType());
		}
		System.out.println("x1:\t" + edges.xCoordinateToIndex(685516.15196));
		System.out.println("y1:\t" + edges.yCoordinateToIndex(6072292.50095));
		System.out.println("x2:\t" + edges.xCoordinateToIndex(685592.2037));
		System.out.println("y2:\t" + edges.yCoordinateToIndex(6072271.15707));
		System.out.println();
		System.out.println("Min x:\t" + edges.xCoordinateToIndex(MIN_X));
		System.out.println("Max x:\t" + edges.xCoordinateToIndex(MAX_X));
		System.out.println("Min y:\t" + edges.xCoordinateToIndex(MIN_Y));
		System.out.println("Max y:\t" + edges.xCoordinateToIndex(MAX_Y));
	}
}
