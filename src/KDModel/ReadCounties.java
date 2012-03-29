package KDModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class ReadCounties {

	public static ArrayList<HashSet<String>> readStuff(String path)
	{
		HashSet<String> vs = new HashSet<String>();
		HashSet<String> hs = new HashSet<String>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String line = in.readLine();
			while (line != null)
			{
				line = in.readLine();
				if (line == null) break;
				String[] s = line.split(",");
				vs.add(s[17]);
				hs.add(s[18]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh noes!: "+ e.getMessage());
		} catch (IOException e) {
 			System.out.println("Oh noes!: "+ e.getMessage());
		}
		ArrayList<HashSet<String>> al = new ArrayList<HashSet<String>>();
		al.add(vs);
		al.add(hs);
		return al;
	}
	
	public static void main(String[] args) {
		ArrayList<HashSet<String>> al = ReadCounties.readStuff("kdv_unload.txt");
		for (HashSet<String> h : al)
			for (String s : h) System.out.println(s);
		System.out.printf("%d zip codes\n", al.get(0).size() + al.get(1).size());
	}
}
