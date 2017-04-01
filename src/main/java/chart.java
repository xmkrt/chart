import org.apache.commons.io.IOUtils;
import org.knowm.xchart.BubbleChart;
import org.knowm.xchart.BubbleChartBuilder;
import org.knowm.xchart.SwingWrapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class chart {
    static List<Cluster> clusters = new ArrayList<Cluster>();
    static List<Point> points = new ArrayList<Point>();

    static Cluster cluster1 = new Cluster(1);
    static Cluster cluster2 = new Cluster(2);
    static Cluster cluster3 = new Cluster(3);
    static Cluster cluster4 = new Cluster(4);
    static Cluster cluster5 = new Cluster(5);


    public static void main(String[] args) {
        BubbleChart chart = getMyChart();
        BubbleChart kmeans = getFinishedChart();
        new SwingWrapper<BubbleChart>(chart).displayChart();
        new SwingWrapper<BubbleChart>(kmeans).displayChart();
    }


    public static BubbleChart getMyChart() {
        List<String> lines = null;
        try {
            lines = IOUtils.readLines(new FileInputStream("data.txt"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        double[] xData = new double[lines.size()];
        double[] yData = new double[lines.size()];
        double[] bubbleData = new double[lines.size()];

        String[] segs;
        int i = 0;
        for (String s : lines) {
            segs = s.split(Pattern.quote(" "));
            xData[i] = Double.parseDouble(segs[0]);
            yData[i] = Double.parseDouble(segs[1]);
            bubbleData[i] = 5;
            i++;
        }

        kmeans4(xData, yData);

        BubbleChart chart = new BubbleChartBuilder().width(800).height(600).title("Viele Punkte").xAxisTitle("X").yAxisTitle("Y").build();

        chart.addSeries("lala", xData, yData, bubbleData);
        return chart;
    }

    private static BubbleChart getFinishedChart() {
        BubbleChart chart = new BubbleChartBuilder().width(800).height(600).title("Viele Punkte mit Cluster").xAxisTitle("X").yAxisTitle("Y").build();
        int i = 1;

        //rumgefummel weil xChart nur arrays mag :(
        for (Cluster cluster : clusters) {
            List<Point> points = cluster.getPoints();
            double[] xData = new double[points.size()];
            double[] yData = new double[points.size()];
            double[] bubbleData = new double[points.size()];
            for (int j = 0; j < points.size(); j++) {
                xData[j] = points.get(j).getX();
                yData[j] = points.get(j).getY();
                bubbleData[j] = 5;
            }
            chart.addSeries("Cluster " + i, xData, yData, bubbleData);
            i++;
        }
        return chart;
    }

    private static void kmeans4(double[] xData, double[] yData) {
        //create  4 clusters and add them to list

        clusters.add(cluster1);
        clusters.add(cluster2);
        clusters.add(cluster3);
        clusters.add(cluster4);

        // set the coordinates for each point from input Data
        for (int i = 0; i< xData.length; i++) {
            points.add(new Point(xData[i], yData[i]));
        }
        for (Point point : points) {
            point.setCluster(ThreadLocalRandom.current().nextInt(1, clusters.size() + 1));
        }

        addPointsToCluster((ArrayList) points, cluster1, cluster2, cluster3, cluster4);

        for (Cluster cluster : clusters) {
            cluster.setRandomCenter();
        }

        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while (!finish) {
            //Clear cluster state
            clearClusters();

            List lastCenters = getCenters(4);

            //Assign points to the closer cluster
            assignCluster(4);

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            List currentCentroids = getCenters(4);

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for (int i = 0; i < lastCenters.size(); i++) {
                distance += Point.distance((Point) lastCenters.get(i), (Point) currentCentroids.get(i));
            }

            if (distance < 0.1) {
                // stop when distance between last and newcenters is small
                finish = true;
            }
        }
    }

    private static void addPointsToCluster(ArrayList<Point> points, Cluster cluster1, Cluster cluster2, Cluster cluster3, Cluster cluster4) {
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
                default:
                    break;
            }
        }
    }

    private static void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private static List getCenters(int k) {
        List centers = new ArrayList(k);
        for (Cluster cluster : clusters) {
            Point aux = cluster.getCenter();
            Point point = new Point(aux.getX(), aux.getY());
            centers.add(point);
        }
        return centers;
    }

    private static void assignCluster(int k) {
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

    private static void calculateCentroids() {
        for (Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();

            for (Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point centroid = cluster.getCenter();
            if (n_points < 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }


}
