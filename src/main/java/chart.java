import org.apache.commons.io.IOUtils;
import org.knowm.xchart.BubbleChart;
import org.knowm.xchart.BubbleChartBuilder;
import org.knowm.xchart.SwingWrapper;


import java.io.FileInputStream;
import java.io.IOException;

import java.util.List;
import java.util.regex.Pattern;

import java.util.concurrent.ThreadLocalRandom;

public class chart {
    public static void main(String[] args) {

        BubbleChart chart = getMyChart();
        new SwingWrapper<BubbleChart>(chart).displayChart();
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

        kmeans4(xData, yData, bubbleData);

        BubbleChart chart = new BubbleChartBuilder().width(800).height(600).title("Viele Punkte").xAxisTitle("X").yAxisTitle("Y").build();

        chart.addSeries("lala", xData, yData, bubbleData);
        return chart;
    }

    private static void kmeans4(double[] xData, double[] yData, double[] bubbleData) {
        int r1, r2, r3, r4; //
        Cluster cluster1, cluster2, cluster3, cluster4;

        Point[] points = new Point[xData.length];

        for (int i = 0; i< xData.length; i++) {
            points[i].setX(xData[i]);
            points[i].setY(yData[i]);
        }

        do {
            r1 = ThreadLocalRandom.current().nextInt(0, xData.length + 1);
            r2 = ThreadLocalRandom.current().nextInt(0, xData.length + 1);
            r3 = ThreadLocalRandom.current().nextInt(0, xData.length + 1);
            r4 = ThreadLocalRandom.current().nextInt(0, xData.length + 1);
        } while (r1 != r2 && r2 != r3 && r3 != r4);
    }


}
