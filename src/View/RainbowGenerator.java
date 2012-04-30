package View;

import java.awt.Color;

/**
 * Class for generating the colors displayed on a trip
 */
public class RainbowGenerator {
	private static int c = 5;
	
	/**
	 * Generates the next color of the rainbow
	 * @return	The generated Color
	 */
	public static Color nextColor() {
		c = (c + 1) % 6;
		switch (c) {
			case 1: 	return Color.red;
			case 2:		return Color.orange;
			case 3:		return Color.yellow;
			case 4:		return Color.green;
			case 5:		return Color.cyan;
			default:	return Color.blue;
		}
	}
}
