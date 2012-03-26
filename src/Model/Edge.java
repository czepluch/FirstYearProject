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
	
	// Getter methods
	public double getFromLat() { return fromLat; }
	public double getFromLong() { return fromLong; }
	public double getToLat() { return toLat; }
	public double getToLong() { return toLong; }
	public double getDistance() { return distance; }
	public int getSpeed() { return speed; }
	public int getType() { return type; }

	@Override
	public String toString()
	{
		return String.format("(%f, %f, %f, %f, %f, %d, %d)",
							fromLat, fromLong, toLat, toLong, distance, speed, type);
	}
}
