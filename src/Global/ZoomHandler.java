package Global;

import static Global.MinAndMaxValues.*;

public class ZoomHandler {
	// Zoom constant
	private static final double ZOOM_CONSTANT = 0.0017;
	private static double zoomRateX = (maxX - minX) * ZOOM_CONSTANT;
	private static double zoomRateY = ((maxY - minY) / (maxX - minX)) * zoomRateX;
	private static final float LINE_WIDTH_CONSTANT = (float) 0.0000002;
	private static final int ZOOM_LIMIT = 1000;
	
	public static void valuesChanged(int x, int y, int zoom) {
		int absZoom = Math.abs(zoom);
		if (zoom > 0 && canZoomIn()) {
			// Zoom in
			
			// Change the min and max values
			minX += absZoom * zoomRateX;
			maxX -= absZoom * zoomRateX;
			minY += absZoom * zoomRateY;
			maxY -= absZoom * zoomRateY;
			
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
			int currentTypes = types; // Needed for computing the need for repaint
			updateTypesToBeDisplayed();
			
			// Compute line widths and drag increments and zoom rate
			updateLineWidths();
			DragHandler.updateDrag();
			updateZoomRate();
			
			// Compute whether or not repaint is needed
			needsRepaint();
			if (types != currentTypes) { updateDrawn(); }
			
		} else if (canZoomOut(absZoom)) { // Set an limit for how far out to zoom
			// Zoom out
			
			// Change the min and max values
			minX -= absZoom * zoomRateX;
			maxX += absZoom * zoomRateX;
			minY -= absZoom * zoomRateY;
			maxY += absZoom * zoomRateY;
			
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
			double xDif = maxX - minX;
			if ((minX - (zoomRateX * absZoom)) < MIN_X) { // minX too close to the left
				minX = MIN_X;
				maxX = minX + (xDif + (2 * (zoomRateX * absZoom)));
			} else { // Then maxX is too close to the right
				maxX = MAX_X;
				minX = maxX - (xDif + (2 * (zoomRateX * absZoom)));
			}
			
			// Then compute the y-values
			double yDif = maxY - minY;
			if ((minY - (zoomRateY * absZoom)) < MIN_Y) { // minY too close to the left
				minY = MIN_Y;
				maxY = minY + (yDif + (2 * (zoomRateY * absZoom)));
			} else { // Then maxX is too close to the right
				maxY = MAX_Y;
				minY = maxY - (yDif + (2 * (zoomRateY * absZoom)));
			}
			
			updateTypesLineWidthDragAndRepaint();
		}
	}
	
	/*
	 * Helper method for updating the zoom rate
	 */
	private static void updateZoomRate() {
		zoomRateX = (maxX - minX) * ZOOM_CONSTANT;
		zoomRateY = ((maxY - minY) / (maxX - minX)) * zoomRateX;
	}
	
	/*
	 * Helper method for updating the line widths
	 */
	private static void updateLineWidths() {
		lineWidth = (float) (maxX - minX) * LINE_WIDTH_CONSTANT;
	}
	
	/*
	 * Helper method called for each zoom-out
	 */
	private static void updateTypesLineWidthDragAndRepaint() {
		// Compute shown types
		int currentTypes = types; // Needed for computing the need for repaint
		updateTypesToBeDisplayed();
				
		// Compute line widths and drag increment and zoom rate
		updateLineWidths();
		DragHandler.updateDrag();
		updateZoomRate();
			
		// Compute whether or not repaint is needed
		needsRepaint();
		if (types != currentTypes) updateDrawn();
	}
	
	/*
	 * Helper method for checking whether or not it should be allowed to zoom out
	 */
	private static boolean canZoomIn() {
		if (((maxX - minX) - (2 * zoomRateX)) < ZOOM_LIMIT) return false;
		return true;
	}
	
	/*
	 * Helper method for checking whether or not it should be allowed to zoom out
	 */
	private static boolean canZoomOut(int zoom) {
		if (((minX - (zoomRateX * zoom)) >= MIN_X) &&
			((maxX + (zoomRateX * zoom)) <= MAX_X) &&
			((minY - (zoomRateY * zoom)) >= MIN_Y) &&
			((maxY + (zoomRateY * zoom)) <= MAX_Y)) return true;
		return false;
	}
}
