package org.metricminer.refactoringcc;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataSplitter;
import org.metricminer.refactoringcc.finder.DecreasedCCFilter;
import org.metricminer.refactoringcc.finder.IncreasedCCFilter;
import org.metricminer.refactoringcc.finder.NotRefactoringFilter;
import org.metricminer.refactoringcc.finder.RefactoringFilter;
import org.metricminer.refactoringcc.model.Commit;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class RefactoringEffects {
    private static Logger logger = Logger.getLogger(RefactoringEffects.class);

    private final List<SourceCodeData> allSources;

    public RefactoringEffects(List<SourceCodeData> allSources) {
        this.allSources = allSources;
    }

    public void compute() {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter("resultado.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        printWriter.println("\"refactoringDecreasedCCTotal\";\"refactoringIncreasedCCTotal\";\"notRefactoringDecreasedCCTotal\";\"notRefactoringIncreasedCCTotal\"");
        
        List<List<SourceCodeData>> sourcesFromProjects = new SourceCodeDataSplitter()
                .splitProjects(allSources);
        DecreasedCCFilter decreasedCCFilter = new DecreasedCCFilter();
        DecreasedCCFilter increaseCCFilter = new IncreasedCCFilter();
        RefactoringFilter refactoringFilter = new RefactoringFilter();
        NotRefactoringFilter notRefactoringFilter = new NotRefactoringFilter();

        long totalDoc = 0;
        long totalUndoc = 0;
        long totalCommits = 0;

        for (List<SourceCodeData> sources : sourcesFromProjects) {
            String projectName = sources.get(0).getProjectName();
            ProjectHistory history = new ProjectHistoryFactory().build(sources);

            List<Commit> commits = history.commits();
            totalCommits += commits.size();

            List<Commit> decreasedCC = decreasedCCFilter.filter(commits);
            List<Commit> documentedRefactorings = refactoringFilter
                    .filter(commits);
            List<Commit> notRefactoring = notRefactoringFilter.filter(commits);
            
            int notRefactoringDecreasedCCTotal = decreasedCCFilter.filter(notRefactoring).size();
            int notRefactoringIncreasedCCTotal = increaseCCFilter.filter(notRefactoring).size();
            int refactoringDecreasedCCTotal = decreasedCCFilter.filter(documentedRefactorings).size();
            int refactoringIncreasedCCTotal = increaseCCFilter.filter(documentedRefactorings).size();
            
            logger.debug("");
            logger.debug("project name: " + projectName);
            logger.debug("===   Refactorings   ===");
            logger.debug("                  total: "
                    + documentedRefactorings.size());
            logger.debug("            decreased cc: " + refactoringDecreasedCCTotal);
            logger.debug("            increased cc: "
                    + refactoringIncreasedCCTotal);
            
            logger.debug("=== Not Refactoring ===");
            logger.debug("                 total: "
                    + notRefactoring.size());
            logger.debug("          decreased cc: "
                    + notRefactoringDecreasedCCTotal);
            logger.debug("          increased cc: "
                    + notRefactoringIncreasedCCTotal);
            

            
//            printWriter.println(String.format("%d; %d; %d; %d", refactoringDecreasedCCTotal, refactoringIncreasedCCTotal, notRefactoringDecreasedCCTotal, notRefactoringIncreasedCCTotal));
            
            totalDoc += documentedRefactorings.size();
            totalUndoc += decreasedCC.size() - documentedRefactorings.size();
        }
//        logger.debug("\n\n\n totals:");
//        logger.debug("         total commits: " + totalCommits);
//        logger.debug("    total decreased cc: " + (totalDoc + totalUndoc));
//        logger.debug("documented refactoring: " + totalDoc);
//        logger.debug("       not refactoring: " + totalUndoc);

    }

}
