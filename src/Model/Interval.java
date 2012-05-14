package Model;

// Taken from http://algs4.cs.princeton.edu/92search/Interval.java.html

/**
 * A one-dimensional interval.
 * Can represent an interval on any given axis, i.e. the x-axis.
 */
public class Interval { 
    public final double low;      // left endpoint
    public final double high;     // right endpoint
    
    /**
     * Constructor
     * @param low Left endpoint
     * @param high Right endpoint
     */
    public Interval(double low, double high) {
        if (less(high, low)) throw new RuntimeException("Illegal argument");
        this.low  = low;
        this.high = high;
    }

    /**
     * Is a given value inside the interval?
     * @param x Is this value inside the interval?
     * @return True if the input value is inside the interval, false otherwise
     */
    public boolean contains(double x) {
        return !less(x, low) && !less(high, x);
    }

    /**
     * Checks whether this interval intersects another given interval
     * @param that The given interval to check for intersection with
     * @return True if the two intervals intersect, false otherwise
     */
    public boolean intersects(Interval that) {
        if (less(this.high, that.low)) return false;
        if (less(that.high, this.low)) return false;
        return true;
    }

    /**
     * Are this and that interval the same?
     * @param that The interval to check for equality with
     * @return True if the two intervals have the same low and high endpoints, false otherwise
     */
    public boolean equals(Interval that) {
        return (this.low == that.low) && (this.high == that.high);
    }


    // comparison helper functions
    private boolean less(double x, double y) {
        return x < y;
    }

    @Override
    public String toString() {
        return "[" + low + ", " + high + "]";
    }
}