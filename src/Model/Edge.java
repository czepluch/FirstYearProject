package Model;

//Class used to represent an edge in the shortest path classes: Dijkstra, Graph, and GraphInput
class Edge
{
  public final Vertex target;
  public final double weight;
  public Edge(Vertex argTarget, double argWeight)
  {
		target = argTarget; 
		weight = argWeight;
	}
}
