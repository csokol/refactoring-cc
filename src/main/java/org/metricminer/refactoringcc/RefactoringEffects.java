package org.metricminer.refactoringcc;

import static java.util.Collections.unmodifiableList;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
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

    private DecreasedCCFilter decreasedCCFilter;

    private DecreasedCCFilter increaseCCFilter;

    private RefactoringFilter refactoringFilter;

    private NotRefactoringFilter notRefactoringFilter;

    public RefactoringEffects(List<SourceCodeData> allSources) {
        this.allSources = allSources;
        decreasedCCFilter = new DecreasedCCFilter();
        increaseCCFilter = new IncreasedCCFilter();
        refactoringFilter = new RefactoringFilter();
        notRefactoringFilter = new NotRefactoringFilter();
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

        long totalDoc = 0;
        long totalUndoc = 0;
        long totalCommits = 0;

        for (List<SourceCodeData> sources : sourcesFromProjects) {
            String projectName = sources.get(0).getProjectName();
            ProjectHistory history = new ProjectHistoryFactory().build(sources);
            totalCommits += history.commits().size();
            List<Integer> results = calculateEffectsFor(history);
            
            int notRefactoringDecreasedCCTotal = results.get(0);
            int notRefactoringIncreasedCCTotal = results.get(1);
            int totalNotRefactoring = notRefactoringDecreasedCCTotal + notRefactoringIncreasedCCTotal;  
            int refactoringDecreasedCCTotal = results.get(2);
            int refactoringIncreasedCCTotal = results.get(3);
            int totalRefactoring = refactoringDecreasedCCTotal + refactoringIncreasedCCTotal; 
            
//            printWriter.println(String.format("%d; %d; %d; %d", refactoringDecreasedCCTotal, refactoringIncreasedCCTotal, notRefactoringDecreasedCCTotal, notRefactoringIncreasedCCTotal));
            
            totalDoc += totalRefactoring;
            totalUndoc += totalNotRefactoring;
        }
//        logger.debug("\n\n\n totals:");
//        logger.debug("         total commits: " + totalCommits);
//        logger.debug("    total decreased cc: " + (totalDoc + totalUndoc));
//        logger.debug("documented refactoring: " + totalDoc);
//        logger.debug("       not refactoring: " + totalUndoc);

    }

    public List<Integer> calculateEffectsFor(ProjectHistory history) {
        List<Commit> commits = history.commits();

        List<Commit> documentedRefactorings = unmodifiableList(refactoringFilter
                .filter(commits));
        List<Commit> notRefactoring = unmodifiableList(notRefactoringFilter.filter(commits));
        
        int notRefactoringDecreasedCCTotal = decreasedCCFilter.filter(notRefactoring).size();
        int notRefactoringIncreasedCCTotal = increaseCCFilter.filter(notRefactoring).size();
        int notRefactoringEqualizedCCTotal = notRefactoring.size() - notRefactoringIncreasedCCTotal - notRefactoringDecreasedCCTotal;
        
        int refactoringDecreasedCCTotal = decreasedCCFilter.filter(documentedRefactorings).size();
        int refactoringIncreasedCCTotal = increaseCCFilter.filter(documentedRefactorings).size();
        int refactoringEqualizedCCTotal = documentedRefactorings.size() - refactoringDecreasedCCTotal - refactoringIncreasedCCTotal;
        
        int totalNotRefactoring = notRefactoring.size();
        int totalRefactoring = documentedRefactorings.size(); 
        
        logger.debug("");
        logger.debug("project name: " + history.getProjectName());
        logger.debug("===   Refactorings   ===");
        logger.debug("                  total: "
                + totalRefactoring);
        logger.debug("            decreased cc: " + refactoringDecreasedCCTotal);
        logger.debug("            increased cc: " + refactoringIncreasedCCTotal);
        logger.debug("            equalized cc: " + refactoringEqualizedCCTotal);
        
        logger.debug("=== Not Refactoring ===");
        logger.debug("                 total: "
                + totalNotRefactoring);
        logger.debug("          decreased cc: "
                + notRefactoringDecreasedCCTotal);
        logger.debug("          increased cc: " + notRefactoringIncreasedCCTotal);
        logger.debug("          equalized cc: " + notRefactoringEqualizedCCTotal);
        

        return Arrays.asList(notRefactoringDecreasedCCTotal,
                notRefactoringIncreasedCCTotal, notRefactoringEqualizedCCTotal, refactoringDecreasedCCTotal,
                refactoringIncreasedCCTotal, refactoringEqualizedCCTotal);
    }

}
