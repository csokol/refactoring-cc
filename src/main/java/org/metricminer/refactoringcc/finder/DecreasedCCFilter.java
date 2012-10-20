package org.metricminer.refactoringcc.finder;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.refactoringcc.model.Commit;

public class DecreasedCCFilter implements Filter {

    @Override
    public List<Commit> filter(List<Commit> commits) {
        ArrayList<Commit> filteredCommits = new ArrayList<Commit>();
        for (Commit commit : commits) {
            if (shouldFIlter(commit)) {
                filteredCommits.add(commit);
            }
        }
        return filteredCommits;
    }

    protected boolean shouldFIlter(Commit commit) {
        return commit.decreasedCC();
    }

}
