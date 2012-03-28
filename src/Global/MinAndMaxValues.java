package Global;

public class MinAndMaxValues {
	public static double minX = 442254.35659;
	public static double maxX = 892658.21706;
	public static double minY = 6049914.43018;
	public static double maxY = 6402050.98297;
	public static int width = 600;
	public static int height = 600;
	public static int types = 2;
	public static float lineWidth = (float) 0.1;
	public static boolean repaint = false;
	// Int values used for setting scrolls sensativity
	private static int sens = 0;
	private static final int ROLLOVER = 1;
	// Zoom constant
	private static final int ZOOM_CONSTANT_X = 4000;
	private static final int ZOOM_CONSTANT_Y = (int) ((maxY - minY) / (maxX - minX)) * ZOOM_CONSTANT_X;
	private static final float LINE_WIDTH_INCREMENT = (float) 0.01;
	// Other variables used for minimizing the need for zooming
	private static int zoomInsSinceLastRepaint = 0;
	// Int limits used for determing which types to be drawn
	private static final int TYPE3 = 100000;
	private static final int TYPE4 = 50000; 
	private static final int TYPE5 = 10000; 
	
	private	MinAndMaxValues () {} //neverton - fuckyeah!
	
	public static void valuesChanged(int x, int y, int zoom) {
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
				if (types != currentTypes) repaint = true;
				else repaint = false;
			} else {
				// Zoom out
				System.out.println("Should have zoomed out here...");
			}
			
			
			/*
		
			// Compute a constant from the given zoom int
			double zoomConstant = 1;
			if (zoom > 0) {
				zoomConstant = 1 + (zoom / 100); // The last value "100" needs to be adjusted
			} else {
				zoomConstant = 1 - (zoom / 100);
			}
		
			// Compute the new differences
			double newXDif = oldXDif * zoomConstant;
			double newYDif = oldYDif * zoomConstant;
		
			// Compute and store the new results
			minX = xUTM - (newXDif / 2);
			maxX = xUTM + (newXDif / 2);
			minY = yUTM - (newYDif / 2);
			maxY = yUTM + (newYDif / 2);
		
			// Compute and store types
			// Missing
		
			// Compute and store line widths
			// Missing
			 */
			
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
