//A second attempt at creating a data set for the trie.
//These classes are completely ad-hoc, and thus not optimized for reuse.
//
//The output of this parser is NOT guaranteed to have any degree of arbritraryness in its order.
//I use the UNIX sort method to randomize it (sort -R TrieData.txt > newFile.txt)
package Model;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.Flushable;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;

public class TrieDataCreator{
	private static final String outFile="TrieData.txt";
	private Map<Short, String> zipMap = new HashMap<Short, String>(); //map from zipcode to cityname
	private List<Road> roads = new ArrayList<Road>(); //list of (unique) roads.
	private Map<String,HashSet<Road>> zipBList = new HashMap<String, HashSet<Road>>();

	//The constructor controlls the flow of this ad-hoc  parser
	public TrieDataCreator (String zips, String edges, String nodes) {
		//initialize the file objects, terminate by runtimeexception if the files do not exist/can't be read
		File zipsF  = assignRFile(zips);
		File edgesF = assignRFile(edges);
		File nodesF = assignRFile(edges);
		File outF = assignWFile(outFile);

		//read contents of files into appropriate data structures
		readZips(zipsF, zipMap); 		//allows zip-code -> city-name mapping
		makeRoadList(edgesF, zipMap, roads); 	//stores unique roads (unique for roadname + zip)
		writeFormats(nodesF, outF, roads); 	//transfer controll to the writeFormats method, which pretty much takes over from here.
	}

	//Reads zips in the format zip; cityName from File f, intro the map zips
	private void readZips(File f, Map<Short, String> zips) {
		System.out.println("Reading zip codes");
		Scanner sc = getFReader(f);
		sc.nextLine(); sc.nextLine(); //Jump 2 lines	
		while(sc.hasNextLine()) {
			String[] l = sc.nextLine().split(";");
			//add K=zip V=cityName tothe zipmap
			zips.put(Short.parseShort(l[0]), l[1]);
		}
		closeStream(sc);
	}

	//Fills ArrayList roads with Road objects, created by reading File edges and mapping the zips to cities via Map zips
	//this method also fills out zipBList
	private void makeRoadList (File edges, Map<Short, String> zips, List<Road> roads) {
		System.out.println("Reading edges to make the road list");
		long ST = System.currentTimeMillis();
		
		//actually reading the roads into a set, so that one road is stored only once, regardless of its number of edges.	
		Set<Road> uniqueRoads = new HashSet<Road>();

		//read the file
		Scanner sc = getFReader(edges);
		sc.nextLine(); //jump a line
		while(sc.hasNextLine()) {
			String[] l = sc.nextLine().split(",");

			String rName = l[6]; //name at sixth place
			rName = rName.substring(1, rName.length()-1); // remove the leading and thrailing '
			Short zip = Short.parseShort(l[17]); 	//zip code at place 17 (and another at 18)
			String city = zips.get(zip);		//get city name from the zip-map
			Integer nId = Integer.parseInt(l[0]); 	//node-id at zeroth place (and another at 1)

			if (city == null || rName.length()==0 ) continue; 	//this goes for sweedish roads, where the data from post danmark is inadequate 
										//so the city name is null.
										//And for kdv_unload entries without a road name
			Road r = new Road(rName, zip, city, nId);
			uniqueRoads.add(r);
			addToZipList(r);
		}

		roads.addAll(uniqueRoads);
		System.out.println("Done reading edges and making the road list. It took: " + ((System.currentTimeMillis()-ST)/1000) + " seconds.\n" );
		closeStream(sc);
	}

	private void writeFormats(File nodeFile, File outFile, List<Road> roads) {
		writeR	(outFile, roads);
		writeC	(nodeFile, outFile, zipBList);
	}

	private void writeR (File outF, List<Road> roads) {
		System.out.println("Writing normal formats formats (i.e; City## not yet included)");
		long ST = System.currentTimeMillis();
		BufferedWriter bw = getFWriter(outF);
		for (Road r : roads) {
			//Road##City
			writeTo(bw, r.rName + "##" + r.cityName + ";" + r.nodeId);
			//Road#Zip#City
			writeTo(bw, r.rName + "#" + r.zipCode + "#" + r.cityName + ";" + r.nodeId);
			//City#Zip#Road
			writeTo(bw, r.cityName + "#" + r.zipCode + "#" + r.rName + ";" + r.nodeId);
			//City##Road
			writeTo(bw, r.cityName + "##" + r.rName + ";" + r.nodeId);
		}
		System.out.println("Done writing normal formats. It took: " + (System.currentTimeMillis()-ST) + " milliseconds.\n" );
		closeStream(bw);
	}

