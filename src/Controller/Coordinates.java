package Controller;

/*
 * Has the responsibility of converting UTM32 coordinates to pixels
 */
public class Coordinates {
	// The width and height of the canvas to which the coordinates are converted
	private int width;
	private int height;
	// Information about the "viewbox" of the canvas
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;

	/*
	 * Constructor
	 * @param minX the minimum x-coordinate (UTM32)
	 * @param maxX the maximum x-coordinate (UTM32)
	 * @param minY the minimum y-coordinate (UTM32)
	 * @param maxY the maximum y-coordinate (UTM32)
	 * @param width the width of the canvas to which the coordinates are converted
	 * @param height the width of the canvas to which the coordinates are converted
	 */
	public Coordinates(double minX, double maxX, double minY, double maxY, int width, int height) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.width = width;
		this.height = height;
	}
	
	/*
	 * Converts a given UTM32 x-coordinate to a corresponding pixel x-coordinate
	 * @param x the UTM32 x-coordinate to be converted
	 * @return the pixel x-coordinate
	 */
	public int convertXToPixels(double x) {
		return 10;
	}
	
	/*
	 * Converts a given UTM32 y-coordinate to a corresponding pixel y-coordinate
	 * @param y the UTM32 y-coordinate to be converted
	 * @return the pixel y-coordinate
	 */
	public int convertYToPixels(double y) {
		return 10;
	}
}
