package Model;

// Class used when creating the graph
public class GEdge
{
	String id1, id2;
	double weight;
	int speed;
	
	public GEdge(String id1, String id2, double weight, int speed)
	{
		this.id1 = id1;
		this.id2 = id2;
		this.weight = weight;
		this.speed = speed;
	}
}