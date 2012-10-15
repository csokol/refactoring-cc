package org.metricminer.refactoringcc;

import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;
import org.metricminer.refactoringcc.factory.SourceCodeDataSplitter;
import org.metricminer.refactoringcc.finder.DecreasedCCFilter;
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
        List<List<SourceCodeData>> sourcesFromProjects = new SourceCodeDataSplitter()
                .splitProjects(allSources);
        DecreasedCCFilter decreasedCCFilter = new DecreasedCCFilter();
        RefactoringFilter refactoringFilter = new RefactoringFilter();

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
                    .filter(decreasedCC);
            logger.debug("");
            logger.debug("project name: " + projectName);
            logger.debug("    total decreased cc: " + decreasedCC.size());
            logger.debug("documented refactoring: "
                    + documentedRefactorings.size());
            logger.debug("       not refactoring: "
                    + (decreasedCC.size() - documentedRefactorings.size()));
            totalDoc += documentedRefactorings.size();
            totalUndoc += decreasedCC.size() - documentedRefactorings.size();
        }
        logger.debug("\n\n\n totals:");
        logger.debug("         total commits: " + totalCommits);
        logger.debug("    total decreased cc: " + (totalDoc + totalUndoc));
        logger.debug("documented refactoring: " + totalDoc);
        logger.debug("       not refactoring: " + totalUndoc);

    }

}
