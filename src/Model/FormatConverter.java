package Model;

import java.util.ArrayList;
import java.util.List;

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
	public static int[][][] convertData(ArrayList<KEdge> edges) {
		c = new Coordinates();
		ArrayList<int[]> type1 = new ArrayList<int[]>();
		ArrayList<int[]> type2 = new ArrayList<int[]>();
		ArrayList<int[]> type3 = new ArrayList<int[]>();
		ArrayList<int[]> type4 = new ArrayList<int[]>();
		for (KEdge e : edges) {
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
	
	/**
	 * Creates a TripEdge of type Integer by converting the given TripEdge
	 * of type Double
	 * @param te 	The TripEdge of type Double to be converted (UTM32)
	 * @param speed	The max speed with which you are to travel
	 * @return		The TripEdge of type Integer as the result of the conversion (pixels)
	 */
	public static TripEdge<Integer> createTripEdge(TripEdge<Double> te, int speed) {
		int[] pixelCoords = convertEdge(te.getFromX(), te.getFromY(), te.getToX(), te.getToY());
		return new TripEdge(pixelCoords[0],
							pixelCoords[1],
							pixelCoords[2],
							pixelCoords[3],
							te.getDistance(),
							te.getTurn(),
							speed);
	}
	
	/**
	 * Creates a TripEdge of type Integer by converting the given TripEdge
	 * of type Double
	 * @param te 	The TripEdge of type Double to be converted (UTM32)
	 * @return		The TripEdge of type Integer as the result of the conversion (pixels)
	 */
	public static TripEdge<Integer> createTripEdge(TripEdge<Double> te) {
		int[] pixelCoords = convertEdge(te.getFromX(), te.getFromY(), te.getToX(), te.getToY());
		return new TripEdge(pixelCoords[0],
							pixelCoords[1],
							pixelCoords[2],
							pixelCoords[3],
							te.getDistance(),
							te.getTurn(),
							te.getSpeed());
	}
	
	/**
	 * Creates a MapLocation of type Integer from the given
	 * MapLocation of type Double
	 * (converts from UTM32 to pixels)
	 * @param ml	The MapLocation of type Double to be converted
	 * @return		The create MapLocation of type Integer
	 */
	public static MapLocation<Integer> createMapLocation(MapLocation<Double> ml) {
		return new MapLocation<Integer>(c.convertXToPixels(ml.getX()),
										c.convertYToPixels(ml.getY()));
	}
	
	/**
	 * Creates a Trip of Integer by converting a given Trip of type Double
	 * @param trip 	The Trip of Double (UTM32) to be converted
	 * @return 		The converted Trip of type Integer (pixels)
	 */
	public static Trip<Integer> createTrip(Trip<Double> trip) {
		Trip<Integer> newTrip = new Trip<Integer>();
		List<TripEdge<Double>> edges = trip.getEdges();
		for (TripEdge<Double> e : edges) newTrip.addEdge(createTripEdge(e));
		newTrip.computeTimeAndDistance();
		return newTrip;
	}
	
	/**
	 * Creates a Trip of Integer by converting a given Trip of type Double
	 * @param trip	The Trip of Double (UTM32) to be converted
	 * @param speed	The time which is to be used for the computations (how
	 * 				fast you are allowed to move on the given edges)
	 * @return	The converted Trip of type Integer (pixels)
	 */
	public static Trip<Integer> createTrip(Trip<Double> trip, int speed) {
		Trip<Integer> newTrip = new Trip<Integer>();
		List<TripEdge<Double>> edges = trip.getEdges();
		for (TripEdge<Double> e : edges) {
			// If the allowed speed of the edge is lower than the given
			// speed, use the speed of the edge
			// Else use the given speed
			if (e.getSpeed() < speed) 	newTrip.addEdge(createTripEdge(e));
			else 						newTrip.addEdge(createTripEdge(e, speed));
		}
		newTrip.computeTimeAndDistance();
		return newTrip;
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
	private static int[] convertEdge(KEdge e) {
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
