package org.metricminer.refactoringcc.finder;

import org.metricminer.refactoringcc.model.Commit;

public class IncreasedCCFilter extends DecreasedCCFilter {
    
    @Override
    protected boolean shouldFIlter(Commit commit) {
        return commit.increasedCC();
    }

}
