package Model;

/**
 * Class representing a location on a map
 * This location stored the coordinates in
 * the type specified at the instantiation
 */
public class MapLocation<T> {
	private T x;
	private T y;
	
	/**
	 * Constructor
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public MapLocation(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x coordinated
	 * @return the x coordinate
	 */
	public T getX() { return x; }
	
	/**
	 * Gets the y-coordinate
	 * @return the y-coordinate
	 */
	public T getY() { return y; }
}
