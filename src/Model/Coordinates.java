package Model;

import static Global.MinAndMaxValues.*;

/**
 * Has the responsibility of converting UTM32 coordinates to pixels
 */
public class Coordinates {
	// Information needed to compute the conversions
	private double c;
	private double xTranslate;
	private double yTranslate;
	private double translatedMaxY;
	// Static information that needs to be either computed every time or stored once computed
	private static final double START_X_TO_Y_FACTOR = (MAX_Y - MIN_Y) / (MAX_X - MIN_X);

	/**
	 * Constructor
	 * Uses variables stored in Global.MinAndMaxValues
	 */
	public Coordinates() {
		double newXToYFactor = height / width;
		if (newXToYFactor > START_X_TO_Y_FACTOR) 	c = (maxY - minY) / height;
		else 										c = (maxX - minX) / width;
		xTranslate = minX;
		yTranslate = minY;
		translatedMaxY = maxY - yTranslate;
	}
	
	/**
	 * Converts a given UTM32 x-coordinate to a corresponding pixel x-coordinate
	 * @param x The UTM32 x-coordinate to be converted
	 * @return The pixel x-coordinate
	 */
	public int convertXToPixels(double x) {
		double translatedX = x - xTranslate;
		return (int) (translatedX / c);
	}
	
	/**
	 * Converts a given UTM32 y-coordinate to a corresponding pixel y-coordinate
	 * @param y The UTM32 y-coordinate to be converted
	 * @return The pixel y-coordinate
	 */
	public int convertYToPixels(double y) {
		double translatedY = y - yTranslate;
		translatedY = translatedMaxY - translatedY;
		return (int) (translatedY / c);
	}
}
