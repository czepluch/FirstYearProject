package Model;

/**
 * Class representing a location on a map
 */
public class MapLocation {
	private int x;
	private int y;
	
	/**
	 * Constructor
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public MapLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x coordinated
	 * @return the x coordinate
	 */
	public int getX() { return x; }
	
	/**
	 * Gets the y-coordinate
	 * @return the y-coordinate
	 */
	public int getY() { return y; }
}
