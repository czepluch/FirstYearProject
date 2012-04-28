package Model;

import java.util.ArrayList;

/**
 * Has the responsibility of converting between the data types stored in the Model
 * and data types needed by the view
 */
public class FormatConverter {
	private static Coordinates c;

	/**
	 * Converts an ArrayList of Edges into a three-dimensional int array
	 * whose dimensions contain the type, the Edge, and the coordinates, respectively.
	 * @param edges The Edge ArrayList containing the data we want to convert
	 * @return A three-dimensional int array containing the data
	 */
	public static int[][][] convertData(ArrayList<Edge> edges) {
		c = new Coordinates();
		ArrayList<int[]> type1 = new ArrayList<int[]>();
		ArrayList<int[]> type2 = new ArrayList<int[]>();
		ArrayList<int[]> type3 = new ArrayList<int[]>();
		ArrayList<int[]> type4 = new ArrayList<int[]>();
		for (Edge e : edges) {
			switch (e.getType()) {
				case 1: type1.add(convertEdge(e)); break; // Highways
				case 2: type2.add(convertEdge(e)); break; // Primary roads
				case 3: type3.add(convertEdge(e)); break; // Secondary roads
				case 4: type4.add(convertEdge(e)); break; // Other roads
				// If some other type exists, it is not drawn
				default: break;
			}
		}

		int[][] edges1 = listToArray(type1);
		int[][] edges2 = listToArray(type2);
		int[][] edges3 = listToArray(type3);
		int[][] edges4 = listToArray(type4);
		
		int[][][] convertedEdges = { edges4, edges3, edges2, edges1 };
		return convertedEdges;
	}
	
	/*
	 * Creates a trip edge, converting the coordinates of a given Edge object
	 * @param e			the edge which has coordinates to be converted
	 * @param prevEdge	prevEdge the previous TripEdge on the trip
	 * 					null if no such exists
	 * @param speed 	the speed of the edge
	 * @return the created TripEdge
	 */
	public static TripEdge createTripEdge(double fromX, double fromY, double toX, double toY, double distance, TripEdge prevEdge, int speed) {
		int[] pixelCoords = convertEdge(fromX, fromY, toX, toY);
		return new TripEdge(pixelCoords[0],
							pixelCoords[1],
							pixelCoords[2],
							pixelCoords[3],
							distance,
							prevEdge,
							speed);
	}
	
	/**
	 * Creates a MapLocation from the given coordinates
	 * @param x the x-coordinate in UTM-32 format
	 * @param y the y-coordinate in UTM-32 format
	 * @return the MapLocation containing coordinates in pixels
	 */
	public static MapLocation createMapLocation(double x, double y) {
		return new MapLocation(c.convertXToPixels(x), c.convertYToPixels(y));
	}
	
	/*
	 * Helper method for convertData(ArrayList<Edge>).
	 * Converts an ArrayList<int[]> to int[][]
	 */
	private static int[][] listToArray(ArrayList<int[]> l) {
		int [][] ret = new int[l.size()][4];
		
		for(int i=0; i<l.size(); i++) {
			ret[i] = l.get(i);
		}
		return ret;
	}
	
	/*
	 * Helper method for converting edges to int[4]
	 * @param e the Edge to be converted
	 * @return the int[4] (x1, y1, x2, y2) representing an edge with four coordinates
	 */
	private static int[] convertEdge(Edge e) {
		int x1 = c.convertXToPixels(e.getFromLong());
		int y1 = c.convertYToPixels(e.getFromLat());
		int x2 = c.convertXToPixels(e.getToLong());
		int y2 = c.convertYToPixels(e.getToLat());
		int[] edge = { x1, y1, x2, y2 };
		return edge;
	}
	
	/*
	 * Helper method for converting edges to int[4]
	 * @param e the Edge to be converted
	 * @return the int[4] (x1, y1, x2, y2) representing an edge with four coordinates
	 */
	private static int[] convertEdge(double fromX, double fromY, double toX, double toY) {
		int x1 = c.convertXToPixels(fromX);
		int y1 = c.convertYToPixels(fromY);
		int x2 = c.convertXToPixels(toX);
		int y2 = c.convertYToPixels(toY);
		int[] edge = { x1, y1, x2, y2 };
		return edge;
	}
	
	/**
	 * Updates the Coordinate field c
	 */
	public static void updateC() {
		c = new Coordinates();
	}
}
