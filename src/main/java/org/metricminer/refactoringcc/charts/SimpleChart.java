package org.metricminer.refactoringcc.charts;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class SimpleChart {

    private JFreeChart chart;

    @SuppressWarnings("rawtypes")
    public SimpleChart(Map<Comparable, Number> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Set<Entry<Comparable, Number>> entrySet = data.entrySet();
        for (Entry<Comparable, Number> entry : entrySet) {
            dataset.addValue(entry.getValue(), "CC", entry.getKey());
        }
        chart = ChartFactory.createLineChart("chart", "Versão", "Complexidade ciclomática total",
                dataset, PlotOrientation.VERTICAL, false, false, false);
    }
    
    public void saveAsPng(String path) throws IOException {
        ChartUtilities.saveChartAsPNG(new File(path), chart, 800, 800);
    }

}
