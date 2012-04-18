package Model;

/*
 * Class representing an edge on a trip
 */
public class TripEdge {
	private TripEdge prevEdge;
	
	private int fromX;
	private int fromY;
	private int toX;
	private int toY;
	
	private double distance;
	private int time;
	private int speed;
	private Turn turn;
	
	/*
	 * Constructor
	 * @param fromX		the first from coordinate in pixels
	 * @param fromY		the second from coordinate in pixels
	 * @param toX		the first to coordinate in pixels
	 * @param toY		the first to coordinate in pixels
	 * @param distance	the distance of the edge
	 * @param prevEdge	the previous edge on the trip
	 * 					null if no such exists
	 * @param speed		the speed of the edge
	 */
	public TripEdge(int fromX, int fromY, int toX, int toY, double distance, TripEdge prevEdge, int speed) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.distance = distance;
		this.prevEdge = prevEdge;
		this.speed = speed;
		computeTime();
		computeTurn();
	}
	
	// Getter methods
	// ______________________________________________________________
	
	public int getSpeed() {
		return speed;
	}
	
	public Turn getTurn() {
		return turn;
	}
	
	public int getTime() {
		return time;
	}
	
	public double getFromY() {
		return fromY;
	}

	public double getFromX() {
		return fromX;
	}

	public double getToY() {
		return toY;
	}

	public double getToX() {
		return toX;
	}

	public double getDistance() {
		return distance;
	}
	
	/*
	 * Helper method
	 * Computes the time according to the distance and speed
	 */
	private void computeTime() {
		time = (int) (getDistance() / speed);
	}
	
	/*
	 * Helper method
	 * Computes the time according to the two edges
	 */
	private void computeTurn() {
		turn = Turn.LEFT; // Mock implementation
	}
}
