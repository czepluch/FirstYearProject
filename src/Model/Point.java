package Model;

import static Model.Directions.*;
import Model.KEdge;

/**
 * 
 */
public class Point{
	double x;
	double y;
	KEdge e;
	
	public Point(double x, double y, KEdge e) {
		this.x = x;
		this.y = y;
		this.e = e;
	}
	
	public int getType() { return e.getType(); }
	public double getX() { return x; }
	public double getY() { return y; }
	public KEdge getEdge() { return e; }
	
	/*
	 * NW:	Smaller than x, bigger than y
	 * NE:	Bigger than both x and y
	 * SE:	Smaller than y, bigger than x
	 * SW:	Smaller than both x and y
	 */
	public Directions getDir(Point p) {
		if (p.x <= this.x && p.y >= this.y)	return NW;
		if (p.x >= this.x && p.y >= this.y)	return NE;
		if (p.x >= this.x && p.y <= this.y)	return SE;
		if (p.x <= this.x && p.y <= this.y)	return SW;
		System.out.println("Damn!");
		return null;
	}
}