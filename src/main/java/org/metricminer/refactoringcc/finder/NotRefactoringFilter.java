package org.metricminer.refactoringcc.finder;

import org.metricminer.refactoringcc.model.Commit;

public class NotRefactoringFilter extends RefactoringFilter {

    @Override
    protected boolean isRefactoring(Commit commit) {
        return !(new IsRefactoring().isRefactoring(commit.getMessage()));
    }
}
