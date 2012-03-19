package Model;

import nu.xom.*;

/*
 * The class in which all data is stored
 */
public class Data {
	private double fromLat, fromLong, toLat, toLong, distance;
	private int speed;
	
	public Data (double fromLat, double fromLong, 
				double toLat, double toLong,
				double distance, int speed) {
		this.fromLat = fromLat;
		this.fromLong = fromLong;
		this.toLat = toLat;
		this.toLong = toLong;
		this.distance = distance;
		this.speed = speed;
	}
	
	// Constructor to restore data from XML element
	public Data (Element data)
	{
		fromLat = Double.parseDouble(data.getFirstChildElement("fromLat").getValue());
		fromLong = Double.parseDouble(data.getFirstChildElement("fromLong").getValue());
		toLat = Double.parseDouble(data.getFirstChildElement("toLat").getValue());
		toLong = Double.parseDouble(data.getFirstChildElement("toLong").getValue());
		distance = Double.parseDouble(data.getFirstChildElement("distance").getValue());
		speed = Integer.parseInt(data.getFirstChildElement("speed").getValue());
	}
}