	private void writeC(File nodeF, File outF, Map<String, HashSet<Road>> zipBList) {
		System.out.println("Writing City## format");
		long ST = System.currentTimeMillis();
		BufferedWriter bw = getFWriter(outF);
		for (Set<Road> s : zipBList.values()){
			Road r = centralRoad(s);
			writeTo(bw, r.cityName + "##" + ";" + r.nodeId);
		}
		
		System.out.println("Done writing City format. It took: " + (System.currentTimeMillis()-ST) + " milliseconds.\n" );
		closeStream(bw);
	}

	//This 'temporary' implementation is relatively useless.
	//It is structured this way to make future changes easy.
	//Code to find good cetral points in cities can be inserted here, without any major changes in the program structure (save perhaps for reading a file)
	private Road centralRoad(Set<Road> roads) {
		Object[] arr = roads.toArray();
		@SuppressWarnings("unchecked")
		Road tmp = (Road) arr[0];
		return tmp;
	}

	/*
	 * Helper methods from here on out
	 * convention for the IO helpers is to throw a RuntimeException on fatal IO problems, and thereby crash the program, unless otherwise noted
	 */ 

	//getRead/write methods, simply provides a 'cleaner' way of instatiating fileIO objects
	private Scanner getFReader(File f){
		try {
			return new Scanner(f);
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}
	
	//the two following methods, clumsy as they may seem, provide a means of writing to a file using relavtively clean code
	//returns a BufferedWriter on the File outF (asumes UTF-8)
	private BufferedWriter getFWriter(File outF) {
		try {
			return new BufferedWriter( new OutputStreamWriter ( new FileOutputStream(outF, true), "UTF-8")); //true means append instead of overwriting
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//write String s, followed by a newline character, to bw
	private void writeTo (BufferedWriter bw, String s) {
		try {
			bw.write(s);
			bw.newLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//verifies the validity of a file denoted by String path, and assigns it to a File f on a successful verification.
	private File assignRFile(String path) {
		File tmp = new File(path);
		if (!tmp.canRead()) {
			throw new RuntimeException("Cant read file " + path);
		}
		return tmp;
	}

	//removes the file if it exists, and returns a file obj representing the newly created file
	//terminates by runtimeexception if any of the IO fails fataly.
	private File assignWFile(String path) {
		File tmp = new File(path);
		if (tmp.exists()) {
			if (!tmp.delete()) throw new RuntimeException("Could not delete file: " + path);
		}
		try {
			tmp.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return tmp;
	}

	//convient way to close streams.
	
	//doesnt crash on IO problems, as an error here is non-fatal for the parser.)
	private  void closeStream(Closeable c) {
		try {
			c.close();
		} catch (IOException e) {
			System.out.println("Huh, couldn't close this");
			e.printStackTrace();
		}
	}

	private <E extends Flushable & Closeable> void closeStream(E c) {
		try {
			c.flush();
			c.close();
		} catch (IOException e) {
			System.out.println("Huh, couldn't close this");
			e.printStackTrace();
		}
	}

	private void addToZipList(Road r) {
		if (zipBList.containsKey(r.cityName)) {
			zipBList.get(r.cityName).add(r);
		} else {
			HashSet<Road> hs = new HashSet<Road>();
			hs.add(r);
			zipBList.put(r.cityName ,hs);
		}
	}

	/*
	 * Helper section over, from here on out only private classes and main()
	 */

	//class representing  a road, with all its neccesary data.
	private class Road {	
		public String rName, cityName;
		public Short zipCode;
		public Integer nodeId;

		public Road(String rn, Short zip, String cName, Integer nId) {
			rName = rn;
			zipCode = zip;
			cityName = cName;
			nodeId = nId;
		}

		@Override
		public boolean equals(Object r) {
			if (r == null || r.getClass() != this.getClass()) return false;
			if (this == r) return true;
			Road other = (Road) r;
			//if (this.rName.equals(other.rName) && this.zipCode.equals(other.zipCode))System.out.println("'" + this.rName + "," + this.zipCode + " = '" + other.rName + "," + other.zipCode);
			return (this.rName.equals(other.rName) && this.zipCode.equals(other.zipCode));
		}

		@Override
		public int hashCode() {
			return rName.hashCode() + zipCode;
		}
	}

	public static void main (String[] args){
		if (args.length != 3) {
			System.out.println("Usage: zipfile edgefile nodefile");
			return;
		}

		new TrieDataCreator(args[0], args[1], args[2]);

	}
}
