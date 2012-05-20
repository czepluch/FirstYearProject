package View;

import java.util.ArrayList;

import java.util.List;

import Model.MapLocation;
import Model.Trip;
import Model.TripEdge;

public class ZoomHandler {
	// Store a reference to the instance of the MinAndMaxValues class
	private static ViewValues v = ViewValues.getInstance();
	// Zoom constant
	private static final double ZOOM_CONSTANT = 0.0017;
	private static double zoomRateX = (v.getMaxX() - v.getMinX()) * ZOOM_CONSTANT;
	private static double zoomRateY = ((v.getMaxY() - v.getMinY()) / (v.getMaxX() - v.getMinX())) * zoomRateX;
	private static final int ZOOM_LIMIT = 1000;
	private static final int DRAG_WHEN_ZOOM_IN = 2;
	private static final double ZOOM_TO_TRIP_PADDING_CONSTANT = 0.1;
	// Values needed only be computed once
	private static final double START_X_DIF = v.getMAX_X() - v.getMIN_X();
	private static final double START_Y_DIF = v.getMAX_Y() - v.getMIN_Y();
	
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
			double c = (v.getMaxX() - v.getMinX()) / v.getWidth();
			double xUTM = (x * c) + v.getMinX();
			double yUTM = (y * c) + v.getMinY();
			
			// Compute the x-values
			double xDif = v.getMaxX() - v.getMinX();
			double xMinToX = xUTM - v.getMinX();
			double xMaxToX = v.getMaxX() - xUTM;
			
			double xMinZoomFactor = xMinToX / xDif;
			double xMaxZoomFactor = xMaxToX / xDif;
			double computedXZoom = 2 * (absZoom * zoomRateX);
			// Update the min and max x-values
			v.setMinX(v.getMinX() + xMinZoomFactor * computedXZoom);
			v.setMaxX(v.getMaxX() - xMaxZoomFactor * computedXZoom);
			
			// Compute the y-values
			double yDif = v.getMaxY() - v.getMinY();
			double yMinToY = yUTM - v.getMinY();
			double yMaxToY = v.getMaxY() - yUTM;
			
			double yMinZoomFactor = yMinToY / yDif;
			double yMaxZoomFactor = yMaxToY / yDif;
			double computedYZoom = 2 * (absZoom * zoomRateY);
			// Update the min and max y-values
			v.setMinY(v.getMinY() + yMaxZoomFactor * computedYZoom);
			v.setMaxY(v.getMaxY() - yMinZoomFactor * computedYZoom);
			
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
			int currentTypes = v.getTypes(); // Needed for computing the need for repaint
			v.updateTypesToBeDisplayed();
			
			// Compute drag increments and zoom rate
			DragHandler.updateDrag();
			updateZoomRate();
			
