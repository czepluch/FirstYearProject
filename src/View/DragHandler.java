package View;

import static Global.MinAndMaxValues.*;

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
	
	/**
	 * Moves the current viewbox to the given location
	 * @param x the x coordinate about which the view is to be centered
	 * @param y the y coordinate about which the view is to be centered
	 */
	public static void moveTo(double x, double y) {
		int steps = 30;
		int sleepTime = 15;
		int xStep = (int) (((((maxX - minX) / 2) - x) / steps) / drag);
		int yStep = (int) (((((maxY - minY) / 2) - y) / steps) / drag);
		
		// For each step, move the screen and wait
		for (int i = 0; i < steps; i++) {
			valuesChanged(xStep, yStep);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException ie) {
				// Do nothing
			}
		}
	}
}
