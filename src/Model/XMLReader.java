package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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
	 * Empty constructor
	 */
	public XMLReader()
	{
	}
	
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
			
			// Skip first two strings
			String line = in.readLine();
			line = in.readLine();
			
			while (line != null)
			{
				double fromLat, fromLong, toLat, toLong, distance = 0;
				int speed, type = 0;
				line = in.readLine(); // Skip line
				
				line = parseXMLLine(in.readLine(),FROM_LAT); // fromLat
				if (line != null) fromLat = Double.parseDouble(line);
				else break;		// End of XML document, assuming it has the right format
				
				line = parseXMLLine(in.readLine(),FROM_LONG); // fromLong
				fromLong = Double.parseDouble(line);
				
				line = parseXMLLine(in.readLine(),TO_LAT); // toLat
				toLat = Double.parseDouble(line);
				
				line = parseXMLLine(in.readLine(),TO_LONG); // toLong
				toLong = Double.parseDouble(line);
				
				line = parseXMLLine(in.readLine(),DISTANCE); // distance
				distance = Double.parseDouble(line);
				
				line = parseXMLLine(in.readLine(),SPEED); // speed
				speed = Integer.parseInt(line);
				
				line = parseXMLLine(in.readLine(),TYPE); // type
				type = translateType(Integer.parseInt(line));
				
				edges.addEdge(new Edge(fromLat, fromLong, toLat, toLong, distance, speed, type));
				line = in.readLine(); // skip line
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
	}
	
	/**
	 * Receives a string representing an XML element 
	 * and returns its value as a string,
	 * or null if the input string was null
	 * @param line The XML line to be parsed
	 * @param i Indicates which element the line represents
	 * @return The value of XML element as a string
	 */
	private static String parseXMLLine(String line, int i)
	{
		if (line != null)
		{
			switch (i)
			{
				case FROM_LAT:
					line = line.trim().substring(9).split("<")[0];
					break;
				case FROM_LONG:
					line = line.trim().substring(10).split("<")[0];
					break;
				case TO_LAT:
					line = line.trim().substring(7).split("<")[0];
					break;
				case TO_LONG:
					line = line.trim().substring(8).split("<")[0];
					break;
				case DISTANCE:
					line = line.trim().substring(10).split("<")[0];
					break;
				case SPEED:
					line = line.trim().substring(7).split("<")[0];
					break;
				case TYPE:
					line = line.trim().substring(6).split("<")[0];
					break;
				default:
					break;
			}
		}
		return line;
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
