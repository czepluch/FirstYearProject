package Controller;

/*
 * Has the responsibility of converting UTM32 coordinates to pixels
 */
public class Coordinates {
	// Information need to compute the conversions
	private double c;
	private double xTranslate;
	private double yTranslate;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double translatedMaxY;

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
		c = (maxX - minX) / width;
		xTranslate = minX;
		yTranslate = minY;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		translatedMaxY = maxY - yTranslate;
	}
	
	/*
	 * Converts a given UTM32 x-coordinate to a corresponding pixel x-coordinate
	 * @param x the UTM32 x-coordinate to be converted
	 * @return the pixel x-coordinate
	 */
	public int convertXToPixels(double x) throws IllegalArgumentException {
		if (x < minX || x > maxX) throw new IllegalArgumentException();
		double translatedX = x - xTranslate;
		return (int) (translatedX / c);
	}
	
	/*
	 * Converts a given UTM32 y-coordinate to a corresponding pixel y-coordinate
	 * @param y the UTM32 y-coordinate to be converted
	 * @return the pixel y-coordinate
	 */
	public int convertYToPixels(double y) throws IllegalArgumentException {
		if (y < minY || y > maxY) throw new IllegalArgumentException();
		double translatedY = y - yTranslate;
		translatedY = translatedMaxY - translatedY;
		return (int) (translatedY / c);
	}
}
