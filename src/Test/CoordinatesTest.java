package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.Coordinates;
import View.ViewValues;

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
	
	@Test
	public void testConvertXToPixelsLowerThanMin() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double x = 10;
		int newX = c.convertXToPixels(x);
		assertEquals(-1, newX);
	}
	
	@Test
	public void testConvertXToPixelsHigherThanMax() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double x = 130;
		int newX = c.convertXToPixels(x);
		assertEquals(11, newX);
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
	
	@Test
	public void testConvertYToPixelsLowerThanMax() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double y = 20;
		int newY = c.convertYToPixels(y);
		assertEquals(11, newY);
	}
	
	@Test
	public void testConvertYToPixelsHigherThanMax() {
		initMinAndMaxValues();
		Coordinates c = new Coordinates();
		
		double y = 140;
		int newY = c.convertYToPixels(y);
		assertEquals(-1, newY);
	}
	
	public void initMinAndMaxValues() {
		ViewValues.minX = 20;
		ViewValues.maxX = 120;
		ViewValues.minY = 30;
		ViewValues.maxY = 130;
		ViewValues.width = 10;
		ViewValues.height = 10;
	}
}
