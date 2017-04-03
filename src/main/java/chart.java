import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class chart {
    private static final int n_clusters = 5; // set to 3, 4 or 5
    private static final double threshold = 0.01; // threshold for kmeans

    public static void main(String[] args) {
        List<Point> points = new ArrayList<Point>();
        List<Double> xData = new ArrayList<Double>();
        List<Double> yData = new ArrayList<Double>();

        //read file and add data to lists
        try {
            Scanner scanner = new Scanner(new File("data.txt"));
            double x, y;
            while (scanner.hasNext()){
                x = Double.parseDouble(scanner.next());
                y = Double.parseDouble(scanner.next());
                xData.add(x);
                yData.add(y);
                points.add(new Point(x,y));            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Kmeans kmeans = null;
        //make kmeans instance
        try {
            kmeans = new Kmeans(points, n_clusters, threshold);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //calc
        kmeans.calc();

        //draw charts
        new SwingWrapper<XYChart>(getChart(xData,yData)).displayChart();
        new SwingWrapper<XYChart>(kmeans.drawXY()).displayChart();
    }

    public static XYChart getChart(List<Double> xData, List<Double> yData){
        XYChart chart = new XYChartBuilder().width(800).height(600).build();
        chart.addSeries(" ", xData, yData);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(8);
        return chart;
    }
}
