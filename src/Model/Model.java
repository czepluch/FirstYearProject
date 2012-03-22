package Model;

import java.util.ArrayList;

/*
 * The part of the application taking care of data handling
 * Includes classes for reading, storing and filtering data
 */
public class Model {
	private ArrayList<Data> edges;
	
	/*
	 * Constructor of the model class
	 * Depends on the readXML-method of the XMLReader class
	 */
	public Model() throws RuntimeException {
		try {
			// edges = XMLReader.readXML("krax.xml");
		} catch (Exception e) { // Exception needs to be specified
			throw new RuntimeException("Could not read map data from file");
		}
	}
	
	/*
	 * Returns the data stored in the model
	 * @return the ArrayList<Edge> stored
	 */
	public ArrayList<Data> getData() {
		return edges;
	}
	
	/*
	 * Returns the data stored in the model filtered according to the given data
	 * @return the filtered ArrayList<Edge>
	 */
	public ArrayList<Data> getData(double minX, double maxX, double minY, double maxY) {
		// Use the methods of the DataFilter class to filter then stored data according the the given data
		// return DataFilter.filterData(edges, minX, maxX, minY, maxY);
		return edges; // The implementation above is more correct but currently not working
	}
}
