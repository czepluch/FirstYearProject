package View;

import static View.MinAndMaxValues.*;
import Model.MapLocation;

public class DragHandler {
	// Drag constants
	private static final double DRAG_CONSTANT = 0.00125; // This constant keeps the map following the cursor when dragging
	private static double drag = (maxX - minX) * DRAG_CONSTANT;
	private static final int MIN_DRAG = 100;
	private static final int MAX_DRAG = 2000;
	
	/**
	 * This method does what needs to be done when the user tries to drag the map
	 * @param x How far the cursor is moved along the x-axis
	 * @param y How far the cursor is moved along the y-axis
	 */
	public static void valuesChanged(int x, int y) {
		// Move horizontally
		int xAbs = Math.abs(x);
		if (x < 0) {
			if ((maxX + (xAbs * drag)) <= MAX_X) {
				maxX += (xAbs * drag);
				minX += (xAbs * drag);
			}
		} else if (x > 0) {
			if ((minX - (xAbs * drag)) >= MIN_X) {
				maxX -= (xAbs * drag);
				minX -= (xAbs * drag);
			}
		}
		
		// Move vertically
		int yAbs = Math.abs(y);
		if (y > 0) { // Reversed y because the panel coordinate system is "upside down"
			if ((maxY + (yAbs * drag)) <= MAX_Y) {
				maxY += (yAbs * drag);
				minY += (yAbs * drag);
			}
		} else if (y < 0) {
			if ((minY - (yAbs * drag)) >= MIN_Y) {
				maxY -= (yAbs * drag);
				minY -= (yAbs * drag);
			}
		}
		
		// check if repaint is needed
		needsRepaint();
	}
	
	public static void updateDrag() {
		drag = (maxX - minX) * DRAG_CONSTANT;
	}
}
