package Global;

/**
 * Contains global variables and methods used when drawing the map.
 */
public class MinAndMaxValues {
	// Final values
	public static final double MIN_X = 400000;
	public static final double MAX_X = 900000;
	public static final double MIN_Y = 6000000;
	public static final double MAX_Y = 6410000;
	public static final int SEARCH_PANEL_WIDTH = 300;
	public static final int EXTRA_LAYOUT_WIDTH = 12;
	// Current values
	public static double minX = MIN_X;
	public static double maxX = MAX_X;
	public static double minY = MIN_Y;
	public static double maxY = MAX_Y;
	public static int width = 800;
	public static int height = (int) (((MAX_Y - MIN_Y) / (MAX_X - MIN_X)) * width);
	public static int types = 2;
	public static final float LINE_WIDTH_CONSTANT = (float) 1;
	public static float lineWidth = (float) 1;
	public static boolean repaint = true;
	// Current min and max values of what is drawn
	public static double drawnMinX = MIN_X;
	public static double drawnMaxX = MAX_X;
	public static double drawnMinY = MIN_Y;
	public static double drawnMaxY = MAX_Y;
	// Limit for how too wide the drawn area can be
	private static int DRAWN_TOO_BIG = 2;
	// Int limits used for determining which types to be drawn
	public static final int NUMBER_OF_TYPES = 4;
	static final int TYPE2 = 550000;
	static final int TYPE3 = 25000;
	static final int TYPE4 = 11000;
	// The max speed selected
	public static int maxSpeed = 130;
	
	private	MinAndMaxValues () {} //neverton - fuckyeah!
	
	/**
	 * Helper method for determining whether or not the new area to be shown needs repainting
	 */
	public static void needsRepaint() {
		if (((drawnMaxX - drawnMinX) / (maxX - minX)) > DRAWN_TOO_BIG) updateDrawn();
		else if ((minX > drawnMinX) && (maxX < drawnMaxX) && (minY > drawnMinY) && (maxY < drawnMaxY)) repaint = false;
		else updateDrawn();
	}
	
	/**
	 * Helper method for updating the drawn min and max values
	 */
	public static void updateDrawn() {
		drawnMinX = minX;
		drawnMaxX = maxX;
		drawnMinY = minY;
		drawnMaxX = maxY;
		repaint = true;
	}
	
	/**
	 * Helper method for determining which types to be shown
	 */
	public static void updateTypesToBeDisplayed() {
		double xDif = maxX - minX;
		if (xDif < TYPE4) 		types = 4;
		else if (xDif < TYPE3) 	types = 3;
		else if (xDif < TYPE2) 	types = 2;
		else types = 1;
	}
}
