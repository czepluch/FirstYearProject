package Model;

import nu.xom.*;

/*
 * The class in which all data is stored
 */
public class Edge {
	private double fromLat, fromLong, toLat, toLong, distance;
	private int speed, type;
	
	public Edge (double fromLat, double fromLong, 
				double toLat, double toLong,
				double distance, int speed, int type) {
		this.fromLat = fromLat;
		this.fromLong = fromLong;
		this.toLat = toLat;
		this.toLong = toLong;
		this.distance = distance;
		this.speed = speed;
		this.type = type;
	}

	@Override
	public String toString()
	{
		return String.format("(%f, %f, %f, %f, %f, %d, %d)",
							fromLat, fromLong, toLat, toLong, distance, speed, type);
	}
}
