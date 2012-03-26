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
		ArrayList<int[]> type1 = new ArrayList<int[]>();
		ArrayList<int[]> type2 = new ArrayList<int[]>();
		ArrayList<int[]> type3 = new ArrayList<int[]>();
		ArrayList<int[]> type4 = new ArrayList<int[]>();
		ArrayList<int[]> type5 = new ArrayList<int[]>();
		for (Edge e : edges) {
			switch (e.getType()) {
				case 1: type1.add(convertEdge(e)); break; // Highways, type 1
				case 2: type1.add(convertEdge(e)); break; // Almost highways, type 1
				case 3: type2.add(convertEdge(e)); break; // Primary routes > 6m, type 2
				case 4: type2.add(convertEdge(e)); break; // Secondary routes > 6m, type 2
				case 5: type3.add(convertEdge(e)); break; // Roads 3 - 6m, type 3
				case 6: type4.add(convertEdge(e)); break; // Other roads, type 4
				case 8: type5.add(convertEdge(e)); break; // Paths, type 5
				case 10: type5.add(convertEdge(e)); break; // Field paths, type 5
				case 11: type5.add(convertEdge(e)); break; // Pedestrian zones, type 5
				case 21: type1.add(convertEdge(e)); break; // Proj. highways, type 1
				case 22: type1.add(convertEdge(e)); break; // Proj. almost highways, type1
				case 23: type2.add(convertEdge(e)); break; // Proj. primary routes > 6m, type 2
				case 24: type2.add(convertEdge(e)); break; // Proj. secondary routes > 6m, type 2
				case 25: type3.add(convertEdge(e)); break; // Proj. roads 3 - 6m, type 4
				case 26: type4.add(convertEdge(e)); break; // Proj. other roads, type 4
				case 28: type5.add(convertEdge(e)); break; // Proj. paths, type 5
				case 31: type1.add(convertEdge(e)); break; // Highway exits, type 1
				case 32: type1.add(convertEdge(e)); break; // Almost highway exits, type 1
				case 33: type2.add(convertEdge(e)); break; // Primary routes > 6m exits, type 2
				case 34: type2.add(convertEdge(e)); break; // Secondary routes > 6m exits, type 2
				case 35: type3.add(convertEdge(e)); break; // Roads 3 - 6m exits, type 3
				case 41: type1.add(convertEdge(e)); break; // Highway tunnels, type 1
				case 42: type1.add(convertEdge(e)); break; // Almost highway tunnels, type 1
				case 43: type2.add(convertEdge(e)); break; // Primary routes > 6m tunnels, type 2
				case 44: type2.add(convertEdge(e)); break; // Secondary routes > 6m tunnels, type 2
				case 45: type3.add(convertEdge(e)); break; // Roads 3 - 6m tunnels, type 3
				case 46: type4.add(convertEdge(e)); break; // Other road tunnels, type 4
				case 48: type5.add(convertEdge(e)); break; // Path tunnels, type 5
				// If some other type exists, it is not drawn
				default: break;
			}
		}
		int[][] edges1 = (int[][]) type1.toArray();
		int[][] edges2 = (int[][]) type2.toArray();
		int[][] edges3 = (int[][]) type3.toArray();
		int[][] edges4 = (int[][]) type4.toArray();
		int[][] edges5 = (int[][]) type5.toArray();
		
		int[][][] convertedEdges = { edges1, edges2, edges3, edges4, edges5 };
		
		return convertedEdges;
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
