/* *****************************************************************************
 *  Name: Alain Plana
 *  Date: 16.04.2020
 *  Description: Examines 4 points at a time and checks whether they all lie
 *  on the same line segment
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        requiredNonDuplicates(points);
        requiredNonNull(points);


        Stack<LineSegment> lineSegments = new Stack<>();
        for (int p = 0; p < points.length; p++)
            for (int q = p + 1; q < points.length; q++)
                for (int r = q + 1; r < points.length; r++)
                    for (int s = r + 1; s < points.length; s++)
                        verifySlopes(
                                new Point[] { points[p], points[q], points[r], points[s] },
                                lineSegments);

        feedSegments(lineSegments);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    // If 4 points are collinear the add to stack
    private void verifySlopes(Point[] p, Stack<LineSegment> stack) {
        assert p.length == 4;
        Arrays.sort(p);
        if (collinearPoints(p)) stack.push(new LineSegment(p[0], p[3]));
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


    // Return true is the given points are collinear
    private static boolean collinearPoints(Point[] points) {
        for (int i = 0; i < points.length - 2; i++)
            if (points[0].slopeTo(points[i + 1]) != points[0].slopeTo(points[i + 2])) return false;
        return true;
    }

    private void feedSegments(Stack<LineSegment> lineSegments) {
        int length = lineSegments.size();
        segments = new LineSegment[length];
        for (int i = 0; i < length; i++) segments[i] = lineSegments.pop();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
