package Model;

//Class used to represent an edge in the shortest path classes: Dijkstra, Graph, and GraphInput
class GEdge
{
  public final GVertex target;
  public final double weight;
  public GEdge(GVertex argTarget, double argWeight)
  {
		target = argTarget; 
		weight = argWeight;
	}
}
