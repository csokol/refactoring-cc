package org.metricminer.refactoringcc.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCode;

public class Main {

    public static void main(String[] args) throws IOException {

        InputStream is = new FileInputStream("src/main/resources/antcc.csv");
        SourceCodeDataFactory factory = new SourceCodeDataFactory(is);
        List<SourceCode> sources = factory.build();
        ProjectHistory history = new ProjectHistoryFactory().build(sources);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<Calendar, Integer> ccByDate = computeCCByDate(history);
        Set<Entry<Calendar, Integer>> entrySet = ccByDate.entrySet();
        for (Entry<Calendar, Integer> entry : entrySet) {
            dataset.addValue(entry.getValue(), "CC", entry.getKey());
        }
        JFreeChart chart = ChartFactory.createLineChart("chart", "Versão", "Complexidade ciclomática total",
                dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartUtilities.saveChartAsPNG(new File("grafico.png"), chart, 800, 800);
        
    }

    private static Map<Calendar, Integer> computeCCByDate(ProjectHistory history) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm");
        Map<String, Integer> ccByClass = new HashMap<String, Integer>();
        Map<Calendar, Integer> ccByDate = new TreeMap<Calendar, Integer>();
        List<Calendar> dates = history.getVersionDates();
        Collections.sort(dates);
        Integer currentCC = 0;

        for (Calendar date : dates) {
            Integer oldCC = currentCC;
            List<SourceCode> sourcesFrom = history.getSourcesFrom(date);
            int deleted = 0;
            for (SourceCode sc : sourcesFrom) {
                if (ccByClass.containsKey(sc.getClassName())) {
                    Integer oldThisCC = ccByClass.get(sc.getClassName());
                    currentCC -= oldThisCC;
                }
                if (!sc.getKind().equals("DELETED")) {
                    currentCC += sc.getCC();
                    ccByClass.put(sc.getClassName(), sc.getCC());
                } else {
                    deleted++;
                }
            }
            ccByDate.put(date, currentCC);
            
            System.out.println("(" + deleted + " deleted) "
                    + sourcesFrom.get(0).getMessage());
            System.out.println(format.format(date.getTime()) + " - " + oldCC
                    + " -> " + currentCC + "\n");
        }
        return ccByDate;
    }

}
