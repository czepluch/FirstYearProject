package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

/**
 * Has the responsibility of reading XML-files of the KRAX-format
 * so the data can be stored in our application
 */
public class RoadsDataReader
{
	private static final int FROM_LAT = 1;
	private static final int FROM_LONG = 2;
	private static final int TO_LAT = 3;
	private static final int TO_LONG = 4;
	private static final int DISTANCE = 5;
	private static final int SPEED = 6;
	private static final int TYPE = 7;
	
	/**
	 * Reads the contents of an XML document and stores it in a data structure.
	 * @param path The path of the XML file from which data is read
	 * @param edges The data structure in which the data is stored
	 */
	public static void readXML(String path, DataStructure edges)
	{
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String line = in.readLine(); // Read the first line
			
			while ((line != null) && (line.length() > 0))
			{	
				String[] parts = line.split(";");
				
				// Parse the line
				double fromLat = Double.parseDouble(parts[FROM_LAT]);
				double fromLong = Double.parseDouble(parts[FROM_LONG]);
				double toLat = Double.parseDouble(parts[TO_LAT]);
				double toLong = Double.parseDouble(parts[TO_LONG]);
				double distance = Double.parseDouble(parts[DISTANCE]);
				int speed = Integer.parseInt(parts[SPEED]);
				int type = translateType(Integer.parseInt(parts[TYPE]));
				
				// Store the edge in the data structure
				edges.addEdge(new KEdge(fromLat, fromLong, toLat, toLong, distance, speed, type));
				
				line = in.readLine(); // Go to the next line
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
	}
	
	/**
	 * Transforms a given road type to another type.
	 * Some types (eg. 1, 31, and 41) all go under the
	 * same category and will thus appear the same on the map.
	 * @param type The type to be translated
	 * @return The type to be drawn
	 */
	private static int translateType(int type) {
		int newType = 0;
		switch (type) {
			// Highways
			case 1: case 31: case 41:
				newType = 1; 	break;
			// Primary roads
			case 2: case 3: case 32: case 33: case 42: case 43:
				newType = 2;	break;
			// Secondary roads
			case 4: case 34: case 44:
				newType = 3;	break;
			// Other roads
			case 80:
				newType = 5;	break;
			default:
				newType = 4;	break;
		}
		return newType;
	}
}
