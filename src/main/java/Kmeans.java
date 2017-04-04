import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Kmeans {
    private List<Cluster> clusters = new ArrayList<Cluster>();
    private List<Point> points = new ArrayList<Point>();

    //amount of clusters
    private int n_clusters;
    private int iterations = 0;
    private double threshold;

    //make 5 clusters


    //add clusters to list
    Kmeans(List<Point> points, int n_clusters, double threshold) {
        this.points = points;
        this.threshold = threshold;
        this.n_clusters = n_clusters;
        for (int i = 1; i <= n_clusters; i++) {
            clusters.add(new Cluster(i));
        }
        //set random clusters for all points
        for (Point point : points) {
            point.setCluster(ThreadLocalRandom.current().nextInt(1, clusters.size() + 1));
            // add points to clusters
            clusters.get(point.getCluster() - 1).addPoint(point);
        }

        for (Cluster cluster : clusters) {
            cluster.setRandomCenter();
        }

    }

    //calculation and console output
    void calc() {
        boolean finish = false;

        while (!finish) {
            iterations++;
            List lastCenters = getCenters(n_clusters);
            calculateCenters();
            //clear and assign
            clearClusters();
            assignCluster(n_clusters);
            List currentCenters = getCenters(n_clusters);
            //total distance between new and old Centers must be under threshold
            double distance = 0;
            for (int i = 0; i < lastCenters.size(); i++) {
                distance += Point.distance((Point) lastCenters.get(i), (Point) currentCenters.get(i));
            }
            if (distance < threshold) {
                // stop when distance between last and new centers is small
                finish = true;
            }
        }
        System.out.println(iterations + " Iterations");
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

    //returns list with centers
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
        double max = 20, min, distance;
        int cluster = 0;

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
            if (n_points > 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                center.setX(newX);
                center.setY(newY);
            }
        }
    }

    //previously used
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
        chart.getStyler().setMarkerSize(7);
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




