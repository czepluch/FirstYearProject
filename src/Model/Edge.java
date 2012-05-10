package Model;

//Class used to represent an edge in the shortest path classes: Dijkstra, Graph, and GraphInput
class Edge
{
  public final Vertex target;
  public final double weight;
  public final int speed;
  public Edge(Vertex target, double weight, int speed)
  {
		this.target = target; 
		this.weight = weight;
		this.speed = speed;
	}
}
