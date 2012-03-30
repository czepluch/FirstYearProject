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
	public static int types = 1;
	public static float lineWidth = (float) 0.1;
	public static boolean repaint = true;
	// Current min and max values of what is drawn
	public static double drawnMinX = MIN_X;
	public static double drawnMaxX = MAX_X;
	public static double drawnMinY = MIN_Y;
	public static double drawnMaxY = MAX_Y;
	// Limit for how too wide the drawn area can be
	private static int DRAWN_TOO_BIG = 2;
	// Int limits used for determining which types to be drawn
	static final int TYPE2 = 200000;
	static final int TYPE3 = 25000;
	static final int TYPE4 = 10000; 
	static final int TYPE5 = 5000; 
	
	private	MinAndMaxValues () {} //neverton - fuckyeah!
	
	/*
	 * Helper method for determining whether or not the new area to be shown needs repainting
	 */
	static void needsRepaint() {
		if (((drawnMaxX - drawnMinX) / (maxX - minX)) > DRAWN_TOO_BIG) updateDrawn();
		else if ((minX > drawnMinX) && (maxX < drawnMaxX) && (minY > drawnMinY) && (maxY < drawnMaxY)) repaint = false;
		else updateDrawn();
	}
	
	/*
	 * Helper method for updating the drawn min and max values
	 */
	static void updateDrawn() {
		drawnMinX = minX;
		drawnMaxX = maxX;
		drawnMinY = minY;
		drawnMaxX = maxY;
		repaint = true;
	}
	
	/*
	 * Helper method for determining which types to be shown
	 */
	public static void updateTypesToBeDisplayed() {
		double xDif = maxX - minX;
		if (xDif < TYPE5) 		types = 5;
		else if (xDif < TYPE4) 	types = 4;
		else if (xDif < TYPE3) 	types = 3;
		else if (xDif < TYPE2) 	types = 2;
		else types = 1;
	}
}
