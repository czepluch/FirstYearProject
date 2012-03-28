package Model;

import java.util.ArrayList;

import Global.MinAndMaxValues;

/*
 * The part of the application taking care of data handling
 * Includes classes for reading, storing and filtering data
 */
public class Model {
	private ArrayList<Edge> edges;
	// Used to avoid filtering the same data again
	private ArrayList<Edge> currentFilteredEdges;
	
	/*
	 * Constructor of the model class
	 * Depends on the readXML-method of the XMLReader class
	 */
	public Model() throws RuntimeException {
		try {
			edges = XMLReader.readXML("krax_complete.xml");
			currentFilteredEdges = DataFilter.filterData(edges);
		} catch (Exception e) { // Exception needs to be specified
			throw new RuntimeException("Could not read map data from file");
		}
	}
	/*
	 * Method no longer in use
	 *
	 * Returns the data stored in the model
	 * @return the ArrayList<Edge> stored
	 *
	public int[][][] getEdges() {
		currentFilteredEdges = edges;
		return FormatConverter.convertData(currentFilteredEdges);
	}
	*/
	
	/*
	 * Returns the data stored in the model filtered according to the given data
	 * @return the filtered ArrayList<Edge>
	 */
	public int[][][] getFilteredEdges() {
		// Filter only if needed
		if (MinAndMaxValues.repaint) currentFilteredEdges = DataFilter.filterData(edges);
		return FormatConverter.convertData(currentFilteredEdges);
	}

}
