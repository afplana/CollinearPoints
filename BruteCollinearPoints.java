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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        requiredNonDuplicates(points);
        requiredNonNull(points);


        Stack<LineSegment> stack = new Stack<>();
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                for (int k = j + 1; k < points.length; k++)
                    for (int m = k + 1; m < points.length; m++)
                        verifyPoints(
                                new Point[] { points[i], points[j], points[k], points[m] },
                                stack);

        int length = stack.size();
        for (int i = 0; i < length; i++) segments.add(stack.pop());
    }

    // If 4 points are collinear the add to stack
    private void verifyPoints(Point[] p, Stack<LineSegment> stack) {
        assert p.length == 4;
        Arrays.sort(p);
        if (collinearPoints(p)) stack.push(new LineSegment(p[0], p[3]));
    }


    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
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
        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();
    }


    // Return true is the given points are collinear
    private static boolean collinearPoints(Point[] points) {
        for (int p = 0; p < points.length - 2; p++) {
            if (points[p].slopeTo(points[p + 1]) != points[p + 1].slopeTo(points[p + 2]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("input6.txt");
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
