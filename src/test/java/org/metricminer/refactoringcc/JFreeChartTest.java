package org.metricminer.refactoringcc;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

public class JFreeChartTest {

    @Test
    public void testName() throws Exception {
        DefaultCategoryDataset defaultXYDataset = new DefaultCategoryDataset();
        double[][] data = {{1,2,3,4,5,6}, {2,4,6,8,10,12}};
        for (int i = 0; i < 10; i++) {
            defaultXYDataset.addValue(2*i, "valor",Integer.valueOf(i));
        }
        JFreeChart chart = ChartFactory.createLineChart("chart", "x", "y", defaultXYDataset, PlotOrientation.VERTICAL, false, false, false);
        ChartUtilities.saveChartAsPNG(new File("grafico.png"), chart, 800, 800);
    }
}
