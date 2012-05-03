package View;

import static Global.MinAndMaxValues.*;

import java.util.ArrayList;

import java.util.List;

import Model.MapLocation;
import Model.Trip;
import Model.TripEdge;

public class ZoomHandler {
	// Zoom constant
	private static final double ZOOM_CONSTANT = 0.0017;
	private static double zoomRateX = (maxX - minX) * ZOOM_CONSTANT;
	private static double zoomRateY = ((maxY - minY) / (maxX - minX)) * zoomRateX;
	private static final int ZOOM_LIMIT = 1000;
	private static final int DRAG_WHEN_ZOOM_IN = 2;
	private static final double ZOOM_TO_TRIP_PADDING_CONSTANT = 0.1;
	// Values needed only be computed once
	private static final double START_X_DIF = MAX_X - MIN_X;
	private static final double START_Y_DIF = MAX_Y - MIN_Y;
	
	/**
	 * This method does what needs to be done when the user tries to zoom in or out
	 * @param x The x-coordinate of the cursor
	 * @param y The y-coordinate of the cursor
	 * @param zoom Negative if zooming in, positive if zooming out
	 */
	public static void valuesChanged(int x, int y, int zoom) {
		int absZoom = Math.abs(zoom);
		if (zoom < 0 && canZoomIn(absZoom)) {
			// Zoom in
			
			// First compute the x and y parameters to UTM coordinates
			double c = (maxX - minX) / width;
			double xUTM = (x * c) + minX;
			double yUTM = (y * c) + minY;
			
			// Compute the x-values
			double xDif = maxX - minX;
			double xMinToX = xUTM - minX;
			double xMaxToX = maxX - xUTM;
			
			double xMinZoomFactor = xMinToX / xDif;
			double xMaxZoomFactor = xMaxToX / xDif;
			double computedXZoom = 2 * (absZoom * zoomRateX);
			// Update the min and max x-values
			minX += xMinZoomFactor * computedXZoom;
			maxX -= xMaxZoomFactor * computedXZoom;
			
			// Compute the y-values
			double yDif = maxY - minY;
			double yMinToY = yUTM - minY;
			double yMaxToY = maxY - yUTM;
			
			double yMinZoomFactor = yMinToY / yDif;
			double yMaxZoomFactor = yMaxToY / yDif;
			double computedYZoom = 2 * (absZoom * zoomRateY);
			// Update the min and max y-values
			minY += yMaxZoomFactor * computedYZoom;
			maxY -= yMinZoomFactor * computedYZoom;
			
			// Call the drag-handler, making the view move according to the zoom
			int xDrag = 0;
			int yDrag = 0;
			// First horizontally
			if (xMinZoomFactor > xMaxZoomFactor) xDrag = (int) (-(DRAG_WHEN_ZOOM_IN * (1 - xMinZoomFactor)));
			else xDrag = (int) (DRAG_WHEN_ZOOM_IN * (1 - xMaxZoomFactor));
			// Then vertically
			if (yMinZoomFactor > yMaxZoomFactor) yDrag = (int) (-(DRAG_WHEN_ZOOM_IN * (1 - yMinZoomFactor)));
			else yDrag = (int) (DRAG_WHEN_ZOOM_IN * (1 - yMaxZoomFactor));
			
			DragHandler.valuesChanged(xDrag, yDrag);
			
			// Compute shown types
			int currentTypes = types; // Needed for computing the need for repaint
			updateTypesToBeDisplayed();
			
			// Compute line widths and drag increments and zoom rate
			updateLineWidths();
			DragHandler.updateDrag();
			updateZoomRate();
			
			// Compute whether or not repaint is needed
			needsRepaint();
			if (types != currentTypes) updateDrawn();
			
		} else if (canZoomOut(absZoom)) { // Set a limit for how far out to zoom
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
		} else if (canZoomOutDif(absZoom)) {
			// First compute the x-values
			double xDif = maxX - minX;
			if ((minX - (zoomRateX * absZoom)) < MIN_X) { // minX too close to the left
				minX = MIN_X;
				maxX = minX + (xDif + (2 * (zoomRateX * absZoom)));
			} else if ((maxX + (zoomRateX * absZoom)) > MAX_X) { // Then maxX is too close to the right
				maxX = MAX_X;
				minX = maxX - (xDif + (2 * (zoomRateX * absZoom)));
			} else { // then y was the problem, x is handled normally
				minX -= absZoom * zoomRateX;
				maxX += absZoom * zoomRateX;
			}
			
			// Then compute the y-values
			double yDif = maxY - minY;
			if ((minY - (zoomRateY * absZoom)) < MIN_Y) { // minY too close to the left
				minY = MIN_Y;
				maxY = minY + (yDif + (2 * (zoomRateY * absZoom)));
			} else if ((maxY + (zoomRateY * absZoom)) > MAX_Y) { // maxX is too close to the right
				maxY = MAX_Y;
				minY = maxY - (yDif + (2 * (zoomRateY * absZoom)));
			} else { // then x was the problem, y is handled normally
				minY -= absZoom * zoomRateY;
				maxY += absZoom * zoomRateY;
			}
			
			updateTypesLineWidthDragAndRepaint();
		}
	}
	
