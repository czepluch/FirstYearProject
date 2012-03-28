package Global;

public class DragHandler {
	// Drag constants
	private static final double DRAG_CONSTANT = 0.0005;
	private static double drag = (MinAndMaxValues.maxX - MinAndMaxValues.minX) * DRAG_CONSTANT;
	private static final int MIN_DRAG = 100;
	private static final int MAX_DRAG = 2000;
	
	public static void valuesChanged(int x, int y) {
		// Move horizontally
		int xAbs = Math.abs(x);
		if (x < 0) {
			if ((MinAndMaxValues.maxX + (xAbs * drag)) <= MinAndMaxValues.MAX_X) {
				MinAndMaxValues.maxX += (xAbs * drag);
				MinAndMaxValues.minX += (xAbs * drag);
			}
		} else if (x > 0) {
			if ((MinAndMaxValues.minX - (xAbs * drag)) >= MinAndMaxValues.MIN_X) {
				MinAndMaxValues.maxX -= (xAbs * drag);
				MinAndMaxValues.minX -= (xAbs * drag);
			}
		}
		
		// Move vertically
		int yAbs = Math.abs(y);
		if (y > 0) { // Reversed y because the panel coordinate system is "upside down"
			if ((MinAndMaxValues.maxY + (yAbs * drag)) <= MinAndMaxValues.MAX_Y) {
				MinAndMaxValues.maxY += (yAbs * drag);
				MinAndMaxValues.minY += (yAbs * drag);
			}
		} else if (y < 0) {
			if ((MinAndMaxValues.minY - (yAbs * drag)) >= MinAndMaxValues.MIN_Y) {
				MinAndMaxValues.maxY -= (yAbs * drag);
				MinAndMaxValues.minY -= (yAbs * drag);
			}
		}
		
		// check if repaint is needed
		MinAndMaxValues.needsRepaint();
	}
	
	public static void updateDrag() {
		drag = (MinAndMaxValues.maxX - MinAndMaxValues.minX) * DRAG_CONSTANT;
		System.out.println("Drag:\t" + drag);
	}
}
