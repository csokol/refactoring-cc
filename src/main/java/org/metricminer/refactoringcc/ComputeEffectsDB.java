package org.metricminer.refactoringcc;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.factory.ConnectionFactory;
import org.metricminer.refactoringcc.factory.EntryDao;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataDBFactory;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class ComputeEffectsDB {
    private static Logger logger = Logger.getLogger(ComputeEffectsDB.class);
    private static PrintWriter printWriter;

    public static void main(String[] args) throws FileNotFoundException {
        Connection connection = new ConnectionFactory().openConnection("jdbc:mysql://localhost/refactoring-cc");
        EntryDao entryDao = new EntryDao(connection);
        SourceCodeDataDBFactory factory = new SourceCodeDataDBFactory(entryDao);
        
        printWriter = new PrintWriter("resultados.csv");
        String line = String.format("project;" + 
                "documented refactorings decreasing cc;" +
        		"documented refactorings increasing cc;" +
        		"undocumented refactorings decreasing cc;" +
        		"undocumented increasing decreasing cc;");
        printWriter.println(line);
        
        List<String> projects = entryDao.projects();
        int i = 1;
        for (String project : projects) {
            List<SourceCodeData> sources = factory.build(project);
            logger.debug("project " + i + " out of " + projects.size());
            computeEffects(sources);
            i++;
        }
        printWriter.close();
        
    }

    private static void computeEffects(List<SourceCodeData> sources) {
        RefactoringEffects refactoringEffects = new RefactoringEffects(
                sources);
        String projectName = sources.get(0).getProjectName();
        ProjectHistory history = new ProjectHistoryFactory().build(sources);
        history.setProjectName(projectName);
        List<Integer> results = refactoringEffects.calculateEffectsFor(history);
        
        int notRefactoringDecreasedCCTotal = results.get(0);
        int notRefactoringIncreasedCCTotal = results.get(1);
        int refactoringDecreasedCCTotal = results.get(2);
        int refactoringIncreasedCCTotal = results.get(3);
        
        
        String line = String.format("%s; %d; %d; %d; %d", projectName, refactoringDecreasedCCTotal,
                refactoringIncreasedCCTotal, notRefactoringDecreasedCCTotal,
                notRefactoringIncreasedCCTotal);
        printWriter.println(line);
    }
}
