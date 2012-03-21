package Controller;

/*
 * Has the responsibility of converting UTM32 coordinates to pixels
 */
public class Coordinates {
	// Information need to compute the conversions
	private double cX;
	private double cY;
	private double xTranslate;
	private double yTranslate;
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
		cX = (maxX - minX) / width;
		cY = (maxY - minY) / height;
		xTranslate = minX;
		yTranslate = minY;
		this.maxY = maxY - yTranslate;
	}
	
	/*
	 * Converts a given UTM32 x-coordinate to a corresponding pixel x-coordinate
	 * @param x the UTM32 x-coordinate to be converted
	 * @return the pixel x-coordinate
	 */
	public int convertXToPixels(double x) {
		double translatedX = x - xTranslate;
		return (int) (translatedX / cX);
	}
	
	/*
	 * Converts a given UTM32 y-coordinate to a corresponding pixel y-coordinate
	 * @param y the UTM32 y-coordinate to be converted
	 * @return the pixel y-coordinate
	 */
	public int convertYToPixels(double y) {
		double translatedY = y - yTranslate;
		translatedY = maxY - translatedY;
		return (int) (translatedY / cY);
	}
}
