package Model;

import java.util.ArrayList;

import Global.MinAndMaxValues;

/*
 * The part of the application taking care of data handling
 * Includes classes for reading, storing and filtering data
 */
public class Model {
	private DataStructure edges;
	
	/*
	 * Constructor of the model class
	 * Depends on the readXML-method of the XMLReader class
	 */
	public Model() throws RuntimeException {
		try {
			edges = new ArrayListDS();
			XMLReader.readXML("krax_complete.xml", edges);
		} catch (Exception e) { // Exception needs to be specified
			throw new RuntimeException("Could not read map data from file");
		}
	}
	
	/*
	 * Returns the data stored in the model filtered according to the given data
	 * @return the filtered ArrayList<Edge>
	 */
	public int[][][] getFilteredEdges() {
		return FormatConverter.convertData(edges.getFilteredEdges());
	}

}
