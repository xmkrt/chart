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

    void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    void addPoint(Point point) {
        points.add(point);
    }

    void setRandomCenter() {
        center = points.get(ThreadLocalRandom.current().nextInt(0, points.size()));
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
}
