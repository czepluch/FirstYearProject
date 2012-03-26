package Controller;

import java.util.ArrayList;
import Model.*;

/*
 * Has the responsibility of converting between the data types stored in the Model
 * and data types needed by the view
 */
public class FormatConverter {
	private static Coordinates c;

	public static int[][][] convertData(ArrayList<Edge> edges, double minX, double maxX, double minY, double maxY, int width, int height) {
		c = new Coordinates(minX, maxX, minY, maxY, width, height);
		
		
		return new int[6][6][6];
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
}