	public static void zoomTo(MapLocation<Double> location, MapListener listener) {
		// Compute the constant to be added / subtracted from the coordinates
		double coordConstant = ZOOM_LIMIT / 2;
		double x = location.getX();
		double y = location.getY();
		// Set the min and max values
		maxX = x + coordConstant;
		minX = x - coordConstant;
		
		double yDif = (maxX - minX) * X_TO_Y_RATIO;
		double yDifConstant = yDif / 2;
		
		maxY = y + yDifConstant;
		minY = y - yDifConstant;
		// Update what needs to be updated
		updateTypesLineWidthDragAndRepaint();
		// Notify the MapListener
		listener.viewboxUpdated();
	}
	
	public static void zoomTo(Trip<Double> trip, MapListener listener) {
		// Find the smallest and highest x- and y-values
		double localMinX = 999999999;
		double localMaxX = -999999999;
		double localMinY = 999999999;
		double localMaxY = -999999999;
		
		List<TripEdge<Double>> edges = trip.getEdges();
		
		// Make sure the trip is not empty
		if (edges.size() > 0) {
			// Look at the "to" coordinates
			// That means that we have to check the "from" node
			// of the first edge first
			
			TripEdge<Double> ed = edges.get(0);
			double x = ed.getFromX();
			double y = ed.getFromY();
			if (x < localMinX) localMinX = x;
			if (x > localMaxX) localMaxX = x;
			if (y < localMinY) localMinY = y;
			if (y < localMaxY) localMaxY = y;
					
			// Then check the rest
			for (TripEdge<Double> e : edges) {
				x = e.getToX();
				y = e.getToY();
				if (x < localMinX) localMinX = x;
				if (x > localMaxX) localMaxX = x;
				if (y < localMinY) localMinY = y;
				if (y > localMaxY) localMaxY = y;
			}
			
			// Check whether the width or height is "dominant"
			double localXToYRatio = (localMaxY - localMinY) / (localMaxX - localMinX);
			if (localXToYRatio > X_TO_Y_RATIO) {
				// The height is dominant
				double yDif = localMaxY - localMinY;
				double padding = yDif * ZOOM_TO_TRIP_PADDING_CONSTANT;
				// Set the min and max values
				maxY = localMaxY + padding;
				minY = localMinY - padding;
				
				double xDif = (maxY - minY) * Y_TO_X_RATIO;
				double xDifConstant = xDif / 2;
				
				double centerX = localMinX + ((localMaxX - localMinX) / 2);
				
				maxX = centerX + xDifConstant;
				minX = centerX - xDifConstant;
				
			} else {
				// The width is dominant
				double xDif = localMaxX - localMinX;
				double padding = xDif * ZOOM_TO_TRIP_PADDING_CONSTANT;
				
				// Set the min and max values
				maxX = localMaxX + padding;
				minX = localMinX - padding;
				
				double yDif = (maxX - minX) * X_TO_Y_RATIO;
				double yDifConstant = yDif / 2;
				
				double centerY = localMinY + ((localMaxY - localMinY) / 2);
				
				maxY = centerY + yDifConstant;
				minY = centerY - yDifConstant;
			}
			
			// Update what needs to be updated
			updateTypesLineWidthDragAndRepaint();
			// Notify the MapListener
			listener.viewboxUpdated();
		}
	}
	
	public static void zoomOut(MapListener listener) {
		// Set the min and max values
		maxX = MAX_X;
		minX = MIN_X;
		maxY = MAX_Y;
		minY = MIN_Y;
		// Update what needs to be updated
		updateTypesLineWidthDragAndRepaint();
		// Notify the MapListener
		listener.viewboxUpdated();
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
		lineWidth = (float) 1;
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
	private static boolean canZoomIn(int zoom) {
		if (((maxX - minX) - (2 * (zoomRateX * zoom))) < ZOOM_LIMIT) return false;
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
	
	/*
	 * Second helper method for checking whether or not it should be allowed to zoom out
	 * This one checks if the coordinates dif (max - min) is too big
	 */
	private static boolean canZoomOutDif(int zoom) {
		if ((((maxX - minX) + (2 * (zoomRateX * zoom))) < START_X_DIF) &&
				(((maxY - minY) + (2 * (zoomRateY * zoom))) < START_Y_DIF)) return true;
		return false;
	}
}
