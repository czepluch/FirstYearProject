package Test;

import static org.junit.Assert.*;
import org.junit.Test;
import Model.TripEdge;
import static Model.Turn.*;

public class TripEdgeTest {

	@Test
	public void testComputeTimeOrdinaryNumbersLargeDistance() {
		int speed = 120;
		double distance = 60000;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
		double time = e.getTime();
		assertTrue(30 == time);
	}
	
	@Test
	public void testComputeTimeOrdinaryNumbersLargeSpeed() {
		int speed = 6000;
		double distance = 200;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
		double time = e.getTime();
		assertTrue(0.002 == time);
	}
	
	@Test
	public void testComputeTimeOrdinaryNumbersSmallDistance() {
		int speed = 80;
		double distance = 120;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
		double time = e.getTime();
		assertTrue(0.09 == time);
	}
	
	@Test
	public void testComputeTimeOrdinaryNumbersDistanceSmallerThanSpeed() {
		int speed = 30;
		double distance = 3;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
		double time = e.getTime();
		assertTrue(0.006 == time);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testComputeTimeNegativeSpeed() {
		int speed = -30;
		double distance = 120;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testComputeTimeNegativeDistance() {
		int speed = 120;
		double distance = -30;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testComputeTimeNegativeBoth() {
		int speed = -60;
		double distance = -120;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testComputeTimeZeroSpeed() {
		int speed = 0;
		double distance = 2000;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
	}
	
	@Test
	public void testComputeTimeZeroDistance() {
		int speed = 80;
		double distance = 0;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
		double time = e.getTime();
		assertTrue(0 == time);
	}
	
	@Test
	public void testComputeTimeZeroBoth() {
		int speed = 0;
		double distance = 0;
		TripEdge<Integer> e = new TripEdge<Integer>(0, 0, 0, 0, distance, LEFT, speed);
		double time = e.getTime();
		assertTrue(0 == time);
	}
}
