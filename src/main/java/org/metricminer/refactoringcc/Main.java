package org.metricminer.refactoringcc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.metricminer.refactoringcc.charts.CCByDateDatasetGenerator;
import org.metricminer.refactoringcc.charts.SimpleChart;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataFactory;
import org.metricminer.refactoringcc.finder.RefactoringFinder;
import org.metricminer.refactoringcc.model.Commit;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class Main {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws IOException {

        InputStream is = new FileInputStream("src/main/resources/history.csv");
        SourceCodeDataFactory factory = new SourceCodeDataFactory(is);
        List<SourceCodeData> sources = factory.build();
        ProjectHistory history = new ProjectHistoryFactory().build(sources);

        Map<Comparable, Number> ccByDate = new CCByDateDatasetGenerator()
                .computeDatasetFor(history);
        SimpleChart simpleChart = new SimpleChart(ccByDate);
        simpleChart.saveAsPng("grafico.png");
        
        List<Commit> commits = new RefactoringFinder().find(history.commits());
        for (Commit commit : commits) {
            System.out.println(commit.getMessage());
        }

    }

}
