package org.metricminer.refactoringcc.finder;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.refactoringcc.model.Commit;

public class RefactoringFinder {

    public List<Commit> find(List<Commit> commits) {
        ArrayList<Commit> refactoringCommits = new ArrayList<Commit>();
        for (Commit commit : commits) {
            if (isRefactoring(commit)) {
                refactoringCommits.add(commit);
            }
        }
        return refactoringCommits;
    }

    private boolean isRefactoring(Commit commit) {
        return new IsRefactoring().isRefactoring(commit.getMessage());
    }

}
