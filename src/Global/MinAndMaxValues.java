package Global;

public class MinAndMaxValues {
	// Final values
	public static final double MIN_X = 442254.35659;
	public static final double MAX_X = 892658.21706;
	public static final double MIN_Y = 6049914.43018;
	public static final double MAX_Y = 6402050.98297;
	// Current values
	public static double minX = MIN_X;
	public static double maxX = MAX_X;
	public static double minY = MIN_Y;
	public static double maxY = MAX_Y;
	public static int width = 600;
	public static int height = 600;
	public static int types = 2;
	public static float lineWidth = (float) 0.1;
	public static boolean repaint = false;
	// Zoom constant
	private static final int ZOOM_CONSTANT_X = 4000;
	private static final int ZOOM_CONSTANT_Y = (int) ((maxY - minY) / (maxX - minX)) * ZOOM_CONSTANT_X;
	private static final float LINE_WIDTH_INCREMENT = (float) 0.005;
	// Other variables used for minimizing the need for zooming
	private static int zoomInsSinceLastRepaint = 0;
	// Int limits used for determining which types to be drawn
	private static final int TYPE3 = 50000;
	private static final int TYPE4 = 25000; 
	private static final int TYPE5 = 5000; 
	
	private	MinAndMaxValues () {} //neverton - fuckyeah!
	
	public static void valuesChangedZoom(int x, int y, int zoom) {
		// Needs to be stored for later
			System.out.println("Zoomed!");
			if (zoom > 0) {
				// Zoom in
				
				// Increment the zoom in count
				zoomInsSinceLastRepaint++;
				
				// Change the min and max values
				minX += ZOOM_CONSTANT_X;
				maxX -= ZOOM_CONSTANT_X;
				minY += ZOOM_CONSTANT_Y;
				maxY -= ZOOM_CONSTANT_Y;
				
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
				types = typesToBeDisplayed();
				
				// Compute line widths
				lineWidth += LINE_WIDTH_INCREMENT;
				
				// Compute whether or not repaint is needed
				if (types != currentTypes) {
					repaint = true;
					zoomInsSinceLastRepaint = 0; // Reset zoom in since last repaint counter
				}
				else repaint = false;
				
			} else if ((minX - ZOOM_CONSTANT_X) > MIN_X) { // Set an limit for how far out to zoom
				// Zoom out

				// Decrement the zoom in count if more than 0
				boolean repaintNeeded = true; // Stores info about whether or not repaint is needed to to the zoom
				if (zoomInsSinceLastRepaint > 0) {
					zoomInsSinceLastRepaint--;
					repaintNeeded = false;
				}
				
				// Change the min and max values
				minX -= ZOOM_CONSTANT_X;
				maxX += ZOOM_CONSTANT_X;
				minY -= ZOOM_CONSTANT_Y;
				maxY += ZOOM_CONSTANT_Y;
				
				// Compute shown types
				int currentTypes = types; // Needed for computing the need for repaint
				types = typesToBeDisplayed();
				
				// Compute line widths
				lineWidth -= LINE_WIDTH_INCREMENT;
				
				// Compute whether or not repaint is needed
				if (types != currentTypes || repaintNeeded) repaint = true;
				else repaint = false;
			}
	}
	
	public void valuesChangedDrag() {
		
	}
	
	/*
	 * Helper method for determining which types to be shown
	 */
	public static int typesToBeDisplayed() {
		double xDif = maxX - minX;
		if (xDif < TYPE5) return 5;
		else if (xDif < TYPE4) return 4;
		else if (xDif < TYPE3) return 3;
		else return 2;
	}
}
