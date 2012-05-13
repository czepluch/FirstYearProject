package Test;

import static org.junit.Assert.*;
import org.junit.Test;
import Model.Vertex;

public class VertexTest {
	private static String[] empty = { };

	@Test
	public void testDistancePositiveNumbersIncreasing() {
		Vertex v1 = new Vertex("0", 8, 4, empty);
		Vertex v2 = new Vertex("0", 16, 12, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(11.3137 == distance);
	}
	
	@Test
	public void testDistancePositiveNumbersDecreasing() {
		Vertex v1 = new Vertex("0", 20, 15, empty);
		Vertex v2 = new Vertex("0", 13, 12, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(7.6158 == distance);
	}
	
	@Test
	public void testDistancePositiveNumbersOneIncreasingOneDecreasing() {
		Vertex v1 = new Vertex("0", 10, 5, empty);
		Vertex v2 = new Vertex("0", 5, 10, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(7.0711 == distance);
	}
	
	@Test
	public void testDistanceNegativeNumbers() {
		Vertex v1 = new Vertex("0", -10, -3, empty);
		Vertex v2 = new Vertex("0", -3, -1, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(7.2801 == distance);
	}
	
	@Test
	public void testDistanceNegativeAndPositiveNumbers() {
		Vertex v1 = new Vertex("0", 7, 5, empty);
		Vertex v2 = new Vertex("0", -7, -5, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(17.2047 == distance);
	}
	
	@Test
	public void testDistancePositiveAndNegativeNumbersMixed() {
		Vertex v1 = new Vertex("0", 1, -3, empty);
		Vertex v2 = new Vertex("0", 17, 2, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(16.7631 == distance);
	}
	
	@Test
	public void testDistanceAllNumbersZero() {
		Vertex v1 = new Vertex("0", 0, 0, empty);
		Vertex v2 = new Vertex("0", 0, 0, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(0.0000 == distance);
	}
	
	@Test
	public void testDistanceOneNumberZero() {
		Vertex v1 = new Vertex("0", 0, 0, empty);
		Vertex v2 = new Vertex("0", -3, 7, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(7.6158 == distance);
	}
	
	@Test
	public void testDistanceDecimalNumbers() {
		Vertex v1 = new Vertex("0", 3.7, 5.9, empty);
		Vertex v2 = new Vertex("0", -3.3, 2.3, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(7.8715 == distance);
	}
	
	@Test
	public void testDistanceLargeNumbersUTM() {
		Vertex v1 = new Vertex("0", 892250.58771, 6147885.93632, empty);
		Vertex v2 = new Vertex("0", 892297.17852, 6147868.40019, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(49.7817 == distance);
	}
	
	@Test
	public void testDistanceLowNumbers() {
		Vertex v1 = new Vertex("0", 0.58171, 0.93432, empty);
		Vertex v2 = new Vertex("0", -0.18852, 0.43019, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(0.9205 == distance);
	}
	
	@Test
	public void testDistanceLargeAndLowNumbers() {
		Vertex v1 = new Vertex("0", 2, 5, empty);
		Vertex v2 = new Vertex("0", 892250.58771, 6147885.93632, empty);
		double distance = Vertex.distance(v1, v2);
		distance = (double) Math.round(distance * 10000) / 10000;
		assertTrue(6212290.0407 == distance);
	}
}
