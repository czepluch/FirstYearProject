package Model;

/**
 * This class represents an edge in our roadmap.
 * An edge is represented by two points, a length (or distance), a speed limit, and a type.
 */
public class KEdge {
	private double fromLat, fromLong, toLat, toLong, distance;
	private int speed, type;
	
	/**
	 * Constructor
	 * @param fromLat The latitude of the first endpoint
	 * @param fromLong The longitude of the first endpoint
	 * @param toLat The latitude of the second endpoint
	 * @param toLong The latitude of the second endpoint
	 * @param distance The distance
	 * @param speed The speed limit
	 * @param type The type
	 */
	public KEdge (double fromLat, double fromLong, 
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
	
	/**
	 * Returns the latitude of the first endpoint
	 * @return The latitude of the first endpoint
	 */
	public double getFromLat() { return fromLat; }
	
	/**
	 * Returns the longitude of the first endpoint
	 * @return The longitude of the first endpoint
	 */
	public double getFromLong() { return fromLong; }
	
	/**
	 * Returns the latitude of the second endpoint
	 * @return The latitude of the second endpoint
	 */
	public double getToLat() { return toLat; }
	
	/**
	 * Returns the longitude of the second endpoint
	 * @return The longitude of the second endpoint
	 */
	public double getToLong() { return toLong; }
	
	/**
	 * Returns the distance of the edge
	 * @return The distance of the edge
	 */
	public double getDistance() { return distance; }
	
	/**
	 * Returns the speed limit of the edge
	 * @return The speed limit of the edge
	 */
	public int getSpeed() { return speed; }
	
	/**
	 * Returns the edge type
	 * @return The edge type
	 */
	public int getType() { return type; }

	@Override
	public String toString()
	{
		return String.format("(%f, %f, %f, %f, %f, %d, %d)",
							fromLat, fromLong, toLat, toLong, distance, speed, type);
	}
}
