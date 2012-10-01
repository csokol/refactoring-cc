package org.metricminer.refactoringcc.finder;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.refactoringcc.model.Commit;

public class RefactoringFinder {

    public List<Commit> find(List<Commit> commits) {
        ArrayList<Commit> refactoringCommits = new ArrayList<Commit>();
        for (Commit commit : commits) {
            if (commit.getMessage().contains("Refactoring")
                    || commit.getMessage().contains("refactoring")) {
                refactoringCommits.add(commit);
            }
        }
        return refactoringCommits;
    }

}
