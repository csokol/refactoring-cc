package org.metricminer.refactoringcc;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.metricminer.refactoringcc.charts.CCByDateDatasetGenerator;
import org.metricminer.refactoringcc.charts.LineChart;
import org.metricminer.refactoringcc.factory.ConnectionFactory;
import org.metricminer.refactoringcc.factory.EntryDao;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataDBFactory;
import org.metricminer.refactoringcc.model.Commit;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class AnalyzeSingleProject {

    private static Logger logger = Logger.getLogger(AnalyzeSingleProject.class);

    public static void main(String[] args) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Connection connection = new ConnectionFactory()
                .openConnection("jdbc:mysql://localhost/refactoring-cc");
        EntryDao entryDao = new EntryDao(connection);
        SourceCodeDataDBFactory factory = new SourceCodeDataDBFactory(entryDao);

        List<SourceCodeData> sources = factory.build("Apache Ant");
        ProjectHistory history = new ProjectHistoryFactory().build(sources);
        List<Commit> commits = history.commits();
        for (Commit commit : commits) {
            if (commit.getPriorCommit() != null) {
                int delta = commit.getTotalCC() - commit.getPriorCommit().getTotalCC();
                if (Math.abs(delta) > 1000) {
                    logger.debug(commit.getMessage());
                    logger.debug(format.format(commit.getDate().getTime()));
                    logger.debug("delta: " + delta);
                }
            }
        }
        
        Map<Comparable, Number> ccByDate = new CCByDateDatasetGenerator()
        .computeDatasetFor(history);
        LineChart simpleChart = new LineChart(ccByDate, "Apache Ant");
        ChartPanel chartPanel = new ChartPanel(simpleChart.getChart());
        JFrame win = new JFrame("");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add(chartPanel);
        
        win.pack();
        win.setSize(1024, 768);
        win.setVisible(true);

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
