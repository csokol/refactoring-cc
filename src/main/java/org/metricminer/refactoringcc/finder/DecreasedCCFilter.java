package org.metricminer.refactoringcc.finder;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.refactoringcc.model.Commit;

public class DecreasedCCFilter implements Filter {

    @Override
    public List<Commit> filter(List<Commit> commits) {
        ArrayList<Commit> filteredCommits = new ArrayList<Commit>();
        for (Commit commit : commits) {
            if (commit.decreasedCC()) {
                filteredCommits.add(commit);
            }
        }
        return filteredCommits;
    }

}
