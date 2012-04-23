import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class TernaryTrie<Value extends String> {

	Node root;

	private class Node{
		char c;
		Value val;
		Node l, m, r;

		public Node(char c) {
			this.c = c;
		}

		public Node() {}
	}
	
	public Value get(String s) {
		Node n = get(root, s, 0);
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

	public void put(String s, Value v) {
		root = put(root, s, v, 0 );
	}

	private Node put(Node node, String s, Value v, int d) {
		char c = s.charAt(d);
		if (node == null) node = new Node(c);
		if 	(c < node.c) node.l = put(node.l, s, v, d);
		else if (c > node.c) node.r = put(node.r, s, v, d);
		else if (d < s.length() -1) node.m = put(node.m, s, v, d+1);
		else node.val = v;

		return node;
	}

	public Iterable<String> startsWith(String pre) {
		Queue<String> q = new LinkedList<String>();
		Node n = get(root, pre, 0);
		if (n == null) return q;
		if (n.val != null) q.offer(pre);
		collect(n.m , pre, q);
		return q;
	}

	private void collect(Node node, String pre, Queue<String> q){
		if (node == null) return;
		collect(node.l, pre, q);	//swapped with next line to easily arange output lexiographically
		if (node.val != null) q.offer(pre + node.c);
		collect(node.m, pre + node.c, q);
		collect(node.r, pre, 		q);
	}

	public void testChars() {
		for (int i=0; i<600; i++){
			if(i%60==0) System.out.println();
			System.out.print((char)i + " ");
		}		
	}

	//helper
	private String cut(String s) {
		return s.substring(0, s.length()-1);
	}

	//The main method is for testing/goofing around.
	public static void main (String[] args) {
		if (args.length < 1) {
			System.out.println("yeah, giving me no parameters is the way to go!\n" +
					"What am I, a freaking guessing machine?");
				return;
		}

		TernaryTrie<String> tt = new TernaryTrie<String>();
		
		try (
			Scanner fileScan = new Scanner(new File(args[0]));
			Scanner inputScan = new Scanner(System.in);
		) {
			System.out.println("Loading " + args[0] + ", please wait.");
			while (fileScan.hasNext()) {
				String name = fileScan.nextLine();
				name = name.toLowerCase();
				tt.put(name, name);
			}

			while (true) {
				System.out.println("Waiting for a search-prefix...");
				String q = inputScan.nextLine().toLowerCase();
				System.out.println("Entries beginning with " + q + ":");

				for (String r : tt.startsWith(q)) {
					System.out.println(r);
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not access file " + args[0]);
		}
	}
}
