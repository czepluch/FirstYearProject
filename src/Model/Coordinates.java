package Model;

import static View.ViewValues.*;
import View.ViewValues;

/**
 * Has the responsibility of converting UTM32 coordinates to pixels
 */
public class Coordinates {
	// Store a reference to the instance of the MinAndMaxValues class
	private ViewValues v = ViewValues.getInstance();
	// Information needed to compute the conversions
	private double c;
	private double xTranslate;
	private double yTranslate;
	private double translatedMaxY;
	// Static information that needs to be either computed every time or stored once computed
	private final double START_X_TO_Y_FACTOR = (v.getMAX_Y() - v.getMIN_Y()) / (v.getMAX_X() - v.getMIN_X());

	/**
	 * Constructor
	 * Uses variables stored in Global.MinAndMaxValues
	 */
	public Coordinates() {
		double newXToYFactor = v.getHeight() / v.getWidth();
		if (newXToYFactor > START_X_TO_Y_FACTOR) 	c = (v.getMaxY() - v.getMinY()) / v.getHeight();
		else 										c = (v.getMaxX() - v.getMinX()) / v.getWidth();
		xTranslate = v.getMinX();
		yTranslate = v.getMinY();
		translatedMaxY = v.getMaxY() - yTranslate;
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
