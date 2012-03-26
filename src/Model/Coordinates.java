package Model;

import Global.*;

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
	public Coordinates() {
		c = (MinAndMaxValues.maxX - MinAndMaxValues.minX) / MinAndMaxValues.width;
		xTranslate = MinAndMaxValues.minX;
		yTranslate = MinAndMaxValues.minY;
		this.minX = MinAndMaxValues.minX;
		this.maxX = MinAndMaxValues.maxX;
		this.minY = MinAndMaxValues.minY;
		this.maxY = MinAndMaxValues.maxY;
		translatedMaxY = MinAndMaxValues.maxY - yTranslate;
	}
	
	/*
	 * Converts a given UTM32 x-coordinate to a corresponding pixel x-coordinate
	 * @param x the UTM32 x-coordinate to be converted
	 * @return the pixel x-coordinate
	 */
	public int convertXToPixels(double x) throws IllegalArgumentException {
		if (x < minX || x > maxX) throw new IllegalArgumentException("x value smaller than min x or larger than max x");
		double translatedX = x - xTranslate;
		return (int) (translatedX / c);
	}
	
	/*
	 * Converts a given UTM32 y-coordinate to a corresponding pixel y-coordinate
	 * @param y the UTM32 y-coordinate to be converted
	 * @return the pixel y-coordinate
	 */
	public int convertYToPixels(double y) throws IllegalArgumentException {
		if (y < minY || y > maxY) throw new IllegalArgumentException("y value smaller than min y or larger than max y");
		double translatedY = y - yTranslate;
		translatedY = translatedMaxY - translatedY;
		return (int) (translatedY / c);
	}
}
