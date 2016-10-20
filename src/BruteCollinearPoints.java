import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by User on 10/18/2016.
 */
public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private List<LineSegment> lineSegmentList;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        if(points == null){
            throw new java.lang.NullPointerException();
        }

        lineSegmentList = new ArrayList<LineSegment>();
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
                                //duplicate point
                                throw new java.lang.IllegalArgumentException();
                        }

                        if(points[i].slopeTo(points[j]) ==  points[i].slopeTo(points[k]) &&
                            points[i].slopeTo(points[j]) ==  points[i].slopeTo(points[l])){
                                lineSegmentList.add(new LineSegment(points[i],points[l]));
                        }
                    }
                }
            }
        }

        lineSegments = lineSegmentList.toArray(new LineSegment[lineSegmentList.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments()  {
        return lineSegments;
    }
}