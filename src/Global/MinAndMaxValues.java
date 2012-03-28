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
	// Current min and max values of what is drawn
	public static double drawnMinX = MIN_X;
	public static double drawnMaxX = MAX_X;
	public static double drawnMinY = MIN_Y;
	public static double drawnMaxY = MAX_Y;
	// Zoom constant
	private static final int ZOOM_CONSTANT_X = 4000;
	private static final int ZOOM_CONSTANT_Y = (int) ((maxY - minY) / (maxX - minX)) * ZOOM_CONSTANT_X;
	private static final float LINE_WIDTH_INCREMENT = (float) 0.005;
	// Int limits used for determining which types to be drawn
	private static final int TYPE3 = 50000;
	private static final int TYPE4 = 25000; 
	private static final int TYPE5 = 5000; 
	
	private	MinAndMaxValues () {} //neverton - fuckyeah!
	
	public static void valuesChangedZoom(int x, int y, int zoom) {
		// Needs to be stored for later
			if (zoom > 0) {
				// Zoom in
				
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
					drawnMinX = minX;
					drawnMaxX = maxX;
					drawnMinY = minY;
					drawnMaxX = maxY;
				}
				else repaint = false;
				
			} else if ((minX - ZOOM_CONSTANT_X) > MIN_X) { // Set an limit for how far out to zoom
				// Zoom out
				
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
				if (types != currentTypes || needsRepaint()) {
					repaint = true;
					drawnMinX = minX;
					drawnMaxX = maxX;
					drawnMinY = minY;
					drawnMaxX = maxY;
				}
				else repaint = false;
			}
	}
	
	public static void valuesChangedDrag(int startX, int startY, int endX, int endY) {
		int difX = endX - startX;
		int difY = endY - startY;
		System.out.println("Test\t" + difX + "\t" + difY);
		
		// Move horizontally
		
		
		// Move vertically
		
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
	
	/*
	 * Helper method for determining whether or not the new area to be shown needs repainting
	 */
	public static boolean needsRepaint() {
		if ((minX > drawnMinX) && (maxX < drawnMaxX) && (minY > drawnMinY) && (maxY < drawnMaxY)) return false;
		return true;
	}
}
