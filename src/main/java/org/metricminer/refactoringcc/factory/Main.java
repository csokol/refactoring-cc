package org.metricminer.refactoringcc.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.metricminer.refactoringcc.charts.CCByDateDatasetGenerator;
import org.metricminer.refactoringcc.charts.SimpleChart;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCode;

public class Main {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws IOException {

        InputStream is = new FileInputStream("src/main/resources/antcc.csv");
        SourceCodeDataFactory factory = new SourceCodeDataFactory(is);
        List<SourceCode> sources = factory.build();
        ProjectHistory history = new ProjectHistoryFactory().build(sources);

        Map<Comparable, Number> ccByDate = new CCByDateDatasetGenerator()
                .computeDatasetFor(history);
        SimpleChart simpleChart = new SimpleChart(ccByDate);
        simpleChart.saveAsPng("grafico.png");

    }

}
