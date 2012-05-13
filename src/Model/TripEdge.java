package Model;

/*
 * Class representing an edge on a trip
 */
public class TripEdge<T> {
	private T fromX;
	private T fromY;
	private T toX;
	private T toY;
	
	private double distance;
	private double time;
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
	public TripEdge(T fromX, T fromY, T toX, T toY, double distance, TripEdge<T> prevEdge, int speed) {
		// Make sure the TripEdge is of type Double or Integer
		if (fromX.getClass() != Integer.class && fromX.getClass() != Double.class) {
			throw new IllegalStateException("TripEdge must have the type Double or Integer");
		}
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.distance = distance;
		this.speed = speed;
		computeTime();
		computeTurn(prevEdge);
	}
	
	public TripEdge(T fromX, T fromY, T toX, T toY, double distance, Turn turn, int speed) {
		// Make sure the TripEdge is of type Double or Integer
		if (fromX.getClass() != Integer.class && fromX.getClass() != Double.class) {
			throw new IllegalStateException("TripEdge must have the type Double or Integer");
		}
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.distance = distance;
		this.speed = speed;
		computeTime();
		this.turn = turn;
	}
	
	// Getter methods
	// ______________________________________________________________
	
	public int getSpeed() {
		return speed;
	}
	
	public Turn getTurn() {
		return turn;
	}
	
	public double getTime() {
		return time;
	}
	
	public T getFromY() {
		return fromY;
	}

	public T getFromX() {
		return fromX;
	}

	public T getToY() {
		return toY;
	}

	public T getToX() {
		return toX;
	}

	public double getDistance() {
		return distance;
	}
	
	@Override
	public String toString() {
		return distance + ", " + time + ", " + turn;
	}
	
	/*
	 * Helper method
	 * Computes the time according to the distance and speed
	 * @throws IllegalArgumentException
	 */
	private void computeTime() throws IllegalArgumentException {
		if ((distance == 0) && (speed == 0)) time = 0;
		else if (speed == 0) throw new IllegalArgumentException("Speed is zero");
		else if ((distance < 0) || (speed < 0)) throw new IllegalArgumentException("Negative speed and/or distance");
		else time = ((getDistance() / 1000) * 60) / speed;
	}
	
	/*
	 * Helper method
	 * Computes the time according to the two edges
	 */
	private void computeTurn(TripEdge<T> prevEdge) {
		turn = Turn.LEFT; // Mock implementation
	}
}
