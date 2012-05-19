package View;

import static View.ViewValues.*;
import Model.MapLocation;

public class DragHandler {
	// Store a reference to the instance of the MinAndMaxValues class
	private static ViewValues v = ViewValues.getInstance();
	// Drag constants
	private static final double DRAG_CONSTANT = 0.00125; // This constant keeps the map following the cursor when dragging
	private static double drag = (v.getMaxX() - v.getMinX()) * DRAG_CONSTANT;
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
			if ((v.getMaxX() + (xAbs * drag)) <= v.getMAX_X()) {
				v.setMaxX(v.getMaxX() + (xAbs * drag));
				v.setMinX(v.getMinX() + (xAbs * drag));
			}
		} else if (x > 0) {
			if ((v.getMinX() - (xAbs * drag)) >= v.getMIN_X()) {
				v.setMaxX(v.getMaxX() - (xAbs * drag));
				v.setMinX(v.getMinX() - (xAbs * drag));
			}
		}
		
		// Move vertically
		int yAbs = Math.abs(y);
		if (y > 0) { // Reversed y because the panel coordinate system is "upside down"
			if ((v.getMaxY() + (yAbs * drag)) <= v.getMAX_Y()) {
				v.setMaxY(v.getMaxY() + (yAbs * drag));
				v.setMinY(v.getMinY() + (yAbs * drag));
			}
		} else if (y < 0) {
			if ((v.getMinY() - (yAbs * drag)) >= v.getMIN_Y()) {
				v.setMaxY(v.getMaxY() - (yAbs * drag));
				v.setMinY(v.getMinY() - (yAbs * drag));
			}
		}
		
		// check if repaint is needed
		v.needsRepaint();
	}
	
	public static void updateDrag() {
		drag = (v.getMaxX() - v.getMinX()) * DRAG_CONSTANT;
	}
}
