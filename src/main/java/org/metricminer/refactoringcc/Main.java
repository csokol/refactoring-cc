package org.metricminer.refactoringcc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.charts.CCByDateDatasetGenerator;
import org.metricminer.refactoringcc.charts.LineChart;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataCSVFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataSplitter;
import org.metricminer.refactoringcc.finder.RefactoringFilter;
import org.metricminer.refactoringcc.model.Commit;
import org.metricminer.refactoringcc.model.Commit.Effect;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class Main {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws IOException {
        
        Logger logger = Logger.getLogger(Main.class);
        InputStream is;
        is = new FileInputStream(
                "/home/csokol/ime/tcc/new-workspace/cc-50-projects.csv");
        SourceCodeDataCSVFactory factory = new SourceCodeDataCSVFactory(is);
        List<SourceCodeData> allSources = factory.build();
        List<List<SourceCodeData>> sourcesFromProjects = new SourceCodeDataSplitter()
                .splitProjects(allSources);

        HashMap<Effect, Integer> effectsCount = new HashMap<Commit.Effect, Integer>();
        effectsCount.put(Effect.DECREASE, 0);
        effectsCount.put(Effect.INCREASE, 0);
        effectsCount.put(Effect.EQUAL, 0);
        
        for (List<SourceCodeData> sources : sourcesFromProjects) {
            String projectName = sources.get(0).getProjectName();
            ProjectHistory history = new ProjectHistoryFactory().build(sources);

            Map<Comparable, Number> ccByDate = new CCByDateDatasetGenerator()
                    .computeDatasetFor(history);
            LineChart simpleChart = new LineChart(ccByDate);
            //simpleChart.saveAsPng("grafico-" + new Slugged(projectName) + ".png");

            List<Commit> commits = new RefactoringFilter().filter(history
                    .commits());
            for (Commit commit : commits) {
                logger.debug(commit.getTotalCC());
                Effect effect = commit.effect();
                Integer count = effectsCount.get(effect);
                count++;
                effectsCount.put(effect, count);
                logger.debug("cc effect: " + effect);
            }
            
        }
        logger.info("refactoring effects:\n ");
        List<Effect> effects = Arrays.asList(Effect.values());
        for (Effect effect : effects) {
            logger.info(effect + ": " + effectsCount.get(effect));
        }
    }

}


/*
SELECT cc.cc, a.name, c.date, m.kind, cm.message, p.name AS project_name from CCResult cc
JOIN SourceCode sc ON sc.id = cc.sourceCode_id 
JOIN Modification m ON sc.modification_id = m.id  
JOIN Commit c ON c.id = m.commit_id 
JOIN Artifact a ON m.artifact_id = a.id  
JOIN CommitMessage cm ON cm.id = c.message_id 
JOIN Project p ON p.id = c.project_id 
WHERE p.id in (SELECT id FROM Project WHERE id < 50);
 */