			// Compute whether or not repaint is needed
			v.needsRepaint();
			if (v.getTypes() != currentTypes) v.updateDrawn();
			
		} else if (canZoomOut(absZoom)) { // Set a limit for how far out to zoom
			// Zoom out
			
			// Change the min and max values
			v.setMinX(v.getMinX() - absZoom * zoomRateX);
			v.setMaxX(v.getMaxX() + absZoom * zoomRateX);
			v.setMinY(v.getMinY() - absZoom * zoomRateY);
			v.setMaxY(v.getMaxY() + absZoom * zoomRateY);
			
			updateTypesDragAndRepaint();
			
			
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
			double xDif = v.getMaxX() - v.getMinX();
			if ((v.getMinX() - (zoomRateX * absZoom)) < v.getMIN_X()) { // minX too close to the left
				v.setMinX(v.getMIN_X());
				v.setMaxX(v.getMinX() + (xDif + (2 * (zoomRateX * absZoom))));
			} else if ((v.getMaxX() + (zoomRateX * absZoom)) > v.getMAX_X()) { // Then maxX is too close to the right
				v.setMaxX(v.getMAX_X());
				v.setMinX(v.getMaxX() - (xDif + (2 * (zoomRateX * absZoom))));
			} else { // then y was the problem, x is handled normally
				v.setMinX(v.getMinX() - absZoom * zoomRateX);
				v.setMaxX(v.getMaxX() + absZoom * zoomRateX);
			}
			
			// Then compute the y-values
			double yDif = v.getMaxY() - v.getMinY();
			if ((v.getMinY() - (zoomRateY * absZoom)) < v.getMIN_Y()) { // minY too close to the left
				v.setMinY(v.getMIN_Y());
				v.setMaxY(v.getMinY() + (yDif + (2 * (zoomRateY * absZoom))));
			} else if ((v.getMaxY() + (zoomRateY * absZoom)) > v.getMAX_Y()) { // maxX is too close to the right
				v.setMaxY(v.getMAX_Y());
				v.setMinY(v.getMaxY() - (yDif + (2 * (zoomRateY * absZoom))));
			} else { // then x was the problem, y is handled normally
				v.setMinY(v.getMinY() - absZoom * zoomRateY);
				v.setMaxY(v.getMaxY() + absZoom * zoomRateY);
			}
			
			updateTypesDragAndRepaint();
		}
	}
	
	public static void zoomTo(MapLocation<Double> location, MapListener listener) {
		// Compute the constant to be added / subtracted from the coordinates
		double coordConstant = ZOOM_LIMIT / 2;
		double x = location.getX();
		double y = location.getY();
		// Set the min and max values
		v.setMaxX(x + coordConstant);
		v.setMinX(x - coordConstant);
		
		double yDif = (v.getMaxX() - v.getMinX()) * v.getX_TO_Y_RATIO();
		double yDifConstant = yDif / 2;
		
		v.setMaxY(y + yDifConstant);
		v.setMinY(y - yDifConstant);
		// Update what needs to be updated
		updateTypesDragAndRepaint();
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
			if (localXToYRatio > v.getX_TO_Y_RATIO()) {
				// The height is dominant
				double yDif = localMaxY - localMinY;
				double padding = yDif * ZOOM_TO_TRIP_PADDING_CONSTANT;
				// Set the min and max values
				v.setMaxY(localMaxY + padding);
				v.setMinY(localMinY - padding);
				
				double xDif = (v.getMaxY() - v.getMinY()) * v.getY_TO_X_RATIO();
				double xDifConstant = xDif / 2;
				
				double centerX = localMinX + ((localMaxX - localMinX) / 2);
				
				v.setMaxX(centerX + xDifConstant);
				v.setMinX(centerX - xDifConstant);
				
			} else {
				// The width is dominant
				double xDif = localMaxX - localMinX;
				double padding = xDif * ZOOM_TO_TRIP_PADDING_CONSTANT;
				
				// Set the min and max values
				v.setMaxX(localMaxX + padding);
				v.setMinX(localMinX - padding);
				
				double yDif = (v.getMaxX() - v.getMinX()) * v.getX_TO_Y_RATIO();
				double yDifConstant = yDif / 2;
				
				double centerY = localMinY + ((localMaxY - localMinY) / 2);
				
				v.setMaxY(centerY + yDifConstant);
				v.setMinY(centerY - yDifConstant);
			}
			
			// Update what needs to be updated
			updateTypesDragAndRepaint();
			// Notify the MapListener
			listener.viewboxUpdated();
		}
	}
	
	public static void zoomOut(MapListener listener) {
		// Set the min and max values
		v.setMaxX(v.getMAX_X());
		v.setMinX(v.getMIN_X());
		v.setMaxY(v.getMAX_Y());
		v.setMinY(v.getMIN_Y());
		// Update what needs to be updated
		updateTypesDragAndRepaint();
		// Notify the MapListener
		listener.viewboxUpdated();
	}
	
	/*
	 * Helper method for updating the zoom rate
	 */
	private static void updateZoomRate() {
		zoomRateX = (v.getMaxX() - v.getMinX()) * ZOOM_CONSTANT;
		zoomRateY = ((v.getMaxY() - v.getMinY()) / (v.getMaxX() - v.getMinX())) * zoomRateX;
	}
	
	/*
	 * Helper method called for each zoom-out
	 */
	private static void updateTypesDragAndRepaint() {
		// Compute shown types
		int currentTypes = v.getTypes(); // Needed for computing the need for repaint
		v.updateTypesToBeDisplayed();
				
		// Compute drag increment and zoom rate
		DragHandler.updateDrag();
		updateZoomRate();
			
		// Compute whether or not repaint is needed
		v.needsRepaint();
		if (v.getTypes() != currentTypes) v.updateDrawn();
	}
	
	/*
	 * Helper method for checking whether or not it should be allowed to zoom out
	 */
	private static boolean canZoomIn(int zoom) {
		if (((v.getMaxX() - v.getMinX()) - (2 * (zoomRateX * zoom))) < ZOOM_LIMIT) return false;
		return true;
	}
	
	/*
	 * Helper method for checking whether or not it should be allowed to zoom out
	 */
	private static boolean canZoomOut(int zoom) {
		if (((v.getMinX() - (zoomRateX * zoom)) >= v.getMIN_X()) &&
			((v.getMaxX() + (zoomRateX * zoom)) <= v.getMAX_X()) &&
			((v.getMinY() - (zoomRateY * zoom)) >= v.getMIN_Y()) &&
			((v.getMaxY() + (zoomRateY * zoom)) <= v.getMAX_Y())) return true;
		return false;
	}
	
	/*
	 * Second helper method for checking whether or not it should be allowed to zoom out
	 * This one checks if the coordinates dif (max - min) is too big
	 */
	private static boolean canZoomOutDif(int zoom) {
		if ((((v.getMaxX() - v.getMinX()) + (2 * (zoomRateX * zoom))) < START_X_DIF) &&
				(((v.getMaxY() - v.getMinY()) + (2 * (zoomRateY * zoom))) < START_Y_DIF)) return true;
		return false;
	}
}
