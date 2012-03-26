package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * Has the responsibility of reading XML-files of the KRAX-format,
 * so the data can be stored
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
	public XMLReader()
	{
	}
	
	public static ArrayList<Edge> readXML(String path)
	{
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
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
				type = Integer.parseInt(line);
				
				edges.add(new Edge(fromLat, fromLong, toLat, toLong, distance, speed, type));
				line = in.readLine(); // skip line
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
		
		System.out.println(edges.size());
		return edges;
	}
	
	/*
	 * Receives a string representing an XML element 
	 * and returns its value as a string,
	 * or null if the input string was null
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
	
	public static void main(String[] args) {
		XMLReader xr = new XMLReader();
		xr.readXML("krax_complete.xml");
	}
}
