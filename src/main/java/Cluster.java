import java.util.ArrayList;


public class Cluster {
    private ArrayList<Point> points;
    private Point center;
    private int id;

    public Cluster(int id) {
        this.points = new ArrayList<Point>();
        this.center = null;
        this.id = id;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void clear() {
        points.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cluster cluster = (Cluster) o;

        if (id != cluster.id) return false;
        if (points != null ? !points.equals(cluster.points) : cluster.points != null) return false;
        return center != null ? center.equals(cluster.center) : cluster.center == null;
    }

    @Override
    public int hashCode() {
        int result = points != null ? points.hashCode() : 0;
        result = 31 * result + (center != null ? center.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
