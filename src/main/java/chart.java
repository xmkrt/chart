import org.knowm.xchart.BubbleChart;
import org.knowm.xchart.BubbleChartBuilder;
import org.knowm.xchart.SwingWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class chart {
    static final int n_clusters = 5;

    public static void main(String[] args) {
        List<Point> points = new ArrayList<Point>();
        List<String> lines = new ArrayList<String>();

        //reading lines with scanner
        try {
            Scanner scanner = new Scanner(new File("data.txt"));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        List<Double> xData = new ArrayList<Double>();
        List<Double> yData = new ArrayList<Double>();
        List<Integer> bubbleData = new ArrayList<Integer>();

        String[] segs;
        for (String s : lines) {
            segs = s.split(Pattern.quote(" "));
            double x = Double.parseDouble(segs[0]);
            double y = Double.parseDouble(segs[1]);
            xData.add(x);
            yData.add(y);
            bubbleData.add(5);
            points.add(new Point(x, y));
        }

        BubbleChart chart = new BubbleChartBuilder().width(800).height(600).title("Viele Punkte").xAxisTitle("X").yAxisTitle("Y").build();

        chart.addSeries(" ", xData, yData, bubbleData);

        Kmeans kmeans = null;

        try {
            kmeans = new Kmeans(points, n_clusters);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        kmeans.init();
        kmeans.calc();


        BubbleChart finishedChart = kmeans.draw();
        new SwingWrapper<BubbleChart>(chart).displayChart();
        new SwingWrapper<BubbleChart>(finishedChart).displayChart();
    }
}
