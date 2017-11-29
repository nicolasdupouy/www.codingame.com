import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private static final int MAX_HORIZONTAL_SPEED = 20;
    private static final int MAX_VERTICAL_SPEED = 40;
    
    
    private static class Point {
        private int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    private static class LandingZone {
        private int startX;
        private int endX;
        private int y;
        
        private LandingZone(int startX, int endX, int y) {
            this.startX = startX;
            this.endX = endX;
            this.y = y;
        }
        
        public static LandingZone compute(List<Point> points) {
            LandingZone result = null;
            Point previousPoint = null;
            for (Point currentPoint : points) {
                if (previousPoint != null 
                    && (previousPoint.y == currentPoint.y)) {
                        result = new LandingZone(previousPoint.x, currentPoint.x, currentPoint.y);
                        break;
                }
                previousPoint = currentPoint;
            }
            
            return result;
        }
        
        @Override
        public String toString() {
            return "startX=" + this.startX
                + "/endX=" + this.endX
                + "/y="+this.y;
        }
    }
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < surfaceN; i++) {
            int landX = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int landY = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
            points.add(new Point(landX, landY));
        }

        // game loop
        while (true) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int hSpeed = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            int vSpeed = in.nextInt(); // the vertical speed (in m/s), can be negative.
            int fuel = in.nextInt(); // the quantity of remaining fuel in liters.
            int rotate = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            int power = in.nextInt(); // the thrust power (0 to 4).

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            LandingZone landingZone = LandingZone.compute(points);
            System.err.println("X="+X+ "/Y="+Y+ "/hSpeed="+hSpeed+ "/vSpeed="+vSpeed);
            System.err.println("LandingZone: " + landingZone);

            // rotate power. rotate is the desired rotation angle. power is the desired thrust power.
            int angleOutput = computeAngle(X, Y, hSpeed, vSpeed, landingZone);
            int powerOutput = computePower(X, vSpeed, landingZone);
            System.out.println(angleOutput + " " + powerOutput);
        }
    }
    
    private static int computeAngle(int X, int Y, int hSpeed, int vSpeed, LandingZone landingZone) {
        if (Y+vSpeed*2 < landingZone.y) {
            return 0;
        }
        else if ((X > landingZone.endX)
                || (hSpeed > MAX_HORIZONTAL_SPEED)) {
            return 20;
        }
        else if (X < landingZone.startX) {
                return -20;
        }
        
        return 0;
    }
    
    private static int computePower(int X, int vSpeed, LandingZone landingZone) {
        if ((landingZone.startX - X < 1000)
            || (vSpeed < -MAX_VERTICAL_SPEED))
            return 4;
        return 3;
    }
    
}