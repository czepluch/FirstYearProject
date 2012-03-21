package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.Coordinates;

public class CoordinatesTest {
	
	// Test for ConvertXToPixels-method
	// __________________________________________________________________
	
	@Test
	public void testConvertXToPixelsOrdinary() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double x = 50;
		int newX = c.convertXToPixels(x);
		assertEquals(3, newX);
	}
	
	@Test
	public void testConvertXToPixelsLow() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double x = 20;
		int newX = c.convertXToPixels(x);
		assertEquals(0, newX);
	}
	
	@Test
	public void testConvertXToPixelsHigh() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double x = 120;
		int newX = c.convertXToPixels(x);
		assertEquals(10, newX);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertXToPixelsTooLow() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double x = 19;
		int newX = c.convertXToPixels(x);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertXToPixelsTooHigh() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double x = 121;
		int newX = c.convertXToPixels(x);
	}
	
	// Test for ConvertYToPixels-method
	// __________________________________________________________________
	
	
	@Test
	public void testConvertYToPixelsOrdinary() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double y = 60;
		int newY = c.convertYToPixels(y);
		assertEquals(7, newY);
	}
	
	@Test
	public void testConvertYToPixelsLow() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double y = 30;
		int newY = c.convertYToPixels(y);
		assertEquals(10, newY);
	}
	
	@Test
	public void testConvertYToPixelsHigh() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double y = 130;
		int newY = c.convertYToPixels(y);
		assertEquals(0, newY);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertYToPixelsTooLow() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double y = 29;
		int newY = c.convertYToPixels(y);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertYToPixelsTooHigh() {
		Coordinates c = new Coordinates(20, 120, 30, 130, 10, 10);
		
		double y = 131;
		int newY = c.convertYToPixels(y);
	}
}
