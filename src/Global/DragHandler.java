package Global;

public class DragHandler {
	// Drag constants
	private static int drag = 500;
	private static final int DRAG_INCREMENT = 10;
	private static final int MIN_DRAG = 50;
	private static final int MAX_DRAG = 2000;
	
	public static void valuesChanged(int x, int y) {
		// Move horizontally
		if (x < 0) {
			if ((MinAndMaxValues.maxX + Math.abs(x * drag)) <= MinAndMaxValues.MAX_X) {
				MinAndMaxValues.maxX += Math.abs(x * drag);
				MinAndMaxValues.minX += Math.abs(x * drag);
			}
		} else if (x > 0) {
			if ((MinAndMaxValues.minX - Math.abs(x * drag)) >= MinAndMaxValues.MIN_X) {
				MinAndMaxValues.maxX -= Math.abs(x * drag);
				MinAndMaxValues.minX -= Math.abs(x * drag);
			}
		}
		
		// Move vertically
		if ((-y) < 0) {
			if ((MinAndMaxValues.maxY + (y * drag)) <= MinAndMaxValues.MAX_Y) {
				MinAndMaxValues.maxY += Math.abs(y * drag);
				MinAndMaxValues.minY += Math.abs(y * drag);
			}
		} else if ((-y) > 0) {
			if ((MinAndMaxValues.minY - (y * drag)) >= MinAndMaxValues.MIN_Y) {
				MinAndMaxValues.maxY -= Math.abs(y * drag);
				MinAndMaxValues.minY -= Math.abs(y * drag);
			}
		}
		
		// check if repaint is needed
		MinAndMaxValues.repaint = MinAndMaxValues.needsRepaint();
		if (MinAndMaxValues.repaint) {
			MinAndMaxValues.drawnMinX = MinAndMaxValues.minX;
			MinAndMaxValues.drawnMaxX = MinAndMaxValues.maxX;
			MinAndMaxValues.drawnMinY = MinAndMaxValues.minY;
			MinAndMaxValues.drawnMaxX = MinAndMaxValues.maxY;
		}
	}
	
	public static void incrementDrag() {
		if (drag < MAX_DRAG) drag += DRAG_INCREMENT;
	}
	
	public static void decrementDrag() {
		if (drag > MIN_DRAG) drag -= DRAG_INCREMENT;
	}
}
