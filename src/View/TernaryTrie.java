//The code in this file is based on the code from http://algs4.cs.princeton.edu/52trie/TST.java.html
package View;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class TernaryTrie {

	Node root;
	
	/**
	 * Build a trie from the standard path
	 */
	public TernaryTrie() {
		this(new File("TrieData.txt"));
	}; 

	/**
	 * Build the trie from the file denoted by the given string path
	 * @param String path to file
	 */
	public TernaryTrie(String fp) {
		this(new File(fp));
	}

	/**
	 * build the trie from a file
	 * @param String the file to build the trie from
	 */
	public TernaryTrie(File f) {
		Scanner fileScan = null;
		try {
			fileScan = new Scanner(f, "UTF-8");
			while (fileScan.hasNext()) {
				String[] name = fileScan.nextLine().split(";");
				put(name[0], name[1]);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			fileScan.close();
		}
	}	

	/**
	 * Retrieve the value correspoding to a given string.
	 * if the given string can not be found, null is returned
	 * @param String string to search for. (exact match).
	 * @return String
	 */
	public String get(String s) {
		Node n = get(root, s, 0);
		if (n== null) return null;
		return n.val;
	}

	private Node get(Node node, String s, int d) {
		if (node == null) return null;
		char c = s.charAt(d);
		if 	(c < node.c) return get(node.l, s, d);
		else if (c > node.c) return get(node.r, s, d);
		else if (d < s.length() -1) return get(node.m, s, d+1);
		else return node;
	}

	/**
	 * insert a key-value pair into the trie
	 * @param String key
	 * @param String value
	 */
	public void put(String s, String v) {
		root = put(root, s.toLowerCase(), v, 0 );
	}

	private Node put(Node node, String s, String v, int d) {
		char c = s.charAt(d);
		if (node == null) node = new Node(c);
		if 	(c < node.c) node.l = put(node.l, s, v, d);
		else if (c > node.c) node.r = put(node.r, s, v, d);
		else if (d < s.length() -1) node.m = put(node.m, s, v, d+1);
		else node.val = v;

		return node;
	}

	/**
	 * Get the keys that are prefixed with a given String
	 * @param String prefix
	 * @return String
	 */
	public Iterable<String> startsWith(String pre) {
		if (pre.length()==0) return null;
		Queue<String> q = new LinkedList<String>();
		Node n = get(root, pre, 0);
		if (n == null) return q;
		if (n.val != null) q.offer(pre);
		collect(n.m , pre, q);
		return q;
	}

	private void collect(Node node, String pre, Queue<String> q){
		if (node == null) return;
		collect(node.l, pre, q);	//swapped with next line to arange output lexiographically (well, sort of)
		if (node.val != null) q.offer(pre + node.c);
		collect(node.m, pre + node.c, q);
		collect(node.r, pre, 		q);
	}

	/**
	 * Prints a 60 character wide chart of characters from ascii code 0 through 600
	 */
	public void testChars() {
		for (int i=0; i<=600; i++){
			if(i%60==0) System.out.println();
			System.out.print((char)i + " ");
		}		
	}


	//helper
	private String cut(String s) {
		return s.substring(0, s.length()-1);
	}

	//private helper class
	private class Node{
		char c;
		String val;
		Node l, m, r;

		public Node(char c) {
			this.c = c;
		}

		public Node() {}
	}	
	
	/**
	 * Mainly for testing purposes
	 */
	public static void main (String[] args) {
		if (args.length < 1) {
			System.out.println("yeah, giving me no parameters is the way to go!\n" +
					"What am I, a freaking guessing machine?");
				return;
		}

		System.out.println("Loading " + args[0] + ", please wait.");
		TernaryTrie tt = new TernaryTrie(args[0]);
		Scanner inputScan = new Scanner(System.in);
		while (true) {
			System.out.println("Waiting for a search-prefix...");
			String q = inputScan.nextLine().toLowerCase();

			if (q.substring(0, 2).equals("g:")) {
				q=q.substring(3);
				System.out.println("Search result for " + q + ":");
				System.out.println(tt.get(q));
			} else {
				System.out.println("Entries beginning with " + q + ":");
				for (String r : tt.startsWith(q)) {
					System.out.println(r);
				}
			}
			System.out.println();
		}
	}
}
