package Model;

import java.util.ArrayList;
import java.util.HashSet;

public class KDTree 
{
	private static final int k = 2;

	
	public static Node createTree(ArrayList<Edge> edges)
	{
		Point[] points = new Point[edges.size() * 2];
		
		int i = 0;
		for (Edge e : edges)
		{
			points[i] = new Point(e.getFromLat(), e.getFromLong(), e);
			points[++i] = new Point(e.getToLat(), e.getToLong(), e);
			i++;
		}
		return createTree(points, 0);
	}
	
	public static Node createTree(Point[] points, int depth)
	{
		int len = points.length;
		if (len == 0) return null;
		int axis = depth % k;
		
		quicksort(points, 0, len - 1, axis);
		Point median = points[len / 2];
		
		Node node = new Node(median.x, median.y, median.e);
		
		Point[] left = new Point[len / 2];
		int i = 0;
		for (;i < left.length; i++) left[i] = points[i];
		
		// Points to the right of the median
		Point[] right = new Point[(len-1) / 2];
		i++;
		for (int j = 0; i < points.length; i++, j++) right[j] = points[i];
		
		//System.out.printf("Depth %d: (%f, %f)\n", depth, median.x, median.y);
		node.left = createTree(left, depth+1);
		node.right = createTree(right, depth+1);
		if (node.right == null)
			if (node.left == null) node.size = 1;
			else node.size = 1 + node.left.size;
		else node.size = 1 + node.left.size + node.right.size;
		return node;
	}

	/*
	 * Quicksort implementation taken from
	 * http://www.roseindia.net/java/beginners/arrayexamples/QuickSort.shtml
	 */
	public static void quicksort(Point[] points, int low, int n, int axis)
	{
		int lo = low;
		int hi = n;
		
		if (lo >= hi) return;
		Point mid = points[(lo + hi) / 2];
		
		while (lo < hi) 
		{
			while (lo < hi && less(points[lo], mid, axis)) lo++;
			while (lo < hi && greater(points[hi], mid, axis)) hi--;
			if (lo < hi) 
			{
				Point P = points[lo];
				points[lo] = points[hi];
				points[hi] = P;
			}
		}
		if (hi < lo) 
		{
			int T = hi;
			hi = lo;
			lo = T;
		}
		quicksort(points, low, lo, axis);
		quicksort(points, lo == low ? lo+1 : lo, n, axis);
	}
	
	// Is p1 less than p2?
	private static boolean less(Point p1, Point p2, int axis)
	{
		switch (axis)
		{
			case 0:
				return p1.x < p2.x;
			case 1:
				return p1.y < p2.y;
			default:
				return false;
		}
	}
	
	// Is p1 greater than p2?
	private static boolean greater(Point p1, Point p2, int axis)
	{
		switch (axis)
		{
			case 0:
				return p1.x >= p2.x;
			case 1:
				return p1.y >= p2.y;
			default:
				return false;
		}
	}
	
	public static class Node
	{
		double x,y;
		Edge e;
		int size;
		Node left;
		Node right;
		
		public Node(double x, double y, Edge e)
		{
			this.x = x;
			this.y = y;
			this.e = e;
		}
		
		public Edge getEdge()
		{
			return e;
		}
		
		public int size()
		{
			if (right == null)
			{
				if (left == null) return 1;
				else return 1 + left.size();
			}
			else return 1 + left.size() + right.size();
		}
		
		public int getSize()
		{
			return size;
		}
		
		public Node left()
		{
			return left;
		}
		
		public Node right()
		{
			return right;
		}
	}
	
	private static class Point
	{
		double x,y;
		Edge e;
		
		public Point(double x, double y, Edge e)
		{
			this.x = x;
			this.y = y;
			this.e = e;
		}
	}
}
