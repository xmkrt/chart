import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Cluster {
    private List<Point> points;
    private Point center;
    private int id;

    Cluster(int id) {
        this.points = new ArrayList<Point>();
        this.center = null;
        this.id = id;
    }

    List<Point> getPoints() {
        return points;
    }

    void addPoint(Point point) {
        points.add(point);
    }

    void setRandomCenter() {
        center = points.get(ThreadLocalRandom.current().nextInt(0, points.size()));
    }

    void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    Point getCenter() {
        return center;
    }

    void setCenter(Point center) {
        this.center = center;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    void clear() {
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
