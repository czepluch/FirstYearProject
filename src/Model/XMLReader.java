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
		
//			case 1: newType = 1; break; // Highways, type 1
//			case 2: newType = 1; break; // Almost highways, type 1
//			case 3: newType = 2; break; // Primary routes > 6m, type 2
//			case 4: newType = 2; break; // Secondary routes > 6m, type 2
//			case 5: newType = 3; break; // Roads 3 - 6m, type 3
//			case 6: newType = 4; break; // Other roads, type 4
//			case 8: newType = 5; break; // Paths, type 5
//			case 10: newType = 5; break; // Field paths, type 5
//			case 11: newType = 5; break; // Pedestrian zones, type 5
//			case 21: newType = 1; break; // Proj. highways, type 1
//			case 22: newType = 1; break; // Proj. almost highways, type1
//			case 23: newType = 2; break; // Proj. primary routes > 6m, type 2
//			case 24: newType = 2; break; // Proj. secondary routes > 6m, type 2
//			case 25: newType = 3; break; // Proj. roads 3 - 6m, type 4
//			case 26: newType = 4; break; // Proj. other roads, type 4
//			case 28: newType = 5; break; // Proj. paths, type 5
//			case 31: newType = 1; break; // Highway exits, type 1
//			case 32: newType = 1; break; // Almost highway exits, type 1
//			case 33: newType = 2; break; // Primary routes > 6m exits, type 2
//			case 34: newType = 2; break; // Secondary routes > 6m exits, type 2
//			case 35: newType = 3; break; // Roads 3 - 6m exits, type 3
//			case 41: newType = 1; break; // Highway tunnels, type 1
//			case 42: newType = 1; break; // Almost highway tunnels, type 1
//			case 43: newType = 2; break; // Primary routes > 6m tunnels, type 2
//			case 44: newType = 2; break; // Secondary routes > 6m tunnels, type 2
//			case 45: newType = 3; break; // Roads 3 - 6m tunnels, type 3
//			case 46: newType = 4; break; // Other road tunnels, type 4
//			case 48: newType = 5; break; // Path tunnels, type 5
//			// If some other type exists, it is of type 6 (not drawn)
//			default: newType = 6; 	break;
		}
		return newType;
	}
}
