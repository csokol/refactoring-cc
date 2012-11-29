package org.metricminer.refactoringcc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.charts.CCByDateDatasetGenerator;
import org.metricminer.refactoringcc.charts.LineChart;
import org.metricminer.refactoringcc.factory.ConnectionFactory;
import org.metricminer.refactoringcc.factory.EntryDao;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataDBFactory;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;
import org.metricminer.refactoringcc.util.Slugged;

public class BuildCharts {

    private static Logger logger = Logger.getLogger(BuildCharts.class);

    public static void main(String[] args) throws IOException {
        Connection connection = new ConnectionFactory()
                .openConnection("jdbc:mysql://localhost/refactoring-cc");
        EntryDao entryDao = new EntryDao(connection);
        SourceCodeDataDBFactory factory = new SourceCodeDataDBFactory(entryDao);
        new File("csv").mkdir();

        List<String> projects = entryDao.projects();
        int i = 1;
        for (String project : projects) {
            List<SourceCodeData> sources = factory.build(project);
            String projectName = sources.get(0).getProjectName();
            logger.debug("project " + i + " out of " + projects.size());
            ProjectHistory history = new ProjectHistoryFactory().build(sources);
            Map<Comparable, Number> ccByDate = new CCByDateDatasetGenerator().computeDatasetFor(history);
            buildChart(projectName, ccByDate);
            saveCsv(projectName, ccByDate);
            i++;
        }

    }

    private static void saveCsv(String projectName, Map<Comparable, Number> ccByDate) throws FileNotFoundException {
        File file = new File("csv/" + new Slugged(projectName) + ".csv");
        PrintWriter printWriter = new PrintWriter(file);
        int version = 0;
        for (Entry<Comparable, Number> entry : ccByDate.entrySet()) {
            printWriter.println(version + ";" + entry.getValue());
            version++;
        }
        printWriter.close();
    }

    private static void buildChart(String projectName, Map<Comparable, Number> ccByDate)
            throws IOException {
        LineChart simpleChart = new LineChart(ccByDate, projectName);
        simpleChart.saveAsPng("graficos/grafico-" + new Slugged(projectName)
                + ".png");
    }

}

/*
 * SELECT cc.cc, a.name, c.date, m.kind, cm.message, p.name AS project_name from
 * CCResult cc JOIN SourceCode sc ON sc.id = cc.sourceCode_id JOIN Modification
 * m ON sc.modification_id = m.id JOIN Commit c ON c.id = m.commit_id JOIN
 * Artifact a ON m.artifact_id = a.id JOIN CommitMessage cm ON cm.id =
 * c.message_id JOIN Project p ON p.id = c.project_id WHERE p.id in (SELECT id
 * FROM Project WHERE id < 50);
 */
