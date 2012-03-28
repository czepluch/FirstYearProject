package Global;

public class ZoomHandler {
	// Zoom constant
	private static final double ZOOM_CONSTANT_X = 1000;
	private static final double ZOOM_CONSTANT_Y = ((MinAndMaxValues.maxY - MinAndMaxValues.minY) / (MinAndMaxValues.maxX - MinAndMaxValues.minX)) * ZOOM_CONSTANT_X;
	private static final float LINE_WIDTH_INCREMENT = (float) 0.0005;
	private static final int ZOOM_LIMIT = 1000;
	
	public static void valuesChanged(int x, int y, int zoom) {
		if (zoom > 0 && canZoomIn()) {
			// Zoom in
			// Change the min and max values
			MinAndMaxValues.minX += ZOOM_CONSTANT_X;
			MinAndMaxValues.maxX -= ZOOM_CONSTANT_X;
			MinAndMaxValues.minY += ZOOM_CONSTANT_Y;
			MinAndMaxValues.maxY -= ZOOM_CONSTANT_Y;
			
			/*
			 * Outcommented version trying to use given x and y coordinates
			 * 
			// Compute the given coordinates in UTM format
			double c = (maxX - minX) / width;
			System.out.println("c:\t" + c);
			double xUTM = (x * c) + minX;
			double yUTM = (y * c) + minY;
			System.out.println("X as UTM:\t" + xUTM);
			System.out.println("Y as UTM:\t" + yUTM);
			
			// Compute new differences
			double difX = (maxX - minX) + ZOOM_CONSTANT_X;
			double difY = (maxY - minY) + ZOOM_CONSTANT_Y;
			System.out.println("Dif X:\t" + difX);
			System.out.println("Dif Y:\t" + difY);
			
			// Change the min and max values
			minX = xUTM - (difX / 2);
			maxX = xUTM + (difX / 2);
			minY = yUTM - (difY / 2);
			maxY = yUTM + (difY / 2);
			System.out.println("Min X\t\tMax X\t\tMin Y\t\tMax Y");
			System.out.println(minX + "\t" + maxX + "\t" + minY + "\t" + maxY);
			*/
			
			// Compute shown types
			int currentTypes = MinAndMaxValues.types; // Needed for computing the need for repaint
			MinAndMaxValues.updateTypesToBeDisplayed();
			
			// Compute line widths and drag increments
			MinAndMaxValues.lineWidth += LINE_WIDTH_INCREMENT;
			DragHandler.updateDrag();
			
			// Compute whether or not repaint is needed
			MinAndMaxValues.needsRepaint();
			if (MinAndMaxValues.types != currentTypes) { MinAndMaxValues.updateDrawn(); }
			
		} else if (canZoomOut()) { // Set an limit for how far out to zoom
			// Zoom out
			
			// Change the min and max values
			MinAndMaxValues.minX -= ZOOM_CONSTANT_X;
			MinAndMaxValues.maxX += ZOOM_CONSTANT_X;
			MinAndMaxValues.minY -= ZOOM_CONSTANT_Y;
			MinAndMaxValues.maxY += ZOOM_CONSTANT_Y;
			
			updateTypesLineWidthDragAndRepaint();
			
			
			// Additional cases for zooming out:
			// 1. Too close to left side only
			// 2. Too close to right side only
			// 3. Too close to top only
			// 4. Too close to bottom only
			// 5. Too close to to left and top
			// 6. Too close to left and bottom
			// 7. Too close to right and top
			// 8. Too close to right and bottom
		} else {
			// First compute the x-values
			double xDif = MinAndMaxValues.maxX - MinAndMaxValues.minX;
			if ((MinAndMaxValues.minX - ZOOM_CONSTANT_X) < MinAndMaxValues.MIN_X) { // minX too close to the left
				MinAndMaxValues.minX = MinAndMaxValues.MIN_X;
				MinAndMaxValues.maxX = MinAndMaxValues.minX + (xDif + (2 * ZOOM_CONSTANT_X));
			} else { // Then maxX is too close to the right
				MinAndMaxValues.maxX = MinAndMaxValues.MAX_X;
				MinAndMaxValues.minX = MinAndMaxValues.maxX - (xDif + (2 * ZOOM_CONSTANT_X));
			}
			
			// Then compute the y-values
			double yDif = MinAndMaxValues.maxY - MinAndMaxValues.minY;
			if ((MinAndMaxValues.minY - ZOOM_CONSTANT_Y) < MinAndMaxValues.MIN_Y) { // minY too close to the left
				MinAndMaxValues.minY = MinAndMaxValues.MIN_Y;
				MinAndMaxValues.maxY = MinAndMaxValues.minY + (yDif + (2 * ZOOM_CONSTANT_Y));
			} else { // Then maxX is too close to the right
				MinAndMaxValues.maxY = MinAndMaxValues.MAX_Y;
				MinAndMaxValues.minY = MinAndMaxValues.maxY - (yDif + (2 * ZOOM_CONSTANT_Y));
			}
			
			updateTypesLineWidthDragAndRepaint();
		}
	}
	
	/*
	 * Helper method called for each zoom-out
	 */
	private static void updateTypesLineWidthDragAndRepaint() {
		// Compute shown types
		int currentTypes = MinAndMaxValues.types; // Needed for computing the need for repaint
		MinAndMaxValues.updateTypesToBeDisplayed();
				
		// Compute line widths and drag increment
		MinAndMaxValues.lineWidth -= LINE_WIDTH_INCREMENT;
		DragHandler.updateDrag();
			
		// Compute whether or not repaint is needed
		MinAndMaxValues.needsRepaint();
		if (MinAndMaxValues.types != currentTypes) MinAndMaxValues.updateDrawn();
	}
	
	/*
	 * Helper method for checking whether or not it should be allowed to zoom out
	 */
	private static boolean canZoomIn() {
		if (((MinAndMaxValues.maxX - MinAndMaxValues.minX) - (2 * ZOOM_CONSTANT_X)) < ZOOM_LIMIT) return false;
		return true;
	}
	
	/*
	 * Helper method for checking whether or not it should be allowed to zoom out
	 */
	private static boolean canZoomOut() {
		if (((MinAndMaxValues.minX - ZOOM_CONSTANT_X) >= MinAndMaxValues.MIN_X) &&
			((MinAndMaxValues.maxX + ZOOM_CONSTANT_X) <= MinAndMaxValues.MAX_X) &&
			((MinAndMaxValues.minY - ZOOM_CONSTANT_Y) >= MinAndMaxValues.MIN_Y) &&
			((MinAndMaxValues.maxY + ZOOM_CONSTANT_Y) <= MinAndMaxValues.MAX_Y)) return true;
		return false;
	}
}
