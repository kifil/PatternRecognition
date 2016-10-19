import java.util.Arrays;

/**
 * Created by User on 10/18/2016.
 */
public class BruteCollinearPoints {

    private int segmentCount = 0;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
//        lineSegments = new LineSegment[points.length];
        lineSegments = new LineSegment[1];// need this to be actual lenght of segment count...
        Arrays.sort(points);

        for(int i = 0; i < points.length; i ++){
            for(int j = i + 1; j < points.length; j ++){
                for(int k = j+ 1; k < points.length; k ++){
                    for (int l =  k + 1; l < points.length; l++){
                        if(points[i] == null ||
                            points[j] == null ||
                            points[k] == null ||
                            points[l] == null ){
                                throw new java.lang.NullPointerException();
                        }

                        if(points[i].compareTo(points[j]) == 0 ||
                            points[i].compareTo(points[k]) == 0 ||
                            points[i].compareTo(points[l]) == 0 ||
                            points[j].compareTo(points[k]) == 0 ||
                            points[j].compareTo(points[l]) == 0 ||
                            points[k].compareTo(points[l]) == 0){
                                throw new java.lang.IllegalArgumentException();
                        }

                        if(points[i].slopeTo(points[j]) ==  points[i].slopeTo(points[k]) &&
                            points[i].slopeTo(points[j]) ==  points[i].slopeTo(points[l])){
                                lineSegments[segmentCount] = new LineSegment(points[i],points[l]);
                                segmentCount++;
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentCount;
    }

    // the line segments
    public LineSegment[] segments()  {
        return lineSegments;
    }
}