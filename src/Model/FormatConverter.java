package Model;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import Model.*;
import Global.*;

/*
 * Has the responsibility of converting between the data types stored in the Model
 * and data types needed by the view
 */
public class FormatConverter {
	private static Coordinates c;

	public static int[][][] convertData(ArrayList<Edge> edges) {
		c = new Coordinates();
		ArrayList<int[]> type1 = new ArrayList<int[]>();
		ArrayList<int[]> type2 = new ArrayList<int[]>();
		ArrayList<int[]> type3 = new ArrayList<int[]>();
		ArrayList<int[]> type4 = new ArrayList<int[]>();
		ArrayList<int[]> type5 = new ArrayList<int[]>();
		for (Edge e : edges) {
			switch (e.getType()) {
				case 1: type1.add(convertEdge(e)); break; // ???
				case 2: type2.add(convertEdge(e)); break; // ???
				case 3: type3.add(convertEdge(e)); break; // ???
				case 4: type4.add(convertEdge(e)); break; // ???
				case 5: type5.add(convertEdge(e)); break; // ???
				// If some other type exists, it is not drawn
				default: break;
			}
		}
		int[][] edges1 = listToArray(type1);
		int[][] edges2 = listToArray(type2);
		int[][] edges3 = listToArray(type3);
		int[][] edges4 = listToArray(type4);
		int[][] edges5 = listToArray(type5);
		
		int[][][] convertedEdges = { edges5, edges4, edges3, edges2, edges1 };
		
		return convertedEdges;
	}
	
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
	 * Updates the Coordinate field c
	 */
	public static void updateC() {
		c = new Coordinates();
	}
}
