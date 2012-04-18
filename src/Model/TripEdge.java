package Model;

/*
 * Class representing an edge on a trip
 */
public class TripEdge {
	private Edge edge;
	private Edge prevEdge;
	private int time;
	private int speed;
	private Turn turn;
	
	/*
	 * Constructor
	 * @param edge 		the edge to which the TripEdge corresponds
	 * @param prevEdge 	the previous edge on the trip. null if
	 * 					no such exists
	 */
	public TripEdge(Edge edge, Edge prevEdge, int speed) {
		this.edge = edge;
		this.prevEdge = prevEdge;
		this.speed = speed;
		computeTime();
		computeTurn();
	}
	
	// Getter methods for TripEdge
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
	
	
	// Getter methods from Edge
	// ______________________________________________________________
	
	public double getFromY() {
		return edge.getFromLat();
	}

	public double getFromX() {
		return edge.getFromLong();
	}

	public double getToY() {
		return edge.getToLat();
	}

	public double getToX() {
		return edge.getToLong();
	}

	public double getDistance() {
		return edge.getDistance();
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
