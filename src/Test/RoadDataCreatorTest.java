package Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import Model.KEdge;
import Model.RoadsDataCreator;

/*
 * This class tests whether the RoadDataCreator class,
 * given some sample input data, writes the correct output data to a file.
 */
public class RoadDataCreatorTest {
	private RoadsDataCreator rdc = new RoadsDataCreator();

	@Test
	public void test() {
		rdc.readData("kdv_test_unload.txt", "kdv_test_node_unload.txt");	// test input files
		rdc.createOutputFile("kdv_test_out.txt");							// output file is kdv_test_out.txt
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream("kdv_test_out.txt"), "UTF-8"));
			// read first line from the test output and compare with expected output
			String line = in.readLine();
			double dist = distance(6402050.98297, 595527.51786, 6402046.17761, 595498.57995);
			String expected = String.format("13;6402050.982970;595527.517860;6402046.177610;595498.579950;%1.6f;30;8", dist);
			assertEquals(line, expected);
			
			// read second line from the test output and compare with expected output
			line = in.readLine();
			dist = distance(6402050.98297, 595527.51786, 6402010.17508, 595747.32137);
			expected = String.format("14;6402050.982970;595527.517860;6402010.175080;595747.321370;%1.6f;30;8", dist);
			assertEquals(line, expected);
			
			// read third line from the test output and compare with expected output
			line = in.readLine();
			dist = distance(6402046.17761, 595498.57995, 6402050.98297, 595527.51786);
			expected = String.format("15;6402046.177610;595498.579950;6402050.982970;595527.517860;%1.6f;30;8", dist);
			assertEquals(line, expected);
			
			// read fourth line from the test output and compare with expected output
			line = in.readLine();
			dist = distance(6402010.17508, 595747.32137, 6402050.98297, 595527.51786);
			expected = String.format("16;6402010.175080;595747.321370;6402050.982970;595527.517860;%1.6f;30;8", dist);
			assertEquals(line, expected);
			
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
	}
	
	/*
	 * This method is strictly for testing purposes.
	 * Return the distance between two points, each defined by an x- and a y-coordinate.
	 */
	private double distance(double x1, double y1, double x2, double y2)
	{
		double x = x2 - x1;
		double y = y2 - y1;
		return Math.sqrt(x*x + y*y);
	}
}
