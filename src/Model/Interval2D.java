package Model;

// Taken from http://algs4.cs.princeton.edu/91primitives/Interval2D.java.html

/**
 * A two-dimensional interval (rectangle)
 */

public class Interval2D { 
    public final Interval intervalX;   // x-interval
    public final Interval intervalY;   // y-interval
   
    /**
     * Constructor
     * @param intervalX The x-interval
     * @param intervalY The y-interval
     */
    public Interval2D(Interval intervalX, Interval intervalY) {
        this.intervalX = intervalX;
        this.intervalY = intervalY;
    }

    /**
     * Checks whether this 2D interval intersects another given 2D interval
     * @param b The given 2D interval to check for intersection with
     * @return True if the two 2D intervals intersect (either on the x- or y-axis), false otherwise
     */
    public boolean intersects(Interval2D b) {
        if (intervalX.intersects(b.intervalX)) return true;
        if (intervalY.intersects(b.intervalY)) return true;
        return false;
    }

    /**
     * Checks whether this 2D interval contains an x- and a y-coordinate
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return True if the 2D interval contains the input values, false otherwise
     */
    public boolean contains(Double x, Double y) {
        return intervalX.contains(x) && intervalY.contains(y);
    }

    @Override
    public String toString() {
        return intervalX + " x " + intervalY;
    }
}