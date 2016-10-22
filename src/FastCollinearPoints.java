import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by User on 10/22/2016.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private List<LineSegment> lineSegmentList;

    private class PointWithSlope implements Comparable<PointWithSlope>{
        public Point point;
        public Double slopeToOrigin;

        PointWithSlope(Point p, Double s){
            point = p;
            slopeToOrigin = s;
        }

        public int compareTo(PointWithSlope that) {
            //order by slope to origin
            if(this.slopeToOrigin < that.slopeToOrigin){
                return -1;
            }
            if(this.slopeToOrigin > that.slopeToOrigin){
                return 1;
            }
            return 0;
        }
    }

    public FastCollinearPoints(Point[] points){
        if(points == null){
            throw new java.lang.NullPointerException();
        }
        lineSegmentList = new ArrayList<LineSegment>();

        //may need this for speed, sorted point array should be same order as a sorted slope array
//        Arrays.sort(points); //hmm would help down below in grabbing points to make segment. Othwewise would want to correspond slope with point in array and sort there


        for(int i = 0; i < points.length; i ++){

            Point originPoint = points[i];
            //create array for holding slops of all points compared ot current point
//            Double[] slopeArray = new Double[points.length - 1 - i];
//            //get the slope of all points compared ot origin point
//            for(int j = i + 1; j< points.length; j++){
//                slopeArray[j-1] = originPoint.slopeTo(points[j]);
//            }
//            Arrays.sort(slopeArray); //may need to implment a merge sort here instead


            PointWithSlope[] pointWithSlopeArray = new PointWithSlope[points.length - i - 1];
            //get the slope of all points compared ot origin point
            for(int j = i + 1; j< points.length; j++){
                pointWithSlopeArray[j-i-1] = new PointWithSlope(points[j], originPoint.slopeTo(points[j]));
            }


            Arrays.sort(pointWithSlopeArray); //may need to implment a merge sort here instead
            
            Double currentSlopeToOrigin = null;
            List<Point> colinearPointsInSegment = new ArrayList<Point>();

            for(int j = i + 1; j < points.length; j++){
                if(currentSlopeToOrigin == null){
                    //1st point in a segment is always colinear with origin point
                    colinearPointsInSegment.add(originPoint);
                    colinearPointsInSegment.add(pointWithSlopeArray[j-i-1].point);
                    currentSlopeToOrigin = pointWithSlopeArray[j-i-1].slopeToOrigin; //make this nicer, slope array is 1 smaller than remaining unchecked points
                }
                else{
                    if((double)currentSlopeToOrigin == (double)pointWithSlopeArray[j-i-1].slopeToOrigin){
                        //point it colinear, add it to current point list
                        colinearPointsInSegment.add(pointWithSlopeArray[j-i-1].point);
                    }
                    else{
                        //point is not colinear

                        // if current segment is >= 4 points add it to result
                        if(colinearPointsInSegment.size() >= 4){
                            //add smallest and largest points in the segment
                            Point[] colinearPoints = colinearPointsInSegment.toArray(new Point[colinearPointsInSegment.size()]);
                            Arrays.sort(colinearPoints);
                            lineSegmentList.add(new LineSegment(colinearPoints[0], colinearPoints[colinearPointsInSegment.size() -1]));
                        }

                        //clear points in current segment and move on
                        colinearPointsInSegment.clear();
                        currentSlopeToOrigin = null;
                    }

                }
            }
            //need check for if at end of input and we have a line segment!
            // if current segment is >= 4 points add it to result
            if(colinearPointsInSegment.size() >= 4){
                //add smallest and largest points in the segment
                Point[] colinearPoints = colinearPointsInSegment.toArray(new Point[colinearPointsInSegment.size()]);
                Arrays.sort(colinearPoints);
                lineSegmentList.add(new LineSegment(colinearPoints[0], colinearPoints[colinearPointsInSegment.size() -1]));
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
