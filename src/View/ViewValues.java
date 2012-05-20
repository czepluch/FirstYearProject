package View;

/**
 * Contains global variables and methods used when drawing the map.
 */
public class ViewValues {
	// Store the only instance of the class
	private static ViewValues instance = new ViewValues();
	// Final values
	private final double MIN_X = 400000;
	private final double MAX_X = 900000;
	private final double MIN_Y = 6000000;
	private final double MAX_Y = 6410000;
	private final int SEARCH_PANEL_WIDTH = 300;
	private final int EXTRA_LAYOUT_WIDTH = 12;
	private final double X_TO_Y_RATIO = (MAX_Y - MIN_Y) / (MAX_X - MIN_X);
	private final double Y_TO_X_RATIO = (MAX_X - MIN_X) / (MAX_Y - MIN_Y);
	// Current values
	private double minX = MIN_X;
	private double maxX = MAX_X;
	private double minY = MIN_Y;
	private double maxY = MAX_Y;
	private int width = 800;
	private int height = (int) (X_TO_Y_RATIO * width);
	private int types = 1;
	private final float LINE_WIDTH_CONSTANT = (float) 1;
	private boolean repaint = true;
	// Current min and max values of what is drawn
	private double drawnMinX = MIN_X;
	private double drawnMaxX = MAX_X;
	private double drawnMinY = MIN_Y;
	private double drawnMaxY = MAX_Y;
	// Limit for how too wide the drawn area can be
	private int DRAWN_TOO_BIG = 2;
	// Int limits used for determining which types to be drawn
	private final int NUMBER_OF_TYPES = 4;
	 final int TYPE2 = 250000;
	 final int TYPE3 = 25000;
	 final int TYPE4 = 11000;
	// The max speed selected
	private int maxSpeed = 130;
	
	/**
	 * Private constructor for use in the singleton design pattern
	 */
	private	ViewValues () { }
	
	/**
	 * Methods for obtaining the only instance of the MinAndMaxValues class
	 * @return The only instance of the MinAndMaxValues class
	 */
	public static ViewValues getInstance() { return instance; }
	
	
	/**
	 * Helper method for determining whether or not the new area to be shown needs repainting
	 */
	public void needsRepaint() {
		if (((drawnMaxX - drawnMinX) / (maxX - minX)) > DRAWN_TOO_BIG) updateDrawn();
		else if ((minX > drawnMinX) && (maxX < drawnMaxX) && (minY > drawnMinY) && (maxY < drawnMaxY)) repaint = false;
		else updateDrawn();
	}
	
	/**
	 * Helper method for updating the drawn min and max values
	 */
	public void updateDrawn() {
		drawnMinX = minX;
		drawnMaxX = maxX;
		drawnMinY = minY;
		drawnMaxX = maxY;
		repaint = true;
	}
	
	/**
	 * Helper method for determining which types to be shown
	 */
	public void updateTypesToBeDisplayed() {
		double xDif = maxX - minX;
		if (xDif < TYPE4) 		types = 4;
		else if (xDif < TYPE3) 	types = 3;
		else if (xDif < TYPE2) 	types = 2;
		else types = 1;
	}

	// Getter and setter methods
	// __________________________________________________________________________
	
	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public boolean isRepaint() {
		return repaint;
	}

	public void setRepaint(boolean repaint) {
		this.repaint = repaint;
	}

	public double getDrawnMinX() {
		return drawnMinX;
	}

	public void setDrawnMinX(double drawnMinX) {
		this.drawnMinX = drawnMinX;
	}

	public double getDrawnMaxX() {
		return drawnMaxX;
	}

	public void setDrawnMaxX(double drawnMaxX) {
		this.drawnMaxX = drawnMaxX;
	}

	public double getDrawnMinY() {
		return drawnMinY;
	}

	public void setDrawnMinY(double drawnMinY) {
		this.drawnMinY = drawnMinY;
	}

	public double getDrawnMaxY() {
		return drawnMaxY;
	}

	public void setDrawnMaxY(double drawnMaxY) {
		this.drawnMaxY = drawnMaxY;
	}

	public int getDRAWN_TOO_BIG() {
		return DRAWN_TOO_BIG;
	}

	public void setDRAWN_TOO_BIG(int dRAWN_TOO_BIG) {
		DRAWN_TOO_BIG = dRAWN_TOO_BIG;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getMIN_X() {
		return MIN_X;
	}

	public double getMAX_X() {
		return MAX_X;
	}

	public double getMIN_Y() {
		return MIN_Y;
	}

	public double getMAX_Y() {
		return MAX_Y;
	}

	public int getSEARCH_PANEL_WIDTH() {
		return SEARCH_PANEL_WIDTH;
	}

	public int getEXTRA_LAYOUT_WIDTH() {
		return EXTRA_LAYOUT_WIDTH;
	}

	public double getX_TO_Y_RATIO() {
		return X_TO_Y_RATIO;
	}

	public double getY_TO_X_RATIO() {
		return Y_TO_X_RATIO;
	}

	public float getLINE_WIDTH_CONSTANT() {
		return LINE_WIDTH_CONSTANT;
	}

	public int getNUMBER_OF_TYPES() {
		return NUMBER_OF_TYPES;
	}

	public int getTYPE2() {
		return TYPE2;
	}

	public int getTYPE3() {
		return TYPE3;
	}

	public int getTYPE4() {
		return TYPE4;
	}
}
