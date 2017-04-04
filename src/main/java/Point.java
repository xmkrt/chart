import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by marcel on 31.03.2017.
 */
public class Point {
    private double x;
    private double y;
    private int cluster;

    public Point(double x, double y, int k) {
        this.x = x;
        this.y = y;
        cluster = ThreadLocalRandom.current().nextInt(1, k);
    }

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    static double distance(Point p, Point center) {
        return Math.sqrt(Math.pow((center.getY() - p.getY()), 2) + Math.pow((center.getX() - p.getX()), 2));
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }

    int getCluster() {
        return cluster;
    }

    void setCluster(int cluster) {
        this.cluster = cluster;
    }

    @Override
    public String toString() {
        return x + " | " + y;
    }
}
