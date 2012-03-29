package KDModel;

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

	public double getFromLat() {
		return fromLat;
	}

	public void setFromLat(double fromLat) {
		this.fromLat = fromLat;
	}

	public double getFromLong() {
		return fromLong;
	}

	public void setFromLong(double fromLong) {
		this.fromLong = fromLong;
	}

	public double getToLat() {
		return toLat;
	}

	public void setToLat(double toLat) {
		this.toLat = toLat;
	}

	public double getToLong() {
		return toLong;
	}

	public void setToLong(double toLong) {
		this.toLong = toLong;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
