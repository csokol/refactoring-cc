package org.metricminer.refactoringcc.finder;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.refactoringcc.model.Commit;

public class RefactoringFilter implements Filter {

    @Override
    public List<Commit> filter(List<Commit> commits) {
        ArrayList<Commit> refactoringCommits = new ArrayList<Commit>();
        for (Commit commit : commits) {
            if (isRefactoring(commit)) {
                refactoringCommits.add(commit);
            }
        }
        return refactoringCommits;
    }

    protected boolean isRefactoring(Commit commit) {
        return new IsRefactoring().isRefactoring(commit.getMessage());
    }

}
