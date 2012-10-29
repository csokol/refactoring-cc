package org.metricminer.refactoringcc.charts;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart {

    private JFreeChart chart;
    private final String name;

    @SuppressWarnings("rawtypes")
    public LineChart(Map<Comparable, Number> data, String name) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        this.name = name;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Set<Entry<Comparable, Number>> entrySet = data.entrySet();
        int i = 1;
        for (Entry<Comparable, Number> entry : entrySet) {
            Calendar calendar = (Calendar) entry.getKey();
            dataset.addValue(entry.getValue(), "CC", format.format(calendar.getTime()));
        }
        chart = ChartFactory.createLineChart(name, "",
                "Complexidade ciclomática total", dataset,
                PlotOrientation.VERTICAL, false, true, false);
    }

    public void saveAsPng(String path) throws IOException {
        ChartUtilities.saveChartAsPNG(new File(path), chart, 800, 800);
    }

    public JFreeChart getChart() {
        return chart;
    }
}
