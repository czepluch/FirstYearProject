package Model;

/**
 * Class used when creating the graph
 */
public class GEdge
{
	String id1, id2;
	double weight;
	int speed;
	
	/**
	 * Constructor of the GEdge class
	 * @param id1		the first node id
	 * @param id2		the second node id
	 * @param weight	the weight of the edge
	 * @param speed		the speed of the edge
	 */
	public GEdge(String id1, String id2, double weight, int speed)
	{
		this.id1 = id1;
		this.id2 = id2;
		this.weight = weight;
		this.speed = speed;
	}
}