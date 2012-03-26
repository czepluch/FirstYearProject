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
	
	private	MinAndMaxValues () {} //neverton - fuckyeah!
	
	public static void valuesChanged(int x, int y, int zoom) {
		// Compute the differences
		double oldXDif = maxX - minX;
		double oldYDif = maxY - minY;
		
		// Compute the given coordinates from pixels to utm coordinate
		double c = (oldXDif) / width;
		double xUTM = x * c;
		double yUTM = y * c;
		
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
	}
}
