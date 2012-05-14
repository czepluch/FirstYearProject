package Test;

import static org.junit.Assert.*;
import static Model.TripCriteria.*;

import java.util.List;

import org.junit.Test;
import Model.Dijkstra;
import Model.Graph;
import Model.PathFinder;
import Model.Trip;
import Model.TripEdge;

/*
 * This class tests whether the Dijkstra class finds the correct
 * fastest or shortest paths between two nodes in a given graph.
 */
public class DijkstraTest {

	private PathFinder pf = new Dijkstra();
	private Graph g1 = Graph.testGraph("test1-vertex.txt", "test1-edges.txt");
	private Graph g2 = Graph.testGraph("test2-vertex.txt", "test2-edges.txt");
	
	/*
	 * TEST DATA SET ONE
	 */
	
	@Test
	public void test1_short_1to4()
	{
		Trip<Double> trip = pf.run("1", "4", SHORT, g1);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(2.0,8.0)-->(13.0,5.0)-->(10.0,9.0)");
	}
	
	@Test
	public void test1_short_1to6()
	{
		Trip<Double> trip = pf.run("1", "6", SHORT, g1);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(2.0,8.0)-->(13.0,5.0)");
	}
	
	@Test
	public void test1_short_1to7() {
		Trip<Double> trip = pf.run("1", "7", SHORT, g1);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(2.0,8.0)-->(7.0,4.0)-->(12.0,3.0)-->(19.0,8.0)");
	}
	
	@Test
	public void test1_fast_1to4()
	{
		Trip<Double> trip = pf.run("1", "4", FAST, g1);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(2.0,8.0)-->(7.0,4.0)-->(12.0,3.0)-->(13.0,5.0)-->(10.0,9.0)");
	}
	
	@Test
	public void test1_fast_1to6()
	{
		Trip<Double> trip = pf.run("1", "6", FAST, g1);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(2.0,8.0)-->(7.0,4.0)-->(12.0,3.0)-->(13.0,5.0)");
	}

	@Test
	public void test1_fast_1to7()
	{
		Trip<Double> trip = pf.run("1", "7", FAST, g1);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(2.0,8.0)-->(7.0,4.0)-->(12.0,3.0)-->(19.0,8.0)");
	}
	
	/*
	 * TEST DATA SET TWO
	 */
	
	@Test
	public void test2_short_1to11()
	{
		Trip<Double> trip = pf.run("1", "11", SHORT, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(0.0,11.0)-->(1.0,9.0)-->(7.0,11.0)");
	}
	
	@Test
	public void test2_short_9to11()
	{
		Trip<Double> trip = pf.run("9", "11", SHORT, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(0.0,2.0)-->(1.0,9.0)-->(7.0,11.0)");
	}
	
	@Test
	public void test2_short_2to7()
	{
		Trip<Double> trip = pf.run("2", "7", SHORT, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(5.0,16.0)-->(7.0,11.0)-->(7.0,0.0)");
	}
	
	@Test
	public void test2_short_4to10()
	{
		Trip<Double> trip = pf.run("4", "10", SHORT, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(13.0,10.0)-->(7.0,11.0)-->(1.0,9.0)");
	}
	
	@Test
	public void test2_fast_1to11()
	{
		Trip<Double> trip = pf.run("1", "11", FAST, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(0.0,11.0)-->(5.0,16.0)-->(12.0,15.0)-->(13.0,10.0)-->(7.0,11.0)");
	}
	
	@Test
	public void test2_fast_9to11()
	{
		Trip<Double> trip = pf.run("9", "11", FAST, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(0.0,2.0)-->(7.0,0.0)-->(11.0,7.0)-->(13.0,10.0)-->(7.0,11.0)");
	}
	
	@Test
	public void test2_fast_2to7()
	{
		Trip<Double> trip = pf.run("2", "7", FAST, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(5.0,16.0)-->(12.0,15.0)-->(13.0,10.0)-->(11.0,7.0)-->(7.0,0.0)");
	}
	
	@Test
	public void test2_fast_4to10()
	{
		Trip<Double> trip = pf.run("4", "10", FAST, g2);
		List<TripEdge<Double>> edges = trip.getEdges();
		String path = tripEdgeString(edges.get(0), true);
		for (int i = 0; i < edges.size(); i++) path += "-->" + tripEdgeString(edges.get(i), false);
		assertEquals(path, "(13.0,10.0)-->(12.0,15.0)-->(5.0,16.0)-->(0.0,11.0)-->(1.0,9.0)");
	}
	
	/*
	 * This is method is stricly for testing purposes.
	 * Return a string representing of either the first or the second endpoint of the edge.
	 * If b is true, returns first end point, other the second endpoint.
	 */
	private String tripEdgeString(TripEdge<Double> e, boolean b)
	{
		if (b) return String.format("(%1.1f,%1.1f)", e.getFromX(), e.getFromY());
		return String.format("(%1.1f,%1.1f)", e.getToX(), e.getToY());
	}
}
