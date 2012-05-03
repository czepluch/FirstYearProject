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
public class XMLReader
{
	private static final int FROM_LAT = 0;
	private static final int FROM_LONG = 1;
	private static final int TO_LAT = 2;
	private static final int TO_LONG = 3;
	private static final int DISTANCE = 4;
	private static final int SPEED = 5;
	private static final int TYPE = 6;
	
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
			
			// Create the regex
			String regex = "\\s*<edge\\sid=\"" +
								"([0-9]+)" +
						   "\"\\sfromLat=\"" +
						   		"([0-9\\.]+)" +
						   "\"\\sfromLong=\"" +
						   		"([0-9\\.]+)" +
						   "\"\\stoLat=\"" +
						   		"([0-9\\.]+)" +
						   "\"\\stoLong=\"" +
						   		"([0-9\\.]+)" +
						   "\"\\sdistance=\"" +
						   		"([0-9\\.]+)" +
						   "\"\\sspeed=\"" +
						   		"([0-9]+)" +
						   "\"\\stype=\"" +
						   		"([0-9]+)" +
						   "\"/>";

			Pattern pattern = Pattern.compile(regex);
			
			// Skip first two strings
			String line = in.readLine();
			line = in.readLine();
			
			line = in.readLine(); // Read the first usable line
			
			while (line != null)
			{
				double fromLat, fromLong, toLat, toLong, distance = 0;
				int speed, type = 0;
				
				Matcher m = pattern.matcher(line);
				
				if (m.matches()) { // Parse the line
					fromLat = Double.parseDouble(m.group(2));
					fromLong = Double.parseDouble(m.group(3));
					toLat = Double.parseDouble(m.group(4));
					toLong = Double.parseDouble(m.group(5));
					distance = Double.parseDouble(m.group(6));
					speed = Integer.parseInt(m.group(7));
					type = translateType(Integer.parseInt(m.group(8)));
					
					// Store the edge
					edges.addEdge(new KEdge(fromLat, fromLong, toLat, toLong, distance, speed, type));
				} else {
					// Reached the end of the file
				}
				
				line = in.readLine(); // Go to the next line
			}
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
			default:
				newType = 4;	break;
		}
		return newType;
	}
}
