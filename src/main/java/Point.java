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

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getCluster() {
        return cluster;
    }

    protected static double distance(Point p, Point center) {
        return Math.sqrt(Math.pow((center.getY() - p.getY()), 2) + Math.pow((center.getX() - p.getX()), 2));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", cluster=" + cluster +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;
        return cluster == point.cluster;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + cluster;
        return result;
    }
}
