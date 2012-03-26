package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.Coordinates;
import Global.*;

public class CoordinatesTest {
	
	// Test for ConvertXToPixels-method
	// __________________________________________________________________
	
	@Test
	public void testConvertXToPixelsOrdinary() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double x = 50;
		int newX = c.convertXToPixels(x);
		assertEquals(3, newX);
	}
	
	@Test
	public void testConvertXToPixelsLow() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double x = 20;
		int newX = c.convertXToPixels(x);
		assertEquals(0, newX);
	}
	
	@Test
	public void testConvertXToPixelsHigh() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double x = 120;
		int newX = c.convertXToPixels(x);
		assertEquals(10, newX);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertXToPixelsTooLow() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double x = 19;
		int newX = c.convertXToPixels(x);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertXToPixelsTooHigh() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double x = 121;
		int newX = c.convertXToPixels(x);
	}
	
	// Test for ConvertYToPixels-method
	// __________________________________________________________________
	
	
	@Test
	public void testConvertYToPixelsOrdinary() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double y = 60;
		int newY = c.convertYToPixels(y);
		assertEquals(7, newY);
	}
	
	@Test
	public void testConvertYToPixelsLow() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double y = 30;
		int newY = c.convertYToPixels(y);
		assertEquals(10, newY);
	}
	
	@Test
	public void testConvertYToPixelsHigh() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double y = 130;
		int newY = c.convertYToPixels(y);
		assertEquals(0, newY);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertYToPixelsTooLow() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double y = 29;
		int newY = c.convertYToPixels(y);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertYToPixelsTooHigh() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double y = 131;
		int newY = c.convertYToPixels(y);
	}
	
	public void initMinAndMaxValues() {
		MinAndMaxValues.minX = 20;
		MinAndMaxValues.maxX = 120;
		MinAndMaxValues.minY = 30;
		MinAndMaxValues.maxY = 130;
		MinAndMaxValues.width = 10;
		MinAndMaxValues.height = 10;
	}
}
