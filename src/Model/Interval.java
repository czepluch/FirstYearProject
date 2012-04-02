package Model;

// http://algs4.cs.princeton.edu/92search/Interval.java.html

/*************************************************************************
 *  Compilation:  javac Interval.java
 *  Execution:    java Interval
 *
 *  Implementation of an interval.
 *
 *************************************************************************/

class Interval { 
    public final double low;      // left endpoint
    public final double high;     // right endpoint
   
    public Interval(double low, double high) {
        if (less(high, low)) throw new RuntimeException("Illegal argument");
        this.low  = low;
        this.high = high;
    }

    // is x between low and high
    public boolean contains(double x) {
        return !less(x, low) && !less(high, x);
    }

    // does this interval intersect that interval?
    public boolean intersects(Interval that) {
        if (less(this.high, that.low)) return false;
        if (less(that.high, this.low)) return false;
        return true;
    }

    // does this interval equal that interval?
    public boolean equals(Interval that) {
        return (this.low == that.low) && (this.high == that.high);
    }


    // comparison helper functions
    private boolean less(double x, double y) {
        return x < y;
    }

    // return string representation
    public String toString() {
        return "[" + low + ", " + high + "]";
    }



//    // test client
//    public static void main(String[] args) {
//        int N = Integer.parseInt(args[0]);
//
//        Interval<Integer> a = new Interval<Integer>(5, 17);
//        Interval<Integer> b = new Interval<Integer>(5, 17);
//        Interval<Integer> c = new Interval<Integer>(5, 18);
//        System.out.println(a.equals(b));
//        System.out.println(!a.equals(c));
//        System.out.println(!b.equals(c));
//
//
//        // generate N random points in [-1, 2] and compute
//        // fraction that lies in [0, 1]
//        Interval<double> interval = new Interval<double>(0.0, 1.0);
//        int cnt = 0;
//        for (int i = 0; i < N; i++) {
//            double x = 3 * Math.random() - 1.0;
//            if (interval.contains(x)) cnt++;
//        }
//        System.out.println("fraction = " + (1.0 * cnt / N));
//    }
}