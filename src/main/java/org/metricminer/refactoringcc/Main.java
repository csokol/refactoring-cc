package org.metricminer.refactoringcc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.charts.CCByDateDatasetGenerator;
import org.metricminer.refactoringcc.charts.SimpleChart;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataSplitter;
import org.metricminer.refactoringcc.finder.RefactoringFinder;
import org.metricminer.refactoringcc.model.Commit;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;

import br.com.caelum.tubaina.util.Slugged;

public class Main {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws IOException {
        
        Logger logger = Logger.getLogger(Main.class);

        InputStream is = new FileInputStream(
                "/home/csokol/ime/tcc/new-workspace/cc-50-projects.csv");
        SourceCodeDataFactory factory = new SourceCodeDataFactory(is);
        List<SourceCodeData> allSources = factory.build();
        List<List<SourceCodeData>> sourcesFromProjects = new SourceCodeDataSplitter()
                .split(allSources);

        for (List<SourceCodeData> sources : sourcesFromProjects) {
            String projectName = sources.get(0).getProjectName();
            ProjectHistory history = new ProjectHistoryFactory().build(sources);

            Map<Comparable, Number> ccByDate = new CCByDateDatasetGenerator()
                    .computeDatasetFor(history);
            SimpleChart simpleChart = new SimpleChart(ccByDate);
            simpleChart.saveAsPng("grafico-" + new Slugged(projectName) + ".png");

            List<Commit> commits = new RefactoringFinder().find(history
                    .commits());
            for (Commit commit : commits) {
                logger.debug(commit.getMessage());
            }

        }
    }

}
