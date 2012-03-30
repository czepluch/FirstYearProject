package KDModel;

import java.util.ArrayList;
import Global.*;

/*
 * Accesses the data and filters out the needed data
 */
public class DataFilter {
	
	/*
	 * Filters the given data according to the given parameters
	 * (Currently it simply returns the data given)
	 */
	public static ArrayList<Edge> filterData(ArrayList<Edge> edges) {
		ArrayList<Edge> filteredEdges = new ArrayList<Edge>();
		for (Edge e : edges) {
			if (e.getType() <= MinAndMaxValues.types) filteredEdges.add(e);
		}
		return filteredEdges;
	}
}