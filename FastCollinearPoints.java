/* *****************************************************************************
 *  Name: Alain Plana
 *  Date: 25.04.2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        requiredNonDuplicates(points);
        requiredNonNull(points);

        Stack<LineSegment> segmentStack = new Stack<>();

        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());
            collect(points, segmentStack);
        }

        feedSegments(segmentStack);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    // Store points in stack
    private void collect(Point[] points, Stack<LineSegment> stack) {
        Point origin = points[0];
        for (int first = 1, last = 2; last < points.length; last++) {
            while (last < points.length &&
                    abs(origin.slopeTo(points[first])) == abs(origin.slopeTo(points[last]))) last++;

            boolean c1 = last - first >= 3;
            boolean c2 = origin.compareTo(points[first]) < 0;
            if (c1 && c2) stack.push(new LineSegment(origin, points[last - 1]));

            first = last;
        }
    }

    // Throws IllegalArgumentException if exist duplicated elements in the array
    private static void requiredNonDuplicates(Point[] points) {
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
    }

    // Throws IllegalArgumentException if exist null elements in the array
    // or the array itself is null
    private static void requiredNonNull(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        else
            for (int i = 0; i < points.length; i++)
                if (points[i] == null || points[i].toString().contains("null"))
                    throw new IllegalArgumentException();
    }

    // Store elements stored in Stack in array datastructure
    private void feedSegments(Stack<LineSegment> lineSegments) {
        int length = lineSegments.size();
        segments = new LineSegment[length];
        for (int i = 0; i < length; i++) segments[i] = lineSegments.pop();
    }


    // Absolute value
    private static double abs(double a) {
        return a <= 0.0D ? 0.0D - a : a;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
