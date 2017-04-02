import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by marcel on 02.04.2017.
 */
public class Kmeans {
    private List<Cluster> clusters = new ArrayList<Cluster>();
    private List<Point> points = new ArrayList<Point>();

    private int n_clusters;

    private Cluster cluster1 = new Cluster(1);
    private Cluster cluster2 = new Cluster(2);
    private Cluster cluster3 = new Cluster(3);
    private Cluster cluster4 = new Cluster(4);
    private Cluster cluster5 = new Cluster(5);

    Kmeans(List<Point> points, int n_clusters) throws Exception {
        this.points = points;
        this.n_clusters = n_clusters;
        switch (n_clusters) {
            case 3:
                clusters.add(cluster1);
                clusters.add(cluster2);
                clusters.add(cluster3);
                break;
            case 4:
                clusters.add(cluster1);
                clusters.add(cluster2);
                clusters.add(cluster3);
                clusters.add(cluster4);
                break;
            case 5:
                clusters.add(cluster1);
                clusters.add(cluster2);
                clusters.add(cluster3);
                clusters.add(cluster4);
                clusters.add(cluster5);
                break;
            default:
                throw new Exception("3-5");
        }

    }

    void init() {
        //set random clusters
        for (Point point : points) {
            point.setCluster(ThreadLocalRandom.current().nextInt(1, clusters.size() + 1));
        }

        // add points to clusters
        for (Point point : points) {
            switch (point.getCluster()) {
                case 1:
                    cluster1.addPoint(point);
                    break;
                case 2:
                    cluster2.addPoint(point);
                    break;
                case 3:
                    cluster3.addPoint(point);
                    break;
                case 4:
                    cluster4.addPoint(point);
                    break;
                case 5:
                    cluster5.addPoint(point);
                    break;
                default:
                    break;
            }
        }

        for (Cluster cluster : clusters) {
            cluster.setRandomCenter();
        }
    }

    void calc() {
        boolean finish = false;

        while (!finish) {
            //Clear cluster state
            clearClusters();
            List lastCenters = getCenters(n_clusters);
            //Assign points to the closer cluster
            assignCluster(n_clusters);
            //Calculate new centers
            calculateCenters();
            List currentCenters = getCenters(n_clusters);
            //Calculates total distance between new and old Centers
            double distance = 0;
            for (int i = 0; i < lastCenters.size(); i++) {
                distance += Point.distance((Point) lastCenters.get(i), (Point) currentCenters.get(i));
            }

            if (distance < 0.1) {
                // stop when distance between last and new centers is small
                finish = true;
            }
        }

        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + (i + 1) + " Center: " + clusters.get(i).getCenter());
            System.out.println("Points:\n" + clusters.get(i).getPoints());
        }
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List getCenters(int k) {
        List<Point> centers = new ArrayList<Point>(k);
        for (Cluster cluster : clusters) {
            Point aux = cluster.getCenter();
            Point point = new Point(aux.getX(), aux.getY());
            centers.add(point);
        }
        return centers;
    }

    private void assignCluster(int k) {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance;

        for (Point point : points) {
            min = max;
            for (int i = 0; i < k; i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCenter());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCenters() {
        for (Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();

            for (Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point center = cluster.getCenter();
            if (n_points < 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                center.setX(newX);
                center.setY(newY);
            }
        }
    }

    BubbleChart drawBubble() {
        BubbleChart chart = new BubbleChartBuilder().width(800).height(600).title("Viele Punkte mit Cluster").xAxisTitle("X").yAxisTitle("Y").build();
        int i = 1;

        //convert to xChart Format
        for (Cluster cluster : clusters) {
            List<Point> points = cluster.getPoints();
            List<Double> xData = new ArrayList<Double>();
            List<Double> yData = new ArrayList<Double>();
            List<Double> bubbleData = new ArrayList<Double>();
            for (int j = 0; j < points.size(); j++) {
                xData.add(points.get(j).getX());
                yData.add(points.get(j).getY());
                bubbleData.add(5.0);
            }
            chart.addSeries("Cluster " + i, xData, yData, bubbleData);
            i++;
        }
        return chart;
    }

    XYChart drawXY() {
        XYChart chart = new XYChartBuilder().width(800).height(600).build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(8);
        int i = 1;

        //convert to xChart Format
        for (Cluster cluster : clusters) {
            List<Point> points = cluster.getPoints();
            List<Double> xData = new ArrayList<Double>();
            List<Double> yData = new ArrayList<Double>();
            for (int j = 0; j < points.size(); j++) {
                xData.add(points.get(j).getX());
                yData.add(points.get(j).getY());
            }
            chart.addSeries("Cluster " + i, xData, yData);
            i++;
        }
        return chart;
    }
}




